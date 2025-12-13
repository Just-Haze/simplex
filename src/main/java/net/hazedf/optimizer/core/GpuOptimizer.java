package net.hazedf.optimizer.core;

import org.lwjgl.opengl.GL11;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GpuOptimizer {
    private static final Logger LOGGER = LoggerFactory.getLogger("HazedfOptimizer");

    public static void optimize() {
        if (!net.hazedf.optimizer.config.OptimizerConfig.getInstance().enableGpuOptimizations) {
            return;
        }
        // Additional GPU specific optimizations can go here
    }

    public static void onRenderInit() {
        try {
            String vendor = GL11.glGetString(GL11.GL_VENDOR);
            String renderer = GL11.glGetString(GL11.GL_RENDERER);

            LOGGER.info("GPU Vendor: {}", vendor);
            LOGGER.info("GPU Renderer: {}", renderer);

            if (vendor != null) {
                String lowerVendor = vendor.toLowerCase();
                if (lowerVendor.contains("nvidia")) {
                    LOGGER.info(
                            "NVIDIA GPU Detected. Optimization: Ensure Threaded Optimization is ON in Control Panel.");
                } else if (lowerVendor.contains("amd") || lowerVendor.contains("ati")) {
                    LOGGER.info("AMD GPU Detected. Optimization: Ensure Smart Access Memory is ON if available.");
                } else if (lowerVendor.contains("intel")) {
                    LOGGER.info("Intel GPU Detected. Optimization: Lower render distance if experiencing lag.");
                }
            }
        } catch (Exception e) {
            LOGGER.error("Failed to detect GPU info", e);
        }
    }
}
