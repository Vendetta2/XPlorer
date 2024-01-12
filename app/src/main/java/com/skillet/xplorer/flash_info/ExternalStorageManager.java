package com.skillet.xplorer.flash_info;

import android.content.Context;
import android.net.Uri;
import androidx.documentfile.provider.DocumentFile;

public class ExternalStorageManager {
    private Context context;
    private Uri treeUri;

    public ExternalStorageManager(Context context, Uri treeUri) {
        this.context = context;
        this.treeUri = treeUri;
    }

    public DocumentFile[] listFilesInDir(DocumentFile directory) {
        if (directory == null) {
            directory = DocumentFile.fromTreeUri(context, treeUri);
        }
        return directory.listFiles();
    }
}