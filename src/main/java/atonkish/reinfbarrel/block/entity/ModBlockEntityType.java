package atonkish.reinfbarrel.block.entity;

import java.util.LinkedHashMap;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;

import atonkish.reinfcore.util.ReinforcingMaterial;
import atonkish.reinfbarrel.block.ModBlocks;

public class ModBlockEntityType {
    public static final LinkedHashMap<ReinforcingMaterial, BlockEntityType<ReinforcedBarrelBlockEntity>> REINFORCED_BARREL_MAP = new LinkedHashMap<>();

    public static BlockEntityType<ReinforcedBarrelBlockEntity> registerMaterial(String namespace,
            ReinforcingMaterial material) {
        String id = material.getName() + "_barrel";
        FabricBlockEntityTypeBuilder<ReinforcedBarrelBlockEntity> builder = FabricBlockEntityTypeBuilder.create(
                createBlockEntityTypeFactory(material),
                ModBlocks.REINFORCED_BARREL_MAP.get(material));
        BlockEntityType<ReinforcedBarrelBlockEntity> blockEntityType = create(namespace, id, builder);

        REINFORCED_BARREL_MAP.put(material, blockEntityType);

        return blockEntityType;
    }

    private static BlockEntityType<ReinforcedBarrelBlockEntity> create(String namespace, String id,
            FabricBlockEntityTypeBuilder<ReinforcedBarrelBlockEntity> builder) {
        return Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(namespace, id),
                builder.build(null));
    }

    private static FabricBlockEntityTypeBuilder.Factory<ReinforcedBarrelBlockEntity> createBlockEntityTypeFactory(
            ReinforcingMaterial material) {
        return (BlockPos blockPos, BlockState blockState) -> new ReinforcedBarrelBlockEntity(material, blockPos,
                blockState);
    }
}
