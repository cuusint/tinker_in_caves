package com.oooooomy.tinkerincaves.modifiers;

import com.oooooomy.tinkerincaves.AlexsCavesEffects;
import com.oooooomy.tinkerincaves.registers.TinkerInCavesModifiers;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.armor.OnAttackedModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.build.ConditionalStatModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.context.EquipmentContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.stat.FloatToolStat;
import slimeknights.tconstruct.library.tools.stat.ToolStats;

public class ScarletMagnetArmor extends Modifier implements ConditionalStatModifierHook, OnAttackedModifierHook {
    public ScarletMagnetArmor() {
    }

    @Override
    protected void registerHooks(ModuleHookMap.Builder builder) {
        super.registerHooks(builder);
        builder.addHook(this, ModifierHooks.CONDITIONAL_STAT);
        builder.addHook(this, ModifierHooks.ON_ATTACKED);
    }

    @Override
    public float modifyStat(IToolStackView tool, ModifierEntry modifier, LivingEntity livingEntity, FloatToolStat stat, float baseValue, float multiplier) {
        if (!hasAzureMagnetArmor(tool)) {
            return baseValue;
        }
        float modifierLevel = modifier.getEffectiveLevel();
        if (stat == ToolStats.ARMOR) {
            baseValue *= (1 + 0.1f * modifierLevel);
        }
        return baseValue;
    }

    @Override
    public void onAttacked(IToolStackView tool, ModifierEntry modifier, EquipmentContext equipmentContext, EquipmentSlot equipmentSlot, DamageSource damageSource, float amount, boolean isDirectDamage) {
        if (hasAzureMagnetArmor(tool)) {
            return;
        }
        if (!equipmentSlot.isArmor()) {
            return;
        }
        LivingEntity living = equipmentContext.getEntity();
        if (living == null) {
            return;
        }
        float modifierLevel = modifier.getEffectiveLevel();
        double knockBackDistance = getKnockBackDistance(living, modifierLevel);
        AlexsCavesEffects.effectScarletAndAzureMagnetArmor(living, modifierLevel, true, knockBackDistance);
    }

    private boolean hasAzureMagnetArmor(IToolStackView tool) {
        return tool.getModifierLevel(TinkerInCavesModifiers.azure_magnet_armor.getId()) > 0;
    }

    private double getKnockBackDistance(LivingEntity living, float modifierLevel) {
        return living.getAttributeValue(Attributes.ATTACK_KNOCKBACK) + 0.3d * modifierLevel;
    }
}
