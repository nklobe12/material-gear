package com.klobe.materialgear;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MaterialGear.MODID);

    /** Nine cobblestone packed into one block. The cobblestone gear is made from this, so it never clashes with vanilla stone tools. */
    public static final RegistryObject<Block> COMPRESSED_COBBLESTONE = BLOCKS.register("compressed_cobblestone",
        () -> new Block(BlockBehaviour.Properties.of()
            .setId(BLOCKS.key("compressed_cobblestone"))
            .mapColor(MapColor.STONE)
            .requiresCorrectToolForDrops()
            .strength(3.0F, 9.0F)
            .sound(SoundType.STONE)
        )
    );

    private ModBlocks() {}
}
