package com.skillet.xplorer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.skillet.xplorer.flash_info.FlashDriveInfo;
import com.skillet.xplorer.storage_recycler.InternalStorageAdapter;

import java.io.File;
import java.util.ArrayList;

public class InternalStorageActivity extends AppCompatActivity {


    TextView linkTV;
    Button createButt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_external_storage);

        RecyclerView recyclerView = findViewById(R.id.files_recycler);
        linkTV = findViewById(R.id.link_tv);
        linkTV.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(InternalStorageActivity.this,v);
                popupMenu.getMenu().add("External Storage");
                ArrayList<String> cards;
                cards = FlashDriveInfo.getFlashDriveName(InternalStorageActivity.this);

                for(int i = 0; i < cards.size(); i++){

                   popupMenu.getMenu().add(cards.get(i));

                }


                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getTitle().equals("External Storage")) {
                            Intent intent = new Intent(InternalStorageActivity.this, InternalStorageActivity.class);
                            String path = Environment.getExternalStorageDirectory().getAbsolutePath();
                            intent.putExtra("path", path);
                            Toast.makeText(InternalStorageActivity.this, path, Toast.LENGTH_SHORT).show();
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            InternalStorageActivity.this.startActivity(intent);

                        }
                        return true;
                    }
                });


                popupMenu.show();
            }
        });


        String path = getIntent().getStringExtra("path");


        assert path != null;
        File root = new File(path);
        File[] filesAndFolders = root.listFiles();

        TextView linkTV = findViewById(R.id.link_tv);
        linkTV.setText(path);

        if(filesAndFolders == null || filesAndFolders.length == 0){
            Toast.makeText(InternalStorageActivity.this, "Files not found", Toast.LENGTH_SHORT).show();
            return;
        }


        recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        recyclerView.setAdapter(new InternalStorageAdapter(getApplicationContext(), filesAndFolders, path));


        createButt = findViewById(R.id.create_butt);
        createButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dirCreate(path);
            }
        });

    }



    private void dirCreate(String path){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Введите название");

        EditText editText = new EditText(this);
        builder.setView(editText);

        builder.setPositiveButton("Ok", ((dialog, which) -> {
            String folderName = editText.getText().toString();

            File newFolder = new File(path, folderName);
            if(newFolder.mkdir()){
                Toast.makeText(this, folderName + " created", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, folderName + " is not created", Toast.LENGTH_SHORT).show();
            }
        }));

        builder.setNegativeButton("Cancel", ((dialog, which) -> {

        }));

        builder.show();

    }


}


