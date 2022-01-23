package atonkish.reinfbarrel.block;

import java.util.LinkedHashMap;

import net.minecraft.block.Block;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import atonkish.reinfcore.util.ReinforcingMaterial;

public class ModBlocks {
    public static final LinkedHashMap<ReinforcingMaterial, Block> REINFORCED_BARREL_MAP = new LinkedHashMap<>();;
    public static final LinkedHashMap<ReinforcingMaterial, Block.Settings> REINFORCED_BARREL_SETTINGS_MAP = new LinkedHashMap<>();;

    public static Block registerMaterial(String namespace, ReinforcingMaterial material, Block.Settings settings) {
        REINFORCED_BARREL_SETTINGS_MAP.put(material, settings);

        Block block = register(namespace, material.getName() + "_barrel",
                new ReinforcedBarrelBlock(material, REINFORCED_BARREL_SETTINGS_MAP.get(material)));

        REINFORCED_BARREL_MAP.put(material, block);

        return block;
    }

    private static Block register(String namespace, String id, Block block) {
        return Registry.register(Registry.BLOCK, new Identifier(namespace, id), block);
    }
}
