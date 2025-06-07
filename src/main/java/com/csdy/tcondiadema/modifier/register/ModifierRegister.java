package com.csdy.tcondiadema.modifier.register;

import com.csdy.tcondiadema.diadema.DiademaRegister;
import com.csdy.tcondiadema.modifier.AuraFormaModifier;
import com.csdy.tcondiadema.modifier.CommonDiademaModifier;
import com.csdy.tcondiadema.modifier.DiademaModifier;
import com.csdy.tcondiadema.modifier.FormaModifier;
import com.csdy.tcondiadema.modifier.diadema.*;
import slimeknights.tconstruct.library.modifiers.util.ModifierDeferredRegister;
import slimeknights.tconstruct.library.modifiers.util.StaticModifier;

import static com.csdy.tcondiadema.TconDiadema.MODID;

public class ModifierRegister {
    public static ModifierDeferredRegister MODIFIERS = ModifierDeferredRegister.create(MODID);
    public static final StaticModifier<AuraFormaModifier> AURA_FORMA_MODIFIER_STATIC_MODIFIER = MODIFIERS.register("aura_forma", AuraFormaModifier::new);
    public static final StaticModifier<FormaModifier> FORMA_MODIFIER_STATIC_MODIFIER = MODIFIERS.register("forma", FormaModifier::new);
    public static final StaticModifier<DiademaModifier> GULA_STATIC_MODIFIER = MODIFIERS.register("gula", CommonDiademaModifier.Create(DiademaRegister.GULA));
    public static final StaticModifier<DiademaModifier> FAKE_MELT_DOWN_STATIC_MODIFIER = MODIFIERS.register("fake_meltdown", CommonDiademaModifier.Create(DiademaRegister.FAKE_MELT_DOWN));
    public static final StaticModifier<DiademaModifier> MELT_DOWN_STATIC_MODIFIER = MODIFIERS.register("meltdown", CommonDiademaModifier.Create(DiademaRegister.MELT_DOWN));
    public static final StaticModifier<DiademaModifier> LUXURIA_STATIC_MODIFIER = MODIFIERS.register("luxuria", CommonDiademaModifier.Create(DiademaRegister.LUXURIA));
    public static final StaticModifier<DiademaModifier> WARDEN_STATIC_MODIFIER = MODIFIERS.register("warden", CommonDiademaModifier.Create(DiademaRegister.WARDEN));
    public static final StaticModifier<DiademaModifier> FAKE_KILL_AURA_STATIC_MODIFIER = MODIFIERS.register("fake_kill_aura", CommonDiademaModifier.Create(DiademaRegister.FAKE_KILL_AURA));
    public static final StaticModifier<DiademaModifier> KILL_AURA_STATIC_MODIFIER = MODIFIERS.register("kill_aura", CommonDiademaModifier.Create(DiademaRegister.KILL_AURA));
    public static final StaticModifier<DiademaModifier> FAKE_PROJECT_E_STATIC_MODIFIER = MODIFIERS.register("fake_projecte", CommonDiademaModifier.Create(DiademaRegister.FAKE_PROJECTE));
    public static final StaticModifier<DiademaModifier> PROJECT_E_STATIC_MODIFIER = MODIFIERS.register("projecte", CommonDiademaModifier.Create(DiademaRegister.PROJECTE));
    public static final StaticModifier<DiademaModifier> WIND_STATIC_MODIFIER = MODIFIERS.register("wind", WindModifier::new);
    public static final StaticModifier<DiademaModifier> MERIDIA_VERSE_STATIC_MODIFIER = MODIFIERS.register("meridia_verse", CommonDiademaModifier.Create(DiademaRegister.MERIDIA_VERSE));
    public static final StaticModifier<DiademaModifier> AVARITA_STATIC_MODIFIER = MODIFIERS.register("avarita", CommonDiademaModifier.Create(DiademaRegister.AVARITA));
    public static final StaticModifier<DiademaModifier> SUPERBIA_STATIC_MODIFIER = MODIFIERS.register("superbia", CommonDiademaModifier.Create(DiademaRegister.SUPERBIA));
    public static final StaticModifier<DiademaModifier> ABYSS_STATIC_MODIFIER = MODIFIERS.register("abyss", CommonDiademaModifier.Create(DiademaRegister.ABYSS));
    public static final StaticModifier<DiademaModifier> FAKE_SHINRATENSEI_STATIC_MODIFIER = MODIFIERS.register("fake_shinratensei", CommonDiademaModifier.Create(DiademaRegister.FAKE_SHINRATENSEI));
    public static final StaticModifier<DiademaModifier> SHINRATENSEI_STATIC_MODIFIER = MODIFIERS.register("shinratensei", CommonDiademaModifier.Create(DiademaRegister.SHINRATENSEI));
    public static final StaticModifier<DiademaModifier> TIME_RAIN_STATIC_MODIFIER = MODIFIERS.register("time_rain", CommonDiademaModifier.Create(DiademaRegister.TIME_RAIN));
    public static final StaticModifier<DiademaModifier> PHEROMONE_MIST_STATIC_MODIFIER = MODIFIERS.register("pheromone_mist", CommonDiademaModifier.Create(DiademaRegister.PHEROMONE_MIST));
    public static final StaticModifier<DiademaModifier> LOVE_TRAIN_STATIC_MODIFIER = MODIFIERS.register("love_train", CommonDiademaModifier.Create(DiademaRegister.LOVE_TRAIN));
    public static final StaticModifier<DiademaModifier> APOLLYON_STATIC_MODIFIER = MODIFIERS.register("apollyon", CommonDiademaModifier.Create(DiademaRegister.APOLLYON));
    public static final StaticModifier<DiademaModifier> FAKE_APOLLYON_STATIC_MODIFIER = MODIFIERS.register("fake_apollyon", CommonDiademaModifier.Create(DiademaRegister.FAKE_APOLLYON));
    public static final StaticModifier<DiademaModifier> MARTYR_STATIC_MODIFIER = MODIFIERS.register("martyr", CommonDiademaModifier.Create(DiademaRegister.MARTYR));









}
