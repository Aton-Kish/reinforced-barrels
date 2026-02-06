package atonkish.reinfbarrel.gametest.testcase;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.ServerRecipeManager;
import net.minecraft.recipe.input.CraftingRecipeInput;
import net.minecraft.recipe.input.RecipeInput;
import net.minecraft.recipe.input.SmithingRecipeInput;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Identifier;

import atonkish.reinfbarrel.ReinforcedBarrelsMod;
import atonkish.reinfbarrel.gametest.util.TestIdentifier;
import atonkish.reinfbarrel.item.ModItems;
import atonkish.reinfcore.gametest.TestFunction;
import atonkish.reinfcore.util.ReinforcingMaterials;

public class RecipeTests {
  private static final String TEST_ENVIRONMENT_DEFAULT =
      String.format("%s:recipe/default", ReinforcedBarrelsMod.MOD_ID);
  private static final String TEST_STRUCTURE_EMPTY = "fabric-gametest-api-v1:empty";

  public static final Collection<TestFunction> TEST_FUNCTIONS =
      new ArrayList<>() {
        {
          // Copper Barrel
          {
            ItemStack baseBarrel = new ItemStack(Items.BARREL);
            ItemStack material = new ItemStack(Items.COPPER_INGOT);
            ItemStack barrel =
                new ItemStack(
                    ModItems.REINFORCED_BARREL_MAP.get(ReinforcingMaterials.MAP.get("copper")));

            add(
                RecipeTests.createTest(
                    "Craft Copper Barrel",
                    RecipeType.CRAFTING,
                    CraftingRecipeInput.create(
                        3,
                        3,
                        List.of(
                            material,
                            material,
                            material,
                            material,
                            baseBarrel,
                            material,
                            material,
                            material,
                            material)),
                    barrel));
          }

          // Iron Barrel
          {
            ItemStack baseBarrel =
                new ItemStack(
                    ModItems.REINFORCED_BARREL_MAP.get(ReinforcingMaterials.MAP.get("copper")));
            ItemStack material = new ItemStack(Items.IRON_INGOT);
            ItemStack barrel =
                new ItemStack(
                    ModItems.REINFORCED_BARREL_MAP.get(ReinforcingMaterials.MAP.get("iron")));

            add(
                RecipeTests.createTest(
                    "Craft Iron Barrel",
                    RecipeType.CRAFTING,
                    CraftingRecipeInput.create(
                        3,
                        3,
                        List.of(
                            material,
                            material,
                            material,
                            material,
                            baseBarrel,
                            material,
                            material,
                            material,
                            material)),
                    barrel));
          }

          // Gold Barrel
          {
            ItemStack baseBarrel =
                new ItemStack(
                    ModItems.REINFORCED_BARREL_MAP.get(ReinforcingMaterials.MAP.get("iron")));
            ItemStack material = new ItemStack(Items.GOLD_INGOT);
            ItemStack barrel =
                new ItemStack(
                    ModItems.REINFORCED_BARREL_MAP.get(ReinforcingMaterials.MAP.get("gold")));

            add(
                RecipeTests.createTest(
                    "Craft Gold Barrel",
                    RecipeType.CRAFTING,
                    CraftingRecipeInput.create(
                        3,
                        3,
                        List.of(
                            material,
                            material,
                            material,
                            material,
                            baseBarrel,
                            material,
                            material,
                            material,
                            material)),
                    barrel));
          }

          // Diamond Barrel
          {
            ItemStack baseBarrel =
                new ItemStack(
                    ModItems.REINFORCED_BARREL_MAP.get(ReinforcingMaterials.MAP.get("gold")));
            ItemStack material = new ItemStack(Items.DIAMOND);
            ItemStack barrel =
                new ItemStack(
                    ModItems.REINFORCED_BARREL_MAP.get(ReinforcingMaterials.MAP.get("diamond")));

            add(
                RecipeTests.createTest(
                    "Craft Diamond Barrel",
                    RecipeType.CRAFTING,
                    CraftingRecipeInput.create(
                        3,
                        3,
                        List.of(
                            material,
                            material,
                            material,
                            material,
                            baseBarrel,
                            material,
                            material,
                            material,
                            material)),
                    barrel));
          }

          // Netherite Barrel
          {
            ItemStack template = new ItemStack(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE);
            ItemStack baseBarrel =
                new ItemStack(
                    ModItems.REINFORCED_BARREL_MAP.get(ReinforcingMaterials.MAP.get("diamond")));
            ItemStack material = new ItemStack(Items.NETHERITE_INGOT);
            ItemStack barrel =
                new ItemStack(
                    ModItems.REINFORCED_BARREL_MAP.get(ReinforcingMaterials.MAP.get("netherite")));

            add(
                RecipeTests.createTest(
                    "Smithing Netherite Barrel",
                    RecipeType.SMITHING,
                    new SmithingRecipeInput(template, baseBarrel, material),
                    barrel));
          }
        }
      };

  private static <I extends RecipeInput, T extends Recipe<I>> TestFunction createTest(
      String name, RecipeType<T> type, I input, ItemStack expected) {
    Identifier testIdentifier =
        TestIdentifier.of(ReinforcedBarrelsMod.MOD_ID, RecipeTests.class, name);

    return new TestFunction(
        testIdentifier,
        RecipeTests.TEST_ENVIRONMENT_DEFAULT,
        RecipeTests.TEST_STRUCTURE_EMPTY,
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
          ServerWorld world = context.getWorld();
          ServerRecipeManager recipeManager = world.getRecipeManager();
          DynamicRegistryManager registryManager = world.getRegistryManager();
          T recipe = recipeManager.getFirstMatch(type, input, world).orElseThrow().value();

          // Act
          ItemStack actual = recipe.craft(input, registryManager);

          // Assert
          try {
            context.assertTrue(
                ItemStack.areEqual(actual, expected),
                Text.of("Recipe result differs from expected."));
          } catch (Exception e) {
            ReinforcedBarrelsMod.LOGGER.error("[{}] {}", testIdentifier, e.getMessage());
            throw e;
          }

          context.complete();
        });
  }
}
