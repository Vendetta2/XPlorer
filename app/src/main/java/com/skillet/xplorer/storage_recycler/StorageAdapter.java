package com.skillet.xplorer.storage_recycler;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.skillet.xplorer.ExternalStorageActivity;
import com.skillet.xplorer.MainMenuActivity;
import com.skillet.xplorer.R;

import java.io.File;
import java.nio.file.Files;


public class StorageAdapter extends RecyclerView.Adapter<StorageAdapter.MyViewHolder> {

    Context context;
    File[] filesAndFolders;

    public StorageAdapter(Context context, File[] filesAndFolders){
        this.context = context;
        this.filesAndFolders = filesAndFolders;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.storage_activity_item_list, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
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
                    Intent intent = new Intent(context, ExternalStorageActivity.class);
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
                Toast.makeText(context.getApplicationContext(), "Is longClicked", Toast.LENGTH_SHORT).show();

                PopupMenu popupMenu = new PopupMenu(context, v);
                popupMenu.getMenu().add("DELETE");
                popupMenu.getMenu().add("MOVE");
                popupMenu.getMenu().add("RENAME");

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if(item.getTitle().equals(null)){
                            Toast.makeText(context.getApplicationContext(),"Is a null but idk", Toast.LENGTH_SHORT).show();
                        } else if(item.getTitle().equals("DELETE")){
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
                            Toast.makeText(context.getApplicationContext(),"Has been Renamed", Toast.LENGTH_SHORT).show();

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

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView fileFolderName;
        ImageView fileFolderIMG;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            fileFolderName = itemView.findViewById(R.id.file_folder_name);
            fileFolderIMG = itemView.findViewById(R.id.file_folder);

        }
    }


}
