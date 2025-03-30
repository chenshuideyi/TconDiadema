package com.csdy.tcondiadema.diadema;

import com.csdy.tcondiadema.ModMain;
import com.csdy.tcondiadema.diadema.abyss.AbyssClientDiadema;
import com.csdy.tcondiadema.diadema.abyss.AbyssDiadema;
import com.csdy.tcondiadema.diadema.avaritia.AvaritaClientDiadema;
import com.csdy.tcondiadema.diadema.avaritia.AvaritaDiadema;
import com.csdy.tcondiadema.diadema.fakekillaura.FakeKillAuraClientDiadema;
import com.csdy.tcondiadema.diadema.fakekillaura.FakeKillAuraDiadema;
import com.csdy.tcondiadema.diadema.fakemeltdown.FakeMeltdownClientDiadema;
import com.csdy.tcondiadema.diadema.fakemeltdown.FakeMeltdownDiadema;
import com.csdy.tcondiadema.diadema.fakeprojecte.FakeProjectEClientDiadema;
import com.csdy.tcondiadema.diadema.fakeprojecte.FakeProjectEDiadema;
import com.csdy.tcondiadema.diadema.fakeshinratensei.FakeShinratenseiClientDiadema;
import com.csdy.tcondiadema.diadema.fakeshinratensei.FakeShinratenseiDiadema;
import com.csdy.tcondiadema.diadema.gula.GulaClientDiadema;
import com.csdy.tcondiadema.diadema.gula.GulaDiadema;
import com.csdy.tcondiadema.diadema.ira.IraClientDiadema;
import com.csdy.tcondiadema.diadema.ira.IraDiadema;
import com.csdy.tcondiadema.diadema.killaura.KillAuraClientDiadema;
import com.csdy.tcondiadema.diadema.killaura.KillAuraDiadema;
import com.csdy.tcondiadema.diadema.luxuria.LuxuriaClinetDiadema;
import com.csdy.tcondiadema.diadema.luxuria.LuxuriaDiadema;
import com.csdy.tcondiadema.diadema.meltdown.MeltdownClientDiadema;
import com.csdy.tcondiadema.diadema.meltdown.MeltdownDiadema;
import com.csdy.tcondiadema.diadema.meridiaVerse.MeridiaVerseClientDiadema;
import com.csdy.tcondiadema.diadema.meridiaVerse.MeridiaVerseDiadema;
import com.csdy.tcondiadema.diadema.projecte.ProjectEClientDiadema;
import com.csdy.tcondiadema.diadema.projecte.ProjectEDiadema;
import com.csdy.tcondiadema.diadema.shinratensei.ShinratenseiClientDiadema;
import com.csdy.tcondiadema.diadema.shinratensei.ShinratenseiDiadema;
import com.csdy.tcondiadema.diadema.superbia.SuperbiaClinetDiadema;
import com.csdy.tcondiadema.diadema.superbia.SuperbiaDiadema;
import com.csdy.tcondiadema.diadema.timerain.TimeRainClinetDiadema;
import com.csdy.tcondiadema.diadema.timerain.TimeRainDiadema;
import com.csdy.tcondiadema.diadema.warden.WardenClientDiadema;
import com.csdy.tcondiadema.diadema.warden.WardenDiadema;
import com.csdy.tcondiadema.diadema.wind.WindClientDiadema;
import com.csdy.tcondiadema.diadema.wind.WindDiadema;
import com.csdy.tcondiadema.frames.CsdyRegistries;
import com.csdy.tcondiadema.frames.diadema.ClientDiademaType;
import com.csdy.tcondiadema.frames.diadema.DiademaType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

@OnlyIn(Dist.CLIENT)
public class ClientDiademaRegister {
    public static final DeferredRegister<ClientDiademaType> CLIENT_DIADEMA_TYPES = DeferredRegister.create(CsdyRegistries.CLIENT_DIADEMA_TYPE, ModMain.MODID);


