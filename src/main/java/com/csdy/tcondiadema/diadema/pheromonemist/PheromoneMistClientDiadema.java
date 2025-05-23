package com.csdy.tcondiadema.diadema.pheromonemist;

import com.csdy.tcondiadema.diadema.timerain.TimeRainDiadema;
import com.csdy.tcondiadema.frames.diadema.ClientDiadema;
import com.csdy.tcondiadema.particle.register.ParticlesRegister;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.Random;

public class PheromoneMistClientDiadema extends ClientDiadema {

    Random random = new Random();
    static final double RADIUS = PheromoneMistDiadema.RADIUS;
    @Override protected void perTick() {
        var level = Minecraft.getInstance().level;

        if (level == null) return;
        if (!level.dimension.location().equals(getDimension())) return; //维度不一致时不触发

        drawParticle(level);
    }

    private void drawParticle(Level level) {
        Vec3 center = getPosition();
        SimpleParticleType type = ParticleTypes.HEART;
        for (int i = 0; i < 45; i++) {
            // 球面随机分布（底部较少）
            double theta = random.nextDouble() * Math.PI * 0.6;
            double phi = random.nextDouble() * Math.PI * 2;

            // 转换为笛卡尔坐标
            double xOffset = Math.sin(theta) * Math.cos(phi) * RADIUS;
            double zOffset = Math.sin(theta) * Math.sin(phi) * RADIUS;
            double yOffset = Math.cos(theta) * RADIUS;

            // 添加随机扰动
            xOffset += (random.nextDouble() - 0.5) * 0.3;
            zOffset += (random.nextDouble() - 0.5) * 0.3;
            yOffset += (random.nextDouble() - 0.5) * 0.3;

            level.addParticle(type,
                    center.x + xOffset,
                    center.y + yOffset, // 基准高度稍提升
                    center.z + zOffset,
                    0,
                    0,
                    0
            );
        }
    }
}

