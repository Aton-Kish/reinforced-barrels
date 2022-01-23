package atonkish.reinfbarrel;

import net.fabricmc.loader.api.FabricLoader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import atonkish.reinfcore.api.ReinforcedCoreModInitializer;
import atonkish.reinfcore.api.ReinforcedCoreRegistry;
import atonkish.reinfcore.util.ReinforcingMaterial;
import atonkish.reinfbarrel.api.ReinforcedBarrelsModInitializer;
import atonkish.reinfbarrel.util.ReinforcingMaterialSettings;

public class ReinforcedBarrelsMod implements ReinforcedCoreModInitializer {
	public static final String MOD_ID = "reinfbarrel";
	public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

	@Override
	public void onInitializeReinforcedCore() {
		// init Reinforced Core
		initializeReinforcedCore();

		// entrypoint: "reinfbarrel"
		FabricLoader.getInstance()
				.getEntrypoints(MOD_ID, ReinforcedBarrelsModInitializer.class)
				.forEach(ReinforcedBarrelsModInitializer::onInitializeReinforcedBarrels);
	}

	private static void initializeReinforcedCore() {
		for (ReinforcingMaterialSettings materialSettings : ReinforcingMaterialSettings.values()) {
			ReinforcingMaterial material = materialSettings.getMaterial();

			// Reinforced Storage Screen Model
			ReinforcedCoreRegistry.registerMaterialSingleBlockScreenModel(material);

			// Reinforced Storage Screen Handler
			ReinforcedCoreRegistry.registerMaterialSingleBlockScreenHandler(material);
		}
	}
}
