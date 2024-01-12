package com.skillet.xplorer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.skillet.xplorer.flash_info.FlashDriveInfo;
import com.skillet.xplorer.storage_recycler.StorageAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ExternalStorageActivity extends AppCompatActivity {


    TextView linkTV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_external_storage);

        RecyclerView recyclerView = findViewById(R.id.files_recycler);
        linkTV = findViewById(R.id.link_tv);
        linkTV.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(ExternalStorageActivity.this,v);
                popupMenu.getMenu().add("External Storage");
                ArrayList<String> cards;
                cards = FlashDriveInfo.getFlashDriveName(ExternalStorageActivity.this);

                for(int i = 0; i < cards.size(); i++){
                   popupMenu.getMenu().add(cards.get(i));
                }


                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getTitle().equals("External Storage")) {
                            Intent intent = new Intent(ExternalStorageActivity.this, ExternalStorageActivity.class);
                            String path = Environment.getExternalStorageDirectory().getAbsolutePath();
                            intent.putExtra("path", path);
                            Toast.makeText(ExternalStorageActivity.this, path, Toast.LENGTH_SHORT).show();
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            ExternalStorageActivity.this.startActivity(intent);

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
            Toast.makeText(ExternalStorageActivity.this, "Files not found", Toast.LENGTH_SHORT).show();
            return;
        }


        recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        recyclerView.setAdapter(new StorageAdapter(getApplicationContext(), filesAndFolders));


    }
}


