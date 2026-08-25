package com.nikit.minecraftandroidlauncher.data.entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "modpacks",
    foreignKeys = @ForeignKey(entity = GameInstance.class,
        parentColumns = "id",
        childColumns = "instanceId",
        onDelete = ForeignKey.CASCADE))
public class Modpack {
    @PrimaryKey(autoGenerate = true)
    public int id;
    
    public int instanceId;
    public String packName;
    public String packVersion;
    public String packDescription;
    public String packPath;
    public String modloaderType;
    public String modloaderVersion;
    public long importedAt;
    
    public Modpack() {}
    
    public Modpack(int instanceId, String packName, String packPath) {
        this.instanceId = instanceId;
        this.packName = packName;
        this.packPath = packPath;
        this.importedAt = System.currentTimeMillis();
    }
}
