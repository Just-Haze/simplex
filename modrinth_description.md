# 🚀 Simplex

**A Minecraft optimization mod focused on rendering and logic improvements.**

**Simplex** is a performance enhancement mod for Minecraft Fabric. It aims to improve game performance by targeting specific areas of the game engine, such as entity rendering and ticking logic.

## ✨ Key Features

### 🎮 Rendering Optimizations

- **Entity Culling**: Stops rendering entities that are obscured or too far away to be seen. This can help improve performance in areas with many entities.
  - _Configurable Distance_: You can set the distance at which entities stop rendering (Default: 64 blocks).
- **Particle Culling**: Limit the rendering of particles based on distance.
  - _Configurable Distance_: Default is 32 blocks.
- **Fast Math**: Utilizes optimized math implementation for specific game calculations.

### 🧠 Logic & Tick Optimizations

- **Lazy Entity Ticking**: Reduces the frequency of logic updates for entities that are far away.
- **Distant AI Optimization**: Simplifies mob AI calculations for entities that are distant from the player.
- **Fast XP**: Optimizes the handling of experience orbs.

### 🌐 Network & Latency

- **Ping Optimizer**: Adjusts network settings which may improve connection stability for some users.
- **Low Latency Mode**: An optional mode (disabled by default) that prioritizes input processing.

### 🧹 Memory Management

- **Explicit Garbage Collection**: An optional feature that triggers garbage collection when leaving a world.
  - _Note_: This may cause a momentary freeze while memory is being freed.

## ⚙️ Configuration

The mod offers configuration options to toggle features and adjust distances.
The config file is located at `.minecraft/config/simplex.json`.

```json
{
  "enableEntityCulling": true,
  "entityCullingDistance": 64,
  "enableFastMath": true,
  "enableGpuOptimizations": true,
  "enableParticleCulling": true,
  "particleCullingDistance": 32,
  "enableLazyEntityTicking": true,
  "enableFastXp": true,
  "enableDistantAiOptimization": true,
  "enablePingOptimizer": true,
  "enableLowLatency": false,
  "enableAggressiveGc": false
}
```

## 📥 Installation

1.  Mase sure **Fabric Loader** is installed.
2.  Download the **Simplex** `.jar` file.
3.  Place the file into your `.minecraft/mods` folder.
4.  Launch the game.

## ❓ FAQ

**Q: Is this compatible with other optimization mods?**
A: Simplex is designed to work alongside mods like Sodium and Lithium, targeting different areas of the engine.

**Q: Can I use this on a server?**
A: This is primarily a **client-side** mod.

**Q: Issues?**
A: Please report any bugs to our issue tracker.

---

_Simplex is a community project._
