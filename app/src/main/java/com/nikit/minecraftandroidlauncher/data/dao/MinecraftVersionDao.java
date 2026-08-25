package com.nikit.minecraftandroidlauncher.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.nikit.minecraftandroidlauncher.data.entity.MinecraftVersion;

import java.util.List;

@Dao
public interface MinecraftVersionDao {
    
    @Insert
    long insertVersion(MinecraftVersion version);
    
    @Update
    void updateVersion(MinecraftVersion version);
    
    @Delete
    void deleteVersion(MinecraftVersion version);
    
    @Query("SELECT * FROM minecraft_versions WHERE versionId = :versionId")
    MinecraftVersion getVersionById(String versionId);
    
    @Query("SELECT * FROM minecraft_versions WHERE versionType = :type ORDER BY releaseTime DESC")
    List<MinecraftVersion> getVersionsByType(String type);
    
    @Query("SELECT * FROM minecraft_versions WHERE downloaded = 1 ORDER BY releaseTime DESC")
    List<MinecraftVersion> getDownloadedVersions();
    
    @Query("SELECT * FROM minecraft_versions WHERE versionType = 'release' ORDER BY releaseTime DESC LIMIT 20")
    List<MinecraftVersion> getLatestReleases();
    
    @Query("SELECT * FROM minecraft_versions WHERE versionType = 'snapshot' ORDER BY releaseTime DESC LIMIT 20")
    List<MinecraftVersion> getLatestSnapshots();
    
    @Query("SELECT DISTINCT versionType FROM minecraft_versions ORDER BY versionType")
    List<String> getAllVersionTypes();
    
    @Query("UPDATE minecraft_versions SET downloaded = :downloaded, localPath = :localPath WHERE versionId = :versionId")
    void setVersionDownloaded(String versionId, boolean downloaded, String localPath);
    
    @Query("DELETE FROM minecraft_versions WHERE versionId = :versionId")
    void deleteVersionById(String versionId);
}
