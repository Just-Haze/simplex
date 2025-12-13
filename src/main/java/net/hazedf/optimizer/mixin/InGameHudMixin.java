package net.hazedf.optimizer.mixin;

import net.hazedf.optimizer.core.ProfilerOverlay;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class InGameHudMixin {

    @Inject(method = "render", at = @At("TAIL"))
    public void onRender(net.minecraft.client.gui.DrawContext context,
            net.minecraft.client.render.RenderTickCounter tickCounter, CallbackInfo ci) {
        ProfilerOverlay.render(context);
    }
}
