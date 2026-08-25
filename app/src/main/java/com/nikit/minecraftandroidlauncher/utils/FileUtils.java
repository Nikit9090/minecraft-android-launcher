package com.nikit.minecraftandroidlauncher.utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;

public class FileUtils {
    
    public static File getGameInstancesDir(Context context) {
        File dir = new File(context.getExternalFilesDir(null), "instances");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir;
    }
    
    public static File getInstanceGameDir(Context context, int instanceId) {
        File dir = new File(getGameInstancesDir(context), "instance_" + instanceId);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir;
    }
    
    public static File getModsDir(Context context, int instanceId) {
        File dir = new File(getInstanceGameDir(context, instanceId), "mods");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir;
    }
    
    public static File getVersionsDir(Context context) {
        File dir = new File(context.getExternalFilesDir(null), "versions");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir;
    }
    
    public static File getLibrariesDir(Context context) {
        File dir = new File(context.getExternalFilesDir(null), "libraries");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir;
    }
    
    public static File getAssetsDir(Context context) {
        File dir = new File(context.getExternalFilesDir(null), "assets");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir;
    }
    
    public static File getModpacksDir(Context context) {
        File dir = new File(context.getExternalFilesDir(null), "modpacks");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir;
    }
    
    public static long getDirectorySize(File dir) {
        long size = 0;
        if (dir.isDirectory()) {
            File[] files = dir.listFiles();
            if (files != null) {
                for (File file : files) {
                    size += getDirectorySize(file);
                }
            }
        } else {
            size = dir.length();
        }
        return size;
    }
    
    public static void deleteRecursive(File fileOrDirectory) {
        if (fileOrDirectory.isDirectory()) {
            File[] files = fileOrDirectory.listFiles();
            if (files != null) {
                for (File file : files) {
                    deleteRecursive(file);
                }
            }
        }
        fileOrDirectory.delete();
    }
}
