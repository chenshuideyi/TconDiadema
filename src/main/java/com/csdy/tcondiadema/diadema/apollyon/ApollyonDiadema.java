package com.csdy.tcondiadema.diadema.apollyon;

import com.Polarice3.Goety.common.effects.GoetyEffects;
import com.Polarice3.Goety.common.entities.boss.Apostle;
import com.csdy.tcondiadema.DiademaConfig;
import com.csdy.tcondiadema.diadema.DiademaRegister;
import com.csdy.tcondiadema.diadema.api.ranges.HalfSphereDiademaRange;
import com.csdy.tcondiadema.effect.register.EffectRegister;
import com.csdy.tcondiadema.frames.diadema.Diadema;
import com.csdy.tcondiadema.frames.diadema.DiademaType;
import com.csdy.tcondiadema.frames.diadema.movement.DiademaMovement;
import com.csdy.tcondiadema.frames.diadema.movement.FollowDiademaMovement;
import com.csdy.tcondiadema.frames.diadema.range.DiademaRange;
import com.mega.revelationfix.common.entity.boss.ApostleServant;
import com.mega.revelationfix.common.entity.cultists.HereticServant;
import com.mega.revelationfix.common.init.ModEntities;
import lombok.NonNull;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.entity.projectile.SmallFireball;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import z1gned.goetyrevelation.entitiy.ModEntityType;
import z1gned.goetyrevelation.entitiy.WitherServant;
import z1gned.goetyrevelation.util.ApollyonAbilityHelper;

import java.lang.reflect.Field;
import java.util.*;

import static com.csdy.tcondiadema.diadema.wither.WitherDiadema.addWitherSkeletonKnight;


public class ApollyonDiadema extends Diadema {

    static Random random = new Random();
    final static double RADIUS = 16;
    private final Entity holder = getCoreEntity();
    private final HalfSphereDiademaRange range = new HalfSphereDiademaRange(this, RADIUS);
    private Diadema apollyonLoveTrain;

    public ApollyonDiadema(DiademaType type, DiademaMovement movement) {
        super(type, movement);
    }

    @Override
    public @NonNull DiademaRange getRange() {
        return range;
    }

    private static final int MAX_SERVANTS = 12;
    private static final int SERVANTS_TO_SPAWN = 12;

    @Override
    protected void perTick() {
        ServerLevel level = getLevel();

        // 只在服务端执行，且确保 entity 是 LivingEntity
        if (level.isClientSide() || !(holder instanceof LivingEntity living)) {
            return;
        }

        if (holder.tickCount % 100 == 0) {
            if (living instanceof ApollyonAbilityHelper) {
                if (((ApollyonAbilityHelper) living).allTitlesApostle_1_20_1$isApollyon()) {
                    if (apollyonLoveTrain != null) this.apollyonLoveTrain.kill();
                }
            }
        }

        if (holder.tickCount % 400 == 0) {
            if (living instanceof ApollyonAbilityHelper) {
                if (((ApollyonAbilityHelper) living).allTitlesApostle_1_20_1$isApollyon()) {
                    int i = ((ApollyonAbilityHelper) living).allTitleApostle$getTitleNumber();
                    sayMessage(i);
                    apollyonTitleSkill(living,i);
                }
            }
        }


        if (holder.tickCount % 1320 != 0 && holder.tickCount!= 0) {
            return;
        }

        if (living instanceof ApollyonAbilityHelper) {
            if (((ApollyonAbilityHelper) living).allTitlesApostle_1_20_1$isApollyon()) {
                int nearbyServants = countNearbyServants(level, getPosition());
                if (nearbyServants >= MAX_SERVANTS) return;
                int toSpawn = Math.min(SERVANTS_TO_SPAWN, MAX_SERVANTS - nearbyServants);
                if (DiademaConfig.EX_APOLLYON.get()) spawnMultipleServants(level, living, toSpawn);
                else spawnHereticServant(level, living, toSpawn);
            }
        }else {
            int nearbyServants = countNearbyServants(level, getPosition());
            if (nearbyServants >= MAX_SERVANTS) return;

            // 计算需要生成的数量
            int toSpawn = Math.min(SERVANTS_TO_SPAWN, MAX_SERVANTS - nearbyServants);
            spawnMultipleServants(level, living, toSpawn);

        }
    }

