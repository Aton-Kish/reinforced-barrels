package atonkish.reinfbarrel;

import net.fabricmc.api.ModInitializer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import atonkish.reinfbarrel.stat.ModStats;

public class ReinforcedBarrelsMod implements ModInitializer {
	public static final String MOD_ID = "reinfbarrel";
	public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		// Stats
		ModStats.init();
	}
}
