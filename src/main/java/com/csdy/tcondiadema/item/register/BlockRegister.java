package com.csdy.tcondiadema.item.register;


import com.csdy.tcondiadema.TconDiadema;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;

import static net.minecraftforge.registries.ForgeRegistries.BLOCKS;

public class BlockRegister {
    public static DeferredRegister<Block> blocks = DeferredRegister.create(BLOCKS, TconDiadema.MODID);
    //public static final RegistryObject<Item> TEST_BLOCK = blocks.register("test", Test::new);
}
