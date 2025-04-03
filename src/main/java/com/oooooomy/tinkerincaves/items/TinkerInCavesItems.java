package com.oooooomy.tinkerincaves.items;

import com.oooooomy.tinkerincaves.TinkerInCaves;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import com.oooooomy.tinkerincaves.blocks.TinkerInCavesBlocks;

public class TinkerInCavesItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, TinkerInCaves.MODID);

    public static final RegistryObject<Item> EXAMPLE_ITEM = ITEMS.register("example_item", () -> new Item(new Item.Properties().food(new FoodProperties.Builder().alwaysEat().nutrition(1).saturationMod(2f).build())));

    public static final RegistryObject<Item> EXAMPLE_BLOCK_ITEM = ITEMS.register("example_block", () -> new BlockItem(TinkerInCavesBlocks.EXAMPLE_BLOCK.get(), new Item.Properties()));

    public static void addCreativeTabItems(CreativeModeTab.Output output)
    {
        output.accept(EXAMPLE_ITEM.get());

        output.accept(EXAMPLE_BLOCK_ITEM.get());
    }
}
