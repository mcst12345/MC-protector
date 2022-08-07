package miku.protector.core.Transformer;

import net.minecraft.launchwrapper.IClassTransformer;
import org.spongepowered.asm.mixin.Mixins;

public class StemWeaponTransformer implements IClassTransformer {
    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass) {
        if ("net.mcreator.stemweapon.procedure.ProcedureGoodbyeworldRedstoneOn".equals(name)) {

            Mixins.addConfiguration("mixins.stemweapon.json");
            Util.rerunMixin();

        }
        return basicClass;
    }
}
