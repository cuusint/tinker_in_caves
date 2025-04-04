package com.oooooomy.tinkerincaves.modifiers.abilities;

import com.oooooomy.tinkerincaves.AlexsCavesInterface;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.UseAnim;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.ModifierId;
import slimeknights.tconstruct.library.modifiers.hook.interaction.GeneralInteractionModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.interaction.InteractionSource;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.tools.modifiers.ability.interaction.BlockingModifier;

public class SeaStaff extends Modifier implements GeneralInteractionModifierHook {
    public SeaStaff(){}

    @Override
    protected void registerHooks(ModuleHookMap.Builder builder){
        builder.addHook(this, ModifierHooks.GENERAL_INTERACT);
    }

    @Override
    public int getUseDuration(IToolStackView tool, ModifierEntry modifier) {
        return 12000+ Mth.clamp(12000-modifier.getLevel()*6000,0,12000);
    }

    @Override
    public UseAnim getUseAction(IToolStackView tool, ModifierEntry modifier) {
        return BlockingModifier.blockWhileCharging(tool, UseAnim.BOW);
    }

    @Override
    public InteractionResult onToolUse(IToolStackView tool, ModifierEntry modifier, Player player, InteractionHand hand, InteractionSource source) {
        if (hand != InteractionHand.MAIN_HAND){
            return InteractionResult.PASS;
        }
        if (source != InteractionSource.RIGHT_CLICK){
            return InteractionResult.PASS;
        }
        if (tool.isBroken()){
            return InteractionResult.PASS;
        }
        GeneralInteractionModifierHook.startUsingWithDrawtime(tool, modifier.getId(), player, hand, 1 + modifier.getLevel()*0.4f);
        return InteractionResult.SUCCESS;
    }

    @Override
    public void onStoppedUsing(IToolStackView tool, ModifierEntry modifier, LivingEntity entity, int timeLeft){
        if (!(entity instanceof Player player))
        {
            return;
        }
        int modifierLevel = modifier.getLevel();
        tool.setDamage(tool.getDamage() + 4 * modifierLevel);

        int boltsCount = tool.getModifierLevel(ModifierId.tryParse("tinker_in_caves:triple_splash"))>0?3:1;
        double seekDistance = 32+ modifierLevel*8+16*tool.getModifierLevel(ModifierId.tryParse("tinker_in_caves:soak_seeking"));
        int seekAmount = modifierLevel+tool.getModifierLevel(ModifierId.tryParse("tinker_in_caves:soak_seeking"));
        boolean bubble = tool.getModifierLevel(ModifierId.tryParse("tinker_in_caves:enveloping_bubble"))>0;
        boolean bouncing = tool.getModifierLevel(ModifierId.tryParse("tinker_in_caves:bouncing_bolt"))>0;
        AlexsCavesInterface.effectSeaStaff(player,boltsCount,seekDistance ,seekAmount,bubble,bouncing);
    }
}
