package com.oooooomy.tinkerincaves;

import com.mojang.logging.LogUtils;
import com.oooooomy.tinkerincaves.blocks.TinkerInCavesBlocks;
import com.oooooomy.tinkerincaves.creativetabs.TinkerInCavesCreativeTabs;
import com.oooooomy.tinkerincaves.fluids.TinkerInCavesFluids;
import com.oooooomy.tinkerincaves.items.TinkerInCavesItems;
import com.oooooomy.tinkerincaves.modifiers.TinkerInCavesModifiers;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(TinkerInCaves.MODID)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class TinkerInCaves
{
    public static final String MODID = "tinker_in_caves";

    public static final Logger LOGGER = LogUtils.getLogger();

    @SubscribeEvent
    public static void gatherData(final GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();
        ExistingFileHelper fileHelper = event.getExistingFileHelper();
        if(event.includeClient()){}
        if(event.includeServer()){}
    }

    public TinkerInCaves(FMLJavaModLoadingContext context)
    {
        IEventBus modEventBus = context.getModEventBus();

        modEventBus.addListener(this::commonSetup);

        TinkerInCavesBlocks.BLOCKS.register(modEventBus);
        TinkerInCavesItems.ITEMS.register(modEventBus);
        TinkerInCavesFluids.FLUIDS.register(modEventBus);
        TinkerInCavesModifiers.MODIFIERS.register(modEventBus);
        TinkerInCavesCreativeTabs.CREATIVE_MODE_TABS.register(modEventBus);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        // Register the item to a creative tab
        //modEventBus.addListener(this::addCreative);

        // Register our mod's ForgeConfigSpec so that Forge can create and load the config file for us
        context.registerConfig(ModConfig.Type.COMMON, TicConfig.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        // Some common setup code
        LOGGER.info("HELLO FROM COMMON SETUP");

        if (TicConfig.logDirtBlock)
            LOGGER.info("DIRT BLOCK >> {}", ForgeRegistries.BLOCKS.getKey(Blocks.DIRT));

        LOGGER.info(TicConfig.magicNumberIntroduction + TicConfig.magicNumber);

        TicConfig.items.forEach((item) -> LOGGER.info("ITEM >> {}", item.toString()));
    }

    // Add the example block item to the building blocks tab
    //private void addCreative(BuildCreativeModeTabContentsEvent event)
    //{
    //    if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS)
    //        event.accept(EXAMPLE_BLOCK_ITEM);
    //}

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
    }

    public static ResourceLocation getResource(String name) {
        //noinspection removal
        return getResource(MODID,name);
    }

    public static ResourceLocation getResource(String modId,String name) {
        //noinspection removal
        return new ResourceLocation(modId, name);
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            // Some client setup code
            //LOGGER.info("HELLO FROM CLIENT SETUP");
            //LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
        }
    }
}
