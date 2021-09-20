package atonkish.reinfbarrel.stat;

import java.util.HashMap;

import net.minecraft.stat.StatFormatter;
import net.minecraft.stat.Stats;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import atonkish.reinfcore.util.ReinforcingMaterial;
import atonkish.reinfbarrel.ReinforcedBarrelsMod;

public class ModStats {
    public static final HashMap<ReinforcingMaterial, Identifier> OPEN_REINFORCED_BARREL_MAP;

    public static void init() {
    }

    private static Identifier register(String id, StatFormatter formatter) {
        Identifier identifier = new Identifier(ReinforcedBarrelsMod.MOD_ID, id);
        Registry.register(Registry.CUSTOM_STAT, id, identifier);
        Stats.CUSTOM.getOrCreateStat(identifier, formatter);
        return identifier;
    }

    static {
        OPEN_REINFORCED_BARREL_MAP = new HashMap<>();
        for (ReinforcingMaterial material : ReinforcingMaterial.values()) {
            Identifier identifier = register("open_" + material.getName() + "_barrel", StatFormatter.DEFAULT);
            OPEN_REINFORCED_BARREL_MAP.put(material, identifier);
        }
    }
}
