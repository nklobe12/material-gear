package com.klobe.materialgear;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.UnaryOperator;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.component.BlockTransformers;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MaterialGear.MODID);

    public static final String[] TOOLS = {"sword", "pickaxe", "axe", "shovel", "hoe"};
    public static final String[] ARMOR = {"helmet", "chestplate", "leggings", "boots"};

    private static final List<RegistryObject<Item>> GEAR = new ArrayList<>();
    private static final Map<String, RegistryObject<Item>> BY_NAME = new HashMap<>();

    public static final RegistryObject<Item> COMPRESSED_COBBLESTONE = ITEMS.register("compressed_cobblestone",
        () -> new BlockItem(ModBlocks.COMPRESSED_COBBLESTONE.get(),
            new Item.Properties().setId(ITEMS.key("compressed_cobblestone"))));

    static {
        for (GearSet set : GearSet.values())
            registerSet(set);
    }

    private static void registerSet(GearSet set) {
        ToolMaterial tool = set.toolMaterial();
        ArmorMaterial armor = set.armorMaterial();

        // Damage and speed offsets follow vanilla: final damage = 1 + offset + material bonus.
        gear(set, "sword",   p -> p.sword(tool, 3.0F, -2.4F));
        gear(set, "pickaxe", p -> p.pickaxe(tool, 1.0F, -2.8F));

        // 26.3 removed AxeItem/ShovelItem/HoeItem. Right-click behaviour (stripping logs,
        // making paths, tilling) now comes from the BLOCK_TRANSFORMER component.
        gear(set, "axe", p -> p.axe(tool, set.axeDamage(), set.axeSpeed())
            .delayedComponent(DataComponents.BLOCK_TRANSFORMER, lookup -> lookup.getOrThrow(BlockTransformers.AXE)));
        gear(set, "shovel", p -> p.shovel(tool, 1.5F, -3.0F)
            .delayedComponent(DataComponents.BLOCK_TRANSFORMER, lookup -> lookup.getOrThrow(BlockTransformers.SHOVEL)));
        gear(set, "hoe", p -> p.hoe(tool, set.hoeDamage(), set.hoeSpeed())
            .delayedComponent(DataComponents.BLOCK_TRANSFORMER, lookup -> lookup.getOrThrow(BlockTransformers.HOE)));

        gear(set, "helmet",     p -> p.humanoidArmor(armor, ArmorType.HELMET));
        gear(set, "chestplate", p -> p.humanoidArmor(armor, ArmorType.CHESTPLATE));
        gear(set, "leggings",   p -> p.humanoidArmor(armor, ArmorType.LEGGINGS));
        gear(set, "boots",      p -> p.humanoidArmor(armor, ArmorType.BOOTS));
    }

    private static void gear(GearSet set, String type, UnaryOperator<Item.Properties> configure) {
        String name = set.id() + "_" + type;
        RegistryObject<Item> item = ITEMS.register(name, () -> {
            Item.Properties properties = new Item.Properties().setId(ITEMS.key(name));
            if (set.fireResistant())
                properties.fireResistant();
            return new Item(configure.apply(properties));
        });
        GEAR.add(item);
        BY_NAME.put(name, item);
    }

    /** All gear in creative-tab order: set by set, tools then armor. */
    public static List<RegistryObject<Item>> gear() {
        return Collections.unmodifiableList(GEAR);
    }

    public static RegistryObject<Item> get(GearSet set, String type) {
        return BY_NAME.get(set.id() + "_" + type);
    }

    private ModItems() {}
}
