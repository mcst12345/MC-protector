package miku.protector.ice_and_fire_mixins;

import com.github.alexthe666.iceandfire.IceAndFire;
import com.github.alexthe666.iceandfire.core.ModSounds;
import com.github.alexthe666.iceandfire.entity.*;
import com.github.alexthe666.iceandfire.message.MessageStoneStatue;
import net.ilexiconn.llibrary.server.animation.Animation;
import net.ilexiconn.llibrary.server.animation.AnimationHandler;
import net.ilexiconn.llibrary.server.entity.EntityPropertiesHandler;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import static com.github.alexthe666.iceandfire.entity.EntityGorgon.ANIMATION_SCARE;

@Mixin(value = EntityGorgon.class,remap = false)
public abstract class MixinEntityGorgon extends EntityMob {
    @Shadow private int playerStatueCooldown;

    @Shadow public abstract void forcePreyToLook(EntityLiving mob);

    @Shadow
    public static boolean isEntityLookingAt(EntityLivingBase looker, EntityLivingBase seen, double degree) {
        return false;
    }

    @Shadow
    public static boolean isBlindfolded(EntityLivingBase attackTarget) {
        return false;
    }

    @Shadow public abstract Animation getAnimation();

    @Shadow public abstract void setAnimation(Animation animation);

    @Shadow public abstract int getAnimationTick();

    public MixinEntityGorgon(World worldIn) {
        super(worldIn);
    }

    /**
     * @author mcst12345
     * @reason Fk
     */
    @Overwrite
    public void func_70636_d() {
        super.onLivingUpdate();
        if (this.playerStatueCooldown > 0) {
            --this.playerStatueCooldown;
        }

        boolean blindness;
        if (this.getAttackTarget() != null) {
            blindness = this.isPotionActive(MobEffects.BLINDNESS) || this.getAttackTarget().isPotionActive(MobEffects.BLINDNESS);
            this.getLookHelper().setLookPosition(this.getAttackTarget().posX, this.getAttackTarget().posY + (double)this.getAttackTarget().getEyeHeight(), this.getAttackTarget().posZ, (float)this.getHorizontalFaceSpeed(), (float)this.getVerticalFaceSpeed());
            if (!blindness && this.deathTime == 0 && this.getAttackTarget() instanceof EntityLiving && !(this.getAttackTarget() instanceof EntityPlayer)) {
                this.forcePreyToLook((EntityLiving)this.getAttackTarget());
            }
        }

        if (this.getAttackTarget() != null && isEntityLookingAt(this, this.getAttackTarget(), 0.4) && isEntityLookingAt(this.getAttackTarget(), this, 0.4) && !isBlindfolded(this.getAttackTarget())) {
            blindness = this.isPotionActive(MobEffects.BLINDNESS) || this.getAttackTarget().isPotionActive(MobEffects.BLINDNESS) || this.getAttackTarget() instanceof IBlacklistedFromStatues && !((IBlacklistedFromStatues)this.getAttackTarget()).canBeTurnedToStone();
            if (!blindness && this.deathTime == 0) {
                if (this.getAnimation() != ANIMATION_SCARE) {
                    this.playSound(ModSounds.GORGON_ATTACK, 1.0F, 1.0F);
                    this.setAnimation(ANIMATION_SCARE);
                }

                if (this.getAnimation() == ANIMATION_SCARE && this.getAnimationTick() > 10) {
                    if (this.getAttackTarget() instanceof EntityPlayer) {
                        if (!this.world.isRemote) {
                            ((EntityPlayer)this.getAttackTarget()).inventory.dropAllItems();
                            this.getAttackTarget().attackEntityFrom(IceAndFire.gorgon, 2.14748365E9F);
                            if (!this.getAttackTarget().isEntityAlive() && this.playerStatueCooldown == 0) {
                                EntityStoneStatue statue = new EntityStoneStatue(this.world);
                                statue.setPositionAndRotation(this.getAttackTarget().posX, this.getAttackTarget().posY, this.getAttackTarget().posZ, this.getAttackTarget().rotationYaw, this.getAttackTarget().rotationPitch);
                                statue.smallArms = true;
                                if (!this.world.isRemote) {
                                    this.world.spawnEntity(statue);
                                }

                                statue.prevRotationYaw = this.getAttackTarget().rotationYaw;
                                statue.rotationYaw = this.getAttackTarget().rotationYaw;
                                statue.rotationYawHead = this.getAttackTarget().rotationYaw;
                                statue.renderYawOffset = this.getAttackTarget().rotationYaw;
                                statue.prevRenderYawOffset = this.getAttackTarget().rotationYaw;
                                this.playerStatueCooldown = 40;
                            }

                            this.setAttackTarget(null);
                        }
                    } else if (this.getAttackTarget() instanceof EntityLiving && !(this.getAttackTarget() instanceof IBlacklistedFromStatues) || this.getAttackTarget() instanceof IBlacklistedFromStatues && ((IBlacklistedFromStatues)this.getAttackTarget()).canBeTurnedToStone()) {
                        StoneEntityProperties properties = EntityPropertiesHandler.INSTANCE.getProperties(this.getAttackTarget(), StoneEntityProperties.class);
                        EntityLiving attackTarget = (EntityLiving)this.getAttackTarget();
                        if (properties != null || !properties.isStone) {
                            properties.isStone = true;
                            if (this.world.isRemote) {
                                IceAndFire.NETWORK_WRAPPER.sendToServer(new MessageStoneStatue(attackTarget.getEntityId(), true));
                            } else {
                                IceAndFire.NETWORK_WRAPPER.sendToAll(new MessageStoneStatue(attackTarget.getEntityId(), true));
                            }

                            this.playSound(ModSounds.GORGON_TURN_STONE, 1.0F, 1.0F);
                            this.setAttackTarget(null);
                        }

                        if (attackTarget instanceof EntityDragonBase) {
                            EntityDragonBase dragon = (EntityDragonBase)attackTarget;
                            dragon.setFlying(false);
                            dragon.setHovering(false);
                        }

                        if (attackTarget instanceof EntityHippogryph) {
                            EntityHippogryph dragon = (EntityHippogryph)attackTarget;
                            dragon.setFlying(false);
                            dragon.setHovering(false);
                            dragon.airTarget = null;
                        }
                    }
                }
            }
        }

        AnimationHandler.INSTANCE.updateAnimations(((EntityGorgon)(Object)this));
    }
}
