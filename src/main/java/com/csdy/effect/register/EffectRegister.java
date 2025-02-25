package com.csdy.effect.register;

import com.csdy.ModMain;
import com.csdy.effect.PhysicalInjury;
import com.csdy.effect.Scared;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class EffectRegister {
    public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, ModMain.MODID);

    public static final RegistryObject<MobEffect> SCARED = EFFECTS.register("scared", Scared::new);
    public static final RegistryObject<MobEffect> PHYSICALINJURY = EFFECTS.register("physical_injury", PhysicalInjury::new);


}
