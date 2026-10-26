# Material Gear (Forge 26.3)

Adds a full set of gear (sword, pickaxe, axe, shovel, hoe, helmet, chestplate, leggings, boots) for eight materials: **Leaf, Dirt, Hardwood, Coal, Cobblestone, Lapis, Emerald, Obsidian**. That is 72 items plus one block (Compressed Cobblestone). Everything has its own creative tab and also shows up in the vanilla Tools and Combat tabs.

Built for **Minecraft 26.3** with **Forge 66.1.0** (Java 25).

## Building

1. Install a Java 25 JDK (Eclipse Temurin is the one Forge recommends).
2. In this folder run `./gradlew build` (Windows: `gradlew.bat build`). The first run downloads Minecraft and Forge and takes a few minutes.
3. The mod jar ends up in `build/libs/materialgear-1.0.0.jar`. Drop it into the `mods` folder of a Forge 26.3 install.

To test in a dev client instead: `./gradlew runClient`.

## Crafting

Every set uses the standard vanilla shapes (sticks for tool handles), with the set's material:

| Set | Material |
|---|---|
| Leaf | any leaves |
| Dirt | dirt, coarse dirt or rooted dirt |
| Hardwood | any log or wood (not planks, so it never clashes with vanilla wooden tools) |
| Coal | coal or charcoal |
| Cobblestone | Compressed Cobblestone (9 cobblestone in a 3x3; craft it back into 9 cobblestone) |
| Lapis | lapis lazuli |
| Emerald | emerald |
| Obsidian | obsidian |

Gear is repaired in an anvil with the same material. Recipes appear in the recipe book as soon as you pick up the material.

## Tool stats

Damage is the sword's total attack damage. Vanilla for comparison: stone 131 durability / 5 damage, iron 250 / 6, diamond 1561 / 7, netherite 2031 / 8.

| Set | Mines up to | Durability | Mining speed | Sword damage | Enchantability |
|---|---|---|---|---|---|
| Leaf | wood tier | 32 | 2.0 | 4 | 22 |
| Dirt | wood tier | 48 | 2.0 | 4 | 6 |
| Hardwood | stone tier (iron ore) | 120 | 3.0 | 4.5 | 15 |
| Coal | stone tier | 170 | 5.5 | 5 | 12 |
| Cobblestone | stone tier | 200 | 4.0 | 5.5 | 5 |
| Lapis | iron tier (diamond ore) | 300 | 6.5 | 6 | 30 |
| Emerald | diamond tier (obsidian) | 1300 | 8.5 | 7 | 20 |
| Obsidian | diamond tier | 1900 | 6.0 | 7.5 | 8 |

## Armor stats

Defense is boots / leggings / chestplate / helmet. Vanilla for comparison: iron 2/5/6/2 (15), diamond 3/6/8/3 (20) with 2 toughness, netherite 3/6/8/3 with 3 toughness and 0.1 knockback resistance.

| Set | Defense | Total | Toughness | Knockback res. | Durability (helm/chest/legs/boots) | Enchantability |
|---|---|---|---|---|---|---|
| Leaf | 1/1/2/1 | 5 | 0 | 0 | 44 / 64 / 60 / 52 | 22 |
| Dirt | 1/2/2/1 | 6 | 0 | 0 | 55 / 80 / 75 / 65 | 6 |
| Hardwood | 1/2/3/1 | 7 | 0 | 0 | 88 / 128 / 120 / 104 | 15 |
| Coal | 1/3/4/2 | 10 | 0 | 0 | 121 / 176 / 165 / 143 | 12 |
| Cobblestone | 2/4/5/2 | 13 | 0 | 0.05 | 143 / 208 / 195 / 169 | 5 |
| Lapis | 2/5/6/2 | 15 | 0 | 0 | 176 / 256 / 240 / 208 | 30 |
| Emerald | 3/6/7/3 | 19 | 1.5 | 0 | 308 / 448 / 420 / 364 | 20 |
| Obsidian | 3/6/8/3 | 20 | 2.5 | 0.1 | 385 / 560 / 525 / 455 | 8 |

Obsidian gear is also fireproof (it survives lava and fire as a dropped item, like netherite).

All numbers live in one table in `src/main/java/com/klobe/materialgear/GearSet.java` if you want to rebalance.

## Notes

- Lapis gear has the best enchantability in the game, so it rolls very strong enchantments.
- The axes strip logs, shovels make paths and hoes till farmland, through 26.3's new block transformer system.
- Swords get sweeping edge, and all armor can take armor trims.
- Baby mobs (26.1+ has a separate baby armor texture layout) won't show this armor if they pick it up; adults and players render it normally.