    public static final RegistryObject<ClientDiademaType> WARDEN =
            CLIENT_DIADEMA_TYPES.register("warden", () -> ClientDiademaType.Create(WardenClientDiadema::new));
    public static final RegistryObject<ClientDiademaType> MERIDIA_VERSE =
            CLIENT_DIADEMA_TYPES.register("meridia_verse", () -> ClientDiademaType.Create(MeridiaVerseClientDiadema::new));
    public static final RegistryObject<ClientDiademaType> ABYSS =
            CLIENT_DIADEMA_TYPES.register("abyss", () -> ClientDiademaType.Create(AbyssClientDiadema::new));
    public static final RegistryObject<ClientDiademaType> WIND =
            CLIENT_DIADEMA_TYPES.register("wind", () -> ClientDiademaType.Create(WindClientDiadema::new));
    public static final RegistryObject<ClientDiademaType> GULA =
            CLIENT_DIADEMA_TYPES.register("gula", () -> ClientDiademaType.Create(GulaClientDiadema::new));
    public static final RegistryObject<ClientDiademaType> LUXURIA =
            CLIENT_DIADEMA_TYPES.register("luxuria", () -> ClientDiademaType.Create(LuxuriaClinetDiadema::new));
    public static final RegistryObject<ClientDiademaType> KILL_AURA =
            CLIENT_DIADEMA_TYPES.register("kill_aura", () -> ClientDiademaType.Create(KillAuraClientDiadema::new));
    public static final RegistryObject<ClientDiademaType> FAKE_KILL_AURA =
            CLIENT_DIADEMA_TYPES.register("fake_kill_aura", () -> ClientDiademaType.Create(FakeKillAuraClientDiadema::new));
    public static final RegistryObject<ClientDiademaType> PROJECTE =
            CLIENT_DIADEMA_TYPES.register("projecte", () -> ClientDiademaType.Create(ProjectEClientDiadema::new));
    public static final RegistryObject<ClientDiademaType> FAKE_PROJECTE =
            CLIENT_DIADEMA_TYPES.register("fake_projecte", () -> ClientDiademaType.Create(FakeProjectEClientDiadema::new));
    public static final RegistryObject<ClientDiademaType> MELT_DOWN =
            CLIENT_DIADEMA_TYPES.register("meltdown", () -> ClientDiademaType.Create(MeltdownClientDiadema::new));
    public static final RegistryObject<ClientDiademaType> FAKE_MELT_DOWN =
            CLIENT_DIADEMA_TYPES.register("fake_meltdown", () -> ClientDiademaType.Create(FakeMeltdownClientDiadema::new));
    public static final RegistryObject<ClientDiademaType> AVARITA =
            CLIENT_DIADEMA_TYPES.register("avarita", () -> ClientDiademaType.Create(AvaritaClientDiadema::new));
    public static final RegistryObject<ClientDiademaType> SUPERBIA =
            CLIENT_DIADEMA_TYPES.register("superbia", () -> ClientDiademaType.Create(SuperbiaClinetDiadema::new));
    public static final RegistryObject<ClientDiademaType> SHINRATENSEI =
            CLIENT_DIADEMA_TYPES.register("shinratensei", () -> ClientDiademaType.Create(ShinratenseiClientDiadema::new));
    public static final RegistryObject<ClientDiademaType> FAKE_SHINRATENSEI =
            CLIENT_DIADEMA_TYPES.register("fake_shinratensei", () -> ClientDiademaType.Create(FakeShinratenseiClientDiadema::new));
    public static final RegistryObject<ClientDiademaType> IRA =
            CLIENT_DIADEMA_TYPES.register("ira", () -> ClientDiademaType.Create(IraClientDiadema::new));
    public static final RegistryObject<ClientDiademaType> TIME_RAIN =
            CLIENT_DIADEMA_TYPES.register("time_rain", () -> ClientDiademaType.Create(TimeRainClinetDiadema::new));
}
