package com.skillet.xplorer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.documentfile.provider.DocumentFile;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import com.skillet.xplorer.flash_info.ExternalStorageManager;
import com.skillet.xplorer.storage_recycler.ExtStorageAdapter;

import java.util.Arrays;
import java.util.List;

public class RemovableStorageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_external_storage);

        Uri treeUri = Uri.parse(getIntent().getStringExtra("treeUri"));

        DocumentFile documentFile = DocumentFile.fromTreeUri(this, treeUri);
        assert documentFile != null;
        List<DocumentFile> files = Arrays.asList(documentFile.listFiles());

       /* ExternalStorageManager manager = new ExternalStorageManager(RemovableStorageActivity.this, treeUri);
        DocumentFile[] files = manager.listFilesInDir(null);*/

        RecyclerView recyclerView = findViewById(R.id.files_recycler);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        ExtStorageAdapter adapter = new ExtStorageAdapter(files, RemovableStorageActivity.this);
        recyclerView.setAdapter(adapter);



    }
}


