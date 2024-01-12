package com.skillet.xplorer.flash_info;

import android.content.Context;
import android.os.Build;
import android.os.storage.StorageManager;
import android.os.storage.StorageVolume;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class FlashDriveInfo {
    static ArrayList<String> cardNames = new ArrayList<>();
    static ArrayList<String> cardPaths = new ArrayList<>();


    public static ArrayList<String> getCardPath(Context context){
        File[] dirs = ContextCompat.getExternalFilesDirs(context, null);
        for (File dir : dirs) {
            cardPaths.add(dir.getParent());
        }
        return cardPaths;
    }

    public static ArrayList<String> getFlashDriveName(Context context){
        StorageManager storageManager = (StorageManager) context.getSystemService(Context.STORAGE_SERVICE);
        for (StorageVolume storageVolume : storageManager.getStorageVolumes()){
            cardNames.add(storageVolume.getDescription(context));
            

        }

        return cardNames;
    }


    private static boolean isExternalStorageRemovable(StorageManager storageManager, StorageVolume storageVolume){
        try{
            return (Boolean) StorageVolume.class.getMethod("isRemovable").invoke(storageVolume);
        } catch (Exception e){
            e.printStackTrace();
        }

        return false;
    }


}
