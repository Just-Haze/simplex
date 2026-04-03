package net.hazedf.optimizer.mixin;

import net.hazedf.optimizer.config.OptimizerConfig;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(net.minecraft.client.MinecraftClient.class)
public class LowLatencyMixin {

    private int frameCounter;

    @Inject(method = "render", at = @At("RETURN"))
    private void onRender(boolean tick, CallbackInfo ci) {
        OptimizerConfig config = OptimizerConfig.getInstance();
        if (config.enableLowLatency) {
            frameCounter++;
            if (frameCounter % config.lowLatencyFrameInterval != 0) {
                return;
            }
            // Force CPU-GPU synchronization to reduce input lag
            GL11.glFinish();
        }
    }
}
