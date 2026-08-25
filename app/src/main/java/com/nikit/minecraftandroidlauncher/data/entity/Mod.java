package com.nikit.minecraftandroidlauncher.data.entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "mods",
    foreignKeys = @ForeignKey(entity = GameInstance.class,
        parentColumns = "id",
        childColumns = "instanceId",
        onDelete = ForeignKey.CASCADE))
public class Mod {
    @PrimaryKey(autoGenerate = true)
    public int id;
    
    public int instanceId;
    public String modName;
    public String modVersion;
    public String modAuthor;
    public String modDescription;
    public String jarPath;
    public boolean enabled;
    public String modloaderType; // "forge", "fabric", "quilt"
    public long installedAt;
    
    public Mod() {}
    
    public Mod(int instanceId, String modName, String modVersion, String jarPath) {
        this.instanceId = instanceId;
        this.modName = modName;
        this.modVersion = modVersion;
        this.jarPath = jarPath;
        this.enabled = true;
        this.installedAt = System.currentTimeMillis();
    }
}
