package net.hazedf.optimizer.mixin;

import net.hazedf.optimizer.config.OptimizerConfig;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(ExperienceOrbEntity.class)
public abstract class ExperienceOrbMixin extends Entity {

    public ExperienceOrbMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Shadow
    protected abstract void merge(ExperienceOrbEntity other);

    @Inject(method = "tick", at = @At("HEAD"))
    public void onTick(CallbackInfo ci) {
        if (OptimizerConfig.getInstance().enableFastXp && !this.getWorld().isClient && this.age % 5 == 0) {
            List<ExperienceOrbEntity> orbs = this.getWorld().getEntitiesByClass(ExperienceOrbEntity.class,
                    this.getBoundingBox().expand(2.0), e -> e != (Object) this);
            for (ExperienceOrbEntity orb : orbs) {
                if (orb.isAlive()) {
                    this.merge(orb);
                    if (!orb.isAlive()) {
                        // Merged successfully
                    }
                }
            }
        }
    }
}
