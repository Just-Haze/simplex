package net.hazedf.optimizer.mixin;

import net.hazedf.optimizer.config.OptimizerConfig;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MobEntity.class)
public class MobEntityMixin {

    @Inject(method = "tickNewAi", at = @At("HEAD"), cancellable = true)
    public void onTickNewAi(CallbackInfo ci) {
        if (OptimizerConfig.getInstance().enableDistantAiOptimization) {
            MobEntity entity = (MobEntity) (Object) this;
            PlayerEntity player = entity.getWorld().getClosestPlayer(entity, 128);

            if (player == null || entity.squaredDistanceTo(player) > 4096) { // 64 blocks
                ci.cancel();
            }
        }
    }
}
