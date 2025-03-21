package com.csdy.diadema.meltdown;

import com.csdy.diadema.api.ranges.SphereDiademaRange;
import com.csdy.frames.diadema.Diadema;
import com.csdy.frames.diadema.DiademaType;
import com.csdy.frames.diadema.movement.DiademaMovement;
import com.csdy.frames.diadema.range.DiademaRange;
import mekanism.common.registries.MekanismDamageTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import mekanism.common.lib.radiation.RadiationManager;
import org.jetbrains.annotations.NotNull;

public class MeltdownDiadema extends Diadema {
    final static double RADIUS = 12;
    private final Player player = getPlayer();
    public MeltdownDiadema(DiademaType type, DiademaMovement movement) {
        super(type, movement);
    }

    private final SphereDiademaRange range = new SphereDiademaRange(this, RADIUS);

    @Override public @NotNull DiademaRange getRange() {
        return range;
    }

    @Override protected void perTick() {
        DamageSource src = MekanismDamageTypes.LASER.source(getLevel());
        for (Entity entity : affectingEntities) {
            if (!entity.equals(player)) {
                if (!(entity instanceof LivingEntity living)) continue;
                living.invulnerableTime = 0;
                living.hurt(src, 2500);
                RadiationManager.INSTANCE.radiate(living, 0.01);
            }
        }
    }
}
