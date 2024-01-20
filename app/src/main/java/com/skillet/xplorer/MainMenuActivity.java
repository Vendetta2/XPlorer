package com.skillet.xplorer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainMenuActivity extends AppCompatActivity {



    public static final int REQUEST_CODE_FOR_PERM = 15487;
    private static String treeUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        Button extStorageButt = findViewById(R.id.ext_storage_butt);
        Button entStorageButt = findViewById(R.id.ent_storage_butt);

        extStorageButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkPermissions()){
                    Intent intent = new Intent(MainMenuActivity.this, InternalStorageActivity.class);
                    String path = Environment.getExternalStorageDirectory().getAbsolutePath();
                    intent.putExtra("path", path);
                    startActivity(intent);
                }else {
                    requestPermission(MainMenuActivity.this);
                }
            }
        });

        entStorageButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkPermissions()){
                    if(treeUri != null){
                        Intent intent = new Intent(MainMenuActivity.this, ExternalStorageActivity.class);
                        intent.putExtra("treeUri", treeUri);
                        startActivity(intent);
                    } else getExtDir();
                }else requestPermission(MainMenuActivity.this);


            }
        });

    }



    private void getExtDir(){
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE);
        MainMenuActivity.this.startActivityForResult(intent, REQUEST_CODE_FOR_PERM);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent resultData){
        super.onActivityResult(requestCode, resultCode, resultData);
        if(requestCode == REQUEST_CODE_FOR_PERM && resultCode == Activity.RESULT_OK){
            if(resultData != null){
                treeUri = resultData.getData().toString();
                Toast.makeText(MainMenuActivity.this, treeUri, Toast.LENGTH_SHORT).show();
            } else Toast.makeText(MainMenuActivity.this, "treeUri is null", Toast.LENGTH_SHORT).show();

        } else Toast.makeText(MainMenuActivity.this, "idk why don't working", Toast.LENGTH_SHORT).show();
    }


    private Boolean checkPermissions(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
            return Environment.isExternalStorageManager();
        } else {
            return ContextCompat.checkSelfPermission(MainMenuActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
        }
    }


    private void requestPermission(Activity activity){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
            try {
                Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                intent.addCategory("android.intent.category.DEFAULT");
                intent.setData(Uri.parse(String.format("package:%s", activity.getPackageName())));
                activity.startActivityForResult(intent, REQUEST_CODE_FOR_PERM);
            }catch (Exception e) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                activity.startActivityForResult(intent, REQUEST_CODE_FOR_PERM);
            }

        }else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainMenuActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                Toast.makeText(MainMenuActivity.this, "Isn't working", Toast.LENGTH_SHORT).show();

            } else {

                ActivityCompat.requestPermissions(MainMenuActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_FOR_PERM);
            }
        }
    }


}