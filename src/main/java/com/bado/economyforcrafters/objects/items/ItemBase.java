package com.bado.economyforcrafters.objects.items;

import com.bado.economyforcrafters.EconomyForCrafters;
import net.minecraft.item.Item;

public class ItemBase extends Item {
    public ItemBase() {
        super(new Item.Properties().group(EconomyForCrafters.TAB));
    }
}
