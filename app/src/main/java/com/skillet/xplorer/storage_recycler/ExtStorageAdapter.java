package com.skillet.xplorer.storage_recycler;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.documentfile.provider.DocumentFile;
import androidx.recyclerview.widget.RecyclerView;

import com.skillet.xplorer.R;
import com.skillet.xplorer.RemovableStorageActivity;
import com.skillet.xplorer.flash_info.ExternalStorageManager;

import java.util.Arrays;
import java.util.List;

public class ExtStorageAdapter extends RecyclerView.Adapter<ExtStorageAdapter.ExtViewHolder > {
    private List<DocumentFile> mFiles;
    Context context;

    public ExtStorageAdapter(List<DocumentFile> files, Context context){
        mFiles = files;
        this.context = context;
    }

    @NonNull
    @Override
    public ExtViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.storage_activity_item_list, parent, false);
        return new ExtViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExtViewHolder holder, int position) {
        DocumentFile file = mFiles.get(position);
        if(file.isDirectory()){
            holder.fileFolder.setImageResource(R.drawable.ic_folder_large);
        } else holder.fileFolder.setImageResource(R.drawable.ic_file_large);

        holder.fileName.setText(file.getName());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(file.isDirectory()){
                    Intent intent = new Intent(context, RemovableStorageActivity.class);
                    Uri treeUri = file.getUri();
                    intent.putExtra("treeUri", treeUri.toString());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    Toast.makeText(context, treeUri.toString(), Toast.LENGTH_SHORT ).show();
                    context.startActivity(intent);

                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mFiles.size();
    }

    public class ExtViewHolder extends RecyclerView.ViewHolder{
        public TextView fileName;
        public ImageView fileFolder;

        public ExtViewHolder(@NonNull View itemView) {
            super(itemView);
            fileName = itemView.findViewById(R.id.file_folder_name);
            fileFolder = itemView.findViewById(R.id.file_folder);
        }
    }

}
