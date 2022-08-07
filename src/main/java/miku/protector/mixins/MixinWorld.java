package miku.protector.mixins;

import com.anotherstar.common.entity.IEntityLoli;
import com.chaoswither.entity.EntityChaosWitherBase;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Loader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = World.class)
public class MixinWorld {
    @Inject(at=@At("HEAD"),method = "spawnEntity", cancellable = true)
    public void spawnEntity(Entity entityIn, CallbackInfoReturnable<Boolean> cir){
        System.out.println("Successfully fucked Minecraft");
        if(Loader.isModLoaded("lolipickaxe")){
            if(entityIn instanceof IEntityLoli) {
                ((IEntityLoli)entityIn).setDispersal(true);
                cir.setReturnValue(false);
            }
        }
        if(Loader.isModLoaded("chaoswither")){
            if(entityIn instanceof EntityChaosWitherBase)cir.setReturnValue(false);
        }
    }

    @Inject(at=@At("HEAD"),method = "onEntityAdded", cancellable = true)
    public void onEntityAdded(Entity entityIn, CallbackInfo ci){
        System.out.println("Successfully fucked Minecraft");
        if (Loader.isModLoaded("lolipickaxe")) {
            if (entityIn instanceof IEntityLoli) {
                ((IEntityLoli) entityIn).setDispersal(true);
                ci.cancel();
            }
        }
        if (Loader.isModLoaded("chaoswither")) {
            if (entityIn instanceof EntityChaosWitherBase) ci.cancel();
        }
    }
}
