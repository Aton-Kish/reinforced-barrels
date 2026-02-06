package atonkish.reinfbarrel.gametest.testcase;

import java.util.ArrayList;
import java.util.Collection;

import net.minecraft.block.Block;
import net.minecraft.block.entity.BarrelBlockEntity;
import net.minecraft.text.Text;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

import atonkish.reinfbarrel.ReinforcedBarrelsMod;
import atonkish.reinfbarrel.block.ModBlocks;
import atonkish.reinfbarrel.gametest.util.TestIdentifier;
import atonkish.reinfcore.gametest.TestFunction;
import atonkish.reinfcore.util.ReinforcingMaterials;

public class InventoryTests {
  private static final String TEST_ENVIRONMENT_DEFAULT =
      String.format("%s:inventory/default", ReinforcedBarrelsMod.MOD_ID);
  private static final String TEST_STRUCTURE_EMPTY = "fabric-gametest-api-v1:empty";

  public static final Collection<TestFunction> TEST_FUNCTIONS =
      new ArrayList<>() {
        {
          // Copper Barrel
          add(
              InventoryTests.createTest(
                  "Copper Barrel inventory size",
                  ModBlocks.REINFORCED_BARREL_MAP.get(ReinforcingMaterials.MAP.get("copper")),
                  45));

          // Iron Barrel
          add(
              InventoryTests.createTest(
                  "Iron Barrel inventory size",
                  ModBlocks.REINFORCED_BARREL_MAP.get(ReinforcingMaterials.MAP.get("iron")),
                  54));

          // Gold Barrel
          add(
              InventoryTests.createTest(
                  "Gold Barrel inventory size",
                  ModBlocks.REINFORCED_BARREL_MAP.get(ReinforcingMaterials.MAP.get("gold")),
                  81));

          // Diamond Barrel
          add(
              InventoryTests.createTest(
                  "Diamond Barrel inventory size",
                  ModBlocks.REINFORCED_BARREL_MAP.get(ReinforcingMaterials.MAP.get("diamond")),
                  108));

          // Netherite Barrel
          add(
              InventoryTests.createTest(
                  "Netherite Barrel inventory size",
                  ModBlocks.REINFORCED_BARREL_MAP.get(ReinforcingMaterials.MAP.get("netherite")),
                  108));
        }
      };

  private static TestFunction createTest(String name, Block barrelBlock, int size) {
    Identifier testIdentifier =
        TestIdentifier.of(ReinforcedBarrelsMod.MOD_ID, InventoryTests.class, name);

    return new TestFunction(
        testIdentifier,
        InventoryTests.TEST_ENVIRONMENT_DEFAULT,
        InventoryTests.TEST_STRUCTURE_EMPTY,
        20,
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
          context.setBlockState(blockPos, barrelBlock);

          // Act
          BarrelBlockEntity entity = context.getBlockEntity(blockPos, BarrelBlockEntity.class);

          // Assert
          try {
            context.assertEquals(
                entity.size(),
                size,
                Text.of(String.format("%s inventory size", barrelBlock.getName().getString())));
          } catch (Exception e) {
            ReinforcedBarrelsMod.LOGGER.error("[{}] {}", testIdentifier, e.getMessage());
            throw e;
          }

          context.complete();
        });
  }
}
