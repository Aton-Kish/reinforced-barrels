package atonkish.reinfbarrel.world.poi;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.OptionalInt;
import java.util.Set;

import com.google.common.collect.ImmutableSet;
import com.mojang.serialization.Lifecycle;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.MutableRegistry;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.poi.PointOfInterestType;

import atonkish.reinfbarrel.block.ModBlocks;
import atonkish.reinfbarrel.mixin.PointOfInterestTypesAccessor;

public class ModPointOfInterestTypes {
    public static void init() {
        Identifier id = new Identifier("fisherman");

        Collection<Block> blockCollection = new ArrayList<Block>();
        blockCollection.add(Blocks.BARREL);
        blockCollection.addAll(ModBlocks.REINFORCED_BARREL_MAP.values());

        Set<BlockState> blockStates = blockCollection.stream()
                .flatMap(block -> block.getStateManager().getStates().stream())
                .collect(ImmutableSet.toImmutableSet());

        PointOfInterestType updatedPoiType = new PointOfInterestType(blockStates, 1, 1);
        PointOfInterestType originalPoiType = Registry.POINT_OF_INTEREST_TYPE.get(id);

        int rawId = Registry.POINT_OF_INTEREST_TYPE.getRawId(originalPoiType);
        RegistryKey<PointOfInterestType> key = RegistryKey.of(Registry.POINT_OF_INTEREST_TYPE_KEY, id);

        ((MutableRegistry<PointOfInterestType>) Registry.POINT_OF_INTEREST_TYPE).replace(OptionalInt.of(rawId), key,
                updatedPoiType, Lifecycle.stable());

        Map<BlockState, RegistryEntry<PointOfInterestType>> poiStatesToType = PointOfInterestTypesAccessor
                .getPointOfInterestStatesToType();
        RegistryEntry<PointOfInterestType> poiTypeEntry = Registry.POINT_OF_INTEREST_TYPE.entryOf(key);
        poiTypeEntry.value().blockStates().forEach(blockState -> {
            if (!poiStatesToType.containsKey(blockState)) {
                poiStatesToType.put(blockState, poiTypeEntry);
            }
        });
    }
}
