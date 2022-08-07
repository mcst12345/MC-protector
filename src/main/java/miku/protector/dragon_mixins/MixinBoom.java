package miku.protector.dragon_mixins;

import com.brandon3055.draconicevolution.lib.ExplosionHelper;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.Chunk;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;

@Mixin(value = ExplosionHelper.class,remap = false)
public class MixinBoom {
    /**
     * @author mcst12345
     * @reason Nope
     */
    @Overwrite
    public void setBlocksForRemoval(LinkedList<HashSet<Integer>> list){}

    /**
     * @author mcst12345
     * @reason Nope
     */
    @Overwrite
    public void addBlocksForUpdate(Collection<Integer> blocksToUpdate){}

    /**
     * @author mcst12345
     * @reason Nope
     */
    @Overwrite
    private void removeBlock(BlockPos pos){}

    /**
     * @author mcst12345
     * @reason Nope
     */
    @Overwrite
    public void setChunkModified(BlockPos blockPos){}

    /**
     * @author mcst12345
     * @reason Nope
     */
    @Overwrite
    public void setChunkModified(Chunk chunk){}

    /**
     * @author mcst12345
     * @reason Nope
     */
    @Overwrite
    private void setRecalcPrecipitationHeightMap(BlockPos pos){}

    /**
     * @author mcst12345
     * @reason Nope
     */
    @Overwrite
    private void fireBlockBreak(BlockPos pos, IBlockState oldState){}

    /**
     * @author mcst12345
     * @reason Nope
     */
    @Overwrite
    private void removeTileEntity(BlockPos pos){}

    /**
     * @author mcst12345
     * @reason Nope
     */
    @Overwrite
    public void finish(){}

    /**
     * @author mcst12345
     * @reason Nope
     */
    @Overwrite
    public boolean isAirBlock(BlockPos pos){
        return true;
    }
}
