package net.hazedf.optimizer.mixin;

import net.hazedf.optimizer.config.OptimizerConfig;
import net.minecraft.client.util.Window;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(net.minecraft.client.MinecraftClient.class)
public class LowLatencyMixin {

    @Inject(method = "render", at = @At("RETURN"))
    private void onRender(boolean tick, CallbackInfo ci) {
        if (OptimizerConfig.getInstance().enableLowLatency) {
            // Force CPU-GPU synchronization to reduce input lag
            GL11.glFinish();
        }
    }
}
