package com.csdy.tcondiadema;

import com.csdy.tcondiadema.diadema.DiademaRegister;
import com.csdy.tcondiadema.effect.register.EffectRegister;
import com.csdy.tcondiadema.frames.diadema.DiademaSyncing;
import com.csdy.tcondiadema.item.register.HideRegister;
import com.csdy.tcondiadema.item.register.ItemRegister;
import com.csdy.tcondiadema.modifier.register.ModifierRegister;
import com.csdy.tcondiadema.network.ParticleSyncing;
import com.csdy.tcondiadema.particle.register.ParticlesRegister;
import com.csdy.tcondiadema.sounds.SoundsRegister;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;


@Mod(ModMain.MODID)
@Mod.EventBusSubscriber(modid = ModMain.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModMain {

    public static final String MODID = "tcondiadema";
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);
    public static final List<Supplier<? extends Item>> TAB_ITEMS_LIST = new ArrayList<>();

    public ModMain() {


        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        CsdyTab.CREATIVE_MODE_TABS.register(bus);

        //注册表
        ItemRegister.ITEMS.register(bus);
        ModifierRegister.MODIFIERS.register(bus);
        HideRegister.HIDE.register(bus);

        MinecraftForge.EVENT_BUS.register(this);
        ParticlesRegister.PARTICLE_TYPES.register(bus);
        EffectRegister.EFFECTS.register(bus);
        SoundsRegister.SOUND_EVENTS.register(bus);

        DiademaSlots.init();
        DiademaRegister.DIADEMA_TYPES.register(bus);

//        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.EXMODE,"tcondiadema-Exmode.toml");
//        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setupClient);

    }

    @SubscribeEvent
    public static void onFMLCommonSetup(FMLCommonSetupEvent event) {
        //网络包
        DiademaSyncing.Init();
        ParticleSyncing.Init();
    }

//    @SubscribeEvent
//    public void onGatherData(GatherDataEvent event) {
//        DataGenerator generator = event.getGenerator();
//        PackOutput packOutput = generator.getPackOutput();
//
//        // 注册数据生成器
//        generator.addProvider(event.includeServer(), new ModRecipeProvider(packOutput));
//    }

}

