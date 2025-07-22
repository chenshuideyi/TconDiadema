package com.csdy.tcondiadema.diadema.ender_dragon;

import com.csdy.tcondiadema.frames.diadema.ClientDiadema;
import com.csdy.tcondiadema.particle.register.ParticlesRegister;
import com.csdy.tcondiadema.particleUtils.PointSets;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Random;

import static com.csdy.tcondiadema.ParticleUtils.DrawCurve;

@OnlyIn(Dist.CLIENT)
public class EnderDragonClientDiadema extends ClientDiadema {

    private static final SimpleParticleType type = ParticleTypes.DRAGON_BREATH;



    @Override
    protected void perTick() {
        var level = Minecraft.getInstance().level;
        var mc = Minecraft.getInstance();

        if (level == null || mc.player == null) return;
        if (!level.dimension.location().equals(getDimension())) return;

        DragonWing(level);

    }

    public void DragonWing(Level level) {
        //乘风行走之物改
        Vec3 center = getPosition();
        double X = center.x;
        double Z = center.z;
        double Y = center.y+0.1;
        double value = 16;

        Random r = new Random();
        double randomX = r.nextDouble() * (value+value)-value;
        double randomY = r.nextDouble() * (value+value)-value;
        double randomZ = r.nextDouble() * (value+value)-value;

        double CX = r.nextDouble() * (value+value)-value;
        double CY = r.nextDouble() * (value+value)-value;
        double CZ = r.nextDouble() * (value+value)-value;

        Vec3 end = new Vec3(X+randomX, Y+randomY, Z+randomZ);
        Vec3 end2 = new Vec3(X-randomX, Y-randomY, Z-randomZ);
        Vec3 start = new Vec3(X,Y,Z);
        Vec3 ctrlpoint = new Vec3(X+CX,Y+CY,Z+CZ);


        DrawCurve(0.01,end,start,ctrlpoint,type,level);

        DrawCurve(0.01,end2,start,ctrlpoint,type,level);
    }

}
