package net.hazedf.optimizer.core;

import net.hazedf.optimizer.config.OptimizerConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.util.math.MatrixStack;

import net.minecraft.text.Text;

public class ProfilerOverlay {
    private static int fps;
    private static long lastTime;
    private static int frames;

    public static void render(net.minecraft.client.gui.DrawContext context) {
        if (!OptimizerConfig.getInstance().showProfiler) {
            return;
        }

        try {
            MinecraftClient client = MinecraftClient.getInstance();
            TextRenderer textRenderer = client.textRenderer;

            long time = System.currentTimeMillis();
            if (time - lastTime > 1000) {
                fps = frames;
                frames = 0;
                lastTime = time;
            }
            frames++;

            String fpsText = "FPS: " + fps;
            context.drawText(textRenderer, Text.literal(fpsText), 10, 10, 0xFFFFFF, true);
        } catch (Exception e) {
            // Prevent crash if something goes wrong
        }
    }
}
