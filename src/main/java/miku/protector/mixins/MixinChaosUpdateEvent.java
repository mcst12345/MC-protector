package miku.protector.mixins;

import com.chaoswither.event.ChaosUpdateEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = ChaosUpdateEvent.class,remap = false)
public abstract class MixinChaosUpdateEvent {

    /**
     * @author mcst12345
     * @reason Protect
     */
    @Overwrite
    public static boolean isGod(EntityLivingBase entity) {
        return true;
    }

    @Inject(at=@At("HEAD"),method = "onEntityJoinWorld", cancellable = true)
    public void onEntityJoinWorld(EntityJoinWorldEvent event, CallbackInfo ci){
        ci.cancel();
    }

    @Inject(at=@At("HEAD"),method = "onTick(Lnet/minecraftforge/fml/common/gameevent/TickEvent$WorldTickEvent;)V", cancellable = true)
    public void onTick(TickEvent.WorldTickEvent event, CallbackInfo ci){
        ci.cancel();
    }

    @Inject(at=@At("HEAD"),method = "onServerTick", cancellable = true)
    public void onServerTick(TickEvent.ServerTickEvent event, CallbackInfo ci){
        ci.cancel();
    }

    @Inject(at=@At("HEAD"),method = "onWorldTickEvent", cancellable = true)
    public void onWorldTickEvent(TickEvent.WorldTickEvent event, CallbackInfo ci){
        ci.cancel();
    }

    @Inject(at=@At("HEAD"),method = "onLivingUpdate", cancellable = true)
    public void onLivingUpdate(LivingEvent.LivingUpdateEvent event, CallbackInfo ci){
        ci.cancel();
    }

    /**
     * @author mcst12345
     * @reason Nope
     */
    @Overwrite
    public static void setTimeStop(Minecraft mc, EntityLivingBase player){}
}
