package com.oooooomy.tinkerincaves.modifiers;

import com.github.alexmodguy.alexscaves.server.item.ACItemRegistry;
import com.github.alexmodguy.alexscaves.server.potion.ACEffectRegistry;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraftforge.registries.ForgeRegistries;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.behavior.ProcessLootModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import java.util.ArrayList;
import java.util.List;

public class FavoriteGummyBear extends Modifier implements ProcessLootModifierHook {
    private ArrayList<Potion> potions = new ArrayList<>();

    public FavoriteGummyBear() {
    }

    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        hookBuilder.addHook(this, ModifierHooks.PROCESS_LOOT);
    }

    @Override
    public void processLoot(IToolStackView tool, ModifierEntry modifier, List<ItemStack> generatedLoot, LootContext context) {
        // if no damage source, probably not a mob
        // otherwise blocks breaking (where THIS_ENTITY is the player) start dropping bacon
        if (!context.hasParam(LootContextParams.DAMAGE_SOURCE)) {
            return;
        }
        int looting = context.getLootingModifier();
        if (RANDOM.nextInt(48 / modifier.intEffectiveLevel()) <= looting) {
            potions.addAll(ForgeRegistries.POTIONS.getValues());
            if (potions.size() > 0) {
                Potion potion = potions.get(Mth.clamp(RANDOM.nextInt(0, potions.size()), 0, potions.size() - 1));
                generatedLoot.add(ACEffectRegistry.createJellybean(potion));
            } else {
                generatedLoot.add(new ItemStack(ACItemRegistry.JELLY_BEAN.get()));
            }
            potions.clear();
        }
    }
}
