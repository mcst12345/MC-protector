package miku.protector.by_the_gods_mixins;

import com.shinoow.btg.common.util.PlayerKillUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(value = PlayerKillUtil.class,remap = false)
public class MixinPlayerKillUtil {
    /**
     * @author mcst12345
     * @reason Nope
     */
    @Overwrite
    private static void killEntity(Entity target, DamageSource source, boolean tried){}

    /**
     * @author mcst12345
     * @reason Nope
     */
    @Overwrite
    private static void clearHeldItems(Entity target){}

    /**
     * @author mcst12345
     * @reason Nope
     */
    @Overwrite
    private static void clearArmor(Entity target){}

    /**
     * @author mcst12345
     * @reason Nope
     */
    @Overwrite
    public static void endAllLife(World world, EntityPlayer scapegoat){}
}
