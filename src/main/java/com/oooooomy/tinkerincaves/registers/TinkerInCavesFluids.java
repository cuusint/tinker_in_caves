package com.oooooomy.tinkerincaves.registers;

import com.oooooomy.tinkerincaves.TinkerInCaves;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.common.SoundActions;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import slimeknights.mantle.registration.deferred.FluidDeferredRegister;
import slimeknights.mantle.registration.object.FlowingFluidObject;
import slimeknights.tconstruct.TConstruct;

public class TinkerInCavesFluids {

    public static final FluidDeferredRegister FLUIDS = new FluidDeferredRegister(TinkerInCaves.MODID);

    public static final FlowingFluidObject<ForgeFlowingFluid> molten_scarlet_neodymium = register("molten_scarlet_neodymium", 700);
    public static final FlowingFluidObject<ForgeFlowingFluid> molten_azure_neodymium = register("molten_azure_neodymium", 700);
    public static final FlowingFluidObject<ForgeFlowingFluid> molten_scarlet_neodymium_ingot = register("molten_scarlet_neodymium_ingot", 800);
    public static final FlowingFluidObject<ForgeFlowingFluid> molten_azure_neodymium_ingot = register("molten_azure_neodymium_ingot", 800);

    private static FlowingFluidObject<ForgeFlowingFluid> register(String name, int temp) {
        return FLUIDS.register(name).type(hot(name).temperature(temp).lightLevel(12)).block(MapColor.COLOR_RED, 12).bucket().flowing();
    }

    private static FluidType.Properties hot(String name) {
        return FluidType.Properties.create().density(2000).viscosity(10000).temperature(1000)
                .descriptionId(TConstruct.makeDescriptionId("fluid", name))
                .sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL_LAVA)
                .sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY_LAVA);
    }

    private static FluidType.Properties cool(String name) {
        return FluidType.Properties.create()
                .descriptionId(TConstruct.makeDescriptionId("fluid", name))
                .sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL)
                .sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY);
    }
}
