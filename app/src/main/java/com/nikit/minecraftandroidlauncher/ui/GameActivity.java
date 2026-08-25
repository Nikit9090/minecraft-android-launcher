package com.nikit.minecraftandroidlauncher.ui;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.nikit.minecraftandroidlauncher.R;
import com.nikit.minecraftandroidlauncher.service.GameLaunchService;

public class GameActivity extends AppCompatActivity implements GameLaunchService.GameLaunchListener {
    
    private static final String TAG = "GameActivity";
    private TextView statusTextView;
    private ProgressBar progressBar;
    private Button stopButton;
    private int instanceId;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        
        instanceId = getIntent().getIntExtra("instance_id", -1);
        
        initializeUI();
        launchGame();
    }
    
    private void initializeUI() {
        statusTextView = findViewById(R.id.status_text);
        progressBar = findViewById(R.id.progress_bar);
        stopButton = findViewById(R.id.stop_button);
        
        stopButton.setOnClickListener(v -> finish());
    }
    
    private void launchGame() {
        GameLaunchService launchService = new GameLaunchService();
        launchService.setLaunchListener(this);
        launchService.launchGame(instanceId);
    }
    
    @Override
    public void onLaunchStarted() {
        Log.d(TAG, "Launch started");
        runOnUiThread(() -> {
            statusTextView.setText("Preparing game...");
            progressBar.setIndeterminate(true);
        });
    }
    
    @Override
    public void onLaunchProgress(String message) {
        Log.d(TAG, "Progress: " + message);
        runOnUiThread(() -> statusTextView.setText(message));
    }
    
    @Override
    public void onLaunchSuccess() {
        Log.d(TAG, "Launch successful");
        runOnUiThread(() -> {
            statusTextView.setText("Game launched!");
            Toast.makeText(GameActivity.this, "Game started", Toast.LENGTH_SHORT).show();
        });
    }
    
    @Override
    public void onLaunchFailed(Exception e) {
        Log.e(TAG, "Launch failed", e);
        runOnUiThread(() -> {
            statusTextView.setText("Launch failed: " + e.getMessage());
            Toast.makeText(GameActivity.this, "Launch failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
        });
    }
}
