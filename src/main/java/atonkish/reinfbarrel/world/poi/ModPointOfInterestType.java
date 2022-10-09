package atonkish.reinfbarrel.world.poi;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.world.poi.PointOfInterestType;

import atonkish.reinfbarrel.block.ModBlocks;
import atonkish.reinfbarrel.mixin.PointOfInterestTypeAccessor;

public class ModPointOfInterestType {
    public static void init() {
        Map<BlockState, PointOfInterestType> blockStateToPointOfInterestType = PointOfInterestTypeAccessor
                .getBlockStateToPointOfInterestType();

        List<BlockState> fishermanBlockStates = new ArrayList<BlockState>(
                ((PointOfInterestTypeAccessor) PointOfInterestType.FISHERMAN).getBlockStates());

        for (Block block : ModBlocks.REINFORCED_BARREL_MAP.values()) {
            ImmutableList<BlockState> blockStates = block.getStateManager().getStates();

            for (BlockState blockState : blockStates) {
                blockStateToPointOfInterestType.putIfAbsent(blockState, PointOfInterestType.FISHERMAN);
            }

            fishermanBlockStates.addAll(blockStates);
        }

        ((PointOfInterestTypeAccessor) PointOfInterestType.FISHERMAN)
                .setBlockStates(ImmutableSet.copyOf(fishermanBlockStates));
    }
}
