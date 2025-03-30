package com.csdy.tcondiadema;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import slimeknights.mantle.client.model.NBTKeyModel;
import slimeknights.tconstruct.library.tools.SlotType;

public class DiademaSlots {
    public static SlotType DIADEMA = SlotType.getOrCreate("diadema");

    public DiademaSlots(){
    }
    @OnlyIn(Dist.CLIENT)
    public static void init() {
        NBTKeyModel.registerExtraTexture(new ResourceLocation("tconstruct:creative_slot")
                ,DIADEMA.getName(),new ResourceLocation("tcondiadema:item/slot/diadema"));
    }
}
