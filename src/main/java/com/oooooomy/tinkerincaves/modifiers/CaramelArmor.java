package com.oooooomy.tinkerincaves.modifiers;

import com.oooooomy.tinkerincaves.AlexsCavesEffects;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.armor.OnAttackedModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.context.EquipmentContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

public class CaramelArmor extends Modifier implements OnAttackedModifierHook {
    public CaramelArmor() {
    }

    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        super.registerHooks(hookBuilder);
        hookBuilder.addHook(this, ModifierHooks.ON_ATTACKED);
    }

    @Override
    public void onAttacked(IToolStackView tool, ModifierEntry modifier, EquipmentContext context, EquipmentSlot slotType, DamageSource source, float var6, boolean isDirectDamage) {
        LivingEntity entity = context.getEntity();
        Entity sourceEntity = source.getEntity();
        if (entity == null || !(sourceEntity instanceof LivingEntity damageSource)) {
            return;
        }
        AlexsCavesEffects.effectCaramelArmor(entity, damageSource, modifier.getLevel());
    }
}
