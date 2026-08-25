package com.nikit.minecraftandroidlauncher.data.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "game_instances")
public class GameInstance {
    @PrimaryKey(autoGenerate = true)
    public int id;
    
    public String name;
    public String minecraftVersion;
    public String modLoader; // "none", "forge", "fabric", "quilt"
    public String modLoaderVersion;
    public String gamePath;
    public long createdAt;
    public long lastPlayedAt;
    public int playCount;
    public String javaArgs;
    public int ramAllocated; // in MB
    
    public GameInstance() {}
    
    public GameInstance(String name, String minecraftVersion, String modLoader) {
        this.name = name;
        this.minecraftVersion = minecraftVersion;
        this.modLoader = modLoader;
        this.createdAt = System.currentTimeMillis();
        this.playCount = 0;
        this.ramAllocated = 2048;
    }
}
