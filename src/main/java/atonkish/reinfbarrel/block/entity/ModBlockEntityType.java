package atonkish.reinfbarrel.block.entity;

import java.util.HashMap;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;

import atonkish.reinfcore.util.ReinforcingMaterial;
import atonkish.reinfcore.util.ReinforcingMaterials;
import atonkish.reinfbarrel.ReinforcedBarrelsMod;
import atonkish.reinfbarrel.block.ModBlocks;

public class ModBlockEntityType {
    public static final HashMap<ReinforcingMaterial, BlockEntityType<ReinforcedBarrelBlockEntity>> REINFORCED_BARREL_MAP;

    public static void init() {
    }

    private static BlockEntityType<ReinforcedBarrelBlockEntity> create(String id,
            FabricBlockEntityTypeBuilder<ReinforcedBarrelBlockEntity> builder) {
        return Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(ReinforcedBarrelsMod.MOD_ID, id),
                builder.build(null));
    }

    private static FabricBlockEntityTypeBuilder.Factory<ReinforcedBarrelBlockEntity> createBlockEntityTypeFactory(
            ReinforcingMaterial material) {
        return (BlockPos blockPos, BlockState blockState) -> new ReinforcedBarrelBlockEntity(material, blockPos,
                blockState);
    }

    static {
        REINFORCED_BARREL_MAP = new HashMap<>();
        for (ReinforcingMaterial material : ReinforcingMaterials.MAP.values()) {
            BlockEntityType<ReinforcedBarrelBlockEntity> blockEntityType = create(material.getName() + "_barrel",
                    FabricBlockEntityTypeBuilder.create(createBlockEntityTypeFactory(material),
                            ModBlocks.REINFORCED_BARREL_MAP.get(material)));
            REINFORCED_BARREL_MAP.put(material, blockEntityType);
        }
    }
}
