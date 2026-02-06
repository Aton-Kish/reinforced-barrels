package atonkish.reinfbarrel.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BarrelBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BarrelBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.mob.PiglinBrain;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import org.jetbrains.annotations.Nullable;

import atonkish.reinfbarrel.block.entity.ReinforcedBarrelBlockEntity;
import atonkish.reinfbarrel.stat.ModStats;
import atonkish.reinfcore.util.ReinforcingMaterial;

public class ReinforcedBarrelBlock extends BarrelBlock {
  private final ReinforcingMaterial material;

  public ReinforcedBarrelBlock(ReinforcingMaterial material, AbstractBlock.Settings settings) {
    super(settings);
    this.material = material;
  }

  @Override
  public ActionResult onUse(
      BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
    if (world instanceof ServerWorld serverWorld
        && world.getBlockEntity(pos) instanceof BarrelBlockEntity barrelBlockEntity) {
      player.openHandledScreen(barrelBlockEntity);
      player.incrementStat(ModStats.OPEN_REINFORCED_BARREL_MAP.get(this.material));
      PiglinBrain.onGuardedBlockInteracted(serverWorld, player, true);
    }

    return ActionResult.SUCCESS;
  }

  @Override
  @Nullable public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
    return new ReinforcedBarrelBlockEntity(this.material, pos, state);
  }

  public ReinforcingMaterial getMaterial() {
    return this.material;
  }
}
