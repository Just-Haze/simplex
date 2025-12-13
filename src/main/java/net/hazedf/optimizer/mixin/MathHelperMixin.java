package net.hazedf.optimizer.mixin;

import net.hazedf.optimizer.util.FastMath;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(MathHelper.class)
public class MathHelperMixin {

    /**
     * @author Hazedf
     * @reason Fast Math Optimization
     */
    @Overwrite
    public static float sin(float f) {
        if (net.hazedf.optimizer.config.OptimizerConfig.getInstance().enableFastMath) {
            return FastMath.sin(f);
        }
        return (float) Math.sin(f);
    }

    /**
     * @author Hazedf
     * @reason Fast Math Optimization
     */
    @Overwrite
    public static float cos(float f) {
        if (net.hazedf.optimizer.config.OptimizerConfig.getInstance().enableFastMath) {
            return FastMath.cos(f);
        }
        return (float) Math.cos(f);
    }
}
