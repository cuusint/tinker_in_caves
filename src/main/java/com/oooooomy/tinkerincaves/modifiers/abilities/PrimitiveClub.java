package com.oooooomy.tinkerincaves.modifiers.abilities;

import com.oooooomy.tinkerincaves.AlexsCavesInterface;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.combat.MeleeHitModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

public class PrimitiveClub extends Modifier implements MeleeHitModifierHook {

    @Override
    protected void registerHooks(ModuleHookMap.Builder builder){
        super.registerHooks(builder);
        builder.addHook(this, ModifierHooks.MELEE_HIT);
    }

    @Override
    public void afterMeleeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damageDealt){
        Entity entity = context.getTarget();
        int level = modifier.getLevel();
        if (level>0&&entity instanceof LivingEntity target){
            AlexsCavesInterface.effectPrimitiveClub(context.getAttacker().getItemInHand(context.getHand()),target, context.getAttacker(),0.5f,20*level,40,level-1);
        }
    }
}
