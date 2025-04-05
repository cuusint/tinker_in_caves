package com.oooooomy.tinkerincaves;

import com.github.alexmodguy.alexscaves.AlexsCaves;
import com.github.alexmodguy.alexscaves.client.particle.ACParticleRegistry;
import com.github.alexmodguy.alexscaves.server.entity.ACEntityRegistry;
import com.github.alexmodguy.alexscaves.server.entity.item.DinosaurSpiritEntity;
import com.github.alexmodguy.alexscaves.server.entity.item.SubmarineEntity;
import com.github.alexmodguy.alexscaves.server.entity.item.WaterBoltEntity;
import com.github.alexmodguy.alexscaves.server.entity.item.WaveEntity;
import com.github.alexmodguy.alexscaves.server.item.SeaStaffItem;
import com.github.alexmodguy.alexscaves.server.message.UpdateEffectVisualityEntityMessage;
import com.github.alexmodguy.alexscaves.server.misc.ACDamageTypes;
import com.github.alexmodguy.alexscaves.server.misc.ACSoundRegistry;
import com.github.alexmodguy.alexscaves.server.potion.ACEffectRegistry;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

public class AlexsCavesInterface {
    public static void effectSeaStaff(Player player, int boltsCount ,double seekDistance,float seekAmount,boolean bubble,boolean bouncing)    {
        Level level =  player.level();
        level.playSound((Player) null, player.getX(), player.getY(), player.getZ(), ACSoundRegistry.SEA_STAFF_CAST.get(), SoundSource.PLAYERS, 0.5F, (player.level().getRandom().nextFloat() * 0.45F + 0.75F));
        if (!level.isClientSide) {
            Entity closestValid = SeaStaffItem.getClosestLookingAtEntityFor(level, player, seekDistance);
            for(int i = 0; i < boltsCount; i++){
                float shootRot = i == 0 ? 0 : i == 1 ? -50 : 50;
                WaterBoltEntity bolt = new WaterBoltEntity(level, player);
                float rot = player.yHeadRot + 45;
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

    public static void effectPrimitiveClub(LivingEntity hurtEntity, LivingEntity player,float dazingProbability,int durationBase,int durationExtra,int dazingEdgeLevel)    {
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

    public static void effectInkBomb(Projectile projectile, EntityHitResult hitResult,boolean glowing)    {
        hitResult.getEntity().hurt(projectile.damageSources().thrown(projectile, projectile.getOwner()), 0F);
        if (hitResult.getEntity() instanceof SubmarineEntity submarine) {
            submarine.setLightsOn(false);
            if (submarine.getFirstPassenger() instanceof Player player) {
                player.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 100));
                if (glowing) {
                    player.addEffect(new MobEffectInstance(MobEffects.GLOWING, 300));
                }
                if (!player.isCreative()) {
                    player.removeEffect(MobEffects.NIGHT_VISION);
                    player.removeEffect(MobEffects.CONDUIT_POWER);
                }
            }
        }
        if (hitResult.getEntity() instanceof LivingEntity living) {
            living.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 100));
            if (!(living instanceof Player player && player.isCreative())) {
                living.removeEffect(MobEffects.NIGHT_VISION);
                living.removeEffect(MobEffects.CONDUIT_POWER);
            }
        }
        effectInkBomb(projectile, glowing);
    }

