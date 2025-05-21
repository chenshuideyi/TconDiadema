package com.csdy.tcondiadema.diadema.killaura;


import com.csdy.tcondiadema.diadema.api.ranges.SphereDiademaRange;
import com.csdy.tcondiadema.frames.diadema.Diadema;
import com.csdy.tcondiadema.frames.diadema.DiademaType;
import com.csdy.tcondiadema.frames.diadema.movement.DiademaMovement;
import com.csdy.tcondiadema.frames.diadema.range.DiademaRange;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

public class KillAuraDiadema extends Diadema {
    static final double DEFAULT_RADIUS = 6;
    private final Entity entity = getCoreEntity();
    private double currentRadius = DEFAULT_RADIUS;

    private final SphereDiademaRange range;

    public KillAuraDiadema(DiademaType type, DiademaMovement movement) {
        super(type, movement);
        this.range = new SphereDiademaRange(this, currentRadius);
    }

    @Override
    public @NotNull DiademaRange getRange() {
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
        if (!this.isPlayer()) return;
        Player player = (Player) entity;
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
