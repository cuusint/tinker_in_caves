package com.oooooomy.tinkerincaves.modifiers;

import com.github.alexmodguy.alexscaves.server.misc.ACSoundRegistry;
import com.oooooomy.tinkerincaves.AlexsCavesInterface;
import com.oooooomy.tinkerincaves.TinkerInCaves;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.tags.ITag;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.ModifierId;
import slimeknights.tconstruct.library.modifiers.hook.interaction.GeneralInteractionModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.interaction.InteractionSource;
import slimeknights.tconstruct.library.modifiers.modules.build.StatBoostModule;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.capability.fluid.ToolTankHelper;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import static slimeknights.tconstruct.library.tools.capability.fluid.ToolTankHelper.TANK_HELPER;

public class RayGun  extends Modifier implements GeneralInteractionModifierHook {
    public RayGun(){}

    @Override
    protected void registerHooks(ModuleHookMap.Builder builder){
        builder.addHook(this, ModifierHooks.GENERAL_INTERACT);
        builder.addModule(ToolTankHelper.TANK_HANDLER);
        builder.addModule(StatBoostModule.add(ToolTankHelper.CAPACITY_STAT).eachLevel(FluidType.BUCKET_VOLUME));
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
        if (source != InteractionSource.RIGHT_CLICK){
            return InteractionResult.PASS;
        }
        if (tool.isBroken()){
            return InteractionResult.PASS;
        }
        int fluidNeed = getFluidNeed(tool);
        FluidStack fluidStack = TANK_HELPER.getFluid(tool);
        if (fluidStack.getAmount() < fluidNeed) {
            return InteractionResult.PASS;
        }
        Fluid fluid = fluidStack.getFluid();
        TagKey<Fluid> myPotionTag = ForgeRegistries.FLUIDS.tags().createTagKey(TinkerInCaves.getResource("forge", "molten_uranium"));
        ITag<Fluid> tagFluid = ForgeRegistries.FLUIDS.tags().getTag(myPotionTag);
        if (!tagFluid.contains(fluid)) {
            return InteractionResult.PASS;
        }
        player.playSound(ACSoundRegistry.RAYGUN_START.get());
        GeneralInteractionModifierHook.startUsing(tool, modifier.getId(), player, hand);
        return InteractionResult.SUCCESS;
    }

    @Override
    public void onUsingTick(IToolStackView tool, ModifierEntry modifier, LivingEntity entity, int timeLeft) {
        int fluidNeed = getFluidNeed(tool);
        FluidStack fluid = TANK_HELPER.getFluid(tool);
        if (fluid.getAmount() < fluidNeed) {
            return;
        }
        int modifierLevelXRay = tool.getModifierLevel(ModifierId.tryParse("tinker_in_caves:x_ray"));
        int modifierLevelGammaRay = tool.getModifierLevel(ModifierId.tryParse("tinker_in_caves:gamma_ray"));

        int damage = modifier.getLevel() + modifierLevelXRay + modifierLevelGammaRay;

        AlexsCavesInterface.effectRayGun(tool,entity,getUseDuration(tool, modifier)-timeLeft,damage,modifierLevelXRay>0,modifierLevelGammaRay>0);

        fluid.shrink(fluidNeed);
        TANK_HELPER.setFluid(tool, fluid);
        tool.setDamage(tool.getDamage() + 1);
    }

    private int getFluidNeed(IToolStackView tool){
        return Mth.clamp(10 - tool.getModifierLevel(ModifierId.tryParse("tinker_in_caves:ray_efficiency")),1,10);
    }
}
