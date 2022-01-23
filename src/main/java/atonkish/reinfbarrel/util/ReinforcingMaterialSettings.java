package atonkish.reinfbarrel.util;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.MapColor;
import net.minecraft.block.Material;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.sound.BlockSoundGroup;
import atonkish.reinfcore.api.ReinforcedCoreRegistry;
import atonkish.reinfcore.item.ModItemGroup;
import atonkish.reinfcore.util.ReinforcingMaterial;

public enum ReinforcingMaterialSettings {
    COPPER(ReinforcedCoreRegistry.registerReinforcingMaterial("copper", 45, Items.COPPER_INGOT),
            FabricBlockSettings.of(Material.METAL, MapColor.ORANGE).strength(2.5F, 6.0F)
                    .sounds(BlockSoundGroup.COPPER),
            new Item.Settings().group(ModItemGroup.REINFORCED_STORAGE)),
    IRON(ReinforcedCoreRegistry.registerReinforcingMaterial("iron", 54, Items.IRON_INGOT),
            FabricBlockSettings.of(Material.METAL, MapColor.IRON_GRAY).strength(2.5F, 6.0F)
                    .sounds(BlockSoundGroup.METAL),
            new Item.Settings().group(ModItemGroup.REINFORCED_STORAGE)),
    GOLD(ReinforcedCoreRegistry.registerReinforcingMaterial("gold", 81, Items.GOLD_INGOT),
            FabricBlockSettings.of(Material.METAL, MapColor.GOLD).strength(2.5F, 6.0F)
                    .sounds(BlockSoundGroup.METAL),
            new Item.Settings().group(ModItemGroup.REINFORCED_STORAGE)),
    DIAMOND(ReinforcedCoreRegistry.registerReinforcingMaterial("diamond", 108, Items.DIAMOND),
            FabricBlockSettings.of(Material.METAL, MapColor.DIAMOND_BLUE).strength(2.5F, 6.0F)
                    .sounds(BlockSoundGroup.METAL),
            new Item.Settings().group(ModItemGroup.REINFORCED_STORAGE)),
    NETHERITE(ReinforcedCoreRegistry.registerReinforcingMaterial("netherite", 108, Items.NETHERITE_INGOT),
            FabricBlockSettings.of(Material.METAL, MapColor.BLACK).strength(2.5F, 1200.0F)
                    .sounds(BlockSoundGroup.NETHERITE),
            new Item.Settings().group(ModItemGroup.REINFORCED_STORAGE).fireproof());

    private final ReinforcingMaterial material;
    private final Block.Settings blockSettings;
    private final Item.Settings itemSettings;

    private ReinforcingMaterialSettings(ReinforcingMaterial material, Block.Settings blockSettings,
            Item.Settings itemSettings) {
        this.material = material;
        this.blockSettings = blockSettings;
        this.itemSettings = itemSettings;
    }

    public ReinforcingMaterial getMaterial() {
        return this.material;
    }

    public Block.Settings getBlockSettings() {
        return this.blockSettings;
    }

    public Item.Settings getItemSettings() {
        return this.itemSettings;
    }
}
