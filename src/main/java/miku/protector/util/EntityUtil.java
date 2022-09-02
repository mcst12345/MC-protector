package miku.protector.util;

import com.anotherstar.common.entity.IEntityLoli;
import com.chaoswither.entity.EntityChaosWither;
import net.mcreator.thelegendofthebrave.MCreatorHerobrine;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.Loader;

public class EntityUtil {
    public static void KillLoli(Entity entity){
        ((IEntityLoli)entity).setDispersal(true);
    }

    public static boolean isDangerous(Entity entity){
        if(Loader.isModLoaded("chaoswither")){
            if(entity instanceof EntityChaosWither)return true;
        }
        if(Loader.isModLoaded("thelegendofthebrave")){
            if(entity instanceof MCreatorHerobrine.EntityCustom)return true;
        }
        if(Loader.isModLoaded("lolipickaxe")){
            if(entity instanceof IEntityLoli) {
                KillLoli(entity);
                return true;
            }
        }
        return false;
    }
}
