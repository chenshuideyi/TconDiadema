package com.csdy.tcondiadema.diadema.api.ranges;

import com.csdy.tcondiadema.frames.diadema.Diadema;
import com.csdy.tcondiadema.frames.diadema.range.CommonDiademaRange;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

@Setter
@Getter
public class SelfDiademaRange extends CommonDiademaRange {

    private Entity entity;

    public SelfDiademaRange(Diadema diadema, Entity entity) {
        super(diadema);
        this.entity = entity;
    }

    @Override
    protected AABB getAABB() {
        Entity user = diadema.getCoreEntity(); // 获取使用者实体
        if (user != null) {
            Vec3 pos = user.position(); // 获取使用者位置
            // 创建一个很小的AABB，基本上只包含使用者自身
            return new AABB(pos.x - 0.1, pos.y - 0.1, pos.z - 0.1,
                    pos.x + 0.1, pos.y + 0.1, pos.z + 0.1);
        }
        return null;
    }

    @Override
    public boolean ifInclude(Vec3 position) {
        Entity user = diadema.getCoreEntity();
        if (user != null) {
            return user.position().distanceToSqr(position) < 0.01; // 0.1 blocks squared
        }
        return false;
    }
}
