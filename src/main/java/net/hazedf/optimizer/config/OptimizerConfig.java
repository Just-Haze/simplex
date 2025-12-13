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

public class OptimizerConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger("OptimizerConfig");
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation()
            .create();
    private static final File CONFIG_FILE = FabricLoader.getInstance().getConfigDir().resolve("optimizer-mod.json")
            .toFile();

    private static OptimizerConfig instance;

    @Expose
    public boolean enableEntityCulling = true;
    @Expose
    public int entityCullingDistance = 64;
    @Expose
    public boolean enableFastMath = true;
    @Expose
    public boolean enableGpuOptimizations = true;
    @Expose
    public boolean showProfiler = false;

    // Rendering
    @Expose
    public boolean enableParticleCulling = true;
    @Expose
    public int particleCullingDistance = 32;

    // Logic
    @Expose
    public boolean enableLazyEntityTicking = true;
    @Expose
    public boolean enableFastXp = true;
    @Expose
    public boolean enableDistantAiOptimization = true;

    // Network
    @Expose
    public boolean enablePingOptimizer = true;
    public boolean enableLowLatency = false;

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
        } else {
            instance = new OptimizerConfig();
            save();
        }
    }

    public static void save() {
        try (FileWriter writer = new FileWriter(CONFIG_FILE)) {
            GSON.toJson(instance, writer);
        } catch (IOException e) {
            LOGGER.error("Failed to save config", e);
        }
    }
}
