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

            // Check if entity is far from any player
            boolean isFar = true;
            List<ServerPlayerEntity> players = world.getPlayers();
            for (ServerPlayerEntity player : players) {
                if (entity.squaredDistanceTo(player) < 1024) { // 32 blocks
                    isFar = false;
                    break;
                }
            }

            if (isFar) {
                // Skip tick every other tick
                if (world.getTime() % 2 == 0) {
                    ci.cancel();
                }
            }
        }
    }
}
