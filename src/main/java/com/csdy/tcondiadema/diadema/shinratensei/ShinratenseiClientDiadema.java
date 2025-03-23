package com.csdy.tcondiadema.diadema.shinratensei;

import com.csdy.tcondiadema.frames.diadema.ClientDiadema;
import com.csdy.tcondiadema.particle.register.ParticlesRegister;
import com.csdy.tcondiadema.particleUtils.PointSets;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.level.Level;

public class ShinratenseiClientDiadema extends ClientDiadema {
    private static final SimpleParticleType type = ParticlesRegister.SHINRATENSEI_PARTICLE.get();
    private static final double RADIUS = 8;
    private static final double START_RATE = 0.5;
    private static final int segX = 8;
    private static final int segY = 8;
    @Override protected void perTick() {
        var level = Minecraft.getInstance().level;

        if (level == null) return;
        if (!level.dimension.location().equals(getDimension())) return; //维度不一致时不触发

        drawParticle(level);
    }

    private void drawParticle(Level level) {
        PointSets.Sphere(RADIUS, segX, segY).forEach(v -> {
            var from = v.scale(START_RATE).add(getPosition());
            var target = v.scale(1-START_RATE);
            level.addParticle(type,from.x,from.y, from.z, target.x, target.y, target.z);
        });
    }

}
