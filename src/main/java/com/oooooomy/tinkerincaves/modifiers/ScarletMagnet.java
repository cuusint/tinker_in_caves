package com.oooooomy.tinkerincaves.modifiers;

import com.oooooomy.tinkerincaves.AlexsCavesEffects;
import com.oooooomy.tinkerincaves.registers.TinkerInCavesModifiers;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.EntityHitResult;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.build.ConditionalStatModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.combat.MeleeHitModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.ranged.ProjectileHitModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;
import slimeknights.tconstruct.library.tools.nbt.ModifierNBT;
import slimeknights.tconstruct.library.tools.stat.FloatToolStat;
import slimeknights.tconstruct.library.tools.stat.ToolStats;

import javax.annotation.Nullable;

public class ScarletMagnet extends Modifier implements ConditionalStatModifierHook, MeleeHitModifierHook, ProjectileHitModifierHook {
    public ScarletMagnet() {
    }

    @Override
    protected void registerHooks(ModuleHookMap.Builder builder) {
        super.registerHooks(builder);
        builder.addHook(this, ModifierHooks.CONDITIONAL_STAT);
        builder.addHook(this, ModifierHooks.MELEE_HIT);
        builder.addHook(this, ModifierHooks.PROJECTILE_HIT);
    }

    @Override
    public float modifyStat(IToolStackView tool, ModifierEntry modifier, LivingEntity living, FloatToolStat stat, float baseValue, float multiplier) {
        if (!hasAzureMagnet(tool)) {
            return baseValue;
        }
        float modifierLevel = modifier.getEffectiveLevel();
        if (stat == ToolStats.ATTACK_DAMAGE) {
            baseValue *= (1 + 0.1f * modifierLevel);
        } else if (stat == ToolStats.PROJECTILE_DAMAGE) {
            baseValue *= (1 + 0.1f * modifierLevel);
        }
        return baseValue;
    }

    @Override
    public void afterMeleeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damageDealt) {
        if (hasAzureMagnet(tool)) {
            return;
        }
        LivingEntity attacker = context.getAttacker();
        LivingEntity target = context.getLivingTarget();
        if (attacker == null || target == null) {
            return;
        }
        float modifierLevel = modifier.getEffectiveLevel();
        double knockBackDistance = getKnockBackDistance(attacker, modifierLevel);
        AlexsCavesEffects.effectScarletAndAzureMagnet(attacker, target, modifierLevel, true, knockBackDistance);
    }

    @Override
    public boolean onProjectileHitEntity(ModifierNBT modifiers, ModDataNBT persistentData, ModifierEntry modifier, Projectile projectile, EntityHitResult hit, @Nullable LivingEntity attacker, @Nullable LivingEntity target) {
        if (hasAzureMagnet(modifiers)) {
            return false;
        }
        if (target == null || attacker == null) {
            return false;
        }
        float modifierLevel = modifier.getEffectiveLevel();
        double knockBackDistance = getKnockBackDistance(attacker, modifierLevel);
        AlexsCavesEffects.effectScarletAndAzureMagnet(attacker, target, modifierLevel, true, knockBackDistance);
        return false;
    }

    private boolean hasAzureMagnet(IToolStackView tool) {
        return tool.getModifierLevel(TinkerInCavesModifiers.azure_magnet.getId()) > 0;
    }

    private boolean hasAzureMagnet(ModifierNBT modifiers) {
        return modifiers.getLevel(TinkerInCavesModifiers.azure_magnet.getId()) > 0;
    }

    private double getKnockBackDistance(LivingEntity attacker, float modifierLevel) {
        return attacker.getAttributeValue(Attributes.ATTACK_KNOCKBACK) + 0.3d * modifierLevel;
    }
}
