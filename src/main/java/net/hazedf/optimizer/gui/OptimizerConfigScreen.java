package net.hazedf.optimizer.gui;

import net.hazedf.optimizer.config.OptimizerConfig;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.text.Text;

public class OptimizerConfigScreen extends Screen {
    private final Screen parent;
    private final OptimizerConfig config;

    public OptimizerConfigScreen(Screen parent) {
        super(Text.literal("Optimizer Config"));
        this.parent = parent;
        this.config = OptimizerConfig.getInstance();
    }

    @Override
    protected void init() {
        int y = 40;
        int center = this.width / 2;

        this.addDrawableChild(
                ButtonWidget.builder(Text.literal("Profile: " + config.profileLabel()), button -> {
                    config.cycleProfile();
                    button.setMessage(Text.literal("Profile: " + config.profileLabel()));
                }).dimensions(center - 100, y, 200, 20).build());
        y += 24;

        // Entity Culling Toggle
        this.addDrawableChild(
                ButtonWidget.builder(Text.literal("Entity Culling: " + config.enableEntityCulling), button -> {
                    config.enableEntityCulling = !config.enableEntityCulling;
                    button.setMessage(Text.literal("Entity Culling: " + config.enableEntityCulling));
                }).dimensions(center - 155, y, 150, 20).build());

        // Entity Culling Distance Slider
        this.addDrawableChild(new SliderWidget(center + 5, y, 150, 20,
                Text.literal("Entity Dist: " + config.entityCullingDistance),
                config.entityCullingDistance / 512.0) {
            @Override
            protected void updateMessage() {
                this.setMessage(Text.literal("Entity Dist: " + config.entityCullingDistance));
            }

            @Override
            protected void applyValue() {
                config.entityCullingDistance = (int) (this.value * 512);
                if (config.entityCullingDistance < 1)
                    config.entityCullingDistance = 1;
            }
        });
        y += 24;

        this.addDrawableChild(
                ButtonWidget.builder(Text.literal("Adaptive Culling: " + config.enableAdaptiveCulling), button -> {
                    config.enableAdaptiveCulling = !config.enableAdaptiveCulling;
                    button.setMessage(Text.literal("Adaptive Culling: " + config.enableAdaptiveCulling));
                }).dimensions(center - 155, y, 150, 20).build());

        this.addDrawableChild(new SliderWidget(center + 5, y, 150, 20,
                Text.literal("Target FPS: " + config.adaptiveTargetFps),
                config.adaptiveTargetFps / 240.0) {
            @Override
            protected void updateMessage() {
                this.setMessage(Text.literal("Target FPS: " + config.adaptiveTargetFps));
            }

            @Override
            protected void applyValue() {
                config.adaptiveTargetFps = Math.max(45, (int) (this.value * 240));
            }
        });
        y += 24;

        // GPU Optimizations (Left)
        this.addDrawableChild(
                ButtonWidget.builder(Text.literal("GPU Opt: " + config.enableGpuOptimizations), button -> {
                    config.enableGpuOptimizations = !config.enableGpuOptimizations;
                    button.setMessage(Text.literal("GPU Opt: " + config.enableGpuOptimizations));
                }).dimensions(center - 155, y, 150, 20).build());
        y += 24;

        // Lazy Entity Tick (Left)
        this.addDrawableChild(
                ButtonWidget.builder(Text.literal("Lazy Tick: " + config.enableLazyEntityTicking), button -> {
                    config.enableLazyEntityTicking = !config.enableLazyEntityTicking;
                    button.setMessage(Text.literal("Lazy Tick: " + config.enableLazyEntityTicking));
                }).dimensions(center - 155, y, 150, 20).build());

        // Fast XP (Right)
        this.addDrawableChild(ButtonWidget.builder(Text.literal("Fast XP: " + config.enableFastXp), button -> {
            config.enableFastXp = !config.enableFastXp;
            button.setMessage(Text.literal("Fast XP: " + config.enableFastXp));
        }).dimensions(center + 5, y, 150, 20).build());
        y += 24;

        // Distant AI (Left)
        this.addDrawableChild(
                ButtonWidget.builder(Text.literal("Distant AI: " + config.enableDistantAiOptimization), button -> {
                    config.enableDistantAiOptimization = !config.enableDistantAiOptimization;
                    button.setMessage(Text.literal("Distant AI: " + config.enableDistantAiOptimization));
                }).dimensions(center - 155, y, 150, 20).build());

        // Aggressive GC (Right)
        this.addDrawableChild(
                ButtonWidget.builder(Text.literal("Aggressive GC: " + config.enableAggressiveGc), button -> {
                    config.enableAggressiveGc = !config.enableAggressiveGc;
                    button.setMessage(Text.literal("Aggressive GC: " + config.enableAggressiveGc));
                }).dimensions(center + 5, y, 150, 20).build());
        y += 24;

        // Ping Optimizer (Left)
        this.addDrawableChild(
                ButtonWidget.builder(Text.literal("Ping Opt: " + config.enablePingOptimizer), button -> {
                    config.enablePingOptimizer = !config.enablePingOptimizer;
                    button.setMessage(Text.literal("Ping Opt: " + config.enablePingOptimizer));
                }).dimensions(center - 155, y, 150, 20).build());

        // Low Latency (Right)
        this.addDrawableChild(
                ButtonWidget.builder(Text.literal("Low Latency: " + config.enableLowLatency), button -> {
                    config.enableLowLatency = !config.enableLowLatency;
                    button.setMessage(Text.literal("Low Latency: " + config.enableLowLatency));
                }).dimensions(center + 5, y, 150, 20).build());
        y += 24;

        // Profiler
        this.addDrawableChild(ButtonWidget.builder(Text.literal("Show Profiler: " + config.showProfiler), button -> {
            config.showProfiler = !config.showProfiler;
            button.setMessage(Text.literal("Show Profiler: " + config.showProfiler));
        }).dimensions(center - 100, y, 200, 20).build());
        y += 24;

        // Done Button
        this.addDrawableChild(ButtonWidget.builder(Text.literal("Done"), button -> {
            this.close();
        }).dimensions(center - 100, this.height - 30, 200, 20).build());
    }

    @Override
    public void close() {
        OptimizerConfig.save();
        if (this.client != null) {
            this.client.setScreen(this.parent);
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        context.fill(0, 0, this.width, this.height, 0x80000000);
        context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, 10, 0xFFFFFF);
        super.render(context, mouseX, mouseY, delta);
    }
}
