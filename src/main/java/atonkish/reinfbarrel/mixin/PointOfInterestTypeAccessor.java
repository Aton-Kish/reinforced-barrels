package atonkish.reinfbarrel.mixin;

import java.util.Map;
import java.util.Set;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.block.BlockState;
import net.minecraft.world.poi.PointOfInterestType;

@Mixin(PointOfInterestType.class)
public interface PointOfInterestTypeAccessor {
    @Accessor("BLOCK_STATE_TO_POINT_OF_INTEREST_TYPE")
    public static Map<BlockState, PointOfInterestType> getBlockStateToPointOfInterestType() {
        throw new AssertionError();
    }

    @Accessor
    public Set<BlockState> getBlockStates();

    @Mutable
    @Accessor("blockStates")
    public void setBlockStates(Set<BlockState> blockStates);
}
