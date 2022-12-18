package atonkish.reinfbarrel.world.poi;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.poi.PointOfInterestType;
import net.minecraft.world.poi.PointOfInterestTypes;

import atonkish.reinfbarrel.block.ModBlocks;
import atonkish.reinfbarrel.mixin.PointOfInterestTypesAccessor;

public class ModPointOfInterestTypes {
    public static void init() {
        Map<BlockState, RegistryEntry<PointOfInterestType>> poiStatesToType = PointOfInterestTypesAccessor
                .getPointOfInterestStatesToType();

        RegistryEntry<PointOfInterestType> fishermanEntry = Registries.POINT_OF_INTEREST_TYPE
                .getEntry(PointOfInterestTypes.FISHERMAN).get();

        PointOfInterestType fishermanPoiType = Registries.POINT_OF_INTEREST_TYPE.get(PointOfInterestTypes.FISHERMAN);

        // NOTE: PointOfInterestType.blockStates is accessible by access widener
        List<BlockState> fishermanBlockStates = new ArrayList<BlockState>(fishermanPoiType.blockStates);

        for (Block block : ModBlocks.REINFORCED_BARREL_MAP.values()) {
            ImmutableList<BlockState> blockStates = block.getStateManager().getStates();

            for (BlockState blockState : blockStates) {
                poiStatesToType.putIfAbsent(blockState, fishermanEntry);
            }

            fishermanBlockStates.addAll(blockStates);
        }

        // NOTE: PointOfInterestType.blockStates is mutable by access widener
        fishermanPoiType.blockStates = ImmutableSet.copyOf(fishermanBlockStates);
    }
}
