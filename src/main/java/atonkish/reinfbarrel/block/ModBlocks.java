package atonkish.reinfbarrel.block;

import java.util.LinkedHashMap;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import atonkish.reinfcore.util.ReinforcingMaterial;

public class ModBlocks {
    public static final Map<ReinforcingMaterial, Block> REINFORCED_BARREL_MAP = new LinkedHashMap<>();;
    public static final Map<ReinforcingMaterial, Block.Settings> REINFORCED_BARREL_SETTINGS_MAP = new LinkedHashMap<>();;

    public static Block registerMaterial(String namespace, ReinforcingMaterial material, Block.Settings settings) {
        if (!REINFORCED_BARREL_SETTINGS_MAP.containsKey(material)) {
            REINFORCED_BARREL_SETTINGS_MAP.put(material, settings);
        }

        if (!REINFORCED_BARREL_MAP.containsKey(material)) {
            Block block = ModBlocks.register(namespace, material.getName() + "_barrel",
                    new ReinforcedBarrelBlock(material, REINFORCED_BARREL_SETTINGS_MAP.get(material)));
            REINFORCED_BARREL_MAP.put(material, block);
        }

        return REINFORCED_BARREL_MAP.get(material);
    }

    private static Block register(String namespace, String id, Block block) {
        return Registry.register(Registries.BLOCK, new Identifier(namespace, id), block);
    }
}
