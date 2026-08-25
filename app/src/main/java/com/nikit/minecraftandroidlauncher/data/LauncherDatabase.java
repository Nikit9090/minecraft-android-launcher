package com.nikit.minecraftandroidlauncher.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.nikit.minecraftandroidlauncher.data.dao.GameInstanceDao;
import com.nikit.minecraftandroidlauncher.data.dao.MinecraftVersionDao;
import com.nikit.minecraftandroidlauncher.data.dao.ModDao;
import com.nikit.minecraftandroidlauncher.data.dao.ModpackDao;
import com.nikit.minecraftandroidlauncher.data.entity.GameInstance;
import com.nikit.minecraftandroidlauncher.data.entity.MinecraftVersion;
import com.nikit.minecraftandroidlauncher.data.entity.Mod;
import com.nikit.minecraftandroidlauncher.data.entity.Modpack;

@Database(
    entities = {
        GameInstance.class,
        Mod.class,
        Modpack.class,
        MinecraftVersion.class
    },
    version = 1,
    exportSchema = false
)
public abstract class LauncherDatabase extends RoomDatabase {
    
    public abstract GameInstanceDao gameInstanceDao();
    public abstract ModDao modDao();
    public abstract ModpackDao modpackDao();
    public abstract MinecraftVersionDao minecraftVersionDao();
    
    private static volatile LauncherDatabase INSTANCE;
    
    public static LauncherDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (LauncherDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            LauncherDatabase.class,
                            "minecraft_launcher_db"
                    ).fallbackToDestructiveMigration().build();
                }
            }
        }
        return INSTANCE;
    }
}
