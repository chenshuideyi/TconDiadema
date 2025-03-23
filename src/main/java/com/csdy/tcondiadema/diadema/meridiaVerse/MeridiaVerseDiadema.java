package com.csdy.tcondiadema.diadema.meridiaVerse;


import com.csdy.tcondiadema.diadema.api.ranges.SphereDiademaRange;
import com.csdy.tcondiadema.frames.diadema.Diadema;
import com.csdy.tcondiadema.frames.diadema.DiademaType;
import com.csdy.tcondiadema.frames.diadema.movement.DiademaMovement;
import com.csdy.tcondiadema.frames.diadema.range.DiademaRange;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

/// 我给它起的中文名是 \[经天纬地\]
public class MeridiaVerseDiadema extends Diadema {
    static final double RADIUS = 12;

    public MeridiaVerseDiadema(DiademaType type, DiademaMovement movement) {
        super(type, movement);
    }


    private final SphereDiademaRange range = new SphereDiademaRange(this, RADIUS);

    @Override public @NotNull DiademaRange getRange() {
        return range;
    }


    @Override protected void perTick() {
        // 像这样就能获取到受影响的实体了，这个东西会自动更显所以你甚至可以存在别的地方而不用反复手动获取！
        var entities = affectingEntities;
        for (var entity : entities) {
            if (!(entity instanceof LivingEntity living)) continue;
            var pos = living.position.subtract(getPosition());
            pos = pos.yRot(0.1f);
            pos = pos.xRot(0.75f);
            //pos = pos.yRot((float) Math.copySign(0.1, pos.z));
            living.position = pos.add(getPosition());
        }
    }
}
