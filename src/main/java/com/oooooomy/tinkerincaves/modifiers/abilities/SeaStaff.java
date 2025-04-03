package com.oooooomy.tinkerincaves.modifiers.abilities;

import com.oooooomy.tinkerincaves.AlexsCavesInterface;
import com.oooooomy.tinkerincaves.TConstructInterface;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.interaction.GeneralInteractionModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.interaction.InteractionSource;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

public class SeaStaff extends Modifier implements GeneralInteractionModifierHook {
    public SeaStaff(){}

    @Override
    protected void registerHooks(ModuleHookMap.Builder builder){
        super.registerHooks(builder);
        builder.addHook(this, ModifierHooks.GENERAL_INTERACT);
    }

    @Override
    public InteractionResult onToolUse(IToolStackView iToolStackView, ModifierEntry modifierEntry, Player player, InteractionHand interactionHand, InteractionSource interactionSource) {
        if (interactionHand!=InteractionHand.MAIN_HAND)
        {
            return InteractionResult.PASS;
        }
        if (TConstructInterface.isItemBroken(player,interactionHand))
        {
            return InteractionResult.PASS;
        }
        int modifierLevel = modifierEntry.getLevel();
        AlexsCavesInterface.effectSeaStaff(player,interactionHand,1 *modifierLevel ,1,false,false);
        player.awardStat(Stats.ITEM_USED.get(player.getItemInHand(interactionHand).getItem()));
        if (!player.getAbilities().instabuild)
        {
            player.getItemInHand(interactionHand).hurtAndBreak(4*modifierLevel, player, (player1) -> {
                player1.broadcastBreakEvent(interactionHand);
            });
        }
        return InteractionResult.CONSUME;
    }
}
