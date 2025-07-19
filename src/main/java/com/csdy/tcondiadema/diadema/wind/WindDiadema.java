package com.csdy.tcondiadema.diadema.wind;

import com.csdy.tcondiadema.diadema.api.ranges.SphereDiademaRange;
import com.csdy.tcondiadema.effect.register.EffectRegister;
import com.csdy.tcondiadema.frames.diadema.Diadema;
import com.csdy.tcondiadema.frames.diadema.DiademaType;
import com.csdy.tcondiadema.frames.diadema.movement.DiademaMovement;
import com.csdy.tcondiadema.frames.diadema.range.DiademaRange;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

public class WindDiadema extends Diadema {
    static final double RADIUS = 8;
    public WindDiadema(DiademaType type, DiademaMovement movement) {
        super(type, movement);
    }

    private final SphereDiademaRange range = new SphereDiademaRange(this,RADIUS);

    @Override
    public @NotNull DiademaRange getRange() {
        return range;
    }

    @Override
    protected void onEntityEnter(Entity entity) {
        var core = getCoreEntity();
        if (core == null || entity.equals(core)) return;
        if (entity instanceof LivingEntity living){
            if (living.getAttribute(Attributes.ARMOR)!= null){
            living.getAttribute(Attributes.ARMOR).setBaseValue(0);
            }
        }
    }

    @Override
    protected void perTick() {
        var core = getCoreEntity();
        if(!(core instanceof Player player)) return;
        player.addEffect(new MobEffectInstance(EffectRegister.WIND.get(), 100, 0));
        player.getAbilities().setFlyingSpeed(0.2f);
        player.getAbilities().mayfly = true;
    }

    @Override
    protected void onEntityExit(Entity entity) {
        var core = getCoreEntity();
        if (core instanceof Player player && entity.equals(core)){
            player.removeEffect(new MobEffectInstance(EffectRegister.WIND.get()).getEffect());
            player.getAbilities().setFlyingSpeed(0.05f);
        }
    }
}
