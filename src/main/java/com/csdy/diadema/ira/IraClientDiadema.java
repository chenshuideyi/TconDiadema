package com.csdy.diadema.ira;

import com.csdy.frames.diadema.ClientDiadema;
import com.csdy.particle.register.ParticlesRegister;
import com.csdy.particleUtils.PointSets;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.level.Level;

public class IraClientDiadema extends ClientDiadema {
    private static final SimpleParticleType type = ParticlesRegister.EMC_PARTICLE.get();
    @Override protected void perTick() {
        var level = Minecraft.getInstance().level;

        if (level == null) return;
        if (!level.dimension.location().equals(getDimension())) return; //维度不一致时不触发

        drawParticle(level);
    }

    private void drawParticle(Level level) {
        PointSets.Circle(8,160).map(vec3 -> vec3.add(getPosition())).forEach(vec3 -> level.addParticle(type, vec3.x,vec3.y,vec3.z,0,0,0));

    }

}
