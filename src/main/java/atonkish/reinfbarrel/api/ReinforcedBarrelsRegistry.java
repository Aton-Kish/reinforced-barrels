package atonkish.reinfbarrel.api;

import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

import atonkish.reinfcore.util.ReinforcingMaterial;
import atonkish.reinfbarrel.block.ModBlocks;
import atonkish.reinfbarrel.block.entity.ModBlockEntityType;
import atonkish.reinfbarrel.block.entity.ReinforcedBarrelBlockEntity;
import atonkish.reinfbarrel.item.ModItems;
import atonkish.reinfbarrel.stat.ModStats;

public class ReinforcedBarrelsRegistry {
    public static Identifier registerMaterialOpenStat(String namespace, ReinforcingMaterial material) {
        return ModStats.registerMaterialOpen(namespace, material);
    }

    public static Block registerMaterialBlock(String namespace, ReinforcingMaterial material, Block.Settings settings) {
        return ModBlocks.registerMaterial(namespace, material, settings);
    }

    public static BlockEntityType<ReinforcedBarrelBlockEntity> registerMaterialBlockEntityType(String namespace,
            ReinforcingMaterial material) {
        return ModBlockEntityType.registerMaterial(namespace, material);
    }

    public static Item registerMaterialItem(String namespace, ReinforcingMaterial material, Item.Settings settings) {
        return ModItems.registerMaterial(material, settings);
    }

    public static void registerMaterialItemGroupIcon(String namespace, ReinforcingMaterial material) {
        ModItems.registerMaterialItemGroupIcon(material);
    }
}
