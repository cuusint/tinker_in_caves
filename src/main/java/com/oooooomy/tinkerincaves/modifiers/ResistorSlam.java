package com.oooooomy.tinkerincaves.modifiers;

import com.oooooomy.tinkerincaves.AlexsCavesEffects;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.UseAnim;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.interaction.GeneralInteractionModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.interaction.InteractionSource;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import static slimeknights.tconstruct.library.modifiers.ModifierId.*;

public class ResistorSlam extends Modifier implements GeneralInteractionModifierHook {
    public ResistorSlam() {
    }

    @Override
    protected void registerHooks(ModuleHookMap.Builder builder) {
        builder.addHook(this, ModifierHooks.GENERAL_INTERACT);
    }

    @Override
    public int getUseDuration(IToolStackView tool, ModifierEntry modifier) {
        return 72000;
    }

    @Override
    public int getPriority() {
        return 200;
    }

    @Override
    public UseAnim getUseAction(IToolStackView tool, ModifierEntry modifier) {
        return UseAnim.BLOCK;
    }

    @Override
    public InteractionResult onToolUse(IToolStackView tool, ModifierEntry modifier, Player player, InteractionHand hand, InteractionSource source) {
        if (source != InteractionSource.RIGHT_CLICK) {
            return InteractionResult.PASS;
        }
        if (tool.isBroken()) {
            return InteractionResult.PASS;
        }
        GeneralInteractionModifierHook.startUsing(tool, modifier.getId(), player, hand);
        return InteractionResult.CONSUME;
    }

    @Override
    public void onUsingTick(IToolStackView tool, ModifierEntry modifier, LivingEntity entity, int timeLeft) {
        int modifierLevel = modifier.getLevel();
        int modifierLevelHeavySlam = tool.getModifierLevel(tryParse("tinker_in_caves:heavy_slam"));
        int modifierLevelScarlet = tool.getModifierLevel(tryParse("tinker_in_caves:scarlet_shield"));
        int modifierLevelAzure = tool.getModifierLevel(tryParse("tinker_in_caves:azure_shield"));

        float range = 1 + 2 * modifierLevel + modifierLevelHeavySlam + modifierLevelAzure + modifierLevelScarlet;
        double hitDamage = 0.2d * entity.getAttributeValue(Attributes.ATTACK_DAMAGE) + 2 * modifierLevel + 4 * modifierLevelHeavySlam + modifierLevelAzure + modifierLevelScarlet;
        double knockBackDistance = entity.getAttributeValue(Attributes.ATTACK_KNOCKBACK) + 0.1d * modifierLevel + 0.2d * modifierLevelHeavySlam + 0.1d * modifierLevelAzure + 0.1d * modifierLevelScarlet;
        AlexsCavesEffects.effectResistorSlam(
                entity,
                getUseDuration(tool, modifier) - timeLeft,
                range,
                (int) hitDamage,
                (int) (hitDamage / 4d),
                knockBackDistance,
                knockBackDistance / 4d,
                modifierLevelScarlet,
                modifierLevelAzure);
    }
}
