package com.klobe.materialgear;

import java.util.Arrays;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

@Mod(MaterialGear.MODID)
public final class MaterialGear {
    public static final String MODID = "materialgear";

    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

    public static final RegistryObject<CreativeModeTab> TAB = CREATIVE_TABS.register("main", () -> CreativeModeTab.builder()
        .title(Component.translatable("itemGroup.materialgear"))
        .withTabsBefore(CreativeModeTabs.COMBAT)
        .icon(() -> ModItems.get(GearSet.OBSIDIAN, "pickaxe").get().getDefaultInstance())
        .displayItems((parameters, output) -> {
            output.accept(ModItems.COMPRESSED_COBBLESTONE.get());
            for (RegistryObject<Item> item : ModItems.gear())
                output.accept(item.get());
        })
        .build());

    public MaterialGear(FMLJavaModLoadingContext context) {
        var modBus = context.getModBusGroup();

        ModBlocks.BLOCKS.register(modBus);
        ModItems.ITEMS.register(modBus);
        CREATIVE_TABS.register(modBus);

        BuildCreativeModeTabContentsEvent.BUS.addListener(MaterialGear::addToVanillaTabs);
    }

    /** Also list everything in the matching vanilla tabs, after their vanilla counterparts. */
    private static void addToVanillaTabs(BuildCreativeModeTabContentsEvent event) {
        var tab = event.getTabKey();
        if (tab == CreativeModeTabs.BUILDING_BLOCKS) {
            event.accept(ModItems.COMPRESSED_COBBLESTONE);
            return;
        }

        boolean tools = tab == CreativeModeTabs.TOOLS_AND_UTILITIES;
        boolean combat = tab == CreativeModeTabs.COMBAT;
        if (!tools && !combat)
            return;

        for (GearSet set : GearSet.values()) {
            if (tools) {
                for (String type : ModItems.TOOLS)
                    if (!type.equals("sword"))
                        event.accept(ModItems.get(set, type));
            } else {
                event.accept(ModItems.get(set, "sword"));
                event.accept(ModItems.get(set, "axe"));
                Arrays.stream(ModItems.ARMOR).forEach(type -> event.accept(ModItems.get(set, type)));
            }
        }
    }
}
