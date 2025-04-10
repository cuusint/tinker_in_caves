package com.oooooomy.tinkerincaves;

import com.oooooomy.tinkerincaves.registers.TinkerInCavesFluids;
import com.oooooomy.tinkerincaves.registers.TinkerInCavesModifiers;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(TinkerInCaves.MODID)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class TinkerInCaves {
    public static final String MODID = "tinker_in_caves";

    @SubscribeEvent
    public static void gatherData(final GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();
        ExistingFileHelper fileHelper = event.getExistingFileHelper();
        if (event.includeClient()) {
        }
        if (event.includeServer()) {
        }
    }

    public TinkerInCaves(FMLJavaModLoadingContext context) {
        IEventBus modEventBus = context.getModEventBus();

        TinkerInCavesFluids.FLUIDS.register(modEventBus);
        TinkerInCavesModifiers.MODIFIERS.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);
    }

    public TinkerInCaves() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        TinkerInCavesFluids.FLUIDS.register(modEventBus);
        TinkerInCavesModifiers.MODIFIERS.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);
    }

    public static ResourceLocation getResource(String name) {
        return getResource(MODID, name);
    }

    public static ResourceLocation getResource(String modId, String name) {
        return new ResourceLocation(modId, name);
    }
}
