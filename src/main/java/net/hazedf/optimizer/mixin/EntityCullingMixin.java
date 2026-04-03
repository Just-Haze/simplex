package net.hazedf.optimizer.mixin;

import net.hazedf.optimizer.config.OptimizerConfig;
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

    private int frames;
    private long lastUpdate;

    @Inject(method = "render", at = @At("HEAD"), cancellable = true, require = 0)
    public <E extends Entity> void onRender(E entity, double x, double y, double z, float tickDelta,
            MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, CallbackInfo ci) {
        OptimizerConfig config = OptimizerConfig.getInstance();
        if (!config.enableEntityCulling) {
            return;
        }

        if (config.enableAdaptiveCulling) {
            long now = System.currentTimeMillis();
            frames++;
            if (lastUpdate == 0L) {
                lastUpdate = now;
            }

            if (now - lastUpdate >= 1000L) {
                int fps = frames;
                frames = 0;
                lastUpdate = now;

                if (fps < config.adaptiveTargetFps - 10) {
                    config.entityCullingDistance = Math.max(config.adaptiveMinCullingDistance,
                            config.entityCullingDistance - config.adaptiveStep);
                } else if (fps > config.adaptiveTargetFps + 10) {
                    config.entityCullingDistance = Math.min(config.adaptiveMaxCullingDistance,
                            config.entityCullingDistance + config.adaptiveStep);
                }
            }
        }

        int distance = config.entityCullingDistance;
        if (!entity.isPlayer()
                && entity.squaredDistanceTo(net.minecraft.client.MinecraftClient.getInstance().cameraEntity) > distance
                        * distance) {
            ci.cancel();
        }
    }
}
