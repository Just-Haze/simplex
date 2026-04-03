# <img src="src/main/resources/icon.png" align="right" width="128" height="128"> Simplex

![Version](https://img.shields.io/badge/version-1.1.0-blue?style=for-the-badge)
![Loader](https://img.shields.io/badge/loader-Fabric-geek?style=for-the-badge&logo=fabric)
![License](https://img.shields.io/badge/license-CC0--1.0-green?style=for-the-badge)

**Simplex** is a lightweight optimization mod for Minecraft Fabric, designed to enhance performance through intelligent rendering culling and logic improvements without compromising the vanilla experience.

## Supported Versions

- Minecraft `1.21.1`
- Minecraft `1.21.11` (latest `1.21.x` target)

## ✨ Key Features

### 🎮 Rendering

- **Entity Culling**: Automatically stops rendering entities that are obscured or too far away.
  - _Configurable Distance_: Default 64 blocks.
- **Particle Culling**: Reduces particle rendering load based on distance.
- **Adaptive Culling**: Automatically adjusts culling distance toward your target FPS.

### 🧠 Logic & Tick

- **Lazy Entity Ticking**: Reduces tick frequency for distant entities with safer interval controls.
- **Distant AI Optimization**: Throttles distant AI updates with configurable distance and interval.
- **Fast XP**: Optimized experience orb handling.

### 🌐 Network & System

- **Low Latency Mode**: Optional frame-sync mode (`glFinish`) with configurable interval.
- **Explicit GC**: Optional aggressive garbage collection on world exit to free memory.
- **Performance Profiles**: `Safe`, `Balanced` (default), and `Aggressive` presets.

## ⚙️ Configuration

Simplex is highly configurable. The configuration file is generated at `.minecraft/config/simplex.json`.

```json
{
  "enableEntityCulling": true,
  "entityCullingDistance": 64,
  "performanceProfile": "balanced",
  "enableFastMath": false,
  "enableGpuOptimizations": true,
  "enableParticleCulling": true,
  "enableLazyEntityTicking": true,
  "enableAdaptiveCulling": true,
  "adaptiveTargetFps": 90,
  "enableLowLatency": false
}
```

## 🛠️ Building from Source

To build Simplex from source, you need JDK 21 installed.

```bash
# Clone the repository
git clone https://github.com/Just-Haze/simplex

# Navigate to the directory
cd simplex

# Build the mod
./gradlew build
```

The compiled jar file will be located in `build/libs/`.

## 📄 License

This project is licensed under the **CC0-1.0** license. You are free to use, modify, and distribute this software without restriction.
