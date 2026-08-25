package com.nikit.minecraftandroidlauncher.ui;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.nikit.minecraftandroidlauncher.R;
import com.nikit.minecraftandroidlauncher.core.InstanceManager;
import com.nikit.minecraftandroidlauncher.utils.ZipUtils;

import java.io.File;

public class ModpackImportActivity extends AppCompatActivity {
    
    private Button selectModpackButton;
    private Button importButton;
    private Button cancelButton;
    private InstanceManager instanceManager;
    private int instanceId;
    private File selectedModpackFile;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modpack_import);
        
        instanceId = getIntent().getIntExtra("instance_id", -1);
        instanceManager = new InstanceManager(this);
        
        initializeUI();
    }
    
    private void initializeUI() {
        selectModpackButton = findViewById(R.id.select_modpack_button);
        importButton = findViewById(R.id.import_button);
        cancelButton = findViewById(R.id.cancel_button);
        
        selectModpackButton.setOnClickListener(v -> {
            // TODO: Open file picker for modpack selection
            Toast.makeText(this, "File picker not yet implemented", Toast.LENGTH_SHORT).show();
        });
        
        importButton.setOnClickListener(v -> importModpack());
        cancelButton.setOnClickListener(v -> finish());
    }
    
    private void importModpack() {
        if (selectedModpackFile == null) {
            Toast.makeText(this, "Please select a modpack", Toast.LENGTH_SHORT).show();
            return;
        }
        
        try {
            if (!ZipUtils.isValidZipFile(selectedModpackFile)) {
                Toast.makeText(this, "Invalid ZIP file", Toast.LENGTH_SHORT).show();
                return;
            }
            
            instanceManager.importModpack(instanceId, selectedModpackFile);
            Toast.makeText(this, "Modpack imported successfully", Toast.LENGTH_SHORT).show();
            finish();
        } catch (Exception e) {
            Toast.makeText(this, "Import failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
