package com.oooooomy.tinkerincaves;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class TConstructInterface {
    public static boolean isItemBroken(Player player, InteractionHand hand)
    {
        ItemStack itemStack = player.getItemInHand(hand);
        CompoundTag tag = itemStack.getTag();
        return itemStack.getTag() == null || tag.getBoolean("tic_broken");
    }
}
