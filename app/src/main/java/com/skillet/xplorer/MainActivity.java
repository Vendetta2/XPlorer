package com.skillet.xplorer;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.skillet.xplorer.mainactivity_recycler.MainA_Adapter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private final String[] storagePerm = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.MANAGE_EXTERNAL_STORAGE};
    private List<FileModel> fileModels;
    private MainA_Adapter mainAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getPerm.launch(storagePerm);

        fileModels = new ArrayList<>();


        File directory = new File(Objects.requireNonNull(getExternalFilesDir(null)).toURI());
        File[] files = directory.listFiles();

        if(files !=null){
            fileModels.clear();

            for (File file : files){
                String name = file.getName();
                String filepath = file.getAbsolutePath();
                long size = file.length();
                fileModels.add(new FileModel(name,filepath,size));
                Log.d("FileName", file.getName());
            }
        } else {
            Toast.makeText(MainActivity.this, "It's not working", Toast.LENGTH_SHORT).show();
        }


        mainAdapter = new MainA_Adapter(fileModels);

        RecyclerView filesRecycler = findViewById(R.id.files_recycler);
        filesRecycler.setLayoutManager(new GridLayoutManager(this, 4));
        filesRecycler.setAdapter(mainAdapter);

        displayFiles(Objects.requireNonNull(getExternalFilesDir(null)).getPath());
    }


    private void displayFiles(String path){
        File directory = new File(path);
        File[] files = directory.listFiles();

        if(files !=null){
            fileModels.clear();

            for (File file : files){
                String name = file.getName();
                String filepath = file.getAbsolutePath();
                long size = file.length();
                fileModels.add(new FileModel(name,filepath,size));
                Log.d("FileName", file.getName());
            }
           // mainAdapter.notifyDataSetChanged();
        } else {
            Toast.makeText(MainActivity.this, "It's not working", Toast.LENGTH_SHORT).show();
        }

    }


    private final ActivityResultLauncher<String[]> getPerm = registerForActivityResult(
            new ActivityResultContracts.RequestMultiplePermissions(),
            new ActivityResultCallback<Map<String, Boolean>>() {
                @Override
                public void onActivityResult(Map<String, Boolean> o) {

                }
            }
    );

}


