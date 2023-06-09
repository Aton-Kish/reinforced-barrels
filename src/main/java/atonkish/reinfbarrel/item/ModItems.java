package atonkish.reinfbarrel.item;

import java.util.LinkedHashMap;
import java.util.Map;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import atonkish.reinfcore.item.ModItemGroup;
import atonkish.reinfcore.item.ModItemGroups;
import atonkish.reinfcore.util.ReinforcingMaterial;
import atonkish.reinfbarrel.block.ModBlocks;

public class ModItems {
    public static final Map<ReinforcingMaterial, Item> REINFORCED_BARREL_MAP = new LinkedHashMap<>();
    public static final Map<ReinforcingMaterial, Item.Settings> REINFORCED_BARREL_SETTINGS_MAP = new LinkedHashMap<>();

    public static Item registerMaterial(ReinforcingMaterial material, Item.Settings settings) {
        if (!REINFORCED_BARREL_SETTINGS_MAP.containsKey(material)) {
            REINFORCED_BARREL_SETTINGS_MAP.put(material, settings);
        }

        if (!REINFORCED_BARREL_MAP.containsKey(material)) {
            Item item = ModItems.register(
                    new BlockItem(ModBlocks.REINFORCED_BARREL_MAP.get(material),
                            REINFORCED_BARREL_SETTINGS_MAP.get(material)));
            ItemGroupEvents.modifyEntriesEvent(ItemGroups.FUNCTIONAL).register(content -> content.add(item));
            ItemGroupEvents.modifyEntriesEvent(ItemGroups.REDSTONE).register(content -> content.add(item));
            ItemGroupEvents.modifyEntriesEvent(ModItemGroups.REINFORCED_STORAGE).register(content -> content.add(item));
            REINFORCED_BARREL_MAP.put(material, item);
        }

        return REINFORCED_BARREL_MAP.get(material);
    }

    public static void registerMaterialItemGroupIcon(ReinforcingMaterial material) {
        Item item = REINFORCED_BARREL_MAP.get(material);
        ModItemGroup.setIcon(Registries.ITEM_GROUP.get(ModItemGroups.REINFORCED_STORAGE), item);
    }

    private static Item register(BlockItem item) {
        return ModItems.register(item.getBlock(), (Item) item);
    }

    protected static Item register(Block block, Item item) {
        return ModItems.register(Registries.BLOCK.getId(block), item);
    }

    private static Item register(Identifier id, Item item) {
        if (item instanceof BlockItem) {
            ((BlockItem) item).appendBlocks(Item.BLOCK_ITEMS, item);
        }

        return Registry.register(Registries.ITEM, id, item);
    }
}
