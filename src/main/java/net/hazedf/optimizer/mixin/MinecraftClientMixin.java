package net.hazedf.optimizer.mixin;

import net.hazedf.optimizer.core.GpuOptimizer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.RunArgs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {

    @Inject(method = "<init>", at = @At("TAIL"))
    private void onInit(RunArgs args, CallbackInfo ci) {
        // RenderSystem should be ready here
        GpuOptimizer.onRenderInit();
    }
}
