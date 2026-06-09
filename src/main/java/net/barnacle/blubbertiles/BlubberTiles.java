package net.barnacle.blubbertiles;

import net.barnacle.blubbertiles.block.BTBlocks;
import net.barnacle.blubbertiles.creativemodetab.BTCreativeModeTabs;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BlubberTiles implements ModInitializer {
	public static final String MOD_ID = "blubbertiles";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {

		BTCreativeModeTabs.registerModCreativeModeTabs();

		BTBlocks.registerModBlocks();
	}
}