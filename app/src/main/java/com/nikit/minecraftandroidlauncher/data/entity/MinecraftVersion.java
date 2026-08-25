package com.nikit.minecraftandroidlauncher.data.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "minecraft_versions")
public class MinecraftVersion {
    @PrimaryKey(autoGenerate = true)
    public int id;
    
    public String versionId;
    public String versionName;
    public String versionType; // "release", "snapshot", "old_alpha", "old_beta"
    public long releaseTime;
    public String urlJson;
    public String urlServer;
    public String urlClient;
    public boolean downloaded;
    public String localPath;
    
    public MinecraftVersion() {}
    
    public MinecraftVersion(String versionId, String versionName, String versionType) {
        this.versionId = versionId;
        this.versionName = versionName;
        this.versionType = versionType;
        this.downloaded = false;
    }
}
