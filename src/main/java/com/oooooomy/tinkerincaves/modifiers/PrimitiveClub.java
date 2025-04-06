package com.oooooomy.tinkerincaves.modifiers;

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

import static slimeknights.tconstruct.library.modifiers.ModifierId.*;

public class PrimitiveClub extends Modifier implements MeleeHitModifierHook {
    public PrimitiveClub(){}

    @Override
    protected void registerHooks(ModuleHookMap.Builder builder){
        super.registerHooks(builder);
        builder.addHook(this, ModifierHooks.MELEE_HIT);
    }

    @Override
    public void afterMeleeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damageDealt){
        Entity entity = context.getTarget();
        int modifierLevel = modifier.getLevel();
        if (!(entity instanceof LivingEntity target)){
           return;
        }
        float dazingProbability = 0.3f * modifierLevel;
        int dazingEdge = 2 * tool.getModifierLevel(tryParse("tinker_in_caves:dazing_sweep"));
        int durationBase = 10 + 5 * modifierLevel + 10 * tool.getModifierLevel(tryParse("tinker_in_caves:tremorsaurus"));
        int durationExtra = durationBase; //10+5*modifierLevel+10*tool.getModifierLevel(ModifierId.tryParse("tinker_in_caves:tremorsaurus"));
        AlexsCavesInterface.effectPrimitiveClub(target, context.getAttacker(),dazingProbability,durationBase,durationExtra,dazingEdge);
    }
}
