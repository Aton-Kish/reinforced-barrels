package atonkish.reinfbarrel.stat;

import java.util.LinkedHashMap;

import net.minecraft.stat.StatFormatter;
import net.minecraft.stat.Stats;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import atonkish.reinfcore.util.ReinforcingMaterial;
import atonkish.reinfbarrel.ReinforcedBarrelsMod;

public class ModStats {
    public static final LinkedHashMap<ReinforcingMaterial, Identifier> OPEN_REINFORCED_BARREL_MAP = new LinkedHashMap<>();

    public static Identifier registerMaterialOpen(ReinforcingMaterial material) {
        String id = "open_" + material.getName() + "_barrel";
        Identifier identifier = register(id, StatFormatter.DEFAULT);

        OPEN_REINFORCED_BARREL_MAP.put(material, identifier);

        return identifier;
    }

    private static Identifier register(String id, StatFormatter formatter) {
        Identifier identifier = new Identifier(ReinforcedBarrelsMod.MOD_ID, id);
        Registry.register(Registry.CUSTOM_STAT, id, identifier);
        Stats.CUSTOM.getOrCreateStat(identifier, formatter);
        return identifier;
    }
}