    public static void spawnHereticServant(Level level, Entity holder, int toSpawn) {
        if (!(holder instanceof LivingEntity livingHolder)) {
            return;
        }

        for (int i = 0; i < toSpawn; i++) {
            HereticServant servant = ModEntities.HERETIC_SERVANT.get().create(level);
            if (servant != null) {
                servant.setTrueOwner(livingHolder);

                double angle = Math.PI * 2 * i / 12; // 圆形分布
                double dx = Math.cos(angle) * 8;
                double dz = Math.sin(angle) * 8;
                servant.moveTo(
                        holder.getX() + dx,
                        holder.getY(),
                        holder.getZ() + dz,
                        holder.getYRot(),
                        holder.getXRot()
                );

                level.addFreshEntity(servant);
            }
        }
    }


    private int countNearbyServants(Level level, Vec3 pos) {
        AABB area = new AABB(
                pos.x - RADIUS / 2, pos.y - 5, pos.z - RADIUS / 2,
                pos.x + RADIUS / 2, pos.y + 5, pos.z + RADIUS / 2
        );

        return level.getEntitiesOfClass(
                ApostleServant.class,
                area,
                e -> e.isAlive() && !e.isRemoved()
        ).size();
    }


    public static void spawnMultipleServants(Level level, Entity holder, int toSpawn) {
        if (!(holder instanceof LivingEntity livingHolder)) {
            return;
        }

        if (!(level instanceof ServerLevel serverLevel)) { // 确保是服务端世界
            return;
        }

        EntityType<ApostleServant> servantType = ModEntities.APOSTLE_SERVANT.get();
        for (int i = 0; i < toSpawn; i++) {
            // 计算生成位置（圆形分布）
            double angle = Math.PI * 2 * i / 12;
            double dx = Math.cos(angle) * 8;
            double dz = Math.sin(angle) * 8;
            BlockPos spawnPos = BlockPos.containing(
                    holder.getX() + dx,
                    holder.getY(),
                    holder.getZ() + dz
            );

            // 使用 spawn 方法生成实体
            ApostleServant servant = servantType.spawn(
                    serverLevel,
                    (ItemStack) null,                          // ItemStack（刷怪蛋等，这里不用）
                    null,                          // Player（触发生成的玩家，这里不用）
                    spawnPos,                      // 生成位置（BlockPos）
                    MobSpawnType.SPAWN_EGG,            // 生成类型（EVENT 表示非自然生成）
                    true,                          // 是否进行碰撞检测（true 避免卡墙）
                    false                          // 是否强制生成（false 允许自然生成规则）
            );

            if (servant != null) {
                servant.setTrueOwner(livingHolder); // 设置主人
                Random random = new Random();
                int randomNumber = random.nextInt(12);
                servant.setTitleNumber(randomNumber);
                servant.moveTo(                     // 调整精确位置（避免 BlockPos 取整）
                        holder.getX() + dx,
                        holder.getY(),
                        holder.getZ() + dz,
                        holder.getYRot(),
                        holder.getXRot()
                );
            }
        }
    }

    private void sayMessage(int i) {
        for (Entity entity : affectingEntities) {
            if (!(entity instanceof Player player)) continue;
            if (!player.getCommandSenderWorld().isClientSide) {
                displayClientMessage(player, i);
            }

        }
    }

    private void displayClientMessage(Player player, int i) {
        if (player == null || player.level().isClientSide) {
            return; // 确保玩家存在且是服务端
        }

        String message = switch (i) {
            case 7 -> "apollyon_message_7"; //天启饥荒
            case 4 -> "apollyon_message_4"; //黑天使之影
            case 2 -> "apollyon_message_2"; //毒蝎之尾
            case 9 -> "apollyon_message_9"; //骇人恶物
            case 3 -> "apollyon_message_3"; //漆黑暗影
            case 6 -> "apollyon_message_6"; //爆燃领主
            case 10 -> "apollyon_message_10"; //荣耀之名
            case 5 -> "apollyon_message_5"; //女巫之王
            case 1 -> "apollyon_message_1";  //憎恶本质
            case 8 -> "apollyon_message_8"; //冷冽寒冬
            case 11 -> "apollyon_message_11"; //十恶不赦
            case 0 -> "apollyon_message_0"; //不灭重生
            case 12 -> "apollyon_message_12"; //万众一心
            case 13 -> "apollyon_message_13"; //末日终结
            default -> null;
        };

        if (message != null) {
            player.displayClientMessage(Component.translatable(message), true);
        }
    }




