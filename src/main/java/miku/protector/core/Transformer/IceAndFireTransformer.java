package miku.protector.core.Transformer;

import net.minecraft.launchwrapper.IClassTransformer;
import org.spongepowered.asm.mixin.Mixins;

public class IceAndFireTransformer implements IClassTransformer {

    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass) {
        if ("com.github.alexthe666.iceandfire.entity.EntityGorgon".equals(name)) {
            Mixins.addConfiguration("mixins.ice_and_fire.json");
            CoreUtil.rerunMixin();
        }
        return basicClass;
    }
}
