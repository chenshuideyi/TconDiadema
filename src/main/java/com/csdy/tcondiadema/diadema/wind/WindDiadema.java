package com.csdy.tcondiadema.diadema.wind;

import com.csdy.tcondiadema.diadema.api.ranges.SphereDiademaRange;
import com.csdy.tcondiadema.frames.diadema.Diadema;
import com.csdy.tcondiadema.frames.diadema.DiademaType;
import com.csdy.tcondiadema.frames.diadema.movement.DiademaMovement;
import com.csdy.tcondiadema.frames.diadema.range.DiademaRange;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
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

    protected void onEntityEnter(Entity entity) {
        var player = getPlayer();
        if (player == null || entity.equals(player)) return;
        if (entity instanceof LivingEntity living){
            if (living.getAttribute(Attributes.ARMOR)!= null){
            living.getAttribute(Attributes.ARMOR).setBaseValue(0);
            System.out.println("设置护甲为0");
            }
        }
    }
}
