package com.csdy.tcondiadema.diadema;

import com.csdy.tcondiadema.ModMain;
import com.csdy.tcondiadema.diadema.abyss.*;
import com.csdy.tcondiadema.diadema.avaritia.*;
import com.csdy.tcondiadema.diadema.fakekillaura.*;
import com.csdy.tcondiadema.diadema.fakemeltdown.*;
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
import com.csdy.tcondiadema.frames.diadema.DiademaType;
import com.csdy.tcondiadema.frames.CsdyRegistries;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

// 把你的领域注册上来就算是完成了！
public class DiademaRegister {
    public static final DeferredRegister<DiademaType> DIADEMA_TYPES = DeferredRegister.create(CsdyRegistries.DIADEMA_TYPE, ModMain.MODID);

    public static final RegistryObject<DiademaType> WARDEN =
            DIADEMA_TYPES.register("warden", () -> DiademaType.Create(WardenDiadema::new, WardenClientDiadema::new));

    public static final RegistryObject<DiademaType> MERIDIA_VERSE =
            DIADEMA_TYPES.register("meridia_verse", () -> DiademaType.Create(MeridiaVerseDiadema::new, MeridiaVerseClientDiadema::new));
    public static final RegistryObject<DiademaType> ABYSS =
            DIADEMA_TYPES.register("abyss", () -> DiademaType.Create(AbyssDiadema::new, AbyssClientDiadema::new));
    public static final RegistryObject<DiademaType> WIND =
            DIADEMA_TYPES.register("wind", () -> DiademaType.Create(WindDiadema::new, WindClientDiadema::new));
    public static final RegistryObject<DiademaType> GULA =
            DIADEMA_TYPES.register("gula", () -> DiademaType.Create(GulaDiadema::new, GulaClientDiadema::new));
    public static final RegistryObject<DiademaType> LUXURIA =
            DIADEMA_TYPES.register("luxuria", () -> DiademaType.Create(LuxuriaDiadema::new, LuxuriaClinetDiadema::new));
    public static final RegistryObject<DiademaType> KILL_AURA =
            DIADEMA_TYPES.register("kill_aura", () -> DiademaType.Create(KillAuraDiadema::new, KillAuraClientDiadema::new));
    public static final RegistryObject<DiademaType> FAKE_KILL_AURA =
            DIADEMA_TYPES.register("fake_kill_aura", () -> DiademaType.Create(FakeKillAuraDiadema::new, FakeKillAuraClientDiadema::new));
    public static final RegistryObject<DiademaType> PROJECTE =
            DIADEMA_TYPES.register("projecte", () -> DiademaType.Create(ProjectEDiadema::new, ProjectEClientDiadema::new));
    public static final RegistryObject<DiademaType> FAKE_PROJECTE =
            DIADEMA_TYPES.register("fake_projecte", () -> DiademaType.Create(FakeProjectEDiadema::new, FakeProjectEClientDiadema::new));
    public static final RegistryObject<DiademaType> MELT_DOWN =
            DIADEMA_TYPES.register("meltdown", () -> DiademaType.Create(MeltdownDiadema::new, MeltdownClientDiadema::new));
    public static final RegistryObject<DiademaType> FAKE_MELT_DOWN =
            DIADEMA_TYPES.register("fake_meltdown", () -> DiademaType.Create(FakeMeltdownDiadema::new, FakeMeltdownClientDiadema::new));
    public static final RegistryObject<DiademaType> AVARITA =
            DIADEMA_TYPES.register("avarita", () -> DiademaType.Create(AvaritaDiadema::new, AvaritaClientDiadema::new));
    public static final RegistryObject<DiademaType> SUPERBIA =
            DIADEMA_TYPES.register("superbia", () -> DiademaType.Create(SuperbiaDiadema::new, SuperbiaClinetDiadema::new));
    public static final RegistryObject<DiademaType> SHINRATENSEI =
            DIADEMA_TYPES.register("shinratensei", () -> DiademaType.Create(ShinratenseiDiadema::new, ShinratenseiClientDiadema::new));
    public static final RegistryObject<DiademaType> FAKE_SHINRATENSEI =
            DIADEMA_TYPES.register("fake_shinratensei", () -> DiademaType.Create(FakeShinratenseiDiadema::new, FakeShinratenseiClientDiadema::new));
    public static final RegistryObject<DiademaType> IRA =
            DIADEMA_TYPES.register("ira", () -> DiademaType.Create(IraDiadema::new, IraClientDiadema::new));
    public static final RegistryObject<DiademaType> TIME_RAIN =
            DIADEMA_TYPES.register("time_rain", () -> DiademaType.Create(TimeRainDiadema::new, TimeRainClinetDiadema::new));
















}
