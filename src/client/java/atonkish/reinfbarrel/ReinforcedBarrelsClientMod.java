package atonkish.reinfbarrel;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import atonkish.reinfcore.api.ReinforcedCoreClientModInitializer;
import atonkish.reinfcore.api.ReinforcedCoreClientRegistry;
import atonkish.reinfcore.util.ReinforcingMaterial;
import atonkish.reinfbarrel.util.ReinforcingMaterialSettings;

@Environment(EnvType.CLIENT)
public class ReinforcedBarrelsClientMod implements ReinforcedCoreClientModInitializer {
	@Override
	public void onInitializeReinforcedCoreClient() {
		// init Reinforced Core
		initializeReinforcedCoreClient();
	}

	private static void initializeReinforcedCoreClient() {
		for (ReinforcingMaterialSettings materialSettings : ReinforcingMaterialSettings.values()) {
			ReinforcingMaterial material = materialSettings.getMaterial();

			// Reinforced Storage Screen
			ReinforcedCoreClientRegistry.registerMaterialSingleBlockScreen(material);
		}
	}
}
