package com.csdy.tcondiadema.method;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.network.syncher.EntityDataAccessor;
import java.lang.reflect.Field;

public class Util {

    private static final EntityDataAccessor<Float> DATA_HEALTH_ID = getHealthDataAccessor();

    private static EntityDataAccessor<Float> getHealthDataAccessor() {
        ///因为mc的狗屎混淆所以DATA_HEALTH_ID得写成f_20961_,不然非开发环境会报错,密码个逼
        ///普猫无视这个,因此AOP贯穿是必要的
        try {
            Field field = LivingEntity.class.getDeclaredField("f_20961_");
            field.setAccessible(true);
            Object value = field.get(null);

            if (value instanceof EntityDataAccessor) {
                return (EntityDataAccessor<Float>) value;
            } else {
                System.err.println("DATA_HEALTH_ID is not of type EntityDataAccessor");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void setHealth(Entity target, DamageSource source, float value) {
        if (!(target instanceof LivingEntity living)) return;
        if (DATA_HEALTH_ID == null) return;
        living.getEntityData().set(DATA_HEALTH_ID, value);
        if (living.getHealth() <= 0.0F) {
            living.die(source);
        }
    }
}

