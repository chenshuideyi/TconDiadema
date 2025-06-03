package com.csdy.tcondiadema.diadema.lovetrain;

import com.csdy.tcondiadema.ParticleUtils;
import com.csdy.tcondiadema.frames.diadema.ClientDiadema;
import com.csdy.tcondiadema.particle.register.ParticlesRegister;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import static java.lang.Math.sqrt;
import static net.minecraft.util.Mth.square;

@OnlyIn(Dist.CLIENT)
public class LoveTrainClientDiadema extends ClientDiadema {

    private int tick = 0;
    private static final SimpleParticleType PARTICLE_TYPE = ParticlesRegister.LOVE_TRAIN_PARTICLE.get();
    @Override protected void perTick() {
        var level = Minecraft.getInstance().level;

        if (level == null) return;
        if (!level.dimension.location().equals(getDimension())) return;
        tick++;
        if (tick>=10){
            tick=0;
            drawParticle(level,LoveTrainDiadema.RADIUS);
        }

    }

    public void drawParticle(Level level, double sideLength) {
        Vec3 center = getPosition();
        double X = center.x;
        double Y = center.y;
        double Z = center.z;

        double halfSide = sideLength / 2.0;
        double halfHeight = halfSide;

        // 底面
        loveTraindrawline(0.05, X-halfSide, Y, Z-halfSide, X+halfSide, Y-halfHeight, Z-halfSide, PARTICLE_TYPE, level);
        loveTraindrawline(0.05, X-halfSide, Y, Z-halfSide, X-halfSide, Y-halfHeight, Z+halfSide, PARTICLE_TYPE, level);
        loveTraindrawline(0.05, X+halfSide, Y, Z-halfSide, X+halfSide, Y-halfHeight, Z+halfSide, PARTICLE_TYPE, level);
        loveTraindrawline(0.05, X-halfSide, Y, Z+halfSide, X+halfSide, Y-halfHeight, Z+halfSide, PARTICLE_TYPE, level);

        loveTraindrawline(0.05, X-halfSide, Y, Z-halfSide, X+halfSide, Y, Z-halfSide, PARTICLE_TYPE, level);
        loveTraindrawline(0.05, X-halfSide, Y, Z-halfSide, X-halfSide, Y, Z+halfSide, PARTICLE_TYPE, level);
        loveTraindrawline(0.05, X+halfSide, Y, Z-halfSide, X+halfSide, Y, Z+halfSide, PARTICLE_TYPE, level);
        loveTraindrawline(0.05, X-halfSide, Y, Z+halfSide, X+halfSide, Y, Z+halfSide, PARTICLE_TYPE, level);

        loveTraindrawline(0.05, X-halfSide, Y+4, Z-halfSide, X+halfSide, Y+4, Z-halfSide, PARTICLE_TYPE, level);
        loveTraindrawline(0.05, X-halfSide, Y+4, Z-halfSide, X-halfSide, Y+4, Z+halfSide, PARTICLE_TYPE, level);
        loveTraindrawline(0.05, X+halfSide, Y+4, Z-halfSide, X+halfSide, Y+4, Z+halfSide, PARTICLE_TYPE, level);
        loveTraindrawline(0.05, X-halfSide, Y+4, Z+halfSide, X+halfSide, Y+4, Z+halfSide, PARTICLE_TYPE, level);
    }

    private static void loveTraindrawline(double interval,double tox, double toy, double toz, double X, double Y, double Z,SimpleParticleType type,Level level){
        //自动连接任意两点
        double deltax = tox-X, deltay = toy-Y, deltaz = toz-Z;
        double length = sqrt(square(deltax) + square(deltay) + square(deltaz));
        int amount = (int)(length/interval + 1);
        for (int i = 0 ; i <= amount; i++) {
            level.addParticle(type,X+deltax*i/amount, Y+deltay*i/amount, Z+deltaz*i/amount,0,0.4,0);
        }
    }

}
