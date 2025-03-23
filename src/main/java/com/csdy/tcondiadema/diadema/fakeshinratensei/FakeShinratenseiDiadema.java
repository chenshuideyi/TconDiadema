package com.csdy.tcondiadema.diadema.fakeshinratensei;


import com.csdy.tcondiadema.diadema.api.ranges.SphereDiademaRange;
import com.csdy.tcondiadema.frames.diadema.Diadema;
import com.csdy.tcondiadema.frames.diadema.DiademaType;
import com.csdy.tcondiadema.frames.diadema.movement.DiademaMovement;
import com.csdy.tcondiadema.frames.diadema.range.DiademaRange;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

public class FakeShinratenseiDiadema extends Diadema {
    static final double RADIUS = 8;
    private final Player player = getPlayer();
    public FakeShinratenseiDiadema(DiademaType type, DiademaMovement movement) {
        super(type, movement);
    }

    private final SphereDiademaRange range = new SphereDiademaRange(this,RADIUS);

    @Override
    public @NotNull DiademaRange getRange() {
        return range;
    }

    @Override
    protected void perTick() {
        for (Entity entity : affectingEntities) {
            if (!(entity instanceof LivingEntity living)) continue;
            if (!entity.equals(player)) {
                living.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,30,1));
            }
        }
    }

}
