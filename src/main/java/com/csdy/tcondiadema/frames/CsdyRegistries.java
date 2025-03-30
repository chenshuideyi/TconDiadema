package com.csdy.tcondiadema.frames;


import com.csdy.tcondiadema.ModMain;
import com.csdy.tcondiadema.frames.diadema.ClientDiademaType;
import com.csdy.tcondiadema.frames.diadema.DiademaType;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.NewRegistryEvent;
import net.minecraftforge.registries.RegistryBuilder;

import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = ModMain.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CsdyRegistries {
    public static final ResourceKey<Registry<DiademaType>> DIADEMA_TYPE = createRegistryKey("diadema_type");
    public static Supplier<IForgeRegistry<DiademaType>> DIADEMA_TYPES_REG;

    public static final ResourceKey<Registry<ClientDiademaType>> CLIENT_DIADEMA_TYPE = createRegistryKey("client_diadema_type");
    @OnlyIn(Dist.CLIENT)
    public static Supplier<IForgeRegistry<ClientDiademaType>> CLIENT_DIADEMA_TYPES_REG;


    @SubscribeEvent
    public static void onNewRegistry(NewRegistryEvent e) {
        RegistryBuilder<DiademaType> diademaTypeRegistryBuilder = new RegistryBuilder<>();
        diademaTypeRegistryBuilder.setName(DIADEMA_TYPE.location())
                .setDefaultKey(new ResourceLocation(ModMain.MODID, "default"));

        DIADEMA_TYPES_REG = e.create(diademaTypeRegistryBuilder);

        // 以下内容仅在客户端运行
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
            RegistryBuilder<ClientDiademaType> clientDiademaTypeRegistryBuilder = new RegistryBuilder<>();
            clientDiademaTypeRegistryBuilder.setName(CLIENT_DIADEMA_TYPE.location())
                    .setDefaultKey(new ResourceLocation(ModMain.MODID, "default"));

            CLIENT_DIADEMA_TYPES_REG = e.create(clientDiademaTypeRegistryBuilder);
        });
    }

    private static <T> ResourceKey<Registry<T>> createRegistryKey(String name) {
        return ResourceKey.createRegistryKey(new ResourceLocation(ModMain.MODID, name));
    }
}
