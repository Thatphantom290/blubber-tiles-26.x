package net.barnacle.blubbertiles.block;

import net.barnacle.blubbertiles.BlubberTiles;
import net.barnacle.blubbertiles.block.custom.DoublePlantWithRotationBlock;
import net.barnacle.blubbertiles.block.custom.WallOrFloorPlantBlock;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;

import java.util.function.Function;

public class BTBlocks {

    public static final Block RED_TONGUE = registerBlock("red_tongue",
             properties -> new WallOrFloorPlantBlock(properties
                    .mapColor(MapColor.PLANT)
                    .noCollision()
                    .instabreak()
                    .sound(SoundType.GRASS)
            )
    );

    public static final Block MUSTARD_CHIMP = registerBlock("mustard_chimp",
            properties -> new DoublePlantWithRotationBlock(properties
                    .mapColor(MapColor.GRASS)
                    .noCollision()
                    .instabreak()
                    .sound(SoundType.GRASS)
                    .offsetType(BlockBehaviour.OffsetType.XZ)
            )
    );
    private static Block registerBlock(String name, Function<BlockBehaviour.Properties, Block> function) {
        Identifier id = Identifier.fromNamespaceAndPath(BlubberTiles.MOD_ID, name);
        ResourceKey<Block> blockKey = ResourceKey.create(Registries.BLOCK, id);

        Block toRegister = function.apply(BlockBehaviour.Properties.of().setId(blockKey));
        registerBlockItem(name, toRegister);

        return Registry.register(BuiltInRegistries.BLOCK, id, toRegister);
    }

    private static void registerBlockItem(String name, Block block) {
        Identifier id = Identifier.fromNamespaceAndPath(BlubberTiles.MOD_ID, name);
        ResourceKey<Item> itemKey = ResourceKey.create(Registries.ITEM, id);

        Registry.register(BuiltInRegistries.ITEM, id,
                new BlockItem(block, new Item.Properties().useBlockDescriptionPrefix().setId(itemKey)));
    }

    public static void registerModBlocks() {
        BlubberTiles.LOGGER.info("Registering Mod Blocks for " + BlubberTiles.MOD_ID);
    }
}