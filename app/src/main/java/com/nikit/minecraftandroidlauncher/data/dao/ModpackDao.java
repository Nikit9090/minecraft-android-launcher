package com.nikit.minecraftandroidlauncher.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.nikit.minecraftandroidlauncher.data.entity.Modpack;

import java.util.List;

@Dao
public interface ModpackDao {
    
    @Insert
    long insertModpack(Modpack modpack);
    
    @Update
    void updateModpack(Modpack modpack);
    
    @Delete
    void deleteModpack(Modpack modpack);
    
    @Query("SELECT * FROM modpacks WHERE id = :id")
    Modpack getModpackById(int id);
    
    @Query("SELECT * FROM modpacks WHERE instanceId = :instanceId ORDER BY importedAt DESC")
    List<Modpack> getModpacksByInstanceId(int instanceId);
    
    @Query("SELECT * FROM modpacks ORDER BY importedAt DESC")
    List<Modpack> getAllModpacks();
    
    @Query("SELECT * FROM modpacks WHERE packName LIKE :searchQuery")
    List<Modpack> searchModpacks(String searchQuery);
    
    @Query("DELETE FROM modpacks WHERE id = :id")
    void deleteModpackById(int id);
    
    @Query("DELETE FROM modpacks WHERE instanceId = :instanceId")
    void deleteAllModpacksByInstanceId(int instanceId);
}
