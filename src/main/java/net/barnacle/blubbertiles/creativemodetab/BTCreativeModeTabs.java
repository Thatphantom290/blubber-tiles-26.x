package net.barnacle.blubbertiles.creativemodetab;

import net.barnacle.blubbertiles.BlubberTiles;
import net.barnacle.blubbertiles.block.BTBlocks;
import net.fabricmc.fabric.api.creativetab.v1.FabricCreativeModeTab;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class BTCreativeModeTabs {
    /*
    public static final CreativeModeTab BLUBBER_TILES_BLOCKS = Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB,
            Identifier.fromNamespaceAndPath(BlubberTiles.MOD_ID, "blubber_tiles_blocks"),
            FabricCreativeModeTab.builder().icon(() -> new ItemStack(BTBlocks.))
                    .title(Component.translatable("creativemodetab.blubbertiles.blubber-tiles_blocks"))
                    .displayItems((parameters, output) -> {



                    }).build());
     */

    public static final CreativeModeTab BLUBBER_TILES_FLORA = Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB,
            Identifier.fromNamespaceAndPath(BlubberTiles.MOD_ID, "blubber_tiles_flora"),
            FabricCreativeModeTab.builder().icon(() -> new ItemStack(BTBlocks.MUSTARD_CHIMP))
                    .title(Component.translatable("creativemodetab.blubbertiles.blubber_tiles_flora"))
                    .displayItems((parameters, output) -> {
                        output.accept(BTBlocks.MUSTARD_CHIMP);
                        output.accept(BTBlocks.RED_TONGUE);



                    }).build());


    public static void registerModCreativeModeTabs() {
        BlubberTiles.LOGGER.info("Registering Creative Mode Tabs for " + BlubberTiles.MOD_ID);
    }
}
