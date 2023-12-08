package com.skillet.xplorer.mainactivity_recycler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.skillet.xplorer.FileModel;
import com.skillet.xplorer.R;

import java.util.List;

public class MainA_Adapter extends RecyclerView.Adapter<MainA_Adapter.MyViewHolder> {

    private List<FileModel> fileList;


    public MainA_Adapter(List<FileModel> fileList){
        this.fileList = fileList;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_activity_item_list, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        FileModel file = fileList.get(position);

        holder.fileName.setText(file.getFileName());


    }

    @Override
    public int getItemCount() {
        return fileList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView fileName;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            fileName = itemView.findViewById(R.id.file_name);
        }
    }


}
