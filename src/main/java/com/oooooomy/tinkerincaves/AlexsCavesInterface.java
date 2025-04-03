package com.oooooomy.tinkerincaves;

import com.github.alexmodguy.alexscaves.AlexsCaves;
import com.github.alexmodguy.alexscaves.server.enchantment.ACEnchantmentRegistry;
import com.github.alexmodguy.alexscaves.server.entity.item.WaterBoltEntity;
import com.github.alexmodguy.alexscaves.server.message.UpdateEffectVisualityEntityMessage;
import com.github.alexmodguy.alexscaves.server.misc.ACSoundRegistry;
import com.github.alexmodguy.alexscaves.server.potion.ACEffectRegistry;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import static com.github.alexmodguy.alexscaves.server.item.SeaStaffItem.getClosestLookingAtEntityFor;

public class AlexsCavesInterface {
    public static void effectSeaStaff(Player player, InteractionHand interactionHand,int boltsCount ,float seekAmount,boolean bubble,boolean bouncing)    {
        Level level =  player.level();
        level.playSound((Player) null, player.getX(), player.getY(), player.getZ(), ACSoundRegistry.SEA_STAFF_CAST.get(), SoundSource.PLAYERS, 0.5F, (player.level().getRandom().nextFloat() * 0.45F + 0.75F));
        player.swing(interactionHand);
        if (!level.isClientSide) {
            double dist = 128;
            Entity closestValid = getClosestLookingAtEntityFor(level, player, dist);
            for(int i = 0; i < boltsCount; i++){
                float shootRot = i == 0 ? 0 : i == 1 ? -50 : 50;
                WaterBoltEntity bolt = new WaterBoltEntity(level, player);
                float rot = player.yHeadRot + (interactionHand == InteractionHand.MAIN_HAND ? 45 : -45);
                bolt.setPos(player.getX() - (double) (player.getBbWidth()) * 1.1F * (double) Mth.sin(rot * ((float) Math.PI / 180F)), player.getEyeY() - (double) 0.4F, player.getZ() + (double) (player.getBbWidth()) * 1.1F * (double) Mth.cos(rot * ((float) Math.PI / 180F)));
                bolt.shootFromRotation(player, player.getXRot(), player.getYRot() + shootRot, -20.0F, i > 0 ? 1F : 2F, 12F);
                if (bubble) {
                    bolt.setBubbling(player.getRandom().nextBoolean());
                }
                if (bouncing) {
                    bolt.ricochet = true;
                }
                bolt.seekAmount = 0.3F + seekAmount * 0.2F;
                if (closestValid != null) {
                    bolt.setArcingTowards(closestValid.getUUID());
                }
                level.addFreshEntity(bolt);
            }
        }
    }

    public static void effectPrimitiveClub(ItemStack stack, LivingEntity hurtEntity, LivingEntity player,float dazingProbability,int durationBase,int durationExtra,int dazingEdgeLevel)    {
        if (!hurtEntity.level().isClientSide) {
            SoundEvent soundEvent = ACSoundRegistry.PRIMITIVE_CLUB_MISS.get();
            if (hurtEntity.getRandom().nextFloat() < dazingProbability) {
                MobEffectInstance instance = new MobEffectInstance(ACEffectRegistry.STUNNED.get(), durationBase + hurtEntity.getRandom().nextInt(durationExtra), 0, false, false);
                if (hurtEntity.addEffect(instance)) {
                    AlexsCaves.sendMSGToAll(new UpdateEffectVisualityEntityMessage(hurtEntity.getId(), player.getId(), 3, instance.getDuration()));
                    soundEvent = ACSoundRegistry.PRIMITIVE_CLUB_HIT.get();
                    if (dazingEdgeLevel > 0) {
                        float f = dazingEdgeLevel + 1.2F;
                        AABB aabb = AABB.ofSize(hurtEntity.position(), f, f, f);
                        for (Entity entity : hurtEntity.level().getEntities(player, aabb, Entity::canBeHitByProjectile)) {
                            if (!entity.is(hurtEntity) && !entity.isAlliedTo(player) && entity.distanceTo(hurtEntity) <= f && entity instanceof LivingEntity inflict) {
                                MobEffectInstance instance2 = new MobEffectInstance(ACEffectRegistry.STUNNED.get(), durationBase + hurtEntity.getRandom().nextInt(durationExtra), 0, false, false);
                                inflict.hurt(inflict.level().damageSources().mobAttack(player), 1.0F);
                                if (inflict.addEffect(instance2)) {
                                    AlexsCaves.sendMSGToAll(new UpdateEffectVisualityEntityMessage(inflict.getId(), player.getId(), 3, instance2.getDuration()));
                                }
                            }
                        }
                    }
                }
            }
            player.level().playSound((Player) null, player.getX(), player.getY(), player.getZ(), soundEvent, player.getSoundSource(), 1.0F, 1.0F);
        }
    }
}
