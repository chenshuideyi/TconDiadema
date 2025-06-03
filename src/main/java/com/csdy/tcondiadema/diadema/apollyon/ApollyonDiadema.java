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
import net.minecraft.world.entity.monster.WitherSkeleton;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
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

    private static final int MAX_SERVANTS = 12;
    private static final int SERVANTS_TO_SPAWN = 12;

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

        int nearbySkeletons = countNearbyServants(level, getPosition());
        if (nearbySkeletons >= MAX_SERVANTS) return;

        // 计算需要生成的数量
        int toSpawn = Math.min(SERVANTS_TO_SPAWN, MAX_SERVANTS - nearbySkeletons);

        spawnMultipleServants(level, living,toSpawn);
    }

    private int countNearbyServants(Level level, Vec3 pos) {
        AABB area = new AABB(
                pos.x - RADIUS/2, pos.y - 5, pos.z - RADIUS/2,
                pos.x + RADIUS/2, pos.y + 5, pos.z + RADIUS/2
        );

        return level.getEntitiesOfClass(
                ApostleServant.class,
                area,
                e -> e.isAlive() && !e.isRemoved()
        ).size();
    }


    private void spawnMultipleServants(Level level, Entity holder, int toSpawn) {
        System.out.println("我被执行了一次");
        if (!(holder instanceof LivingEntity livingHolder)) {
            return;
        }

        for (int i = 0; i < toSpawn; i++) {
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
