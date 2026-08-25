package com.nikit.minecraftandroidlauncher.core;

import android.content.Context;
import android.util.Log;

import com.nikit.minecraftandroidlauncher.data.LauncherDatabase;
import com.nikit.minecraftandroidlauncher.data.entity.GameInstance;
import com.nikit.minecraftandroidlauncher.data.entity.Mod;
import com.nikit.minecraftandroidlauncher.utils.FileUtils;
import com.nikit.minecraftandroidlauncher.utils.ZipUtils;

import java.io.File;
import java.util.List;

public class ModManager {
    
    private static final String TAG = "ModManager";
    private final Context context;
    private final LauncherDatabase database;
    
    public ModManager(Context context) {
        this.context = context;
        this.database = LauncherDatabase.getInstance(context);
    }
    
    public void addMod(int instanceId, File modFile, String modloaderType) throws Exception {
        GameInstance instance = database.gameInstanceDao().getInstanceById(instanceId);
        if (instance == null) {
            throw new Exception("Instance not found");
        }
        
        File modsDir = FileUtils.getModsDir(context, instanceId);
        File destFile = new File(modsDir, modFile.getName());
        
        // Copy mod file
        java.nio.file.Files.copy(
            modFile.toPath(),
            destFile.toPath(),
            java.nio.file.StandardCopyOption.REPLACE_EXISTING
        );
        
        // Create mod entry in database
        Mod mod = new Mod(instanceId, modFile.getName(), "1.0", destFile.getAbsolutePath());
        mod.modloaderType = modloaderType;
        database.modDao().insertMod(mod);
        
        Log.d(TAG, "Mod added: " + modFile.getName());
    }
    
    public void removeMod(int modId) throws Exception {
        Mod mod = database.modDao().getModById(modId);
        if (mod != null) {
            File modFile = new File(mod.jarPath);
            if (modFile.exists()) {
                modFile.delete();
            }
            database.modDao().deleteMod(mod);
            Log.d(TAG, "Mod removed: " + mod.modName);
        }
    }
    
    public void toggleMod(int modId, boolean enabled) {
        database.modDao().setModEnabled(modId, enabled);
        Log.d(TAG, "Mod " + (enabled ? "enabled" : "disabled") + ": " + modId);
    }
    
    public List<Mod> getEnabledMods(int instanceId) {
        return database.modDao().getEnabledModsByInstanceId(instanceId);
    }
    
    public List<Mod> getAllMods(int instanceId) {
        return database.modDao().getModsByInstanceId(instanceId);
    }
}
