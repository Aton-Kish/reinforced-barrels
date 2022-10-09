package atonkish.reinfbarrel.mixin;

import java.util.Map;

import net.minecraft.block.BlockState;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.poi.PointOfInterestType;
import net.minecraft.world.poi.PointOfInterestTypes;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(PointOfInterestTypes.class)
public interface PointOfInterestTypesAccessor {
    @Accessor("POI_STATES_TO_TYPE")
    static Map<BlockState, RegistryEntry<PointOfInterestType>> getPointOfInterestStatesToType() {
        throw new UnsupportedOperationException();
    }
}
