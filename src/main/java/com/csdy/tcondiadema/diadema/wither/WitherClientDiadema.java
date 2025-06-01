package com.csdy.tcondiadema.diadema.wither;

import com.csdy.tcondiadema.ParticleUtils;
import com.csdy.tcondiadema.diadema.warden.WardenDiadema;
import com.csdy.tcondiadema.frames.diadema.ClientDiadema;
import com.csdy.tcondiadema.particle.register.ParticlesRegister;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Random;

@OnlyIn(Dist.CLIENT)
public class WitherClientDiadema extends ClientDiadema {

    static final double RADIUS = WitherDiadema.RADIUS;
    private static final float interval = 1f;
    
    public void Wither(Level level, double sideLength) {
        Vec3 center = getPosition();
        double X = center.x;
        double Y = center.y + 2;
        double Z = center.z;
        SimpleParticleType type = ParticleTypes.SOUL.getType();

        // 计算半边长，使立方体对称分布在玩家周围
        double halfSide = sideLength / 2.0;
        double halfHeight = halfSide;  // 高度也使用相同的半边长

        // 底面
        ParticleUtils.Drawline(interval, X-halfSide, Y-halfHeight, Z-halfSide, X+halfSide, Y-halfHeight, Z-halfSide, type, level);
        ParticleUtils.Drawline(interval, X-halfSide, Y-halfHeight, Z-halfSide, X-halfSide, Y-halfHeight, Z+halfSide, type, level);
        ParticleUtils.Drawline(interval, X+halfSide, Y-halfHeight, Z-halfSide, X+halfSide, Y-halfHeight, Z+halfSide, type, level);
        ParticleUtils.Drawline(interval, X-halfSide, Y-halfHeight, Z+halfSide, X+halfSide, Y-halfHeight, Z+halfSide, type, level);

        // 顶面
        ParticleUtils.Drawline(interval, X-halfSide, Y+halfHeight, Z-halfSide, X+halfSide, Y+halfHeight, Z-halfSide, type, level);
        ParticleUtils.Drawline(interval, X-halfSide, Y+halfHeight, Z-halfSide, X-halfSide, Y+halfHeight, Z+halfSide, type, level);
        ParticleUtils.Drawline(interval, X+halfSide, Y+halfHeight, Z-halfSide, X+halfSide, Y+halfHeight, Z+halfSide, type, level);
        ParticleUtils.Drawline(interval, X-halfSide, Y+halfHeight, Z+halfSide, X+halfSide, Y+halfHeight, Z+halfSide, type, level);

        // 连接底面和顶面的四条垂直线
        ParticleUtils.Drawline(interval, X-halfSide, Y-halfHeight, Z-halfSide, X-halfSide, Y+halfHeight, Z-halfSide, type, level);
        ParticleUtils.Drawline(interval, X+halfSide, Y-halfHeight, Z-halfSide, X+halfSide, Y+halfHeight, Z-halfSide, type, level);
        ParticleUtils.Drawline(interval, X-halfSide, Y-halfHeight, Z+halfSide, X-halfSide, Y+halfHeight, Z+halfSide, type, level);
        ParticleUtils.Drawline(interval, X+halfSide, Y-halfHeight, Z+halfSide, X+halfSide, Y+halfHeight, Z+halfSide, type, level);
    }

    @Override protected void perTick() {
        var level = Minecraft.getInstance().level;

        if (level == null) return;
        if (!level.dimension.location().equals(getDimension())) return; //维度不一致时不触发

        Wither(level,RADIUS);
    }
}
