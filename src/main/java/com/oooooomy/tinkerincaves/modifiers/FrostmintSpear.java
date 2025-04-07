package com.oooooomy.tinkerincaves.modifiers;

import com.oooooomy.tinkerincaves.AlexsCavesEffects;
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

public class FrostmintSpear extends Modifier implements GeneralInteractionModifierHook {
    public FrostmintSpear(){}

    @Override
    protected void registerHooks(ModuleHookMap.Builder builder){
        builder.addHook(this, ModifierHooks.GENERAL_INTERACT);
    }

    @Override
    public int getUseDuration(IToolStackView tool, ModifierEntry modifier) {
        return 72000;
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
    public void onUsingTick(IToolStackView tool, ModifierEntry modifier, LivingEntity entity, int timeLeft) {
        int chargeTime = getUseDuration(tool, modifier) - timeLeft;
        int modifierLevel = modifier.getLevel();
        int chargeTimeRequire = Mth.clamp(20 - 4 * modifierLevel,5,100);
        if (chargeTime <= chargeTimeRequire){
            return;
        }
        if (!(entity instanceof Player player)){
            return;
        }
        if (!player.isCreative() && tool.isBroken()){
            return;
        }

        int interval = Mth.clamp(20 - 3 * modifierLevel,2,100);
        if ((chargeTime - chargeTimeRequire) % interval != 0){
            return;
        }

        AlexsCavesEffects.effectFrostmintSpear(entity,modifierLevel);

        if (!player.isCreative()){
            tool.setDamage(tool.getDamage() + 4 * modifierLevel);
        }
    }

    private float getSpeedFactor(IToolStackView tool, ModifierEntry modifier){
        return 1 + modifier.getLevel()*0.4f;
    }
}
