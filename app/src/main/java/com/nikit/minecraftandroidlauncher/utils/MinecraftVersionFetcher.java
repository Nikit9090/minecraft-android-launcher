package com.nikit.minecraftandroidlauncher.utils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class MinecraftVersionFetcher {
    
    private static final String VERSION_MANIFEST_URL = "https://launcher.mojang.com/v1/objects/versions/version_manifest.json";
    private static final Gson gson = new Gson();
    
    public static class VersionManifest {
        public JsonArray versions;
        public JsonObject latest;
    }
    
    public static VersionManifest fetchVersionManifest() throws IOException {
        URL url = new URL(VERSION_MANIFEST_URL);
        URLConnection connection = url.openConnection();
        
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            return gson.fromJson(sb.toString(), VersionManifest.class);
        }
    }
    
    public static JsonObject fetchVersionJson(String url) throws IOException {
        URLConnection connection = new URL(url).openConnection();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            return gson.fromJson(sb.toString(), JsonObject.class);
        }
    }
}
