package atonkish.reinfbarrel.block.entity;

import java.util.LinkedHashMap;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;

import atonkish.reinfcore.util.ReinforcingMaterial;

import atonkish.reinfbarrel.block.ModBlocks;
import atonkish.reinfbarrel.mixin.BlockEntityTypeAccessor;
import atonkish.reinfbarrel.mixin.BlockEntityTypeInvoker;

public class ModBlockEntityType {
    public static final Map<ReinforcingMaterial, BlockEntityType<ReinforcedBarrelBlockEntity>> REINFORCED_BARREL_MAP = new LinkedHashMap<>();

    public static BlockEntityType<ReinforcedBarrelBlockEntity> registerMaterial(String namespace,
            ReinforcingMaterial material) {
        if (!REINFORCED_BARREL_MAP.containsKey(material)) {
            String id = material.getName() + "_barrel";
            Block block = ModBlocks.REINFORCED_BARREL_MAP.get(material);
            BlockEntityType<ReinforcedBarrelBlockEntity> blockEntityType = BlockEntityTypeInvoker.create(
                    Identifier.of(namespace, id).toString(),
                    (blockPos, blockState) -> new ReinforcedBarrelBlockEntity(material, blockPos, blockState),
                    block);
            REINFORCED_BARREL_MAP.put(material, blockEntityType);

            ((BlockEntityTypeAccessor) BlockEntityType.BARREL).getBlocks().add(block);
        }

        return REINFORCED_BARREL_MAP.get(material);
    }
}
