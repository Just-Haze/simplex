package net.hazedf.optimizer.mixin;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRenderDispatcher.class)
public class EntityCullingMixin {

    @Inject(method = "render", at = @At("HEAD"), cancellable = true, require = 0)
    public <E extends Entity> void onRender(E entity, double x, double y, double z, float tickDelta,
            MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, CallbackInfo ci) {
        // Simple distance culling
        // If entity is further than 100 blocks (squared 10000) and not a player, skip.
        // This is "aggressive" culling.

        if (!net.hazedf.optimizer.config.OptimizerConfig.getInstance().enableEntityCulling) {
            return;
        }

        int distance = net.hazedf.optimizer.config.OptimizerConfig.getInstance().entityCullingDistance;
        if (!entity.isPlayer()
                && entity.squaredDistanceTo(net.minecraft.client.MinecraftClient.getInstance().cameraEntity) > distance
                        * distance) {
            ci.cancel();
        }
    }
}
