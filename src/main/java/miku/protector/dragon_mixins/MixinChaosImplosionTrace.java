package miku.protector.dragon_mixins;

import com.brandon3055.draconicevolution.entity.ProcessChaosImplosion;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(value = ProcessChaosImplosion.ChaosImplosionTrace.class,remap = false)
public class MixinChaosImplosionTrace {
    /**
     * @author mcst12345
     * @reason Nope
     */
    @Overwrite
    public void updateProcess(){}
}
