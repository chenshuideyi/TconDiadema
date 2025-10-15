package com.csdy.tcondiadema.diadema.real_csdy;

import com.csdy.tcondiadema.diadema.api.ranges.HalfSphereDiademaRange;
import com.csdy.tcondiadema.frames.diadema.Diadema;
import com.csdy.tcondiadema.frames.diadema.DiademaType;
import com.csdy.tcondiadema.frames.diadema.movement.DiademaMovement;
import com.csdy.tcondiadema.frames.diadema.range.DiademaRange;
import lombok.NonNull;

public class RealCsdyWorldDiadema extends Diadema {

    final static double RADIUS = 16;

    public RealCsdyWorldDiadema(DiademaType type, DiademaMovement movement) {
        super(type, movement);
        setShouldBeCsdyKilled(false);
    }

    private final HalfSphereDiademaRange range = new HalfSphereDiademaRange(this, RADIUS);

    @Override
    public @NonNull DiademaRange getRange() {
        return range;
    }


}
