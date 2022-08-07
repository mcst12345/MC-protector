package miku.protector.core.Transformer;

import net.minecraft.launchwrapper.IClassTransformer;
import org.spongepowered.asm.mixin.Mixins;

public class ByTheGodsTransformer implements IClassTransformer {
    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass) {
        if ("com.shinoow.btg.common.rituals.AzathothInvocationRitual".equals(name)) {

            Mixins.addConfiguration("mixins.by_the_gods.json");
            Util.rerunMixin();

        }
        return basicClass;
    }

}
