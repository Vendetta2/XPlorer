package com.skillet.xplorer.storage_recycler;

import android.content.Context;
import android.content.Intent;
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
import com.skillet.xplorer.ExternalStorageActivity;

import java.util.List;

public class ExternalStorageAdapter extends RecyclerView.Adapter<ExternalStorageAdapter.ExtViewHolder > {
    private List<DocumentFile> mFiles;
    Context context;

    public ExternalStorageAdapter(List<DocumentFile> files, Context context){
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


                    Intent intent = new Intent(context, ExternalStorageActivity.class);
                    String treeUri = file.getUri().toString();
                    intent.removeExtra("treeUri");
                    intent.putExtra("treeUri", treeUri);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    Toast.makeText(context, treeUri, Toast.LENGTH_SHORT ).show();
                    context.startActivity(intent);

                }/*
                Uri treeUri = file.getUri();
                context.startActivity(new Intent(Intent.ACTION_VIEW, treeUri));*/
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
