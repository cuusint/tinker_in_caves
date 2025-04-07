package com.oooooomy.tinkerincaves.modifiers;

import com.oooooomy.tinkerincaves.AlexsCavesEffects;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.UseAnim;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.ModifierId;
import slimeknights.tconstruct.library.modifiers.hook.interaction.GeneralInteractionModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.interaction.InteractionSource;
import slimeknights.tconstruct.library.modifiers.impl.NoLevelsModifier;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

public class SugarStaff extends NoLevelsModifier implements GeneralInteractionModifierHook {
    public SugarStaff(){}

    @Override
    protected void registerHooks(ModuleHookMap.Builder builder){
        builder.addHook(this, ModifierHooks.GENERAL_INTERACT);
    }

    @Override
    public int getUseDuration(IToolStackView tool, ModifierEntry modifier) {
        int modifierLevelPeppermintPunting = getModifierLevelPeppermintPunting(tool);
        if (modifierLevelPeppermintPunting <= 0){
            return 20;
        }
        return 10;
    }

    @Override
    public UseAnim getUseAction(IToolStackView tool, ModifierEntry modifier) {
        return UseAnim.BOW;
    }

    @Override
    public InteractionResult onToolUse(IToolStackView tool, ModifierEntry modifier, Player player, InteractionHand hand, InteractionSource source) {
        if (hand != InteractionHand.MAIN_HAND){
            return InteractionResult.PASS;
        }
        if (source != InteractionSource.RIGHT_CLICK){
            return InteractionResult.PASS;
        }
        if (!player.isCreative() && tool.isBroken()){
            return InteractionResult.PASS;
        }
        GeneralInteractionModifierHook.startUsingWithDrawtime(tool, modifier.getId(), player, hand, getSpeedFactor(tool, modifier));
        return InteractionResult.SUCCESS;
    }

    @Override
    public void onFinishUsing(IToolStackView tool, ModifierEntry modifier, LivingEntity entity) {
        if (!(entity instanceof Player player)){
            return;
        }
        if (!player.isCreative() && tool.isBroken()){
            return;
        }
        int modifierLevelPeppermintPunting = getModifierLevelPeppermintPunting(tool);
        int modifierLevelMultipleMint = getModifierLevelMultipleMint(tool);
        AlexsCavesEffects.effectSugarStaff(entity, modifierLevelMultipleMint,modifierLevelPeppermintPunting > 0);
        if (!player.isCreative()){
            tool.setDamage(tool.getDamage() + 4 );
        }
    }

    private float getSpeedFactor(IToolStackView tool, ModifierEntry modifier){
        return 1 + modifier.getLevel() * 0.4f;
    }

    private int getModifierLevelPeppermintPunting(IToolStackView tool){
        return tool.getModifierLevel(ModifierId.tryParse("tinker_in_caves:peppermint_punting"));
    }

    private int getModifierLevelMultipleMint(IToolStackView tool){
        return tool.getModifierLevel(ModifierId.tryParse("tinker_in_caves:multiple_mint"));
    }
}
