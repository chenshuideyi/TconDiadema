package com.csdy.tcondiadema.diadema.shinratensei;


import com.csdy.tcondiadema.diadema.api.ranges.SphereDiademaRange;
import com.csdy.tcondiadema.frames.diadema.Diadema;
import com.csdy.tcondiadema.frames.diadema.DiademaType;
import com.csdy.tcondiadema.frames.diadema.movement.DiademaMovement;
import com.csdy.tcondiadema.frames.diadema.range.DiademaRange;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class ShinratenseiDiadema extends Diadema {
    static final double RADIUS = 8;
    private final Player player = getPlayer();
    public ShinratenseiDiadema(DiademaType type, DiademaMovement movement) {
        super(type, movement);
    }

    private final SphereDiademaRange range = new SphereDiademaRange(this,RADIUS);

    @Override
    public @NotNull DiademaRange getRange() {
        return range;
    }

    @Override
    protected void perTick() {
        for (Entity entity : affectingEntities) {
            if (!entity.equals(player) && (!(entity instanceof Arrow))){
                repelEntity(entity);
            }
            else if (entity instanceof Arrow) {
                if (player.position.subtract(entity.position).dot(entity.getDeltaMovement()) < 0)return;
                repelArrow((Arrow) entity);
            }
        }
    }

    private void repelEntity(Entity entity) {
        // 获取玩家和实体的位置
        Vec3 playerPos = player.position();
        Vec3 entityPos = entity.position();

        // 计算距离
        double distanceSquared = entityPos.distanceToSqr(playerPos);
        double radiusSquared = RADIUS * RADIUS;

        // 计算斥力强度（1 - (距离平方 / 领域半径平方)）
        double repelStrength = 1 - (distanceSquared / radiusSquared);

        // 确保斥力强度在 [0, 1] 范围内
        repelStrength = Math.max(0, Math.min(1, repelStrength));

        // 计算排斥方向（从玩家指向实体）
        Vec3 repelDirection = entityPos.subtract(playerPos).normalize();

        // 设置排斥速度
        double maxRepelStrength = 6; // 排斥强度，可以调整
        Vec3 repelVelocity = repelDirection.scale(maxRepelStrength * repelStrength);

        // 为实体设置速度
        entity.setDeltaMovement(repelVelocity);

    }
    private void repelArrow(Arrow arrow) {
        // 获取箭矢的速度
        Vec3 arrowVelocity = arrow.getDeltaMovement();

        // 反转速度方向，使其远离玩家
        Vec3 repelVelocity = arrowVelocity.reverse();

        // 调整弹走力度
        double repelStrength = 6.0; // 弹走力度，可以调整
        repelVelocity = repelVelocity.scale(repelStrength);

        // 为箭矢设置速度
        arrow.setDeltaMovement(repelVelocity);

        // 可选：防止箭矢在空中悬浮
        arrow.setNoGravity(false); // 恢复重力
    }
}
