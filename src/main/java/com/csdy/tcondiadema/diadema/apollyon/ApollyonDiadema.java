package com.csdy.tcondiadema.diadema.apollyon;

import com.csdy.tcondiadema.diadema.api.ranges.HalfSphereDiademaRange;
import com.csdy.tcondiadema.frames.diadema.ClientDiadema;
import com.csdy.tcondiadema.frames.diadema.Diadema;
import com.csdy.tcondiadema.frames.diadema.DiademaType;
import com.csdy.tcondiadema.frames.diadema.movement.DiademaMovement;
import com.csdy.tcondiadema.frames.diadema.range.DiademaRange;
import com.mega.revelationfix.common.entity.boss.ApostleServant;
import com.mega.revelationfix.common.init.ModEntities;
import lombok.NonNull;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;


public class ApollyonDiadema extends Diadema {

    final static double RADIUS = 16;
    private final Entity holder = getCoreEntity();
    private final HalfSphereDiademaRange range = new HalfSphereDiademaRange(this, RADIUS);

    public ApollyonDiadema(DiademaType type, DiademaMovement movement) {
        super(type, movement);
    }

    @Override
    public @NonNull DiademaRange getRange() {
        return range;
    }

    @Override
    protected void perTick() {
        ServerLevel level = getLevel();

        // 只在服务端执行，且确保 entity 是 LivingEntity
        if (level.isClientSide() || !(holder instanceof LivingEntity living)) {
            return;
        }

        if (holder.tickCount % 120 != 0) {
            return;
        }

//        if (holder.tickCount % 1200 != 0) {
//            return;
//        }

        // 生成 12 个仆从
        spawnMultipleServants(level, living);
    }

    private void spawnMultipleServants(Level level, Entity holder) {
        if (!(holder instanceof LivingEntity livingHolder)) {
            return;
        }

        for (int i = 0; i < 12; i++) {
            ApostleServant servant = ModEntities.APOSTLE_SERVANT.get().create(level);
            if (servant != null) {
                servant.setTrueOwner(livingHolder);

                double angle = Math.PI * 2 * i / 12; // 圆形分布
                double dx = Math.cos(angle) * 8;
                double dz = Math.sin(angle) * 8;
                servant.moveTo(
                        holder.getX() + dx,
                        holder.getY(),
                        holder.getZ() + dz,
                        holder.getYRot(),
                        holder.getXRot()
                );

                level.addFreshEntity(servant);
            }
        }
    }

}
