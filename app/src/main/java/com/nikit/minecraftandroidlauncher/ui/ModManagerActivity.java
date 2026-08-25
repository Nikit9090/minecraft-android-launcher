package com.nikit.minecraftandroidlauncher.ui;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.nikit.minecraftandroidlauncher.R;
import com.nikit.minecraftandroidlauncher.core.ModManager;
import com.nikit.minecraftandroidlauncher.data.entity.Mod;
import com.nikit.minecraftandroidlauncher.ui.adapter.ModAdapter;

import java.util.List;

public class ModManagerActivity extends AppCompatActivity {
    
    private RecyclerView modsRecyclerView;
    private ModAdapter adapter;
    private ModManager modManager;
    private FloatingActionButton fabAddMod;
    private int instanceId;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mod_manager);
        
        instanceId = getIntent().getIntExtra("instance_id", -1);
        modManager = new ModManager(this);
        
        initializeUI();
        loadMods();
    }
    
    private void initializeUI() {
        modsRecyclerView = findViewById(R.id.mods_recycler_view);
        fabAddMod = findViewById(R.id.fab_add_mod);
        
        modsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ModAdapter(this, modManager, instanceId);
        modsRecyclerView.setAdapter(adapter);
        
        fabAddMod.setOnClickListener(v -> {
            // TODO: Open file picker to select mod
            Toast.makeText(this, "File picker not yet implemented", Toast.LENGTH_SHORT).show();
        });
    }
    
    private void loadMods() {
        try {
            List<Mod> mods = modManager.getAllMods(instanceId);
            adapter.setMods(mods);
        } catch (Exception e) {
            Toast.makeText(this, "Failed to load mods: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
