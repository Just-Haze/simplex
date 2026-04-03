package net.hazedf.optimizer.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;

public class OptimizerConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger("OptimizerConfig");
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation()
            .create();
    private static final File CONFIG_FILE = FabricLoader.getInstance().getConfigDir().resolve("simplex.json")
            .toFile();
    private static final File LEGACY_CONFIG_FILE = FabricLoader.getInstance().getConfigDir().resolve("optimizer-mod.json")
            .toFile();

    private static OptimizerConfig instance;

    @Expose
    public boolean enableEntityCulling = true;
    @Expose
    public int entityCullingDistance = 72;
    @Expose
    public boolean enableFastMath = false;
    @Expose
    public boolean enableGpuOptimizations = true;
    @Expose
    public boolean showProfiler = false;

    @Expose
    public String performanceProfile = "balanced";

    // Rendering
    @Expose
    public boolean enableParticleCulling = true;
    @Expose
    public int particleCullingDistance = 32;
    @Expose
    public boolean enableAdaptiveCulling = true;
    @Expose
    public int adaptiveTargetFps = 90;
    @Expose
    public int adaptiveMinCullingDistance = 40;
    @Expose
    public int adaptiveMaxCullingDistance = 112;
    @Expose
    public int adaptiveStep = 4;

    // Logic
    @Expose
    public boolean enableLazyEntityTicking = true;
    @Expose
    public int lazyTickInterval = 3;
    @Expose
    public int lazyTickDistance = 40;
    @Expose
    public boolean enableFastXp = true;
    @Expose
    public boolean enableDistantAiOptimization = true;
    @Expose
    public int distantAiInterval = 3;
    @Expose
    public int distantAiDistance = 64;

    // Network
    @Expose
    public boolean enablePingOptimizer = true;
    @Expose
    public boolean enableLowLatency = false;
    @Expose
    public int lowLatencyFrameInterval = 2;

    // Memory
    @Expose
    public boolean enableAggressiveGc = false;

    public static OptimizerConfig getInstance() {
        if (instance == null) {
            load();
        }
        return instance;
    }

    public static void load() {
        if (CONFIG_FILE.exists()) {
            try (FileReader reader = new FileReader(CONFIG_FILE)) {
                instance = GSON.fromJson(reader, OptimizerConfig.class);
            } catch (IOException e) {
                LOGGER.error("Failed to load config", e);
                instance = new OptimizerConfig();
            }
        } else if (LEGACY_CONFIG_FILE.exists()) {
            try (FileReader reader = new FileReader(LEGACY_CONFIG_FILE)) {
                instance = GSON.fromJson(reader, OptimizerConfig.class);
                save();
                Files.deleteIfExists(LEGACY_CONFIG_FILE.toPath());
                LOGGER.info("Migrated legacy optimizer-mod.json to simplex.json");
            } catch (IOException e) {
                LOGGER.error("Failed to migrate legacy config", e);
                instance = new OptimizerConfig();
            }
        } else {
            instance = new OptimizerConfig();
            save();
        }

        if (instance == null) {
            instance = new OptimizerConfig();
        }
        instance.validateAndNormalize();
    }

    public static void save() {
        try (FileWriter writer = new FileWriter(CONFIG_FILE)) {
            GSON.toJson(instance, writer);
        } catch (IOException e) {
            LOGGER.error("Failed to save config", e);
        }
    }

    public void cycleProfile() {
        if ("safe".equals(performanceProfile)) {
            performanceProfile = "balanced";
        } else if ("balanced".equals(performanceProfile)) {
            performanceProfile = "aggressive";
        } else {
            performanceProfile = "safe";
        }
        applyProfileDefaults();
    }

    public String profileLabel() {
        return switch (performanceProfile) {
            case "safe" -> "Safe";
            case "aggressive" -> "Aggressive";
            default -> "Balanced";
        };
    }

    private void applyProfileDefaults() {
        if ("safe".equals(performanceProfile)) {
            enableFastMath = false;
            enableLazyEntityTicking = true;
            lazyTickInterval = 2;
            lazyTickDistance = 48;
            enableDistantAiOptimization = true;
            distantAiInterval = 2;
            distantAiDistance = 72;
            enableLowLatency = false;
            lowLatencyFrameInterval = 2;
            enableAdaptiveCulling = true;
            adaptiveTargetFps = 75;
            adaptiveMinCullingDistance = 48;
            adaptiveMaxCullingDistance = 96;
            adaptiveStep = 4;
            entityCullingDistance = Math.min(Math.max(entityCullingDistance, adaptiveMinCullingDistance),
                    adaptiveMaxCullingDistance);
        } else if ("aggressive".equals(performanceProfile)) {
            enableFastMath = true;
            enableLazyEntityTicking = true;
            lazyTickInterval = 4;
            lazyTickDistance = 32;
            enableDistantAiOptimization = true;
            distantAiInterval = 4;
            distantAiDistance = 56;
            enableLowLatency = false;
            lowLatencyFrameInterval = 1;
            enableAdaptiveCulling = true;
            adaptiveTargetFps = 120;
            adaptiveMinCullingDistance = 32;
            adaptiveMaxCullingDistance = 128;
            adaptiveStep = 6;
            entityCullingDistance = Math.min(Math.max(entityCullingDistance, adaptiveMinCullingDistance),
                    adaptiveMaxCullingDistance);
        } else {
            performanceProfile = "balanced";
            enableFastMath = false;
            enableLazyEntityTicking = true;
            lazyTickInterval = 3;
            lazyTickDistance = 40;
            enableDistantAiOptimization = true;
            distantAiInterval = 3;
            distantAiDistance = 64;
            enableLowLatency = false;
            lowLatencyFrameInterval = 2;
            enableAdaptiveCulling = true;
            adaptiveTargetFps = 90;
            adaptiveMinCullingDistance = 40;
            adaptiveMaxCullingDistance = 112;
            adaptiveStep = 4;
            entityCullingDistance = Math.min(Math.max(entityCullingDistance, adaptiveMinCullingDistance),
                    adaptiveMaxCullingDistance);
        }
    }

    private void validateAndNormalize() {
        if (!"safe".equals(performanceProfile) && !"balanced".equals(performanceProfile)
                && !"aggressive".equals(performanceProfile)) {
            performanceProfile = "balanced";
        }

        entityCullingDistance = clamp(entityCullingDistance, 16, 256);
        particleCullingDistance = clamp(particleCullingDistance, 8, 128);
        lazyTickInterval = clamp(lazyTickInterval, 2, 8);
        lazyTickDistance = clamp(lazyTickDistance, 24, 128);
        distantAiInterval = clamp(distantAiInterval, 2, 8);
        distantAiDistance = clamp(distantAiDistance, 32, 192);
        lowLatencyFrameInterval = clamp(lowLatencyFrameInterval, 1, 8);
        adaptiveTargetFps = clamp(adaptiveTargetFps, 45, 240);
        adaptiveMinCullingDistance = clamp(adaptiveMinCullingDistance, 16, 192);
        adaptiveMaxCullingDistance = clamp(adaptiveMaxCullingDistance, adaptiveMinCullingDistance, 256);
        adaptiveStep = clamp(adaptiveStep, 1, 12);
        entityCullingDistance = clamp(entityCullingDistance, adaptiveMinCullingDistance, adaptiveMaxCullingDistance);
    }

    private static int clamp(int value, int min, int max) {
        return Math.max(min, Math.min(max, value));
    }
}
