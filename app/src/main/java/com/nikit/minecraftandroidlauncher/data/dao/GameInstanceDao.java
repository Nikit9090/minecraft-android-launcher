package com.nikit.minecraftandroidlauncher.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.nikit.minecraftandroidlauncher.data.entity.GameInstance;

import java.util.List;

@Dao
public interface GameInstanceDao {
    
    @Insert
    long insertInstance(GameInstance instance);
    
    @Update
    void updateInstance(GameInstance instance);
    
    @Delete
    void deleteInstance(GameInstance instance);
    
    @Query("SELECT * FROM game_instances WHERE id = :id")
    GameInstance getInstanceById(int id);
    
    @Query("SELECT * FROM game_instances ORDER BY lastPlayedAt DESC")
    List<GameInstance> getAllInstances();
    
    @Query("SELECT * FROM game_instances ORDER BY createdAt DESC")
    List<GameInstance> getInstancesByCreatedDate();
    
    @Query("SELECT * FROM game_instances WHERE minecraftVersion = :version")
    List<GameInstance> getInstancesByVersion(String version);
    
    @Query("SELECT * FROM game_instances WHERE modLoader = :modLoader")
    List<GameInstance> getInstancesByModLoader(String modLoader);
    
    @Query("DELETE FROM game_instances WHERE id = :id")
    void deleteInstanceById(int id);
    
    @Query("UPDATE game_instances SET lastPlayedAt = :timestamp, playCount = playCount + 1 WHERE id = :id")
    void updatePlayStats(int id, long timestamp);
}
