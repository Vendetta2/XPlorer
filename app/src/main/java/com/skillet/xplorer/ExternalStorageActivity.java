package com.skillet.xplorer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.skillet.xplorer.storage_recycler.StorageAdapter;

import java.io.File;

public class ExternalStorageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_external_storage);

        RecyclerView recyclerView = findViewById(R.id.files_recycler);

        String path = getIntent().getStringExtra("path");

        File root = new File(path);
        File[] filesAndFolders = root.listFiles();

        if(filesAndFolders == null || filesAndFolders.length == 0){
            Toast.makeText(ExternalStorageActivity.this, "Files not found", Toast.LENGTH_SHORT).show();
            return;
        }


        recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        recyclerView.setAdapter(new StorageAdapter(getApplicationContext(), filesAndFolders));


    }
}


