package com.csdy.tcondiadema.diadema.pheromonemist;

import com.csdy.tcondiadema.diadema.api.ranges.SphereDiademaRange;
import com.csdy.tcondiadema.frames.diadema.Diadema;
import com.csdy.tcondiadema.frames.diadema.DiademaType;
import com.csdy.tcondiadema.frames.diadema.movement.DiademaMovement;
import com.csdy.tcondiadema.frames.diadema.range.DiademaRange;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

public class PheromoneMistDiadema extends Diadema {
    static final double RADIUS = 8;

    public PheromoneMistDiadema(DiademaType type, DiademaMovement movement) {
        super(type, movement);
    }


    private final SphereDiademaRange range = new SphereDiademaRange(this, RADIUS);

    @Override public @NotNull DiademaRange getRange() {
        return range;
    }


    @Override protected void perTick() {
        // 全部给我发情！！！！！
        var entities = affectingEntities;
        for (var entity : entities) {
            if (!(entity instanceof Animal animal)) continue;
            if (!animal.isInLove()) animal.setInLove(null);
        }
    }
}
