package com.oooooomy.tinkerincaves.modifiers;

import com.oooooomy.tinkerincaves.AlexsCavesEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.combat.MeleeHitModifierHook;
import slimeknights.tconstruct.library.modifiers.impl.NoLevelsModifier;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import static slimeknights.tconstruct.library.modifiers.ModifierId.*;

public class DesolateDagger extends NoLevelsModifier implements MeleeHitModifierHook {
    public DesolateDagger() {
    }

    @Override
    protected void registerHooks(ModuleHookMap.Builder builder) {
        super.registerHooks(builder);
        builder.addHook(this, ModifierHooks.MELEE_HIT);
    }

    @Override
    public void afterMeleeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damageDealt) {
        LivingEntity attacker = context.getAttacker();
        LivingEntity target = context.getLivingTarget();
        if (attacker == null || target == null) {
            return;
        }
        ItemStack itemStack = context.getPlayerAttacker().getItemInHand(context.getHand());
        int multipleStab = tool.getModifierLevel(tryParse("tinker_in_caves:multiple_stab"));
        int impendingStab = tool.getModifierLevel(tryParse("tinker_in_caves:impending_stab"));

        AlexsCavesEffects.effectDesolateDagger(itemStack, attacker, target, multipleStab, impendingStab);
    }
}
