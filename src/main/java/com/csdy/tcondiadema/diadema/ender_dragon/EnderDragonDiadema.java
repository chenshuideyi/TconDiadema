package com.csdy.tcondiadema.diadema.ender_dragon;

import com.csdy.tcondiadema.TconDiadema;
import com.csdy.tcondiadema.diadema.api.ranges.SphereDiademaRange;
import com.csdy.tcondiadema.frames.diadema.Diadema;
import com.csdy.tcondiadema.frames.diadema.DiademaType;
import com.csdy.tcondiadema.frames.diadema.movement.DiademaMovement;
import com.csdy.tcondiadema.frames.diadema.range.DiademaRange;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.boss.enderdragon.EndCrystal;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static net.minecraftforge.eventbus.api.EventPriority.HIGHEST;

@Mod.EventBusSubscriber(modid = TconDiadema.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class EnderDragonDiadema extends Diadema {


    private final Entity holder = getCoreEntity();

    public EnderDragonDiadema(DiademaType type, DiademaMovement movement) {
        super(type, movement);
    }

    private final SphereDiademaRange range = new SphereDiademaRange(this, 64);

    @Override
    public @NotNull DiademaRange getRange() {
        return range;
    }


    private boolean hasActiveCrystals() {
        return !getCoreEntity().level().getEntitiesOfClass(EndCrystal.class,
                new AABB(-256, 0, -256, 256, 256, 256)).isEmpty();
    }

    @Override
    protected void perTick() {
        if (!(holder instanceof LivingEntity living) || affectingEntities.isEmpty()) return;

        // 创建龙息云（AreaEffectCloud）
        AreaEffectCloud cloud = new AreaEffectCloud(EntityType.AREA_EFFECT_CLOUD, holder.level());

        // 设置龙息云的位置（从施法者位置发射）
        cloud.moveTo(
                living.getX(),
                living.getEyeY() - 6,
                living.getZ(),
                living.getYRot(),
                living.getXRot()
        );

        // 设置龙息云的属性（类似原版龙息）
        cloud.setRadius(4.0f);          // 影响半径
        cloud.setRadiusOnUse(-0.5f);    // 每次作用后半径减少
        cloud.setWaitTime(5);          // 生成后多久开始生效（tick）
        cloud.setDuration(60);         // 持续时间（3秒 = 60 tick）
        cloud.setParticle(ParticleTypes.DRAGON_BREATH);  // 粒子效果
        cloud.addEffect(new MobEffectInstance(MobEffects.HARM, 1, 1));  // 伤害效果

        // 计算随机方向并赋予初速度
        Vec3 direction = new Vec3(
                holder.level().random.nextGaussian() * 0.5,
                holder.level().random.nextGaussian() * 0.2 + 0.5,
                holder.level().random.nextGaussian() * 0.5
        ).normalize();
        cloud.setDeltaMovement(direction.scale(0.5));  // 初速度（比龙息弹慢）

        // 添加到世界
        holder.level().addFreshEntity(cloud);


        if (holder.tickCount % 200 == 0) { // 每10秒执行一次
            // 1. 随机选择一个末影人
            List<EnderMan> enderMan = holder.level().getEntitiesOfClass(EnderMan.class,
                    holder.getBoundingBox().inflate(10.0));
            if (enderMan.isEmpty()) return;

            EnderMan chosenEnder = enderMan.get(holder.level().random.nextInt(enderMan.size()));

            // 2. "吃掉"末影人并回血
            living.heal(15.0F); // 回15点血
            chosenEnder.discard();

            // 播放音效
            holder.level().playSound(null,
                    living.getX(), living.getY(), living.getZ(),
                    SoundEvents.ENDER_DRAGON_SHOOT,
                    SoundSource.HOSTILE,
                    2.0F,
                    0.8F + holder.level().random.nextFloat() * 0.4F);
        }
    }


    @SubscribeEvent(priority = HIGHEST)
    public void onDragonHurt(LivingHurtEvent e) {
        var source = e.getSource();
        if (hasActiveCrystals() && !source.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
            holder.level().playSound(null,
                    holder.getX(),
                    holder.getY(),
                    holder.getZ(),
                    SoundEvents.ENDER_DRAGON_GROWL,
                    SoundSource.HOSTILE,
                    3.0F,
                    0.5F);

            MinecraftServer server = holder.level().getServer();
            if (server != null) {
                for (ServerPlayer onlinePlayer : server.getPlayerList().getPlayers()) {
                    onlinePlayer.displayClientMessage(
                            Component.translatable("ender_dragon_block"),
                            true
                    );
                }
            }



            e.setCanceled(true);
        }

    }

    @SubscribeEvent
    public void onBedDamage(LivingDamageEvent e) {
        var source = e.getSource();
        var entity = e.getEntity();
        if (entity instanceof EnderDragon) {
            if ("badRespawnPoint".equals(source.getMsgId())) {
                entity.setHealth(0);

            }

        }

    }
}
