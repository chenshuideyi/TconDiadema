package com.csdy.tcondiadema.diadema.api.ranges;

import com.csdy.tcondiadema.frames.diadema.Diadema;
import com.csdy.tcondiadema.frames.diadema.range.CommonDiademaRange;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import lombok.Getter;
import lombok.Setter;



@Setter
@Getter
public class SquareDiademaRange extends CommonDiademaRange {
    public SquareDiademaRange(Diadema diadema, double radius) {
        super(diadema);
        this.radius = radius;
    }

    private double radius;

    @Override
    protected AABB getAABB() {
        Vec3 center = diadema.getPosition();

        double minX = center.x() - radius;
        double minY = center.y() - radius;
        double minZ = center.z() - radius;

        double maxX = center.x() + radius;
        double maxY = center.y() + radius;
        double maxZ = center.z() + radius;

        return new AABB(minX, minY, minZ, maxX, maxY, maxZ);
    }

    @Override
    public boolean ifInclude(Vec3 position) {
        AABB boundingBox = getAABB();
        return boundingBox.contains(position);
    }
}
