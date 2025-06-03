package com.csdy.tcondiadema.diadema.apollyon;

import com.Polarice3.Goety.client.particles.ModParticleTypes;
import com.csdy.tcondiadema.ParticleUtils;
import com.csdy.tcondiadema.frames.diadema.ClientDiadema;
import com.csdy.tcondiadema.particle.register.ParticlesRegister;
import com.csdy.tcondiadema.particleUtils.PointSets;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Random;

import static java.lang.Math.*;

@OnlyIn(Dist.CLIENT)
public class ApollyonClientDiadema extends ClientDiadema {
    @Override protected void perTick() {
        var level = Minecraft.getInstance().level;

        if (level == null) return;
        if (!level.dimension.location().equals(getDimension())) return; //维度不一致时不触发

        Star(getPosition(),level);
    }


    static int ritualTickCounter = 0;

    static SimpleParticleType type = ModParticleTypes.WARLOCK.get();
    static SimpleParticleType type1 = ModParticleTypes.TOTEM_EFFECT.get();
    static SimpleParticleType type2 = ModParticleTypes.SOUL_EXPLODE.get();
    static SimpleParticleType type3 = ParticleTypes.SQUID_INK;

    static double rs = 0;
    public static void Star(Vec3 pos, Level level) {
        double X = pos.x;
        double Z = pos.z;
        double y = pos.y + 0.15;
        double r = 16;
        int EdgeCount = 6;

        ritualTickCounter++;
        //六芒星
        //二维空间内距离原点长度为r且角度为a的点p坐标是：r*(cosa,sina)

        double[] x1 = new double[EdgeCount];
        double[] y1 = new double[EdgeCount];

        for (int i = 0; i < EdgeCount; i++) {
            double rad = (2 * Math.PI / EdgeCount) * i + rs;
            x1[i] = (r * cos(rad) + X);
            y1[i] = (r * sin(rad) + Z);

            //现场计算六芒星顶点
        }

        for (int i = 0; i < EdgeCount; i++) {
            ParticleUtils.Drawline(0.05, x1[i], y, y1[i], x1[(i + 2) % EdgeCount], y, y1[(i + 2) % EdgeCount], type, level);
            if (ritualTickCounter % 40 == 0) { // 每隔一段时间爆发一次
                for (int j = 0; j < 5; j++) { // 爆发少量粒子
                    level.addParticle(type2,
                            x1[i] + (level.random.nextDouble() - 0.5) * 0.5,
                            y,
                            y1[i] + (level.random.nextDouble() - 0.5) * 0.5,
                            (level.random.nextDouble() - 0.5) * 0.1, // 随机速度
                            level.random.nextDouble() * 0.2 + 0.1,   // 向上喷发
                            (level.random.nextDouble() - 0.5) * 0.1);
                }
            }
        }

        rs = rs + 0.0015;


        for (int i = 0; i <= 360; i++) {
            double rad = i * 0.017453292519943295;
            double x = r * Math.cos(rad);
            double z = r * Math.sin(rad);
            level.addParticle(type3, X + x, y, Z + z, 0, 0, 0);

        }

        RandomSource random = RandomSource.create();
        for (int i = 0; i < 60; i++) {
            float radius = 1.5f + random.nextFloat() * 5f;
            float angle = random.nextFloat() * Mth.TWO_PI;
            float x = Mth.cos(angle) * radius;
            float z = Mth.sin(angle) * radius;

            float speedX = (random.nextFloat() - 0.5f) * 0.02f;
            float speedY = 0.1f + random.nextFloat() * 0.3f;    // 缓慢上升
            float speedZ = (random.nextFloat() - 0.5f) * 0.02f;

            level.addParticle(
                    type1,
                    pos.x + x,
                    pos.y + 0.2,
                    pos.z + z,
                    speedX,
                    speedY,
                    speedZ
            );
        }
    }
}
