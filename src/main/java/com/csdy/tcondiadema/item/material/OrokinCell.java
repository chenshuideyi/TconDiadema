package com.csdy.tcondiadema.item.material;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public class OrokinCell extends Item {
    public OrokinCell() {
        super((new Item.Properties()).stacksTo(64).rarity(Rarity.UNCOMMON));
    }
}
