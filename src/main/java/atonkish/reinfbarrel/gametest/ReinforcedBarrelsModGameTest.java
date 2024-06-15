package atonkish.reinfbarrel.gametest;

import java.util.ArrayList;
import java.util.Collection;

import net.minecraft.test.CustomTestProvider;
import net.minecraft.test.TestFunction;

import atonkish.reinfbarrel.gametest.testcase.AdvancementTests;
import atonkish.reinfbarrel.gametest.testcase.InventoryTests;
import atonkish.reinfbarrel.gametest.testcase.LootTableTests;
import atonkish.reinfbarrel.gametest.testcase.OpenTests;
import atonkish.reinfbarrel.gametest.testcase.PiglinTests;
import atonkish.reinfbarrel.gametest.testcase.PointOfInterestTests;
import atonkish.reinfbarrel.gametest.testcase.RecipeTests;

public class ReinforcedBarrelsModGameTest {
    @CustomTestProvider
    public Collection<TestFunction> registerTests() {
        Collection<TestFunction> testFunctions = new ArrayList<>();

        if (System.getProperty(this.getClass().getPackageName()) == null) {
            return testFunctions;
        }

        testFunctions.addAll(AdvancementTests.TEST_FUNCTIONS);
        testFunctions.addAll(InventoryTests.TEST_FUNCTIONS);
        testFunctions.addAll(LootTableTests.TEST_FUNCTIONS);
        testFunctions.addAll(OpenTests.TEST_FUNCTIONS);
        testFunctions.addAll(PiglinTests.TEST_FUNCTIONS);
        testFunctions.addAll(PointOfInterestTests.TEST_FUNCTIONS);
        testFunctions.addAll(RecipeTests.TEST_FUNCTIONS);

        return testFunctions;
    }
}
