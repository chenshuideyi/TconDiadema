package com.csdy.tcondiadema.item.material;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public class Morphics extends Item {
    public Morphics() {
        super((new Item.Properties()).stacksTo(64).rarity(Rarity.COMMON));
    }
}
