package com.nikit.minecraftandroidlauncher.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nikit.minecraftandroidlauncher.R;
import com.nikit.minecraftandroidlauncher.core.ModManager;
import com.nikit.minecraftandroidlauncher.data.entity.Mod;

import java.util.ArrayList;
import java.util.List;

public class ModAdapter extends RecyclerView.Adapter<ModAdapter.ModViewHolder> {
    
    private final Context context;
    private final ModManager modManager;
    private final int instanceId;
    private List<Mod> mods = new ArrayList<>();
    
    public ModAdapter(Context context, ModManager modManager, int instanceId) {
        this.context = context;
        this.modManager = modManager;
        this.instanceId = instanceId;
    }
    
    public void setMods(List<Mod> mods) {
        this.mods = mods;
        notifyDataSetChanged();
    }
    
    @NonNull
    @Override
    public ModViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_mod, parent, false);
        return new ModViewHolder(view);
    }
    
    @Override
    public void onBindViewHolder(@NonNull ModViewHolder holder, int position) {
        Mod mod = mods.get(position);
        
        holder.nameTextView.setText(mod.modName);
        holder.versionTextView.setText("Version: " + mod.modVersion);
        holder.authorTextView.setText("Author: " + (mod.modAuthor != null ? mod.modAuthor : "Unknown"));
        holder.enableSwitch.setChecked(mod.enabled);
        
        holder.enableSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            modManager.toggleMod(mod.id, isChecked);
            mod.enabled = isChecked;
        });
        
        holder.removeButton.setOnClickListener(v -> {
            try {
                modManager.removeMod(mod.id);
                mods.remove(position);
                notifyItemRemoved(position);
                Toast.makeText(context, "Mod removed", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(context, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    
    @Override
    public int getItemCount() {
        return mods.size();
    }
    
    public static class ModViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        TextView versionTextView;
        TextView authorTextView;
        Switch enableSwitch;
        Button removeButton;
        
        public ModViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.mod_name);
            versionTextView = itemView.findViewById(R.id.mod_version);
            authorTextView = itemView.findViewById(R.id.mod_author);
            enableSwitch = itemView.findViewById(R.id.mod_enabled_switch);
            removeButton = itemView.findViewById(R.id.remove_mod_button);
        }
    }
}
