package com.csdy.tcondiadema.diadema.killaura;

import com.csdy.tcondiadema.frames.diadema.ClientDiadema;
import com.csdy.tcondiadema.particle.register.ParticlesRegister;
import com.csdy.tcondiadema.particleUtils.PointSets;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class KillAuraClientDiadema extends ClientDiadema {
    private static final SimpleParticleType type = ParticleTypes.SWEEP_ATTACK;

    static final double DEFAULT_RADIUS = 8;
    private double currentRadius = DEFAULT_RADIUS;

    @Override protected void perTick() {
        var level = Minecraft.getInstance().level;

        if (level == null) return;
        if (!level.dimension.location().equals(getDimension())) return; //维度不一致时不触发

        drawParticle(level);
    }

    @Override
    protected void updating(FriendlyByteBuf byteBuf) {
        double b = byteBuf.readDouble();
        currentRadius=b;
    }

    private void drawParticle(Level level) {
        PointSets.Circle(currentRadius, (int) (20*currentRadius)).map(vec3 -> vec3.add(getPosition())).forEach(vec3 -> level.addParticle(type, vec3.x,vec3.y,vec3.z,0,0,0));

    }
}
