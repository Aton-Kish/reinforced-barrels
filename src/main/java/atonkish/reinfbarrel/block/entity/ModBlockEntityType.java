package atonkish.reinfbarrel.block.entity;

import java.util.LinkedHashMap;
import java.util.Map;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

import atonkish.reinfcore.util.ReinforcingMaterial;
import atonkish.reinfbarrel.block.ModBlocks;

public class ModBlockEntityType {
    public static final Map<ReinforcingMaterial, BlockEntityType<ReinforcedBarrelBlockEntity>> REINFORCED_BARREL_MAP = new LinkedHashMap<>();

    public static BlockEntityType<ReinforcedBarrelBlockEntity> registerMaterial(String namespace,
            ReinforcingMaterial material) {
        if (!REINFORCED_BARREL_MAP.containsKey(material)) {
            String id = material.getName() + "_barrel";
            FabricBlockEntityTypeBuilder<ReinforcedBarrelBlockEntity> builder = FabricBlockEntityTypeBuilder.create(
                    ModBlockEntityType.createBlockEntityTypeFactory(material),
                    ModBlocks.REINFORCED_BARREL_MAP.get(material));
            BlockEntityType<ReinforcedBarrelBlockEntity> blockEntityType = ModBlockEntityType.create(namespace, id,
                    builder);
            REINFORCED_BARREL_MAP.put(material, blockEntityType);
        }

        return REINFORCED_BARREL_MAP.get(material);
    }

    private static BlockEntityType<ReinforcedBarrelBlockEntity> create(String namespace, String id,
            FabricBlockEntityTypeBuilder<ReinforcedBarrelBlockEntity> builder) {
        return Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(namespace, id),
                builder.build(null));
    }

    private static FabricBlockEntityTypeBuilder.Factory<ReinforcedBarrelBlockEntity> createBlockEntityTypeFactory(
            ReinforcingMaterial material) {
        return (BlockPos blockPos, BlockState blockState) -> new ReinforcedBarrelBlockEntity(material, blockPos,
                blockState);
    }
}
