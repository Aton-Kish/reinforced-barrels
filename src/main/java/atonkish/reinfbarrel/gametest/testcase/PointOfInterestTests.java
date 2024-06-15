package atonkish.reinfbarrel.gametest.testcase;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;

import net.fabricmc.fabric.api.gametest.v1.FabricGameTest;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.test.StructureTestUtil;
import net.minecraft.test.TestFunction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.village.VillagerProfession;

import atonkish.reinfbarrel.ReinforcedBarrelsMod;
import atonkish.reinfbarrel.block.ModBlocks;
import atonkish.reinfcore.util.ReinforcingMaterials;

public class PointOfInterestTests {
    private static final String BATCH_ID = String.format("%s:PointOfInterestBatch",
            ReinforcedBarrelsMod.MOD_ID);

    public static final Collection<TestFunction> TEST_FUNCTIONS = new ArrayList<>() {
        {
            // Copper Barrel
            add(PointOfInterestTests.createTest(
                    "Villager have a fisherman profession at Copper Barrel",
                    ModBlocks.REINFORCED_BARREL_MAP.get(ReinforcingMaterials.MAP.get("copper"))));

            // Iron Barrel
            add(PointOfInterestTests.createTest(
                    "Villager have a fisherman profession at Iron Barrel",
                    ModBlocks.REINFORCED_BARREL_MAP.get(ReinforcingMaterials.MAP.get("iron"))));

            // Gold Barrel
            add(PointOfInterestTests.createTest(
                    "Villager have a fisherman profession at Gold Barrel",
                    ModBlocks.REINFORCED_BARREL_MAP.get(ReinforcingMaterials.MAP.get("gold"))));

            // Diamond Barrel
            add(PointOfInterestTests.createTest(
                    "Villager have a fisherman profession at Diamond Barrel",
                    ModBlocks.REINFORCED_BARREL_MAP.get(ReinforcingMaterials.MAP.get("diamond"))));

            // Netherite Barrel
            add(PointOfInterestTests.createTest(
                    "Villager have a fisherman profession at Netherite Barrel",
                    ModBlocks.REINFORCED_BARREL_MAP.get(ReinforcingMaterials.MAP.get("netherite"))));
        }
    };

    private static TestFunction createTest(String name, Block barrelBlock) {
        String testName = String.format("%s %s %s",
                ReinforcedBarrelsMod.MOD_ID,
                PointOfInterestTests.class.getSimpleName(),
                name)
                .replace(" ", "_");

        return new TestFunction(
                PointOfInterestTests.BATCH_ID,
                testName,
                FabricGameTest.EMPTY_STRUCTURE,
                StructureTestUtil.getRotation(0),
                100,
                0L,
                false,
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
                    context.runAtTick(tickOrigin, () -> {
                        context.setBlockState(blockPos, barrelBlock);

                        futurePartialAct1.complete(null);
                    });

                    long tickGetProfession = 100;
                    context.runAtTick(tickGetProfession, () -> {
                        futurePartialAct2.complete(null);
                    });

                    // Assert
                    CompletableFuture.allOf(futurePartialAct1, futurePartialAct2).thenRun(() -> {
                        try {
                            context.assertEquals(villager.getVillagerData().getProfession(),
                                    VillagerProfession.FISHERMAN,
                                    "villager profession");
                        } catch (Exception e) {
                            ReinforcedBarrelsMod.LOGGER.error("[{}] {}", testName, e.getMessage());
                            throw e;
                        }

                        context.complete();
                    });
                });
    }
}
