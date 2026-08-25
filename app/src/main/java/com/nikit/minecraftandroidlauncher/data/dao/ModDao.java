package com.nikit.minecraftandroidlauncher.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.nikit.minecraftandroidlauncher.data.entity.Mod;

import java.util.List;

@Dao
public interface ModDao {
    
    @Insert
    long insertMod(Mod mod);
    
    @Update
    void updateMod(Mod mod);
    
    @Delete
    void deleteMod(Mod mod);
    
    @Query("SELECT * FROM mods WHERE id = :id")
    Mod getModById(int id);
    
    @Query("SELECT * FROM mods WHERE instanceId = :instanceId ORDER BY modName ASC")
    List<Mod> getModsByInstanceId(int instanceId);
    
    @Query("SELECT * FROM mods WHERE instanceId = :instanceId AND enabled = 1")
    List<Mod> getEnabledModsByInstanceId(int instanceId);
    
    @Query("SELECT * FROM mods WHERE instanceId = :instanceId AND enabled = 0")
    List<Mod> getDisabledModsByInstanceId(int instanceId);
    
    @Query("SELECT * FROM mods WHERE modloaderType = :modloaderType AND instanceId = :instanceId")
    List<Mod> getModsByLoaderType(String modloaderType, int instanceId);
    
    @Query("UPDATE mods SET enabled = :enabled WHERE id = :id")
    void setModEnabled(int id, boolean enabled);
    
    @Query("DELETE FROM mods WHERE id = :id")
    void deleteModById(int id);
    
    @Query("DELETE FROM mods WHERE instanceId = :instanceId")
    void deleteAllModsByInstanceId(int instanceId);
}
