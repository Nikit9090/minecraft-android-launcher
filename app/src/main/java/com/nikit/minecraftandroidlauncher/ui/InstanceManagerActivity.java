package com.nikit.minecraftandroidlauncher.ui;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.nikit.minecraftandroidlauncher.R;
import com.nikit.minecraftandroidlauncher.core.InstanceManager;
import com.nikit.minecraftandroidlauncher.data.entity.GameInstance;

public class InstanceManagerActivity extends AppCompatActivity {
    
    private EditText instanceNameEdit;
    private Spinner versionSpinner;
    private Spinner modLoaderSpinner;
    private Button createButton;
    private Button cancelButton;
    private InstanceManager instanceManager;
    private String mode;
    private int instanceId = -1;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instance_manager);
        
        instanceManager = new InstanceManager(this);
        mode = getIntent().getStringExtra("mode");
        if (mode == null) mode = "create";
        
        if ("edit".equals(mode)) {
            instanceId = getIntent().getIntExtra("instance_id", -1);
        }
        
        initializeUI();
        loadData();
    }
    
    private void initializeUI() {
        instanceNameEdit = findViewById(R.id.instance_name_edit);
        versionSpinner = findViewById(R.id.version_spinner);
        modLoaderSpinner = findViewById(R.id.modloader_spinner);
        createButton = findViewById(R.id.create_button);
        cancelButton = findViewById(R.id.cancel_button);
        
        createButton.setOnClickListener(v -> createOrUpdateInstance());
        cancelButton.setOnClickListener(v -> finish());
    }
    
    private void loadData() {
        if ("edit".equals(mode) && instanceId != -1) {
            GameInstance instance = instanceManager.getInstance(instanceId);
            if (instance != null) {
                instanceNameEdit.setText(instance.name);
            }
        }
    }
    
    private void createOrUpdateInstance() {
        String name = instanceNameEdit.getText().toString().trim();
        String version = versionSpinner.getSelectedItem().toString();
        String modLoader = modLoaderSpinner.getSelectedItem().toString();
        
        if (name.isEmpty()) {
            Toast.makeText(this, "Please enter instance name", Toast.LENGTH_SHORT).show();
            return;
        }
        
        try {
            if ("create".equals(mode)) {
                instanceManager.createInstance(name, version, modLoader);
                Toast.makeText(this, "Instance created", Toast.LENGTH_SHORT).show();
            } else {
                GameInstance instance = instanceManager.getInstance(instanceId);
                if (instance != null) {
                    instance.name = name;
                    instance.minecraftVersion = version;
                    instance.modLoader = modLoader;
                    instanceManager.updateInstance(instance);
                    Toast.makeText(this, "Instance updated", Toast.LENGTH_SHORT).show();
                }
            }
            finish();
        } catch (Exception e) {
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
