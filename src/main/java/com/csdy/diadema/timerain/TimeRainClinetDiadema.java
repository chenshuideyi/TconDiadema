package com.csdy.diadema.timerain;

import com.csdy.diadema.warden.WardenDiadema;
import com.csdy.frames.diadema.ClientDiadema;
import com.csdy.particle.register.ParticlesRegister;
import com.csdy.particleUtils.PointSets;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.lang.Math.sqrt;
import static net.minecraft.util.Mth.square;

public class TimeRainClinetDiadema extends ClientDiadema {
    Random random = new Random();
    static final double RADIUS = TimeRainDiadema.RADIUS;
    @Override protected void perTick() {
        var level = Minecraft.getInstance().level;

        if (level == null) return;
        if (!level.dimension.location().equals(getDimension())) return; //维度不一致时不触发

        drawParticle(level);
    }

    private void drawParticle(Level level) {
        Vec3 center = getPosition();
        Vec3 vec1 = new Vec3(-RADIUS,0,0);



        SimpleParticleType type = ParticlesRegister.TIME_RAIN_PARTICLE.get();
//        PointSets.Circle(8,160).map(vec3 -> vec3.add(getPosition())).forEach(vec3 -> level.addParticle(type, vec3.x,vec3.y+8,vec3.z,0,0,0));

        for (int i = 1; i <= 48; i++) {
            double n = 0.1;
            double y = random.nextDouble() * (Math.PI / 2 - n);
            double x = random.nextDouble() * 2 * Math.PI;
            var pos = vec1.zRot((float) y).yRot((float) x);
            level.addParticle(type, pos.x+center.x, pos.y+center.y+0.3, pos.z+center.z, 0, 0, 0);
        }
    }
}
