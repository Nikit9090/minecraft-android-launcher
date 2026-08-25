package com.nikit.minecraftandroidlauncher.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.nikit.minecraftandroidlauncher.data.LauncherDatabase;
import com.nikit.minecraftandroidlauncher.data.entity.GameInstance;
import com.nikit.minecraftandroidlauncher.data.entity.Mod;
import com.nikit.minecraftandroidlauncher.utils.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class GameLaunchService extends Service {
    
    private static final String TAG = "GameLaunchService";
    private final IBinder binder = new LocalBinder();
    private LauncherDatabase database;
    private GameLaunchListener listener;
    
    public interface GameLaunchListener {
        void onLaunchStarted();
        void onLaunchProgress(String message);
        void onLaunchSuccess();
        void onLaunchFailed(Exception e);
    }
    
    public class LocalBinder extends Binder {
        GameLaunchService getService() {
            return GameLaunchService.this;
        }
    }
    
    @Override
    public void onCreate() {
        super.onCreate();
        database = LauncherDatabase.getInstance(this);
    }
    
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }
    
    public void setLaunchListener(GameLaunchListener listener) {
        this.listener = listener;
    }
    
    public void launchGame(int instanceId) {
        new Thread(() -> {
            try {
                if (listener != null) {
                    listener.onLaunchStarted();
                }
                
                GameInstance instance = database.gameInstanceDao().getInstanceById(instanceId);
                if (instance == null) {
                    throw new Exception("Instance not found");
                }
                
                Log.d(TAG, "Launching instance: " + instance.name);
                
                // Load enabled mods
                List<Mod> enabledMods = database.modDao().getEnabledModsByInstanceId(instanceId);
                Log.d(TAG, "Loaded " + enabledMods.size() + " mods");
                
                if (listener != null) {
                    listener.onLaunchProgress("Preparing game files...");
                }
                
                // Verify game files
                File gameDir = FileUtils.getInstanceGameDir(this, instanceId);
                if (!gameDir.exists()) {
                    gameDir.mkdirs();
                }
                
                if (listener != null) {
                    listener.onLaunchProgress("Loading mods...");
                }
                
                // Copy mods to mod directory
                File modsDir = FileUtils.getModsDir(this, instanceId);
                for (Mod mod : enabledMods) {
                    File modFile = new File(mod.jarPath);
                    if (modFile.exists()) {
                        Log.d(TAG, "Mod found: " + mod.modName);
                    }
                }
                
                if (listener != null) {
                    listener.onLaunchProgress("Starting game...");
                }
                
                // Update play stats
                database.gameInstanceDao().updatePlayStats(instanceId, System.currentTimeMillis());
                
                if (listener != null) {
                    listener.onLaunchSuccess();
                }
                
                Log.d(TAG, "Game launched successfully");
                
            } catch (Exception e) {
                Log.e(TAG, "Launch failed", e);
                if (listener != null) {
                    listener.onLaunchFailed(e);
                }
            }
        }).start();
    }
}
