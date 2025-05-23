package com.csdy.tcondiadema.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class LoveTrainParticle extends TextureSheetParticle {

    protected LoveTrainParticle(ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
        super(level, x, y, z, xSpeed, ySpeed, zSpeed);
        this.setSize(1, 1); // 粒子大小
        this.lifetime = 40; // 粒子存活时间（单位：刻）
        this.xd = 0; // X 方向速度
        this.yd = 0.2; // Y 方向速度
        this.zd = 0; // Z 方向速度
        this.gravity = 0; // 无重力
        this.hasPhysics = false; // 是否受物理影响

    }

    @Override
    public ParticleRenderType getRenderType() {
        // 使用发光的渲染类型
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    @Override
    public int getLightColor(float partialTick) {
        // 设置粒子发光亮度（15 是最大亮度）
        return 15 << 20 | 15 << 4;
    }

    @OnlyIn(Dist.CLIENT)
    public static class Provider implements ParticleProvider<SimpleParticleType> {

        private final SpriteSet spriteSet;

        public Provider(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        @Override
        public Particle createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            LoveTrainParticle particle = new LoveTrainParticle(level, x, y, z, xSpeed, ySpeed, zSpeed);
            particle.pickSprite(this.spriteSet); // 设置粒子纹理
            return particle;
        }
    }
}
