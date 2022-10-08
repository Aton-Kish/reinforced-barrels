package atonkish.reinfbarrel;

import net.fabricmc.loader.api.FabricLoader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import atonkish.reinfcore.api.ReinforcedCoreModInitializer;
import atonkish.reinfcore.api.ReinforcedCoreRegistry;
import atonkish.reinfcore.util.ReinforcingMaterial;
import atonkish.reinfbarrel.api.ReinforcedBarrelsModInitializer;
import atonkish.reinfbarrel.api.ReinforcedBarrelsRegistry;
import atonkish.reinfbarrel.util.ReinforcingMaterialSettings;
import atonkish.reinfbarrel.world.poi.ModPointOfInterestType;

public class ReinforcedBarrelsMod implements ReinforcedCoreModInitializer {
	public static final String MOD_ID = "reinfbarrel";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitializeReinforcedCore() {
		// init Reinforced Core
		initializeReinforcedCore();

		// init Reinforced Barrels
		initializeReinforcedBarrels();

		// entrypoint: "reinfbarrel"
		FabricLoader.getInstance()
				.getEntrypoints(MOD_ID, ReinforcedBarrelsModInitializer.class)
				.forEach(ReinforcedBarrelsModInitializer::onInitializeReinforcedBarrels);

		// Point of Interest Types
		ModPointOfInterestType.init();
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

	private static void initializeReinforcedBarrels() {
		for (ReinforcingMaterialSettings materialSettings : ReinforcingMaterialSettings.values()) {
			ReinforcingMaterial material = materialSettings.getMaterial();

			// Stats
			ReinforcedBarrelsRegistry.registerMaterialOpenStat(MOD_ID, material);

			// Blocks
			ReinforcedBarrelsRegistry.registerMaterialBlock(MOD_ID, material, materialSettings.getBlockSettings());
			ReinforcedBarrelsRegistry.registerMaterialBlockEntityType(MOD_ID, material);

			// Items
			ReinforcedBarrelsRegistry.registerMaterialItem(MOD_ID, material, materialSettings.getItemSettings());
		}

		// Item Group Icon
		if (!(FabricLoader.getInstance().isModLoaded("reinfshulker")
				|| FabricLoader.getInstance().isModLoaded("reinfchest"))) {
			ReinforcedBarrelsRegistry
					.registerMaterialItemGroupIcon(MOD_ID, ReinforcingMaterialSettings.NETHERITE.getMaterial());
		}
	}
}
