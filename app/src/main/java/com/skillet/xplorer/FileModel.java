package com.skillet.xplorer;

public class FileModel {
    private String name;
    private String path;
    private long size;
    public FileModel(String fileName, String path, long size){
        this.name = fileName;
        this.path = path;
        this.size = size;

    }

    public String getFileName() {
        return name;
    }

    public String getPath(){
        return path;
    }

    public long getSize(){
        return size;
    }


}
