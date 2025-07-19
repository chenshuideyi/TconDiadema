package com.csdy.tcondiadema.diadema.wonderofyou;

import com.csdy.tcondiadema.diadema.api.ranges.SelfDiademaRange;
import com.csdy.tcondiadema.frames.diadema.Diadema;
import com.csdy.tcondiadema.frames.diadema.DiademaType;
import com.csdy.tcondiadema.frames.diadema.movement.DiademaMovement;
import com.csdy.tcondiadema.frames.diadema.range.DiademaRange;
import lombok.NonNull;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class WonderOfYouDiadema extends Diadema {

    public WonderOfYouDiadema(DiademaType type, DiademaMovement movement) {
        super(type, movement);
    }

    private final SelfDiademaRange range = new SelfDiademaRange(this, getCoreEntity());

    @Override public @NotNull DiademaRange getRange() {
        return range;
    }

    @Override
    protected void perTick() {
        var core = getCoreEntity();
        if (!(core instanceof LivingEntity coreLiving)) return;

        // 获取世界中的所有生物
        for (LivingEntity entity : core.level().getEntitiesOfClass(LivingEntity.class, core.getBoundingBox().inflate(100.0))) {
            // 1. 处理玩家瞄准核心的情况
            if (entity instanceof Player player && !player.equals(core)) {
                if (isPlayerLookingAtEntity(player, coreLiving)) {
                    player.kill();
                }
            }
            // 2. 处理生物以核心为仇恨目标的情况
            else if (entity instanceof Mob mob) {
                if (mob.getTarget() != null && mob.getTarget().equals(coreLiving)) {
                    mob.kill();
                }
            }
        }
    }

    // 检查玩家是否正在瞄准实体
    private boolean isPlayerLookingAtEntity(Player player, LivingEntity target) {
        // 获取玩家的视线方向
        Vec3 lookVec = player.getLookAngle();
        Vec3 playerEyePos = player.getEyePosition(1.0f);

        // 计算到目标的向量
        Vec3 targetPos = target.getBoundingBox().getCenter();
        Vec3 toTarget = targetPos.subtract(playerEyePos);

        // 标准化向量并计算点积
        toTarget = toTarget.normalize();
        double dot = lookVec.dot(toTarget);

        // 如果点积接近1（视线方向与目标方向夹角很小）
        return dot > 0.99; // 调整此值可以改变判定精度
    }

}
