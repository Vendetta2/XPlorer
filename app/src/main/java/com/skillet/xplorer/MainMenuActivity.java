package com.skillet.xplorer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        Button extStorageButt = findViewById(R.id.ext_storage_butt);

        extStorageButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkPermissions()){
                    Intent intent = new Intent(MainMenuActivity.this, ExternalStorageActivity.class);
                    String path = Environment.getExternalStorageDirectory().getPath();
                    intent.putExtra("path", path);
                    startActivity(intent);
                }else {
                    requestPermission();
                }
            }
        });


    }

    private Boolean checkPermissions(){
        int result = ContextCompat.checkSelfPermission(MainMenuActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if(result == PackageManager.PERMISSION_GRANTED){
            return true;
        } else return false;
    }


    private void requestPermission(){
        if (ActivityCompat.shouldShowRequestPermissionRationale(MainMenuActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)){
            Toast.makeText(MainMenuActivity.this, "Isn't working", Toast.LENGTH_SHORT).show();

        }else {

            ActivityCompat.requestPermissions(MainMenuActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 15487);
        }
    }


}