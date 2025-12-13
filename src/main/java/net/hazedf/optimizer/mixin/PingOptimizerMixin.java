package net.hazedf.optimizer.mixin;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOption;
import net.hazedf.optimizer.config.OptimizerConfig;
import net.minecraft.network.ClientConnection;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientConnection.class)
public class PingOptimizerMixin {

    @Inject(method = "channelActive", at = @At("HEAD"))
    public void onChannelActive(ChannelHandlerContext context, CallbackInfo ci) {
        if (OptimizerConfig.getInstance().enablePingOptimizer) {
            try {
                context.channel().config().setOption(ChannelOption.TCP_NODELAY, true);
            } catch (Exception e) {
                // Ignore if not supported
            }
        }
    }
}
