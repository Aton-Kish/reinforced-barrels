package atonkish.reinfbarrel.gametest.testcase;

import java.util.ArrayList;
import java.util.Collection;

import net.fabricmc.fabric.api.gametest.v1.FabricGameTest;

import net.minecraft.block.Block;
import net.minecraft.block.entity.BarrelBlockEntity;
import net.minecraft.test.StructureTestUtil;
import net.minecraft.test.TestFunction;
import net.minecraft.util.math.BlockPos;

import atonkish.reinfbarrel.ReinforcedBarrelsMod;
import atonkish.reinfbarrel.block.ModBlocks;
import atonkish.reinfcore.util.ReinforcingMaterials;

public class InventoryTests {
    private static final String BATCH_ID = String.format("%s:InventoryBatch",
            ReinforcedBarrelsMod.MOD_ID);

    public static final Collection<TestFunction> TEST_FUNCTIONS = new ArrayList<>() {
        {
            // Copper Barrel
            add(InventoryTests.createTest(
                    "Copper Barrel inventory size",
                    ModBlocks.REINFORCED_BARREL_MAP.get(ReinforcingMaterials.MAP.get("copper")),
                    45));

            // Iron Barrel
            add(InventoryTests.createTest(
                    "Iron Barrel inventory size",
                    ModBlocks.REINFORCED_BARREL_MAP.get(ReinforcingMaterials.MAP.get("iron")),
                    54));

            // Gold Barrel
            add(InventoryTests.createTest(
                    "Gold Barrel inventory size",
                    ModBlocks.REINFORCED_BARREL_MAP.get(ReinforcingMaterials.MAP.get("gold")),
                    81));

            // Diamond Barrel
            add(InventoryTests.createTest(
                    "Diamond Barrel inventory size",
                    ModBlocks.REINFORCED_BARREL_MAP.get(ReinforcingMaterials.MAP.get("diamond")),
                    108));

            // Netherite Barrel
            add(InventoryTests.createTest(
                    "Netherite Barrel inventory size",
                    ModBlocks.REINFORCED_BARREL_MAP.get(ReinforcingMaterials.MAP.get("netherite")),
                    108));
        }
    };

    private static TestFunction createTest(String name, Block barrelBlock, int size) {
        String testName = String.format("%s %s %s",
                ReinforcedBarrelsMod.MOD_ID,
                InventoryTests.class.getSimpleName(),
                name)
                .replace(" ", "_");

        return new TestFunction(
                InventoryTests.BATCH_ID,
                testName,
                FabricGameTest.EMPTY_STRUCTURE,
                StructureTestUtil.getRotation(0),
                100,
                0L,
                true,
                false,
                1,
                1,
                false,
                (context) -> {
                    // Arrange
                    BlockPos blockPos = BlockPos.ORIGIN;
                    context.setBlockState(blockPos, barrelBlock);

                    // Act
                    BarrelBlockEntity entity = (BarrelBlockEntity) context.getBlockEntity(blockPos);

                    // Assert
                    try {
                        context.assertEquals(entity.size(), size,
                                String.format("%s inventory size", barrelBlock.getName().getString()));
                    } catch (Exception e) {
                        ReinforcedBarrelsMod.LOGGER.error("[{}] {}", testName, e.getMessage());
                        throw e;
                    }

                    context.complete();
                });
    }
}
