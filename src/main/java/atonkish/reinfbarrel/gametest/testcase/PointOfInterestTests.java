package atonkish.reinfbarrel.gametest.testcase;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.village.VillagerProfession;

import atonkish.reinfbarrel.ReinforcedBarrelsMod;
import atonkish.reinfbarrel.block.ModBlocks;
import atonkish.reinfbarrel.gametest.util.TestIdentifier;
import atonkish.reinfcore.gametest.TestFunction;
import atonkish.reinfcore.util.ReinforcingMaterials;

public class PointOfInterestTests {
  private static final String TEST_ENVIRONMENT_DEFAULT =
      String.format("%s:point_of_interest/default", ReinforcedBarrelsMod.MOD_ID);
  private static final String TEST_STRUCTURE_EMPTY = "fabric-gametest-api-v1:empty";

  public static final Collection<TestFunction> TEST_FUNCTIONS =
      new ArrayList<>() {
        {
          // Copper Barrel
          add(
              PointOfInterestTests.createTest(
                  "Villager have a fisherman profession at Copper Barrel",
                  ModBlocks.REINFORCED_BARREL_MAP.get(ReinforcingMaterials.MAP.get("copper"))));

          // Iron Barrel
          add(
              PointOfInterestTests.createTest(
                  "Villager have a fisherman profession at Iron Barrel",
                  ModBlocks.REINFORCED_BARREL_MAP.get(ReinforcingMaterials.MAP.get("iron"))));

          // Gold Barrel
          add(
              PointOfInterestTests.createTest(
                  "Villager have a fisherman profession at Gold Barrel",
                  ModBlocks.REINFORCED_BARREL_MAP.get(ReinforcingMaterials.MAP.get("gold"))));

          // Diamond Barrel
          add(
              PointOfInterestTests.createTest(
                  "Villager have a fisherman profession at Diamond Barrel",
                  ModBlocks.REINFORCED_BARREL_MAP.get(ReinforcingMaterials.MAP.get("diamond"))));

          // Netherite Barrel
          add(
              PointOfInterestTests.createTest(
                  "Villager have a fisherman profession at Netherite Barrel",
                  ModBlocks.REINFORCED_BARREL_MAP.get(ReinforcingMaterials.MAP.get("netherite"))));
        }
      };

  private static TestFunction createTest(String name, Block barrelBlock) {
    Identifier testIdentifier =
        TestIdentifier.of(ReinforcedBarrelsMod.MOD_ID, PointOfInterestTests.class, name);

    return new TestFunction(
        testIdentifier,
        PointOfInterestTests.TEST_ENVIRONMENT_DEFAULT,
        PointOfInterestTests.TEST_STRUCTURE_EMPTY,
        100,
        0,
        true,
        BlockRotation.NONE,
        false,
        1,
        1,
        false,
        (context) -> {
          // Arrange
          BlockPos blockPos = BlockPos.ORIGIN;

          context.setBlockState(blockPos.south(2).up(1), Blocks.BARRIER);
          context.setBlockState(blockPos.south(1).east(1).up(1), Blocks.BARRIER);
          context.setBlockState(blockPos.south(1).up(2), Blocks.BARRIER);

          VillagerEntity villager = context.spawnEntity(EntityType.VILLAGER, blockPos.south(1));

          // Act
          CompletableFuture<Void> futurePartialAct1 = new CompletableFuture<>();
          CompletableFuture<Void> futurePartialAct2 = new CompletableFuture<>();

          long tickOrigin = 0;
          context.runAtTick(
              tickOrigin,
              () -> {
                context.setBlockState(blockPos, barrelBlock);

                futurePartialAct1.complete(null);
              });

          long tickGetProfession = 100;
          context.runAtTick(
              tickGetProfession,
              () -> {
                futurePartialAct2.complete(null);
              });

          // Assert
          CompletableFuture.allOf(futurePartialAct1, futurePartialAct2)
              .thenRun(
                  () -> {
                    try {
                      context.assertEquals(
                          villager.getVillagerData().profession().getKey().orElse(null),
                          VillagerProfession.FISHERMAN,
                          Text.of("villager profession"));
                    } catch (Exception e) {
                      ReinforcedBarrelsMod.LOGGER.error("[{}] {}", testIdentifier, e.getMessage());
                      throw e;
                    }

                    context.complete();
                  });
        });
  }
}
