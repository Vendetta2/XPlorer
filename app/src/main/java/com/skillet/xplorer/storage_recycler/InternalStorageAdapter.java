package com.skillet.xplorer.storage_recycler;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.skillet.xplorer.InternalStorageActivity;
import com.skillet.xplorer.R;

import java.io.File;


public class InternalStorageAdapter extends RecyclerView.Adapter<InternalStorageAdapter.InternalViewHolder> {

    Context context;
    File[] filesAndFolders;
    String path;

    public InternalStorageAdapter(Context context, File[] filesAndFolders, String path){
        this.context = context;
        this.filesAndFolders = filesAndFolders;
        this.path = path;
    }


    @NonNull
    @Override
    public InternalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.storage_activity_item_list, parent, false);

        return new InternalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InternalViewHolder holder, int position) {
        File selectedFile = filesAndFolders[position];
        holder.fileFolderName.setText(selectedFile.getName());

        if(selectedFile.isDirectory()){
            holder.fileFolderIMG.setImageResource(R.drawable.ic_folder_large);
        }else{
            holder.fileFolderIMG.setImageResource(R.drawable.ic_file_large);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedFile.isDirectory()){
                    Intent intent = new Intent(context, InternalStorageActivity.class);
                    String path = selectedFile.getAbsolutePath();
                    intent.putExtra("path", path);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                } else{
                    try {
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_VIEW);
                        String type = "image/";
                        intent.setDataAndType(Uri.parse(selectedFile.getAbsolutePath()),type);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    } catch (Exception e){
                        Toast.makeText(context.getApplicationContext(), "Cannot open the file", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });


        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                PopupMenu popupMenu = new PopupMenu(context, v);
                popupMenu.getMenu().add("DELETE");
                popupMenu.getMenu().add("MOVE");
                popupMenu.getMenu().add("RENAME");

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if(item.getTitle().equals("DELETE")){
                            boolean isDeleted = selectedFile.delete();
                            if(isDeleted){
                                Toast.makeText(context.getApplicationContext(),"Has been Deleted", Toast.LENGTH_SHORT).show();
                                v.setVisibility(View.GONE);
                            }
                        }
                        if(item.getTitle().equals("MOVE")){
                            Toast.makeText(context.getApplicationContext(),"Has been Moved", Toast.LENGTH_SHORT).show();

                        }
                        if(item.getTitle().equals("RENAME")){
/*
                            Toast.makeText(context.getApplicationContext(),"Has been Renamed", Toast.LENGTH_SHORT).show();
                            fileRename(selectedFile);*/

                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
                            builder.setTitle("Введите новое название");

                            EditText editText = new EditText(context);
                            builder.setView(editText);

                            builder.setPositiveButton("Rename", (dialog, which) -> {
                                String folderName = editText.getText().toString();

                                File newFolder = new File(path, folderName);
                                if(selectedFile.renameTo(newFolder)){
                                    Toast.makeText(context, "Renamed to " + folderName, Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                                }

                            });

                            builder.setNegativeButton("Cancel", (dialog, which) -> {

                            });

                            builder.show();

                            return true;

                        }
                        return true;
                    }
                });

                popupMenu.show();
                return true;
            }
        });


    }



    @Override
    public int getItemCount() {
        return filesAndFolders.length;
    }

    public static class InternalViewHolder extends RecyclerView.ViewHolder {
        TextView fileFolderName;
        ImageView fileFolderIMG;

        public InternalViewHolder(@NonNull View itemView) {
            super(itemView);
            fileFolderName = itemView.findViewById(R.id.file_folder_name);
            fileFolderIMG = itemView.findViewById(R.id.file_folder);

        }
    }


/*    public void fileRename(File file2Ren){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Введите новое название");

        EditText editText = new EditText(context);
        builder.setView(editText);

        builder.setPositiveButton("Rename", ((dialog, which) -> {
            String folderName = editText.getText().toString();

            File newFolder = new File(path, folderName);
            if(file2Ren.renameTo(newFolder)){
                Toast.makeText(context, "Renamed to " + folderName, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
            }

        }));

        builder.setNegativeButton("Cancel", ((dialog, which) -> {

        }));

        builder.show();

    }*/


}
