package com.nikit.minecraftandroidlauncher.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nikit.minecraftandroidlauncher.R;
import com.nikit.minecraftandroidlauncher.core.InstanceManager;
import com.nikit.minecraftandroidlauncher.data.entity.GameInstance;
import com.nikit.minecraftandroidlauncher.ui.GameActivity;
import com.nikit.minecraftandroidlauncher.ui.InstanceManagerActivity;
import com.nikit.minecraftandroidlauncher.ui.ModManagerActivity;

import java.util.ArrayList;
import java.util.List;

public class InstanceAdapter extends RecyclerView.Adapter<InstanceAdapter.InstanceViewHolder> {
    
    private final Context context;
    private final InstanceManager instanceManager;
    private List<GameInstance> instances = new ArrayList<>();
    
    public InstanceAdapter(Context context, InstanceManager instanceManager) {
        this.context = context;
        this.instanceManager = instanceManager;
    }
    
    public void setInstances(List<GameInstance> instances) {
        this.instances = instances;
        notifyDataSetChanged();
    }
    
    @NonNull
    @Override
    public InstanceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_instance, parent, false);
        return new InstanceViewHolder(view);
    }
    
    @Override
    public void onBindViewHolder(@NonNull InstanceViewHolder holder, int position) {
        GameInstance instance = instances.get(position);
        
        holder.nameTextView.setText(instance.name);
        holder.versionTextView.setText("Version: " + instance.minecraftVersion);
        holder.modLoaderTextView.setText("Mod Loader: " + instance.modLoader);
        holder.playcountTextView.setText("Played: " + instance.playCount + " times");
        
        holder.playButton.setOnClickListener(v -> {
            Intent intent = new Intent(context, GameActivity.class);
            intent.putExtra("instance_id", instance.id);
            context.startActivity(intent);
        });
        
        holder.editButton.setOnClickListener(v -> {
            Intent intent = new Intent(context, InstanceManagerActivity.class);
            intent.putExtra("mode", "edit");
            intent.putExtra("instance_id", instance.id);
            context.startActivity(intent);
        });
        
        holder.modsButton.setOnClickListener(v -> {
            Intent intent = new Intent(context, ModManagerActivity.class);
            intent.putExtra("instance_id", instance.id);
            context.startActivity(intent);
        });
        
        holder.deleteButton.setOnClickListener(v -> {
            try {
                instanceManager.deleteInstance(instance.id);
                instances.remove(position);
                notifyItemRemoved(position);
                Toast.makeText(context, "Instance deleted", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(context, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    
    @Override
    public int getItemCount() {
        return instances.size();
    }
    
    public static class InstanceViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        TextView versionTextView;
        TextView modLoaderTextView;
        TextView playcountTextView;
        Button playButton;
        Button editButton;
        Button modsButton;
        Button deleteButton;
        
        public InstanceViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.instance_name);
            versionTextView = itemView.findViewById(R.id.instance_version);
            modLoaderTextView = itemView.findViewById(R.id.instance_modloader);
            playcountTextView = itemView.findViewById(R.id.instance_playcount);
            playButton = itemView.findViewById(R.id.play_button);
            editButton = itemView.findViewById(R.id.edit_button);
            modsButton = itemView.findViewById(R.id.mods_button);
            deleteButton = itemView.findViewById(R.id.delete_button);
        }
    }
}
