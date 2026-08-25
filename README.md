# Minecraft Android Launcher

A modern Android launcher application for Minecraft with support for modpacks, mods, and multiple game instances.

## Features

- 🎮 Create and manage multiple Minecraft instances
- 📦 Support for modpacks and mods
- 🔄 Version management (Vanilla, Snapshots)
- 🛠️ Mod loader support (Forge, Fabric)
- 📊 Instance statistics and management
- 💾 Room database for persistence
- 🌐 Download management with progress tracking

## Architecture

### Data Layer
- **Entities**: GameInstance, Mod, Modpack, MinecraftVersion
- **DAOs**: GameInstanceDao, ModDao, ModpackDao, MinecraftVersionDao
- **Database**: Room-based LauncherDatabase

### Service Layer
- **GameLaunchService**: Handles game launching with progress tracking
- **DownloadService**: Manages downloads with callback listeners

### Core Layer
- **InstanceManager**: Instance lifecycle management
- **ModManager**: Mod installation and toggling

### UI Layer
- **MainActivity**: Main activity showing all instances
- **InstanceManagerActivity**: Create/edit instances
- **ModManagerActivity**: Manage mods for instances
- **ModpackImportActivity**: Import modpacks
- **GameActivity**: Game launch screen

### Utilities
- **FileUtils**: File system operations
- **ZipUtils**: ZIP extraction and validation
- **DownloadUtils**: HTTP downloads with progress
- **MinecraftVersionFetcher**: Fetch Minecraft versions from Mojang

## Build

```bash
./gradlew :app:assembleDebug
```

## Requirements

- Android 7.0+ (API 24)
- 2GB+ RAM
- External storage access

## Dependencies

- AndroidX AppCompat
- Material Design Components
- Room Database
- OkHttp3
- GSON

## License

MIT License
