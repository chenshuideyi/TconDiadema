package com.csdy.tcondiadema.diadema.fakeapollyon;

import com.Polarice3.Goety.client.particles.ModParticleTypes;
import com.csdy.tcondiadema.ParticleUtils;
import com.csdy.tcondiadema.frames.diadema.ClientDiadema;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

@OnlyIn(Dist.CLIENT)
public class FakeApollyonClientDiadema extends ClientDiadema {
    @Override protected void perTick() {
        var level = Minecraft.getInstance().level;

        if (level == null) return;
        if (!level.dimension.location().equals(getDimension())) return; //维度不一致时不触发

        Star(getPosition(),level);
    }


    static int ritualTickCounter = 0;

    static SimpleParticleType type = ParticleTypes.SOUL_FIRE_FLAME;

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

        rs = rs + 0.0015;

        for (int i = 0; i <= 360; i++) {
            double rad = i * 0.017453292519943295;
            double x = r * Math.cos(rad);
            double z = r * Math.sin(rad);
            level.addParticle(type, X + x, y, Z + z, 0, 0, 0);

        }
    }
}