    private void apollyonTitleSkill(LivingEntity living, int title) {
        if (living == null || living.level().isClientSide) {
            return; // 确保实体存在且在服务端
        }

        Level level = living.level();
        Vec3 pos = living.position();

        //天启饥荒 给予饥饿和反胃
        if (title == 7 || title == 12) {
            for (Entity entity : affectingEntities) {
                if (!(entity instanceof Player player)) continue;
                if (!player.getCommandSenderWorld().isClientSide) {
                    player.addEffect(new MobEffectInstance(MobEffects.HUNGER, 400, 3));
                    player.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 400, 3));
                }
            }
        }
        //黑天使之影 召唤骷髅骑士
        if (title == 4 || title == 12) {
            addWitherSkeletonKnight(level, pos, 6);
        }

        //毒蝎之尾 发射剧毒药水箭
        if (title == 2 || title == 12) {
            for (int i = 0; i < 14; i++) {
                Arrow arrow = new Arrow(level, (LivingEntity) holder);

                // 设置为中毒箭
                arrow.addEffect(new MobEffectInstance(
                        MobEffects.POISON,
                        100,
                        2
                ));

                // 随机散射
                float spread = 120.0F; // 散射角度
                float xRot = holder.getXRot() + (level.random.nextFloat() - 0.5F) * spread;
                float yRot = holder.getYRot() + (level.random.nextFloat() - 0.5F) * spread;

                arrow.shootFromRotation(holder, xRot, yRot, 0.0F, 1.5F, 1.0F);
                level.addFreshEntity(arrow);
            }
        }
        //骇人恶物 给予惊恐buff
        if (title == 9 || title == 12) {
            for (Entity entity : affectingEntities) {
                if (!(entity instanceof Player player)) continue;
                if (!player.getCommandSenderWorld().isClientSide) {
                    player.addEffect(new MobEffectInstance(EffectRegister.SCARED.get(), 100, 0));
                }
            }
        }
        //漆黑魅影 召唤黑色的虚弱药水云
        if (title == 3 || title == 12) {
            createEffectClouds(level, living, 10);
        }
        //爆燃领主 烈焰弹来咯
        if (title == 6 || title == 12) {
            fireBomb(level, pos, living);
        }
        //荣耀之名 和你的无敌帧说再见吧
        if (title == 10 || title == 12) {
            for (Entity entity : affectingEntities) {
                if (!(entity instanceof Player player)) continue;
                if (!player.getCommandSenderWorld().isClientSide) {
                    player.invulnerableTime = 0;
                }
            }
        }
        //女巫之王 末影溶解
        if (title == 5 || title == 12) {
            for (Entity entity : affectingEntities) {
                if (!(entity instanceof Player player)) continue;
                if (!player.getCommandSenderWorld().isClientSide) {
                    player.addEffect(new MobEffectInstance(GoetyEffects.ENDER_FLUX.get(), 100, 0));
                }
            }
        }
        //憎恶本质 让范围内所有生物仇视玩家
        if (title == 1 || title == 12) {
            for (Entity entity : affectingEntities) {
                if (!(entity instanceof Mob mob)) continue;
                mob.setTarget(living.getLastHurtMob());
            }
        }
        //凛冽寒风 全部吹飞
        if (title == 8 || title == 12) {
            for (Entity entity : affectingEntities) {
                repelEntity(entity);
            }
        }
        //十恶不赦，召唤凋零
        if (title == 11 || title == 12) {
            summonWither(level, living, 4);
        }
        //不灭重生
        if (title == 0 || title == 12) {
            this.apollyonLoveTrain = DiademaRegister.LOVE_TRAIN.get().CreateInstance(
                    new FollowDiademaMovement(living));
        }


        //末日终结 读条即死
        if (title == 13) {
            for (Entity entity : affectingEntities) {
                if (!(entity instanceof LivingEntity livingEntity)) continue;
                if (!entity.equals(living)) {
                    if (livingEntity.hasEffect(GoetyEffects.DOOM.get())) {
                        if (livingEntity.getEffect(GoetyEffects.DOOM.get()).getAmplifier() >= 20) {
                            livingEntity.setHealth(0);
                        }
                        livingEntity.addEffect(new MobEffectInstance(GoetyEffects.DOOM.get(), 800,
                                livingEntity.getEffect(GoetyEffects.DOOM.get()).getAmplifier() + 10));
                    }
                    else {
                        livingEntity.addEffect(new MobEffectInstance(GoetyEffects.DOOM.get(), 800, 10));
                    }
                }
            }
        }
    }






    private static @NotNull List<AreaEffectCloud> createEffectClouds(Level level, LivingEntity living, int number) {
        List<AreaEffectCloud> clouds = new ArrayList<>();
        for (int i = 0; i < number; i++) {
            double angle = random.nextDouble() * 2 * Math.PI;
            double distance = random.nextDouble() * RADIUS;
            double randomX = Math.cos(angle) * distance;
            double randomZ = Math.sin(angle) * distance;
            double randomY = random.nextDouble() * RADIUS /5;

            AreaEffectCloud cloud = new AreaEffectCloud(level,
                    living.getX() + randomX,
                    living.getY() + randomY,
                    living.getZ() + randomZ);

            cloud.setOwner(null);
            cloud.setFixedColor(0x000000);
            cloud.setParticle(ParticleTypes.SQUID_INK);
            cloud.setRadius(4.5F);
            cloud.setRadiusOnUse(-0.5F);
            cloud.setRadiusPerTick(-0.005F);
            cloud.setDuration(90);
            cloud.setWaitTime(20);
            cloud.setPotion(Potions.WEAKNESS);

            level.addFreshEntity(cloud);
            clouds.add(cloud);
        }
        return clouds;
    }


    private void repelEntity(Entity entity) {
        // 获取玩家和实体的位置
        Vec3 playerPos = this.holder.position();
        Vec3 entityPos = entity.position();

        // 计算距离
        double distanceSquared = entityPos.distanceToSqr(playerPos);
        double radiusSquared = RADIUS * RADIUS;

        // 计算斥力强度（1 - (距离平方 / 领域半径平方)）
        double repelStrength = 1 - (distanceSquared / radiusSquared);

        // 确保斥力强度在 [0, 1] 范围内
        repelStrength = Math.max(0, Math.min(1, repelStrength));

        // 计算排斥方向（从玩家指向实体）
        Vec3 repelDirection = entityPos.subtract(playerPos).normalize();

        // 设置排斥速度
        double maxRepelStrength = 6; // 排斥强度，可以调整
        Vec3 repelVelocity = repelDirection.scale(maxRepelStrength * repelStrength);

        // 为实体设置速度
        entity.setDeltaMovement(repelVelocity);
        entity.hurtMarked = true;
    }

    public static void summonWither(Level level, Entity holder, int toSpawn) {
        if (!(holder instanceof LivingEntity livingHolder)) {
            return;
        }

        for (int i = 0; i < toSpawn; i++) {
            WitherServant servant = new WitherServant(ModEntityType.WITHER_SERVANT.get(), level);
            if (servant != null) {
                servant.setTrueOwner(livingHolder);

                double angle = Math.PI * 2 * i / 12; // 圆形分布
                double dx = Math.cos(angle) * 8;
                double dz = Math.sin(angle) * 8;
                servant.moveTo(
                        holder.getX() + dx,
                        holder.getY(),
                        holder.getZ() + dz,
                        holder.getYRot(),
                        holder.getXRot()
                );

                level.addFreshEntity(servant);
            }
        }
    }

    private void fireBomb(Level level,Vec3 pos,LivingEntity living){
        for (int j = 0; j < 33; j++) {
            level.setBlock(new BlockPos(
                            (int)pos.x + level.random.nextInt(7) - 3,
                            (int)pos.y,
                            (int)pos.z + level.random.nextInt(7) - 3),
                    Blocks.FIRE.defaultBlockState(), 3);
        }

        for (int i = 0; i < 10; i++) {
            SmallFireball fireball = new SmallFireball(level,
                    living,  // 发射者
                    level.random.nextGaussian() * 0.1,  // 随机X方向偏移
                    0.1,  // 向上的初始速度
                    level.random.nextGaussian() * 0.1); // 随机Z方向偏移

            // 设置火焰弹位置（在实体周围随机位置）
            fireball.setPos(
                    pos.x + (level.random.nextDouble() - 0.5) * 3.0,
                    pos.y + 1.0,
                    pos.z + (level.random.nextDouble() - 0.5) * 3.0);

            // 设置火焰弹速度（随机散射）
            fireball.setDeltaMovement(
                    (level.random.nextDouble() - 0.5) * 0.5,
                    0.2,  // 初始向上的速度
                    (level.random.nextDouble() - 0.5) * 0.5);

            level.addFreshEntity(fireball);
        }
    }

}
