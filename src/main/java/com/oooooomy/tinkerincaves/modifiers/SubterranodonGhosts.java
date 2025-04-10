package com.oooooomy.tinkerincaves.modifiers;

import com.oooooomy.tinkerincaves.AlexsCavesEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.EntityHitResult;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.combat.MeleeHitModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.ranged.ProjectileHitModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;
import slimeknights.tconstruct.library.tools.nbt.ModifierNBT;

import javax.annotation.Nullable;

public class SubterranodonGhosts extends Modifier implements MeleeHitModifierHook, ProjectileHitModifierHook {
    public SubterranodonGhosts() {
    }

    @Override
    protected void registerHooks(ModuleHookMap.Builder builder) {
        super.registerHooks(builder);
        builder.addHook(this, ModifierHooks.MELEE_HIT);
        builder.addHook(this, ModifierHooks.PROJECTILE_HIT);
    }

    @Override
    public void afterMeleeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damageDealt) {
        int modifierLevel = modifier.getLevel();
        if (modifierLevel <= 0) {
            return;
        }
        LivingEntity target = context.getLivingTarget();
        LivingEntity attacker = context.getAttacker();
        if (target == null || attacker == null) {
            return;
        }
        float damage = getDamage(attacker);
        AlexsCavesEffects.effectSubterranodonGosts(attacker, target, damage, modifierLevel);
    }

    @Override
    public boolean onProjectileHitEntity(ModifierNBT modifiers, ModDataNBT persistentData, ModifierEntry modifier, Projectile projectile, EntityHitResult hit, @Nullable LivingEntity attacker, @Nullable LivingEntity target) {
        int modifierLevel = modifier.getLevel();
        if (modifierLevel <= 0) {
            return false;
        }
        if (target == null || attacker == null) {
            return false;
        }
        float damage = getDamage(attacker);
        AlexsCavesEffects.effectSubterranodonGosts(attacker, target, damage, modifierLevel);
        return false;
    }

    private float getDamage(LivingEntity attacker) {
        return 0.2f * (float) attacker.getAttributeValue(Attributes.ATTACK_DAMAGE);
    }
}
