package com.nikit.minecraftandroidlauncher.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.nikit.minecraftandroidlauncher.R;
import com.nikit.minecraftandroidlauncher.core.InstanceManager;
import com.nikit.minecraftandroidlauncher.data.entity.GameInstance;
import com.nikit.minecraftandroidlauncher.ui.adapter.InstanceAdapter;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    
    private RecyclerView instancesRecyclerView;
    private InstanceAdapter adapter;
    private InstanceManager instanceManager;
    private FloatingActionButton fabCreateInstance;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        instanceManager = new InstanceManager(this);
        
        initializeUI();
        loadInstances();
    }
    
    private void initializeUI() {
        instancesRecyclerView = findViewById(R.id.instances_recycler_view);
        fabCreateInstance = findViewById(R.id.fab_create_instance);
        
        instancesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        
        adapter = new InstanceAdapter(this, instanceManager);
        instancesRecyclerView.setAdapter(adapter);
        
        fabCreateInstance.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, InstanceManagerActivity.class);
            intent.putExtra("mode", "create");
            startActivity(intent);
        });
    }
    
    private void loadInstances() {
        try {
            List<GameInstance> instances = instanceManager.getAllInstances();
            adapter.setInstances(instances);
        } catch (Exception e) {
            Toast.makeText(this, "Failed to load instances: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        loadInstances();
    }
}
