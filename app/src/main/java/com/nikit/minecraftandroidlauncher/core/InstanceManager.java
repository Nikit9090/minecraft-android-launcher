package com.nikit.minecraftandroidlauncher.core;

import android.content.Context;
import android.util.Log;

import com.nikit.minecraftandroidlauncher.data.LauncherDatabase;
import com.nikit.minecraftandroidlauncher.data.entity.GameInstance;
import com.nikit.minecraftandroidlauncher.utils.FileUtils;
import com.nikit.minecraftandroidlauncher.utils.ZipUtils;

import java.io.File;
import java.util.List;

public class InstanceManager {
    
    private static final String TAG = "InstanceManager";
    private final Context context;
    private final LauncherDatabase database;
    
    public InstanceManager(Context context) {
        this.context = context;
        this.database = LauncherDatabase.getInstance(context);
    }
    
    public long createInstance(String name, String version, String modLoader) throws Exception {
        GameInstance instance = new GameInstance(name, version, modLoader);
        instance.gamePath = FileUtils.getInstanceGameDir(context, (int) System.currentTimeMillis()).getAbsolutePath();
        
        long instanceId = database.gameInstanceDao().insertInstance(instance);
        
        // Create directory structure
        FileUtils.getInstanceGameDir(context, (int) instanceId);
        FileUtils.getModsDir(context, (int) instanceId);
        
        Log.d(TAG, "Instance created: " + name + " (ID: " + instanceId + ")");
        return instanceId;
    }
    
    public void deleteInstance(int instanceId) throws Exception {
        GameInstance instance = database.gameInstanceDao().getInstanceById(instanceId);
        if (instance != null) {
            File instanceDir = new File(instance.gamePath);
            if (instanceDir.exists()) {
                FileUtils.deleteRecursive(instanceDir);
            }
            database.gameInstanceDao().deleteInstance(instance);
            Log.d(TAG, "Instance deleted: " + instance.name);
        }
    }
    
    public GameInstance getInstance(int instanceId) {
        return database.gameInstanceDao().getInstanceById(instanceId);
    }
    
    public List<GameInstance> getAllInstances() {
        return database.gameInstanceDao().getAllInstances();
    }
    
    public void updateInstance(GameInstance instance) {
        database.gameInstanceDao().updateInstance(instance);
        Log.d(TAG, "Instance updated: " + instance.name);
    }
    
    public long getInstanceSize(int instanceId) {
        GameInstance instance = getInstance(instanceId);
        if (instance != null) {
            File instanceDir = new File(instance.gamePath);
            return FileUtils.getDirectorySize(instanceDir);
        }
        return 0;
    }
    
    public void importModpack(int instanceId, File modpackFile) throws Exception {
        GameInstance instance = getInstance(instanceId);
        if (instance == null) {
            throw new Exception("Instance not found");
        }
        
        if (!ZipUtils.isValidZipFile(modpackFile)) {
            throw new Exception("Invalid modpack file");
        }
        
        File instanceDir = new File(instance.gamePath);
        ZipUtils.extractZip(modpackFile, instanceDir);
        
        Log.d(TAG, "Modpack imported for instance: " + instance.name);
    }
}
