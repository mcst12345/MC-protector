package miku.protector.core.Transformer;

import org.spongepowered.asm.mixin.MixinEnvironment;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Util {
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
