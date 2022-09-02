package miku.protector.ice_and_fire_mixins;

import com.github.alexthe666.iceandfire.IceAndFire;
import com.github.alexthe666.iceandfire.core.ModSounds;
import com.github.alexthe666.iceandfire.entity.*;
import com.github.alexthe666.iceandfire.item.ItemGorgonHead;
import com.github.alexthe666.iceandfire.message.MessageStoneStatue;
import com.google.common.base.Predicates;
import net.ilexiconn.llibrary.server.entity.EntityPropertiesHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

@Mixin(value = ItemGorgonHead.class,remap = false)
public class MixinItemGorgonHead {
    /**
     * @author mcst12345
     * @reason Nope
     */
    @Overwrite
    public void func_77615_a(ItemStack stack, World worldIn, EntityLivingBase entity, int timeLeft) {
        double dist = 32.0;
        Vec3d vec3d = entity.getPositionEyes(1.0F);
        Vec3d vec3d1 = entity.getLook(1.0F);
        Vec3d vec3d2 = vec3d.add(vec3d1.x * dist, vec3d1.y * dist, vec3d1.z * dist);
        Entity pointedEntity = null;
        List<Entity> list = worldIn.getEntitiesInAABBexcluding(entity, entity.getEntityBoundingBox().expand(vec3d1.x * dist, vec3d1.y * dist, vec3d1.z * dist).grow(1.0, 1.0, 1.0), Predicates.and(EntitySelectors.NOT_SPECTATING, entity12 -> {
            boolean blindness = entity12 instanceof EntityLivingBase && ((EntityLivingBase) entity12).isPotionActive(MobEffects.BLINDNESS) || entity12 instanceof IBlacklistedFromStatues && !((IBlacklistedFromStatues) entity12).canBeTurnedToStone();
            return entity12 != null && entity12.canBeCollidedWith() && !blindness && (entity12 instanceof EntityPlayer || entity12 instanceof EntityLiving && EntityPropertiesHandler.INSTANCE.getProperties(entity12, StoneEntityProperties.class) != null && !EntityPropertiesHandler.INSTANCE.getProperties(entity12, StoneEntityProperties.class).isStone);
        }));
        double d2 = dist;

        for (Entity value : list) {
            AxisAlignedBB axisalignedbb = value.getEntityBoundingBox().grow(value.getCollisionBorderSize());
            RayTraceResult raytraceresult = axisalignedbb.calculateIntercept(vec3d, vec3d2);
            if (axisalignedbb.contains(vec3d)) {
                if (d2 >= 0.0) {
                    pointedEntity = value;
                    d2 = 0.0;
                }
            } else if (raytraceresult != null) {
                double d3 = vec3d.distanceTo(raytraceresult.hitVec);
                if (d3 < d2 || d2 == 0.0) {
                    if (value.getLowestRidingEntity() == entity.getLowestRidingEntity() && !entity.canRiderInteract()) {
                        if (d2 == 0.0) {
                            pointedEntity = value;
                        }
                    } else {
                        pointedEntity = value;
                        d2 = d3;
                    }
                }
            }
        }

        if ((pointedEntity instanceof EntityLiving || pointedEntity instanceof EntityPlayer)) {
            if (pointedEntity instanceof EntityPlayer) {
                pointedEntity.playSound(ModSounds.GORGON_TURN_STONE, 1.0F, 1.0F);
                ((EntityPlayer)pointedEntity).inventory.dropAllItems();
                pointedEntity.attackEntityFrom(IceAndFire.gorgon, 2.14748365E9F);
                EntityStoneStatue statue = new EntityStoneStatue(worldIn);
                statue.setPositionAndRotation(pointedEntity.posX, pointedEntity.posY, pointedEntity.posY, pointedEntity.rotationYaw, pointedEntity.rotationPitch);
                statue.smallArms = true;
                if (!worldIn.isRemote) {
                    worldIn.spawnEntity(statue);
                }
            } else {
                StoneEntityProperties properties = EntityPropertiesHandler.INSTANCE.getProperties(pointedEntity, StoneEntityProperties.class);
                if (properties != null) {
                    properties.isStone = true;
                }

                IceAndFire.NETWORK_WRAPPER.sendToServer(new MessageStoneStatue(pointedEntity.getEntityId(), true));
                if (pointedEntity instanceof EntityDragonBase) {
                    EntityDragonBase dragon = (EntityDragonBase)pointedEntity;
                    dragon.setFlying(false);
                    dragon.setHovering(false);
                }

                if (pointedEntity instanceof EntityHippogryph) {
                    EntityHippogryph dragon = (EntityHippogryph)pointedEntity;
                    dragon.setFlying(false);
                    dragon.setHovering(false);
                    dragon.airTarget = null;
                }

                if (pointedEntity instanceof IDropArmor) {
                    ((IDropArmor)pointedEntity).dropArmor();
                }
            }

            if (pointedEntity instanceof EntityGorgon) {
                entity.playSound(ModSounds.GORGON_PETRIFY, 1.0F, 1.0F);
            } else {
                entity.playSound(ModSounds.GORGON_TURN_STONE, 1.0F, 1.0F);
            }

            SoundEvent deathSound = null;
            Method deathSoundMethod = ReflectionHelper.findMethod(EntityLivingBase.class, "getDeathSound", "func_184615_bR", (Class<?>) null);

            try {
                deathSound = (SoundEvent)deathSoundMethod.invoke(pointedEntity, (Object[])null);
            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException var22) {
                var22.printStackTrace();
            }

            if (deathSound != null) {
                entity.playSound(deathSound, 1.0F, 1.0F);
            }

            if (!(entity instanceof EntityPlayer) || !((EntityPlayer)entity).isCreative()) {
                stack.shrink(1);
            }
        }

        stack.setItemDamage(0);
    }
}
