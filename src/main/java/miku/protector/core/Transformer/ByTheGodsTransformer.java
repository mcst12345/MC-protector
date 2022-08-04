package miku.protector.core.Transformer;

import net.minecraft.launchwrapper.IClassTransformer;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.Mixins;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ByTheGodsTransformer implements IClassTransformer {
    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass) {
        if ("com.shinoow.btg.common.rituals.AzathothInvocationRitual".equals(name)) {

            Mixins.addConfiguration("mixins.by_the_gods.json");
            rerunMixin();

        }
        return basicClass;
    }

    public static void rerunMixin() {

        try {

            Class<?> klass = Class.forName("org.spongepowered.asm.mixin.transformer.Proxy");

            Field field = klass.getDeclaredField("transformer");



            field.setAccessible(true);

            Object transformer = field.get(null);



            klass = Class.forName("org.spongepowered.asm.mixin.transformer.MixinTransformer");

            field = klass.getDeclaredField("processor");



            field.setAccessible(true);

            Object processor = field.get(transformer);



            klass = Class.forName("org.spongepowered.asm.mixin.transformer.MixinProcessor");

            Method method = klass.getDeclaredMethod("select", MixinEnvironment.class);



            method.setAccessible(true);

            method.invoke(processor, MixinEnvironment.getCurrentEnvironment());

        } catch (ClassNotFoundException | NoSuchMethodException | NoSuchFieldException | InvocationTargetException | IllegalAccessException e) {

            // no-op

        }

    }

}
