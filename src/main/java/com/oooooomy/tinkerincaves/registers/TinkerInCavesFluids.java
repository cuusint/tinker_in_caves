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
import slimeknights.tconstruct.fluids.block.BurningLiquidBlock;

public class TinkerInCavesFluids {

    public static final FluidDeferredRegister FLUIDS = new FluidDeferredRegister(TinkerInCaves.MODID);

    public static final FlowingFluidObject<ForgeFlowingFluid> molten_scarlet_neodymium = register("molten_scarlet_neodymium", 2000, 10000, 700, 10, MapColor.COLOR_RED, 8, 5f);
    public static final FlowingFluidObject<ForgeFlowingFluid> molten_azure_neodymium = register("molten_azure_neodymium", 2000, 10000, 700, 12, MapColor.COLOR_BLUE, 8, 5f);
    public static final FlowingFluidObject<ForgeFlowingFluid> molten_scarlet_neodymium_ingot = register("molten_scarlet_neodymium_ingot", 2000, 10000, 800, 12, MapColor.COLOR_RED, 12, 8f);
    public static final FlowingFluidObject<ForgeFlowingFluid> molten_azure_neodymium_ingot = register("molten_azure_neodymium_ingot", 2000, 10000, 800, 12, MapColor.COLOR_BLUE, 12, 8);

    private static FlowingFluidObject<ForgeFlowingFluid> register(String name, int density, int viscosity, int temperature, int lightLevel, MapColor mapColor, int burnTime, float damage) {
        return FLUIDS.register(name).type(hot(name, density, viscosity, temperature)).block(BurningLiquidBlock.createBurning(mapColor, lightLevel, 10, damage)).bucket().flowing();
    }

    private static FluidType.Properties hot(String name, int density, int viscosity, int temperature) {
        return FluidType.Properties.create().density(density).viscosity(viscosity).temperature(temperature)
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
