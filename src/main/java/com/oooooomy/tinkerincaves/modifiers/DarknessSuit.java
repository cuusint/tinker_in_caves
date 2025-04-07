package com.oooooomy.tinkerincaves.modifiers;

import com.github.alexmodguy.alexscaves.AlexsCaves;
import com.github.alexmodguy.alexscaves.server.potion.ACEffectRegistry;
import com.oooooomy.tinkerincaves.AlexsCavesEffects;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.interaction.InventoryTickModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

public class DarknessSuit extends Modifier implements InventoryTickModifierHook {
    public DarknessSuit(){}

    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        super.registerHooks(hookBuilder);
        hookBuilder.addHook(this,  ModifierHooks.INVENTORY_TICK);
    }

    @Override
    public void onInventoryTick(IToolStackView tool, ModifierEntry modifier, Level level, LivingEntity livingEntity, int itemSlot, boolean isSelected, boolean isHeld, ItemStack itemStack) {
        if (livingEntity == null) {
            return;
        }
        if (!checkStack(livingEntity, itemStack)){
            return;
        }
        MobEffect darknessEffect = ACEffectRegistry.DARKNESS_INCARNATE.get();
        if (livingEntity.hasEffect(darknessEffect)){
            var effect = livingEntity.getEffect(darknessEffect);
            if (effect.getDuration() <= 0){
                livingEntity.removeEffect(darknessEffect);
            }
            return;
        }
        if (AlexsCaves.PROXY.getClientSidePlayer() == livingEntity && AlexsCaves.PROXY.isKeyDown(2)){
            AlexsCavesEffects.effectDarknessSuit(livingEntity,itemStack);
            damageTool(tool, modifier);
        }
    }

    private boolean checkStack(LivingEntity livingEntity,ItemStack itemStack){
        if (livingEntity.getItemBySlot(EquipmentSlot.HEAD)==itemStack){
            return true;
        }
        if (livingEntity.getItemBySlot(EquipmentSlot.CHEST)==itemStack){
            return true;
        }
        if (livingEntity.getItemBySlot(EquipmentSlot.LEGS)==itemStack){
            return true;
        }
        if (livingEntity.getItemBySlot(EquipmentSlot.FEET)==itemStack){
            return true;
        }
        return false;
    }

    private void damageTool(IToolStackView tool, ModifierEntry modifier){
        int damage = Mth.clamp(10 * modifier.getLevel(),5,50);
        tool.setDamage(tool.getDamage()+damage);
    }
}
