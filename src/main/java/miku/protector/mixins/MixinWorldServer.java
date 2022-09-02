package miku.protector.mixins;

import miku.protector.util.EntityUtil;
import net.minecraft.entity.Entity;
import net.minecraft.world.WorldServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = WorldServer.class)
public class MixinWorldServer {
    @Inject(at=@At("HEAD"),method = "spawnEntity", cancellable = true)
    public void spawnEntity(Entity entityIn, CallbackInfoReturnable<Boolean> cir) {
        if(EntityUtil.isDangerous(entityIn))cir.setReturnValue(false);
    }

    @Inject(at=@At("HEAD"),method = "canAddEntity", cancellable = true)
    private void canAddEntity(Entity entityIn, CallbackInfoReturnable<Boolean> cir){
        if(EntityUtil.isDangerous(entityIn))cir.setReturnValue(false);
    }

    @Inject(at=@At("HEAD"),method = "onEntityAdded", cancellable = true)
    public void onEntityAdded(Entity entityIn, CallbackInfo ci){
        if(EntityUtil.isDangerous(entityIn))ci.cancel();
    }
}
