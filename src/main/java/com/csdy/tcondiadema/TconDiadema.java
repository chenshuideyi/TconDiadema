package com.csdy.tcondiadema;

import com.csdy.tcondiadema.client.HaloRender;
import com.csdy.tcondiadema.diadema.ClientDiademaRegister;
import com.csdy.tcondiadema.diadema.DiademaRegister;
import com.csdy.tcondiadema.diadema.warden.WardenBlindnessEffect;
import com.csdy.tcondiadema.effect.register.EffectRegister;
import com.csdy.tcondiadema.frames.diadema.DiademaSyncing;
import com.csdy.tcondiadema.item.register.HideRegister;
import com.csdy.tcondiadema.item.register.ItemRegister;
import com.csdy.tcondiadema.modifier.register.ModifierRegister;
import com.csdy.tcondiadema.network.VisualChannel;
import com.csdy.tcondiadema.particle.register.ParticlesRegister;
import com.csdy.tcondiadema.sounds.SoundsRegister;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;


@Mod(TconDiadema.MODID)
@Mod.EventBusSubscriber(modid = TconDiadema.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class TconDiadema {

    public static final String MODID = "tcondiadema";
    public static boolean IS_GOETY_REVELATION_LOADED = false;

    public static boolean isLoadRevelation(){
        return ModList.get().isLoaded("goety_revelation");
    }

    public TconDiadema() {


        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        CsdyTab.CREATIVE_MODE_TABS.register(bus);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onCommonSetup);

        //注册表
        ItemRegister.ITEMS.register(bus);
        ModifierRegister.MODIFIERS.register(bus);
        HideRegister.HIDE.register(bus);

        MinecraftForge.EVENT_BUS.register(this);
        ParticlesRegister.PARTICLE_TYPES.register(bus);
        EffectRegister.EFFECTS.register(bus);
        SoundsRegister.SOUND_EVENTS.register(bus);

        DiademaRegister.DIADEMA_TYPES.register(bus);

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, DiademaConfig.DIADEMA_CONFIG,"tcondiadema_config.toml");
        //        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setupClient);

        // 以下代码仅在客户端运行
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
            ClientDiademaRegister.CLIENT_DIADEMA_TYPES.register(bus);
        });

        if (isLoadRevelation()){
            DiademaRegister.MEGA_DIADEMA_TYPES.register(bus);

            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
            ClientDiademaRegister.MEGA_CLIENT_DIADEMA_TYPES.register(bus);
            });

        }
    }

    @SubscribeEvent
    public static void onFMLCommonSetup(FMLCommonSetupEvent event) {
        //网络包
        DiademaSyncing.Init();
        VisualChannel.Init();

        // 以下代码仅在客户端运行
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
            DiademaSlots.init();
            event.enqueueWork(WardenBlindnessEffect::init);
        });
    }

    private void onCommonSetup(FMLCommonSetupEvent event) {
        IS_GOETY_REVELATION_LOADED = ModList.get().isLoaded("goety_revelation");
        System.out.println("[TConDiadema] 启示录加载状态确认: " + IS_GOETY_REVELATION_LOADED);
    }

    @SubscribeEvent
    public static void onAddLayers(EntityRenderersEvent.AddLayers event) {
        // 这个事件会为所有实体触发，我们只关心玩家皮肤
        // event.getSkin("default") 获取默认（Steve）模型的渲染器
        LivingEntityRenderer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> defaultRenderer = event.getSkin("default");
        if (defaultRenderer != null) {
            defaultRenderer.addLayer(new HaloRender(defaultRenderer)); // 传入 renderer
        }

        // event.getSkin("slim") 获取 slim（Alex）模型的渲染器
        LivingEntityRenderer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> slimRenderer = event.getSkin("slim");
        if (slimRenderer != null) {
            slimRenderer.addLayer(new HaloRender(slimRenderer)); // 传入 renderer
        }
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

