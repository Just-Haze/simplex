package net.hazedf.optimizer.mixin;

import net.hazedf.optimizer.config.OptimizerConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public class GcMixin {

    @Inject(method = "setWorld", at = @At("HEAD"))
    public void onSetWorld(ClientWorld world, CallbackInfo ci) {
        // If world is null, we are unloading/leaving a world
        if (world == null && OptimizerConfig.getInstance().enableAggressiveGc) {
            System.gc();
        }
    }
}
