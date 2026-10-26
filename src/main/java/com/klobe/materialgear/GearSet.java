package com.klobe.materialgear;

import java.util.EnumMap;
import java.util.Map;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.minecraft.world.item.equipment.EquipmentAssets;
import net.minecraft.world.level.block.Block;

/**
 * Every gear set the mod adds, and all of its balance numbers in one place.
 *
 * <p>Vanilla reference points (tools: durability / speed / damage bonus / enchantability):
 * wood 59/2/0/15, stone 131/4/1/5, copper 190/5/1/13, iron 250/6/2/14,
 * diamond 1561/8/3/10, netherite 2031/9/4/15.
 *
 * <p>Vanilla armor (durability multiplier; boots/legs/chest/helmet; toughness; knockback resistance):
 * leather 5; 1/2/3/1, chain 15; 1/4/5/2, iron 15; 2/5/6/2, diamond 33; 3/6/8/3; 2.0,
 * netherite 37; 3/6/8/3; 3.0; 0.1. Armor durability = multiplier x (helmet 11, chest 16, legs 15, boots 13).
 */
public enum GearSet {
    //           id             mining level                              dur   speed  dmg+  ench   axe dmg/spd     hoe dmg/spd
    LEAF       ("leaf",        BlockTags.INCORRECT_FOR_WOODEN_TOOL,      32,   2.0F,  0.0F, 22,    6.0F, -3.2F,    0.0F, -3.0F,
    //           armor mult  boots legs chest helm   tough  knockback  ench  equip sound                         fire-proof
                 4,          1,    1,   2,    1,     0.0F,  0.0F,      22,   SoundEvents.ARMOR_EQUIP_LEATHER,    false),

    DIRT       ("dirt",        BlockTags.INCORRECT_FOR_WOODEN_TOOL,      48,   2.0F,  0.0F, 6,     6.0F, -3.2F,    0.0F, -3.0F,
                 5,          1,    2,   2,    1,     0.0F,  0.0F,      6,    SoundEvents.ARMOR_EQUIP_GENERIC,    false),

    HARDWOOD   ("hardwood",    BlockTags.INCORRECT_FOR_STONE_TOOL,       120,  3.0F,  0.5F, 15,    6.0F, -3.2F,    0.0F, -3.0F,
                 8,          1,    2,   3,    1,     0.0F,  0.0F,      15,   SoundEvents.ARMOR_EQUIP_GENERIC,    false),

    COAL       ("coal",        BlockTags.INCORRECT_FOR_STONE_TOOL,       170,  5.5F,  1.0F, 12,    7.0F, -3.2F,   -1.0F, -2.0F,
                 11,         1,    3,   4,    2,     0.0F,  0.0F,      12,   SoundEvents.ARMOR_EQUIP_CHAIN,      false),

    COBBLESTONE("cobblestone", BlockTags.INCORRECT_FOR_STONE_TOOL,       200,  4.0F,  1.5F, 5,     7.0F, -3.2F,   -1.0F, -2.0F,
                 13,         2,    4,   5,    2,     0.0F,  0.05F,     5,    SoundEvents.ARMOR_EQUIP_IRON,       false),

    LAPIS      ("lapis",       BlockTags.INCORRECT_FOR_IRON_TOOL,        300,  6.5F,  2.0F, 30,    6.0F, -3.1F,   -2.0F, -1.0F,
                 16,         2,    5,   6,    2,     0.0F,  0.0F,      30,   SoundEvents.ARMOR_EQUIP_GOLD,       false),

    EMERALD    ("emerald",     BlockTags.INCORRECT_FOR_DIAMOND_TOOL,     1300, 8.5F,  3.0F, 20,    5.0F, -3.0F,   -3.0F,  0.0F,
                 28,         3,    6,   7,    3,     1.5F,  0.0F,      20,   SoundEvents.ARMOR_EQUIP_DIAMOND,    false),

    OBSIDIAN   ("obsidian",    BlockTags.INCORRECT_FOR_DIAMOND_TOOL,     1900, 6.0F,  3.5F, 8,     5.0F, -3.0F,   -3.5F,  0.0F,
                 35,         3,    6,   8,    3,     2.5F,  0.1F,      8,    SoundEvents.ARMOR_EQUIP_NETHERITE,  true);

    private final String id;
    private final float axeDamage;
    private final float axeSpeed;
    private final float hoeDamage;
    private final float hoeSpeed;
    private final boolean fireResistant;
    private final TagKey<Item> materialTag;
    private final ToolMaterial toolMaterial;
    private final ArmorMaterial armorMaterial;

    GearSet(String id, TagKey<Block> incorrectBlocksForDrops, int durability, float speed, float attackDamageBonus,
            int toolEnchantability, float axeDamage, float axeSpeed, float hoeDamage, float hoeSpeed,
            int armorDurabilityMultiplier, int boots, int leggings, int chestplate, int helmet,
            float toughness, float knockbackResistance, int armorEnchantability, Holder<SoundEvent> equipSound,
            boolean fireResistant) {
        this.id = id;
        this.axeDamage = axeDamage;
        this.axeSpeed = axeSpeed;
        this.hoeDamage = hoeDamage;
        this.hoeSpeed = hoeSpeed;
        this.fireResistant = fireResistant;

        // data/materialgear/tags/item/gear_materials/<id>.json: used for crafting and anvil repair
        this.materialTag = TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(MaterialGear.MODID, "gear_materials/" + id));

        this.toolMaterial = new ToolMaterial(incorrectBlocksForDrops, durability, speed, attackDamageBonus,
            toolEnchantability, this.materialTag);

        Map<ArmorType, Integer> defense = new EnumMap<>(ArmorType.class);
        defense.put(ArmorType.BOOTS, boots);
        defense.put(ArmorType.LEGGINGS, leggings);
        defense.put(ArmorType.CHESTPLATE, chestplate);
        defense.put(ArmorType.HELMET, helmet);
        defense.put(ArmorType.BODY, chestplate);

        // assets/materialgear/equipment/<id>.json: points at the worn armor textures
        ResourceKey<EquipmentAsset> asset = ResourceKey.create(EquipmentAssets.ROOT_ID,
            Identifier.fromNamespaceAndPath(MaterialGear.MODID, id));

        this.armorMaterial = new ArmorMaterial(armorDurabilityMultiplier, defense, armorEnchantability, equipSound,
            toughness, knockbackResistance, this.materialTag, asset);
    }

    public String id() {
        return this.id;
    }

    public ToolMaterial toolMaterial() {
        return this.toolMaterial;
    }

    public ArmorMaterial armorMaterial() {
        return this.armorMaterial;
    }

    public float axeDamage() {
        return this.axeDamage;
    }

    public float axeSpeed() {
        return this.axeSpeed;
    }

    public float hoeDamage() {
        return this.hoeDamage;
    }

    public float hoeSpeed() {
        return this.hoeSpeed;
    }

    public boolean fireResistant() {
        return this.fireResistant;
    }

    public TagKey<Item> materialTag() {
        return this.materialTag;
    }
}
