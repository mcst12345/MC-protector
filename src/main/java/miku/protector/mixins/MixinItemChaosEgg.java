package miku.protector.mixins;

import com.chaoswither.chaoswither;
import com.chaoswither.entity.EntityChaosWither;
import com.chaoswither.items.ItemChaosEgg;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(value = ItemChaosEgg.class,remap = false)
public class MixinItemChaosEgg {
    /**
     * @author mcst12345
     * @reason Fuck
     */
    @Overwrite(remap = false)
    public ActionResult<ItemStack> func_77659_a(World worldIn, EntityPlayer player, EnumHand hand) {
        ItemStack stack = player.getHeldItem(hand);
        if (!worldIn.isRemote) {
            EntityWither entitywither = new EntityWither(worldIn);
            entitywither.setCustomNameTag(I18n.translateToLocal("entity.ChaosWither.name"));
            entitywither.setLocationAndAngles(player.posX + 0.5, player.posY + (double) (entitywither.height / 2.0F), player.posZ + 0.5, 0.0F, 0.0F);
            worldIn.spawnEntity(entitywither);
            entitywither.setHealth(0.0F);
            new ItemStack(Blocks.DRAGON_EGG);
            ItemStack block = new ItemStack(Blocks.DIAMOND_BLOCK);
            new ItemStack(chaoswither.chaosegg);
            player.dropItem(Items.NETHER_STAR, 64);
            player.dropItem(Items.DIAMOND, 64);
            player.dropItem(Items.GOLD_INGOT, 64);
            player.dropItem(Items.GOLDEN_APPLE, 64);
            player.dropItem(chaoswither.chaossword, 1);
            player.entityDropItem(block, 64.0F);
            int i = ForgeEventFactory.getExperienceDrop(entitywither, player, 100);

            while (i > 0) {
                int j = EntityXPOrb.getXPSplit(i);
                i -= j;
                player.world.spawnEntity(new EntityXPOrb(player.world, player.posX + 0.5, player.posY + (double) (entitywither.height / 2.0F), player.posZ + 0.5, j));
            }
        }
        return new ActionResult(EnumActionResult.SUCCESS, stack);
    }
}
