package atonkish.reinfbarrel;

import net.fabricmc.api.ModInitializer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import atonkish.reinfbarrel.block.ModBlocks;
import atonkish.reinfbarrel.block.entity.ModBlockEntityType;
import atonkish.reinfbarrel.stat.ModStats;

public class ReinforcedBarrelsMod implements ModInitializer {
	public static final String MOD_ID = "reinfbarrel";
	public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		// Stats
		ModStats.init();

		// Blocks
		ModBlocks.init();
		ModBlockEntityType.init();
	}
}
