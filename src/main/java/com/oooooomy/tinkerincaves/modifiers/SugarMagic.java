package com.oooooomy.tinkerincaves.modifiers;

import com.oooooomy.tinkerincaves.AlexsCavesInterface;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.ModifierId;
import slimeknights.tconstruct.library.modifiers.hook.ranged.ProjectileHitModifierHook;
import slimeknights.tconstruct.library.modifiers.impl.NoLevelsModifier;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;
import slimeknights.tconstruct.library.tools.nbt.ModifierNBT;

import javax.annotation.Nullable;

public class SugarMagic extends NoLevelsModifier implements ProjectileHitModifierHook {
    public SugarMagic(){}

    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder){
        super.registerHooks(hookBuilder);
        hookBuilder.addHook(this, ModifierHooks.PROJECTILE_HIT);
    }

    public boolean onProjectileHitEntity(ModifierNBT modifiers, ModDataNBT persistentData, ModifierEntry modifier, Projectile projectile, EntityHitResult hit, @Nullable LivingEntity attacker, @Nullable LivingEntity target) {
        AlexsCavesInterface.effectSugarMagic(attacker,target,getModifierLevelHumungousHex(modifiers),getModifierLevelSpellLasting(modifiers));
        return false;
    }

    public void onProjectileHitBlock(ModifierNBT modifiers, ModDataNBT persistentData, ModifierEntry modifier, Projectile projectile, BlockHitResult hit, @Nullable LivingEntity attacker) {
        AlexsCavesInterface.effectSugarMagic(attacker,hit.getBlockPos(),getModifierLevelHumungousHex(modifiers),getModifierLevelSpellLasting(modifiers));
    }

    private int getModifierLevelHumungousHex(ModifierNBT modifiers){
        return modifiers.getLevel(ModifierId.tryParse("tinker_in_caves:humungous_hex"));
    }

    private int getModifierLevelSpellLasting(ModifierNBT modifiers){
        return modifiers.getLevel(ModifierId.tryParse("tinker_in_caves:spell_lasting"));
    }
}
