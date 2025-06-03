package com.csdy.tcondiadema.particle.register;

import com.csdy.tcondiadema.TconDiadema;
import com.csdy.tcondiadema.particle.*;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = TconDiadema.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ParticlesRegister {
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, TconDiadema.MODID);

    public static final RegistryObject<SimpleParticleType> CUSTOM_PARTICLE = PARTICLE_TYPES.register("custom_particle", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> SHADOW_PARTICLE = PARTICLE_TYPES.register("shadow_particle", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> EMC_PARTICLE = PARTICLE_TYPES.register("emc_particle", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> SULFUR_PARTICLE = PARTICLE_TYPES.register("sulfur_particle", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> DARK_PARTICLE = PARTICLE_TYPES.register("dark_particle", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> ABYSS_PARTICLE = PARTICLE_TYPES.register("abyss_particle", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> MERIDIA_VERSE__PARTICLE = PARTICLE_TYPES.register("meridia_verse_particle", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> GULA_PARTICLE = PARTICLE_TYPES.register("gula_particle", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> KILL_AURA_PARTICLE = PARTICLE_TYPES.register("kill_aura_particle", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> MELTDOWN_PARTICLE = PARTICLE_TYPES.register("meltdown_particle", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> LUXURIA_PARTICLE = PARTICLE_TYPES.register("luxuria_particle", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> AVARITA_PARTICLE = PARTICLE_TYPES.register("avarita_particle", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> SUPERNIAPARTICLE_PARTICLE = PARTICLE_TYPES.register("superbia_particle", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> SHINRATENSEI_PARTICLE = PARTICLE_TYPES.register("shinratensei_particle", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> TIME_RAIN_PARTICLE = PARTICLE_TYPES.register("time_rain_particle", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> LOVE_TRAIN_PARTICLE = PARTICLE_TYPES.register("love_train_particle", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> APOLLYON_PARTICLE = PARTICLE_TYPES.register("apollyon_particle", () -> new SimpleParticleType(false));



    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void onClientSetup(RegisterParticleProvidersEvent event) {
        // 注册粒子工厂
        Minecraft.getInstance().particleEngine.register(ParticlesRegister.CUSTOM_PARTICLE.get(), CustomParticle.Provider::new);
        Minecraft.getInstance().particleEngine.register(ParticlesRegister.SHADOW_PARTICLE.get(), ShadowPartice.Provider::new);
        Minecraft.getInstance().particleEngine.register(ParticlesRegister.EMC_PARTICLE.get(), EmcParticle.Provider::new);
        Minecraft.getInstance().particleEngine.register(ParticlesRegister.SULFUR_PARTICLE.get(), SulfurParticle.Provider::new);
        Minecraft.getInstance().particleEngine.register(ParticlesRegister.DARK_PARTICLE.get(), DarkPartice.Provider::new);
        Minecraft.getInstance().particleEngine.register(ParticlesRegister.ABYSS_PARTICLE.get(), AbyssParticle.Provider::new);
        Minecraft.getInstance().particleEngine.register(ParticlesRegister.MERIDIA_VERSE__PARTICLE.get(), MeridiaVerseParticle.Provider::new);
        Minecraft.getInstance().particleEngine.register(ParticlesRegister.GULA_PARTICLE.get(), GulaParticle.Provider::new);
        Minecraft.getInstance().particleEngine.register(ParticlesRegister.KILL_AURA_PARTICLE.get(), KillAuraParticle.Provider::new);
        Minecraft.getInstance().particleEngine.register(ParticlesRegister.MELTDOWN_PARTICLE.get(), MeltDownPartice.Provider::new);
        Minecraft.getInstance().particleEngine.register(ParticlesRegister.LUXURIA_PARTICLE.get(), LuxuriaParticle.Provider::new);
        Minecraft.getInstance().particleEngine.register(ParticlesRegister.AVARITA_PARTICLE.get(), AvaritaParticle.Provider::new);
        Minecraft.getInstance().particleEngine.register(ParticlesRegister.SUPERNIAPARTICLE_PARTICLE.get(), SuperbiaParticle.Provider::new);
        Minecraft.getInstance().particleEngine.register(ParticlesRegister.SHINRATENSEI_PARTICLE.get(), ShinratenseiParticle.Provider::new);
        Minecraft.getInstance().particleEngine.register(ParticlesRegister.TIME_RAIN_PARTICLE.get(), TimeRainParticle.Provider::new);
        Minecraft.getInstance().particleEngine.register(ParticlesRegister.LOVE_TRAIN_PARTICLE.get(), LoveTrainParticle.Provider::new);
        Minecraft.getInstance().particleEngine.register(ParticlesRegister.APOLLYON_PARTICLE.get(), ApollyonParticle.Provider::new);


    }
}
