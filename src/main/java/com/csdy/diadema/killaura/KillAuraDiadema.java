package com.csdy.diadema.killaura;

import com.csdy.diadema.ranges.SphereDiademaRange;
import com.csdy.frames.diadema.Diadema;
import com.csdy.frames.diadema.DiademaType;
import com.csdy.frames.diadema.movement.DiademaMovement;
import com.csdy.frames.diadema.range.DiademaRange;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class KillAuraDiadema extends Diadema {
    static final double DEFAULT_RADIUS = 6;
    private final Player player = getPlayer();
    private double currentRadius = DEFAULT_RADIUS;

    private final SphereDiademaRange range;

    public KillAuraDiadema(DiademaType type, DiademaMovement movement) {
        super(type, movement);
        this.range = new SphereDiademaRange(this, currentRadius);
    }

    @Override
    public DiademaRange getRange() {
        return range;
    }


    public void setRange(double newRadius) {
        this.currentRadius = newRadius;
        this.range.setRadius(newRadius);
    }

    @Override
    protected void writeCustomSyncData(FriendlyByteBuf buf) {
        buf.writeDouble(range.getRadius());
    }

    @Override
    protected void perTick() {
        double reach = player.getBlockReach();
        setRange(reach);
        for (Entity entity : affectingEntities) {
            if (!(entity instanceof LivingEntity)) continue;
            if (!entity.equals(player)) {
                entity.invulnerableTime = 0;
                player.attack(entity);
            }
        }
    }
}
