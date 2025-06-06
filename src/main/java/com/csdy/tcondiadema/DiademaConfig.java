package com.csdy.tcondiadema;

import net.minecraftforge.common.ForgeConfigSpec;

///为什么没有forge:config????
public class DiademaConfig {
    public static final ForgeConfigSpec.Builder CONFIG = new ForgeConfigSpec.Builder();

    public static final ForgeConfigSpec.BooleanValue EX_APOLLYON = CONFIG.comment("亚伯伦-完全体模式", "Apollyon Full Power Mode").define("ApollyonEX",false);

    public static final ForgeConfigSpec DIADEMA_CONFIG = CONFIG.build();
}
