package com.oooooomy.tinkerincaves.modifiers.upgrades;

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
import slimeknights.tconstruct.library.modifiers.hook.interaction.GeneralInteractionModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.interaction.InteractionSource;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.tools.modifiers.ability.interaction.BlockingModifier;

public class Ortholance extends Modifier implements GeneralInteractionModifierHook {
    public Ortholance(){}

    @Override
    protected void registerHooks(ModuleHookMap.Builder builder){
        builder.addHook(this, ModifierHooks.GENERAL_INTERACT);
    }

    @Override
    public int getUseDuration(IToolStackView tool, ModifierEntry modifier) {
        return 36000+ Mth.clamp(36000-modifier.getLevel()*12000,0,36000);
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
        GeneralInteractionModifierHook.startUsingWithDrawtime(tool, modifier.getId(), player, hand, 1 + modifier.getLevel()*0.2f);
        return InteractionResult.SUCCESS;
    }

    @Override
    public void onStoppedUsing(IToolStackView tool, ModifierEntry modifier, LivingEntity entity, int timeLeft){
        int modifierLevel = modifier.getLevel();
        tool.setDamage(tool.getDamage() + 4 * modifierLevel);

        int flinging = modifierLevel-1;
        boolean tsunami = modifierLevel>=3;
        boolean secondWave = modifierLevel>=2;
        AlexsCavesInterface.effectOrtholance(entity.level(),entity,getUseDuration(tool, modifier)-timeLeft,flinging,tsunami,secondWave);
    }
}
