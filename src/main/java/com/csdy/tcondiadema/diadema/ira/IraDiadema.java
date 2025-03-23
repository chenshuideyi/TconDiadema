package com.csdy.tcondiadema.diadema.ira;


import com.csdy.tcondiadema.diadema.api.ranges.SphereDiademaRange;
import com.csdy.tcondiadema.frames.diadema.Diadema;
import com.csdy.tcondiadema.frames.diadema.DiademaType;
import com.csdy.tcondiadema.frames.diadema.movement.DiademaMovement;
import com.csdy.tcondiadema.frames.diadema.range.DiademaRange;
import lombok.NonNull;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;

public class IraDiadema extends Diadema {
    static final double RADIUS = 8;
    private Player player = getPlayer();

    public IraDiadema(DiademaType type, DiademaMovement movement) {
        super(type, movement);
    }

    private final SphereDiademaRange range = new SphereDiademaRange(this,RADIUS);

    @Override
    public @NonNull DiademaRange getRange() {
        return range;
    }

    @Override protected void perTick() {
        for (Entity entity : affectingEntities) {
            if (!entity.equals(player)) {
                if (!(entity instanceof Mob mob)) continue;
                mob.setTarget(mob);
            }
        }
    }
}
