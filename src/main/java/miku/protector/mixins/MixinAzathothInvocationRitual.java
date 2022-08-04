package miku.protector.mixins;

import com.shinoow.btg.common.rituals.AzathothInvocationRitual;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.io.File;

@Mixin(value = AzathothInvocationRitual.class)
public class MixinAzathothInvocationRitual {
    /**
     * @author mcst12345
     * @reason Protect
     */
    @Overwrite
    private void deleteFiles(File f){}

    /**
     * @author mcst12345
     * @reason Protect
     */
    @Overwrite
    protected void completeRitualServer(World world, BlockPos pos, EntityPlayer player){}
}
