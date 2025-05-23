package com.csdy.tcondiadema.diadema;

import com.csdy.tcondiadema.TconDiadema;
import com.csdy.tcondiadema.diadema.abyss.*;
import com.csdy.tcondiadema.diadema.avaritia.*;
import com.csdy.tcondiadema.diadema.fakekillaura.*;
import com.csdy.tcondiadema.diadema.fakemeltdown.*;
import com.csdy.tcondiadema.diadema.fakeprojecte.FakeProjectEDiadema;
import com.csdy.tcondiadema.diadema.fakeshinratensei.FakeShinratenseiDiadema;
import com.csdy.tcondiadema.diadema.gula.GulaDiadema;
import com.csdy.tcondiadema.diadema.ira.IraDiadema;
import com.csdy.tcondiadema.diadema.killaura.KillAuraDiadema;
import com.csdy.tcondiadema.diadema.lovetrain.LoveTrainDiadema;
import com.csdy.tcondiadema.diadema.luxuria.LuxuriaDiadema;
import com.csdy.tcondiadema.diadema.meltdown.MeltdownDiadema;
import com.csdy.tcondiadema.diadema.meridiaVerse.MeridiaVerseDiadema;
import com.csdy.tcondiadema.diadema.pheromonemist.PheromoneMistDiadema;
import com.csdy.tcondiadema.diadema.projecte.ProjectEDiadema;
import com.csdy.tcondiadema.diadema.shinratensei.ShinratenseiDiadema;
import com.csdy.tcondiadema.diadema.superbia.SuperbiaDiadema;
import com.csdy.tcondiadema.diadema.timerain.TimeRainDiadema;
import com.csdy.tcondiadema.diadema.warden.WardenDiadema;
import com.csdy.tcondiadema.diadema.wind.WindDiadema;
import com.csdy.tcondiadema.diadema.wither.WitherDiadema;
import com.csdy.tcondiadema.frames.diadema.DiademaType;
import com.csdy.tcondiadema.frames.CsdyRegistries;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

// 把你的领域注册上来就算是完成了！
public class DiademaRegister {
    public static final DeferredRegister<DiademaType> DIADEMA_TYPES = DeferredRegister.create(CsdyRegistries.DIADEMA_TYPE, TconDiadema.MODID);

    public static final RegistryObject<DiademaType> WARDEN =
            DIADEMA_TYPES.register("warden", () -> DiademaType.create(WardenDiadema::new));
    public static final RegistryObject<DiademaType> MERIDIA_VERSE =
            DIADEMA_TYPES.register("meridia_verse", () -> DiademaType.create(MeridiaVerseDiadema::new));
    public static final RegistryObject<DiademaType> ABYSS =
            DIADEMA_TYPES.register("abyss", () -> DiademaType.create(AbyssDiadema::new));
    public static final RegistryObject<DiademaType> WIND =
            DIADEMA_TYPES.register("wind", () -> DiademaType.create(WindDiadema::new));
    public static final RegistryObject<DiademaType> GULA =
            DIADEMA_TYPES.register("gula", () -> DiademaType.create(GulaDiadema::new));
    public static final RegistryObject<DiademaType> LUXURIA =
            DIADEMA_TYPES.register("luxuria", () -> DiademaType.create(LuxuriaDiadema::new));
    public static final RegistryObject<DiademaType> KILL_AURA =
            DIADEMA_TYPES.register("kill_aura", () -> DiademaType.create(KillAuraDiadema::new));
    public static final RegistryObject<DiademaType> FAKE_KILL_AURA =
            DIADEMA_TYPES.register("fake_kill_aura", () -> DiademaType.create(FakeKillAuraDiadema::new));
    public static final RegistryObject<DiademaType> PROJECTE =
            DIADEMA_TYPES.register("projecte", () -> DiademaType.create(ProjectEDiadema::new));
    public static final RegistryObject<DiademaType> FAKE_PROJECTE =
            DIADEMA_TYPES.register("fake_projecte", () -> DiademaType.create(FakeProjectEDiadema::new));
    public static final RegistryObject<DiademaType> MELT_DOWN =
            DIADEMA_TYPES.register("meltdown", () -> DiademaType.create(MeltdownDiadema::new));
    public static final RegistryObject<DiademaType> FAKE_MELT_DOWN =
            DIADEMA_TYPES.register("fake_meltdown", () -> DiademaType.create(FakeMeltdownDiadema::new));
    public static final RegistryObject<DiademaType> AVARITA =
            DIADEMA_TYPES.register("avarita", () -> DiademaType.create(AvaritaDiadema::new));
    public static final RegistryObject<DiademaType> SUPERBIA =
            DIADEMA_TYPES.register("superbia", () -> DiademaType.create(SuperbiaDiadema::new));
    public static final RegistryObject<DiademaType> SHINRATENSEI =
            DIADEMA_TYPES.register("shinratensei", () -> DiademaType.create(ShinratenseiDiadema::new));
    public static final RegistryObject<DiademaType> FAKE_SHINRATENSEI =
            DIADEMA_TYPES.register("fake_shinratensei", () -> DiademaType.create(FakeShinratenseiDiadema::new));
    public static final RegistryObject<DiademaType> IRA =
            DIADEMA_TYPES.register("ira", () -> DiademaType.create(IraDiadema::new));
    public static final RegistryObject<DiademaType> TIME_RAIN =
            DIADEMA_TYPES.register("time_rain", () -> DiademaType.create(TimeRainDiadema::new));
    public static final RegistryObject<DiademaType> WITHER =
            DIADEMA_TYPES.register("wither", () -> DiademaType.create(WitherDiadema::new));
    public static final RegistryObject<DiademaType> PHEROMONE_MIST =
            DIADEMA_TYPES.register("pheromone_mist", () -> DiademaType.create(PheromoneMistDiadema::new));
    public static final RegistryObject<DiademaType> LOVE_TRAIN =
            DIADEMA_TYPES.register("love_train", () -> DiademaType.create(LoveTrainDiadema::new));

}
