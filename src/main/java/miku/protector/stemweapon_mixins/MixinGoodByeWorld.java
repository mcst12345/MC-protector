package miku.protector.stemweapon_mixins;

import net.mcreator.stemweapon.procedure.ProcedureGoodbyeworldRedstoneOn;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.util.HashMap;

@Mixin(value = ProcedureGoodbyeworldRedstoneOn.class,remap = false)
public class MixinGoodByeWorld {
    /**
     * @author mcst12345
     * @reason Nope
     */
    @Overwrite
    public static void executeProcedure(HashMap<String, Object> dependencies){}
}
