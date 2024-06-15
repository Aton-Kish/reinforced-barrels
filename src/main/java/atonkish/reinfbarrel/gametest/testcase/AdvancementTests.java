package atonkish.reinfbarrel.gametest.testcase;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import net.fabricmc.fabric.api.gametest.v1.FabricGameTest;

import net.minecraft.advancement.AdvancementEntry;
import net.minecraft.advancement.AdvancementProgress;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.test.StructureTestUtil;
import net.minecraft.test.TestFunction;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameMode;

import atonkish.reinfbarrel.ReinforcedBarrelsMod;
import atonkish.reinfbarrel.gametest.util.MockServerPlayerHelper;
import atonkish.reinfbarrel.item.ModItems;
import atonkish.reinfcore.util.ReinforcingMaterials;

public class AdvancementTests {
    private static final String BATCH_ID = String.format("%s:AdvancementBatch",
            ReinforcedBarrelsMod.MOD_ID);

    public static final Collection<TestFunction> TEST_FUNCTIONS = new ArrayList<>() {
        {
            // Copper Barrel
            add(AdvancementTests.createTest(
                    "Obtain Copper Barrel recipe advancement by having Barrel",
                    Items.BARREL,
                    new Identifier(ReinforcedBarrelsMod.MOD_ID, "recipes/decorations/copper_barrel")));
            add(AdvancementTests.createTest(
                    "Obtain Copper Barrel recipe advancement by having Copper Ingot",
                    Items.COPPER_INGOT,
                    new Identifier(ReinforcedBarrelsMod.MOD_ID, "recipes/decorations/copper_barrel")));

            // Iron Barrel
            add(AdvancementTests.createTest(
                    "Obtain Iron Barrel recipe advancement by having Copper Barrel",
                    ModItems.REINFORCED_BARREL_MAP.get(ReinforcingMaterials.MAP.get("copper")),
                    new Identifier(ReinforcedBarrelsMod.MOD_ID, "recipes/decorations/iron_barrel")));
            add(AdvancementTests.createTest(
                    "Obtain Iron Barrel recipe advancement by having Iron Ingot",
                    Items.IRON_INGOT,
                    new Identifier(ReinforcedBarrelsMod.MOD_ID, "recipes/decorations/iron_barrel")));

            // Gold Barrel
            add(AdvancementTests.createTest(
                    "Obtain Gold Barrel recipe advancement by having Iron Barrel",
                    ModItems.REINFORCED_BARREL_MAP.get(ReinforcingMaterials.MAP.get("iron")),
                    new Identifier(ReinforcedBarrelsMod.MOD_ID, "recipes/decorations/gold_barrel")));
            add(AdvancementTests.createTest(
                    "Obtain Gold Barrel recipe advancement by having Gold Ingot",
                    Items.GOLD_INGOT,
                    new Identifier(ReinforcedBarrelsMod.MOD_ID, "recipes/decorations/gold_barrel")));

            // Diamond Barrel
            add(AdvancementTests.createTest(
                    "Obtain Diamond Barrel recipe advancement by having Gold Barrel",
                    ModItems.REINFORCED_BARREL_MAP.get(ReinforcingMaterials.MAP.get("gold")),
                    new Identifier(ReinforcedBarrelsMod.MOD_ID, "recipes/decorations/diamond_barrel")));
            add(AdvancementTests.createTest(
                    "Obtain Diamond Barrel recipe advancement by having Diamond Ingot",
                    Items.DIAMOND,
                    new Identifier(ReinforcedBarrelsMod.MOD_ID, "recipes/decorations/diamond_barrel")));

            // Netherite Barrel
            add(AdvancementTests.createTest(
                    "Obtain Netherite Barrel recipe advancement by having Diamond Barrel",
                    ModItems.REINFORCED_BARREL_MAP.get(ReinforcingMaterials.MAP.get("diamond")),
                    new Identifier(ReinforcedBarrelsMod.MOD_ID, "recipes/decorations/netherite_barrel_smithing")));
            add(AdvancementTests.createTest(
                    "Obtain Netherite Barrel recipe advancement by having Netherite Ingot",
                    Items.NETHERITE_INGOT,
                    new Identifier(ReinforcedBarrelsMod.MOD_ID, "recipes/decorations/netherite_barrel_smithing")));
        }
    };

    private static TestFunction createTest(String name, Item item, Identifier advancementId) {
        String testName = String.format("%s %s %s",
                ReinforcedBarrelsMod.MOD_ID,
                AdvancementTests.class.getSimpleName(),
                name)
                .replace(" ", "_");
        return new TestFunction(
                AdvancementTests.BATCH_ID,
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
                    ServerPlayerEntity player = MockServerPlayerHelper.spawn(context,
                            GameMode.SURVIVAL, Vec3d.of(BlockPos.ORIGIN));
                    AdvancementEntry entry = context.getWorld().getServer().getAdvancementLoader()
                            .get(advancementId);
                    AdvancementProgress progress = player.getAdvancementTracker().getProgress(entry);

                    // Act
                    CompletableFuture<Void> futurePartialAct1 = new CompletableFuture<>();
                    CompletableFuture<Void> futurePartialAct2 = new CompletableFuture<>();

                    Map<String, Boolean> progressMap = new HashMap<String, Boolean>();
                    String progressMapKeyBeforeHavingItem = "beforeHavingItem";
                    String progressMapKeyAfterHavingItem = "afterHavingItem";

                    long tickOrigin = 0;
                    context.runAtTick(tickOrigin, () -> {
                        progressMap.put(progressMapKeyBeforeHavingItem, progress.isDone());

                        player.giveItemStack(new ItemStack(item));

                        futurePartialAct1.complete(null);
                    });

                    long tickObtained = 1;
                    context.runAtTick(tickObtained, () -> {
                        progressMap.put(progressMapKeyAfterHavingItem, progress.isDone());

                        futurePartialAct2.complete(null);
                    });

                    // Assert
                    CompletableFuture.allOf(futurePartialAct1, futurePartialAct2).thenRun(() -> {
                        try {
                            context.assertFalse(progressMap.get(progressMapKeyBeforeHavingItem), String.format(
                                    "Expected that advancement %s has not been done yet, but it has been already done.",
                                    entry));
                            context.assertTrue(progressMap.get(progressMapKeyAfterHavingItem), String.format(
                                    "Expected that advancement %s has been done, but it has not been done yet.",
                                    entry));
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
