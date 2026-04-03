package net.hazedf.optimizer.mixin;

import net.hazedf.optimizer.config.OptimizerConfig;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(ServerWorld.class)
public class EntityTickMixin {

    @Inject(method = "tickEntity", at = @At("HEAD"), cancellable = true)
    public void onTickEntity(Entity entity, CallbackInfo ci) {
        if (OptimizerConfig.getInstance().enableLazyEntityTicking && entity instanceof MobEntity) {
            ServerWorld world = (ServerWorld) (Object) this;
            OptimizerConfig config = OptimizerConfig.getInstance();

            // Check if entity is far from any player
            boolean isFar = true;
            List<ServerPlayerEntity> players = world.getPlayers();
            double distanceSq = (double) config.lazyTickDistance * (double) config.lazyTickDistance;
            for (ServerPlayerEntity player : players) {
                if (entity.squaredDistanceTo(player) < distanceSq) {
                    isFar = false;
                    break;
                }
            }

            if (isFar) {
                if (world.getTime() % config.lazyTickInterval != 0) {
                    ci.cancel();
                }
            }
        }
    }
}
