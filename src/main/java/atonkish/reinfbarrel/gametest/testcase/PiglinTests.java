package atonkish.reinfbarrel.gametest.testcase;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.mob.PiglinEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.test.StructureTestUtil;
import net.minecraft.test.TestFunction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameMode;

import net.fabricmc.fabric.api.gametest.v1.FabricGameTest;

import atonkish.reinfcore.util.ReinforcingMaterials;

import atonkish.reinfbarrel.ReinforcedBarrelsMod;
import atonkish.reinfbarrel.block.ModBlocks;
import atonkish.reinfbarrel.gametest.util.MockServerPlayerHelper;

public class PiglinTests {
    private static final String BATCH_ID = String.format("%s:PiglinBatch",
            ReinforcedBarrelsMod.MOD_ID);

    public static final Collection<TestFunction> TEST_FUNCTIONS = new ArrayList<>() {
        {
            // Copper Barrel
            add(PiglinTests.createTest(
                    "Piglin get angry after opening Copper Barrel",
                    ModBlocks.REINFORCED_BARREL_MAP.get(ReinforcingMaterials.MAP.get("copper"))));

            // Iron Barrel
            add(PiglinTests.createTest(
                    "Piglin get angry after opening Iron Barrel",
                    ModBlocks.REINFORCED_BARREL_MAP.get(ReinforcingMaterials.MAP.get("iron"))));

            // Gold Barrel
            add(PiglinTests.createTest(
                    "Piglin get angry after opening Gold Barrel",
                    ModBlocks.REINFORCED_BARREL_MAP.get(ReinforcingMaterials.MAP.get("gold"))));

            // Diamond Barrel
            add(PiglinTests.createTest(
                    "Piglin get angry after opening Diamond Barrel",
                    ModBlocks.REINFORCED_BARREL_MAP.get(ReinforcingMaterials.MAP.get("diamond"))));

            // Netherite Barrel
            add(PiglinTests.createTest(
                    "Piglin get angry after opening Netherite Barrel",
                    ModBlocks.REINFORCED_BARREL_MAP.get(ReinforcingMaterials.MAP.get("netherite"))));
        }
    };

    private static TestFunction createTest(String name, Block barrelBlock) {
        String testName = String.format("%s %s %s",
                ReinforcedBarrelsMod.MOD_ID,
                PiglinTests.class.getSimpleName(),
                name)
                .replace(" ", "_");

        return new TestFunction(
                PiglinTests.BATCH_ID,
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

                    ServerPlayerEntity player = MockServerPlayerHelper.spawn(context,
                            GameMode.SURVIVAL, Vec3d.of(blockPos.south(4)));
                    ArmorItem armor = (ArmorItem) Items.GOLDEN_CHESTPLATE;
                    player.equipStack(EquipmentSlot.CHEST, new ItemStack(armor));

                    PiglinEntity piglin = context.spawnMob(EntityType.PIGLIN, blockPos.east(1));

                    // Act
                    CompletableFuture<Void> futurePartialAct1 = new CompletableFuture<>();
                    CompletableFuture<Void> futurePartialAct2 = new CompletableFuture<>();

                    Map<String, Boolean> angryAtMap = new HashMap<String, Boolean>();
                    String angryAtMapKeyBeforeAngryAtPlayer = "beforeAngryAtPlayer";
                    String angryAtMapKeyAfterAngryAtPlayer = "afterAngryAtPlayer";

                    long tickChestOpen = 20;
                    context.runAtTick(tickChestOpen, () -> {
                        angryAtMap.put(angryAtMapKeyBeforeAngryAtPlayer,
                                piglin.getBrain().hasMemoryModuleWithValue(MemoryModuleType.ANGRY_AT,
                                        player.getUuid()));

                        context.useBlock(blockPos, player);

                        futurePartialAct1.complete(null);
                    });

                    long tickAngryAtPlayer = 21;
                    context.runAtTick(tickAngryAtPlayer, () -> {
                        angryAtMap.put(angryAtMapKeyAfterAngryAtPlayer,
                                piglin.getBrain().hasMemoryModuleWithValue(MemoryModuleType.ANGRY_AT,
                                        player.getUuid()));

                        futurePartialAct2.complete(null);
                    });

                    // Assert
                    CompletableFuture.allOf(futurePartialAct1, futurePartialAct2).thenRun(() -> {
                        try {
                            context.assertFalse(angryAtMap.get(angryAtMapKeyBeforeAngryAtPlayer),
                                    "Expected that the piglin is not angry at player, but it has been already angry.");
                            context.assertTrue(angryAtMap.get(angryAtMapKeyAfterAngryAtPlayer),
                                    "Expected that the piglin is angry at player, but it has not been angry yet.");
                        } catch (Exception e) {
                            ReinforcedBarrelsMod.LOGGER.error("[{}] {}", testName, e.getMessage());
                            throw e;
                        } finally {
                            MockServerPlayerHelper.destroy(context, player);
                        }

                        context.complete();
                    });
                });
    }
}
