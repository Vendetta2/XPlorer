package com.skillet.xplorer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.documentfile.provider.DocumentFile;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.PopupMenu;

import com.skillet.xplorer.storage_recycler.ExternalStorageAdapter;

import java.util.Arrays;
import java.util.List;

public class ExternalStorageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_external_storage);


        String treeUri = getIntent().getStringExtra("treeUri");
















































        DocumentFile documentFile = DocumentFile.fromTreeUri(this, Uri.parse(treeUri));
        assert documentFile != null;
        List<DocumentFile> files = Arrays.asList(documentFile.listFiles());


        RecyclerView recyclerView = findViewById(R.id.files_recycler);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        ExternalStorageAdapter adapter = new ExternalStorageAdapter(files, ExternalStorageActivity.this);
        recyclerView.setAdapter(adapter);


    }
}