    public static void effectInkBomb(Projectile projectile, boolean glowing)    {
        if (!projectile.level().isClientSide) {
            projectile.level().broadcastEntityEvent(projectile, (byte) 3);
            projectile.discard();
            AreaEffectCloud areaeffectcloud = new AreaEffectCloud(projectile.level(), projectile.getX(), projectile.getY() + 0.2F, projectile.getZ());
            areaeffectcloud.setParticle(glowing ? ParticleTypes.GLOW_SQUID_INK : ParticleTypes.SQUID_INK);
            areaeffectcloud.setFixedColor(0);
            areaeffectcloud.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 100));
            if (glowing) {
                areaeffectcloud.addEffect(new MobEffectInstance(MobEffects.GLOWING, 300));
            }
            areaeffectcloud.setRadius(2F);
            areaeffectcloud.setDuration(60);
            areaeffectcloud.setRadiusPerTick(-areaeffectcloud.getRadius() / (float) areaeffectcloud.getDuration());
            projectile.level().addFreshEntity(areaeffectcloud);
        }
    }

    public static void effectOrtholance(Level level, LivingEntity livingEntity,int chargeTime,int flinging,boolean tsunami,boolean secondWave)    {
        float f = 0.1F * chargeTime + flinging * 0.1F;
        Vec3 vec3 = livingEntity.getDeltaMovement().add(livingEntity.getViewVector(1.0F).normalize().multiply(f, f * 0.15F, f));
        if (chargeTime >= 10 && !level.isClientSide) {
            level.playSound(null, livingEntity, ACSoundRegistry.ORTHOLANCE_WAVE.get(), SoundSource.NEUTRAL, 4.0F, 1.0F);
            int maxWaves = chargeTime / 5;
            if(tsunami){
                maxWaves = 5;
                Vec3 waveCenterPos = livingEntity.position().add(vec3);
                WaveEntity tsunamiWaveEntity = new WaveEntity(level, livingEntity);
                tsunamiWaveEntity.setPos(waveCenterPos.x, livingEntity.getY(), waveCenterPos.z);
                tsunamiWaveEntity.setLifespan(20);
                tsunamiWaveEntity.setWaveScale(5.0F);
                tsunamiWaveEntity.setWaitingTicks(2);
                tsunamiWaveEntity.setYRot(-(float) (Mth.atan2(vec3.x, vec3.z) * (double) (180F / (float) Math.PI)));
                level.addFreshEntity(tsunamiWaveEntity);
            }
            for (int wave = 0; wave < maxWaves; wave++) {
                float f1 = (float) wave / maxWaves;
                int lifespan = 3 + (int) ((1F - f1) * 3);
                Vec3 waveCenterPos = livingEntity.position().add(vec3.scale(f1 * 2));
                WaveEntity leftWaveEntity = new WaveEntity(level, livingEntity);
                leftWaveEntity.setPos(waveCenterPos.x, livingEntity.getY(), waveCenterPos.z);
                leftWaveEntity.setLifespan(lifespan);
                leftWaveEntity.setYRot(-(float) (Mth.atan2(vec3.x, vec3.z) * (double) (180F / (float) Math.PI)) + 60 - 15 * wave);
                level.addFreshEntity(leftWaveEntity);
                WaveEntity rightWaveEntity = new WaveEntity(level, livingEntity);
                rightWaveEntity.setPos(waveCenterPos.x, livingEntity.getY(), waveCenterPos.z);
                rightWaveEntity.setLifespan(lifespan);
                rightWaveEntity.setYRot(-(float) (Mth.atan2(vec3.x, vec3.z) * (double) (180F / (float) Math.PI)) - 60 + 15 * wave);
                level.addFreshEntity(rightWaveEntity);
            }
            if(secondWave){
                int maxSecondWaves = Math.max(1, maxWaves - 1);
                for (int wave = 0; wave < maxSecondWaves; wave++) {
                    float f1 = (float) wave / maxSecondWaves;
                    int lifespan = 3 + (int) ((1F - f1) * 3);
                    Vec3 waveCenterPos = livingEntity.position().add(vec3.scale(f1 * 2));
                    WaveEntity leftWaveEntity = new WaveEntity(level, livingEntity);
                    leftWaveEntity.setPos(waveCenterPos.x, livingEntity.getY(), waveCenterPos.z);
                    leftWaveEntity.setLifespan(lifespan);
                    leftWaveEntity.setYRot(-(float) (Mth.atan2(vec3.x, vec3.z) * (double) (180F / (float) Math.PI)) + 60 - 15 * wave);
                    leftWaveEntity.setWaitingTicks(8);
                    level.addFreshEntity(leftWaveEntity);
                    WaveEntity rightWaveEntity = new WaveEntity(level, livingEntity);
                    rightWaveEntity.setPos(waveCenterPos.x, livingEntity.getY(), waveCenterPos.z);
                    rightWaveEntity.setLifespan(lifespan);
                    rightWaveEntity.setYRot(-(float) (Mth.atan2(vec3.x, vec3.z) * (double) (180F / (float) Math.PI)) - 60 + 15 * wave);
                    rightWaveEntity.setWaitingTicks(8);
                    level.addFreshEntity(rightWaveEntity);
                }
            }
            AABB aabb = new AABB(livingEntity.position(), livingEntity.position().add(vec3.scale(maxWaves))).inflate(1);
            DamageSource source = livingEntity.damageSources().mobAttack(livingEntity);
            double d = 0;
            d += livingEntity.getAttributeValue(Attributes.ATTACK_DAMAGE);
            for (LivingEntity entity : level.getEntitiesOfClass(LivingEntity.class, aabb)) {
                if (!livingEntity.isAlliedTo(entity) && !livingEntity.equals(entity) && livingEntity.hasLineOfSight(entity)) {
                    entity.hurt(source, (float) d);
                    entity.stopRiding();
                }
            }
        }
        Vec3 deltaMovement = vec3.add(0, (livingEntity.onGround() ? 0.2F : 0) + (flinging * 0.1F), 0);
        livingEntity.addDeltaMovement(deltaMovement);
    }

    public static void effectOrtholance(LivingEntity player, LivingEntity target){
        Vec3 vec3 = player.getViewVector(1.0F);
        WaveEntity waveEntity = new WaveEntity(target.level(), player);
        waveEntity.setPos(player.getX(), target.getY(), player.getZ());
        waveEntity.setLifespan(5);
        waveEntity.setYRot(-(float) (Mth.atan2(vec3.x, vec3.z) * (double) (180F / (float) Math.PI)));
        player.level().addFreshEntity(waveEntity);
    }

    public static void effectTremorsaurusGhosts(LivingEntity attacker, LivingEntity target, int levelChompingSprite)    {
        target.setSecondsOnFire(2 + 2 * levelChompingSprite);
        DinosaurSpiritEntity dinosaurSpirit = ACEntityRegistry.DINOSAUR_SPIRIT.get().create(attacker.level());
        Vec3 between = attacker.position().add(target.position()).scale(0.5F);//todo fix position
        dinosaurSpirit.setPos(between.x, attacker.getY() + 1.0F, between.z);
        dinosaurSpirit.setDinosaurType(DinosaurSpiritEntity.DinosaurType.TREMORSAURUS);
        dinosaurSpirit.setPlayerUUID(attacker.getUUID());
        dinosaurSpirit.setEnchantmentLevel(levelChompingSprite);
        dinosaurSpirit.setAttackingEntityId(target.getId());
        dinosaurSpirit.lookAt(EntityAnchorArgument.Anchor.EYES, target.getEyePosition());
        dinosaurSpirit.setDelaySpawn(5);

        attacker.level().addFreshEntity(dinosaurSpirit);
    }

    public static void effectSubterranodonGosts(LivingEntity attacker,LivingEntity target,float damage,int modifierLevel){
        DamageSource damagesource = ACDamageTypes.causeSpiritDinosaurDamage(attacker.level().registryAccess(), attacker);
        target.setSecondsOnFire(5);
        if (target.hurt(damagesource, damage)) {
            if (target.getType() == EntityType.ENDERMAN) {
                return;
            }
            if (target instanceof LivingEntity) {
                EnchantmentHelper.doPostHurtEffects(target, attacker);
                EnchantmentHelper.doPostDamageEffects(attacker, target);
            }
            DinosaurSpiritEntity dinosaurSpirit = ACEntityRegistry.DINOSAUR_SPIRIT.get().create(attacker.level());
            dinosaurSpirit.setPos(target.getX(), target.getY() + target.getBbHeight(), target.getZ());
            dinosaurSpirit.setDinosaurType(DinosaurSpiritEntity.DinosaurType.SUBTERRANODON);
            dinosaurSpirit.setPlayerUUID(attacker.getUUID());
            dinosaurSpirit.setAttackingEntityId(target.getId());
            dinosaurSpirit.lookAt(EntityAnchorArgument.Anchor.EYES, attacker.getEyePosition());
            dinosaurSpirit.setEnchantmentLevel(modifierLevel);
            target.playSound(ACSoundRegistry.EXTINCTION_SPEAR_SUMMON.get(), 1.0F, 1.0F);
            attacker.level().addFreshEntity(dinosaurSpirit);
        }
    }

    public static void effectResistorSlam(LivingEntity living,int timeUsed,float range,int firstHitDamage,int restHitDamage,double firstKnockBackDistance,double restKnockBackDistance,int scarlet,int azure){
        boolean firstHit = timeUsed >= 10 && timeUsed <= 12;
        Level level = living.level();
        if(timeUsed == 10){
            living.playSound(ACSoundRegistry.RESITOR_SHIELD_SLAM.get());
        }
        if (timeUsed >= 10 && timeUsed % 5 == 0) {
            AlexsCaves.PROXY.playWorldSound(living, (byte) ((scarlet-azure)>0 ? 9 : 10));
            Vec3 particlesFrom = living.position().add(0, 0.2, 0);
            float particleMax = 2 + 2 * scarlet + 2 * azure + living.getRandom().nextInt(5);
            for (int particles = 0; particles < particleMax; particles++) {
                Vec3 vec3 = new Vec3((living.getRandom().nextFloat() - 0.5) * 0.3F, (living.getRandom().nextFloat() - 0.5) * 0.3F, range * 0.5F + range * 0.5F * living.getRandom().nextFloat()).yRot((float) ((particles / particleMax) * Math.PI * 2)).add(particlesFrom);
                level.addParticle(ACParticleRegistry.SCARLET_SHIELD_LIGHTNING.get(), vec3.x, vec3.y, vec3.z, particlesFrom.x, particlesFrom.y, particlesFrom.z);
                level.addParticle(ACParticleRegistry.AZURE_SHIELD_LIGHTNING.get(), particlesFrom.x, particlesFrom.y, particlesFrom.z, vec3.x, vec3.y, vec3.z);
            }
        }
        if (timeUsed >= 10 && timeUsed % 5 == 0) {
            AABB bashBox = living.getBoundingBox().inflate(range, 1, range);
            for (LivingEntity entity : living.level().getEntitiesOfClass(LivingEntity.class, bashBox)) {
                if (!living.isAlliedTo(entity) && !entity.equals(living) && entity.distanceTo(living) <= range) {
                    entity.hurt(living.damageSources().mobAttack(living), firstHit ? firstHitDamage : restHitDamage);
                    if ((scarlet-azure)>0) {
                        entity.knockback(firstHit ? firstKnockBackDistance : restKnockBackDistance, entity.getX() - living.getX(), entity.getZ() - living.getZ());
                    }
                    else{
                        entity.knockback(firstHit ? firstKnockBackDistance : restKnockBackDistance, living.getX() - living.getX(), living.getZ() - entity.getZ());
                    }
                }
            }
        }
    }
}
