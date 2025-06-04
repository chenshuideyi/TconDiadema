package com.csdy.tcondiadema.item.register;


import com.csdy.tcondiadema.TconDiadema;
import com.csdy.tcondiadema.item.food.EnderDragonHeart;
import com.csdy.tcondiadema.item.food.Poop;
import com.csdy.tcondiadema.item.food.WardenHeart;
import com.csdy.tcondiadema.item.sword.*;
import com.csdy.tcondiadema.item.material.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tiers;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemRegister {
    public static DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, TconDiadema.MODID);
    public static final RegistryObject<Item> BROKEN_SACRED_RELIC = ITEMS.register("broken_sacred_relic", BrokenSacredRelic::new);
    public static final RegistryObject<Item> SACRED_RELIC = ITEMS.register("sacred_relic", () -> new SacredRelic(Tiers.NETHERITE, 0, -2.4F, new Item.Properties()));
    public static final RegistryObject<Item> WARDEN_HEART = ITEMS.register("warden_heart", WardenHeart::new);
    public static final RegistryObject<Item> ENDERDRAGON_HEART = ITEMS.register("enderdragon_heart", EnderDragonHeart::new);
    public static final RegistryObject<Item> FORMA = ITEMS.register("forma", Forma::new);
    public static final RegistryObject<Item> NEURON = ITEMS.register("neuron", Neuron::new);
    public static final RegistryObject<Item> MORPHICS = ITEMS.register("morphics", Morphics::new);
    public static final RegistryObject<Item> OROKIN_CELL = ITEMS.register("orokin_cell", OrokinCell::new);
    public static final RegistryObject<Item> NEURAL_SENSORS = ITEMS.register("neural_sensors", NeuralSensors::new);
    public static final RegistryObject<Item> AURA_FORMA = ITEMS.register("aura_forma", AuraForma::new);
    public static final RegistryObject<Item> POOP = ITEMS.register("poop", Poop::new);


}
