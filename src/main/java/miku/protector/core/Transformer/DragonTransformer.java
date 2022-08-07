package miku.protector.core.Transformer;

import net.minecraft.launchwrapper.IClassTransformer;
import org.spongepowered.asm.mixin.Mixins;

public class DragonTransformer implements IClassTransformer {
    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass) {
        if ("com.brandon3055.draconicevolution.lib.ExplosionHelper".equals(name)) {

            Mixins.addConfiguration("mixins.dragon.json");
            Util.rerunMixin();

        }
        return basicClass;
    }
}
