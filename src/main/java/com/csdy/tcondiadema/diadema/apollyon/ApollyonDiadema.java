package com.csdy.tcondiadema.diadema.apollyon;

import com.Polarice3.Goety.common.effects.GoetyEffects;
import com.csdy.tcondiadema.diadema.api.ranges.HalfSphereDiademaRange;
import com.csdy.tcondiadema.effect.register.EffectRegister;
import com.csdy.tcondiadema.frames.diadema.ClientDiadema;
import com.csdy.tcondiadema.frames.diadema.Diadema;
import com.csdy.tcondiadema.frames.diadema.DiademaType;
import com.csdy.tcondiadema.frames.diadema.movement.DiademaMovement;
import com.csdy.tcondiadema.frames.diadema.range.DiademaRange;
import com.mega.revelationfix.common.entity.boss.ApostleServant;
import com.mega.revelationfix.common.init.ModEffects;
import com.mega.revelationfix.common.init.ModEntities;
import lombok.NonNull;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Difficulty;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.monster.WitherSkeleton;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.entity.projectile.SmallFireball;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import z1gned.goetyrevelation.entitiy.ModEntityType;
import z1gned.goetyrevelation.entitiy.WitherServant;
import z1gned.goetyrevelation.util.ApollyonAbilityHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.csdy.tcondiadema.diadema.wither.WitherDiadema.addWitherSkeletonKnight;


public class ApollyonDiadema extends Diadema {

    static Random random = new Random();
    final static double RADIUS = 16;
    private final Entity holder = getCoreEntity();
    private final HalfSphereDiademaRange range = new HalfSphereDiademaRange(this, RADIUS);

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

        int nearbySkeletons = countNearbyServants(level, getPosition());
        if (nearbySkeletons >= MAX_SERVANTS) return;

        // 计算需要生成的数量
        int toSpawn = Math.min(SERVANTS_TO_SPAWN, MAX_SERVANTS - nearbySkeletons);
        spawnMultipleServants(level, living, toSpawn);


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

        for (int i = 0; i < toSpawn; i++) {
            ApostleServant servant = ModEntities.APOSTLE_SERVANT.get().create(level);
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
            case 7 -> "他们得了权柄可以管辖，又要用刀剑、饥荒、瘟疫和地上的野兽去杀地上四分之一的人。"; //天启饥荒
            case 4 -> "第四位天使吹号，日头的三分之一、月亮的三分之一、星辰的三分之一都被击打。"; //黑天使之影
            case 2 -> "在这磐石上，我要建立我的教会，阴间的权柄也不能战胜他"; //毒蝎之尾
            case 9 -> "那兽开口向神说亵渎的话，亵渎神的名并他的帐幕，以及那些住在天上的。"; //骇人恶物
            case 3 -> "我授予你们“半尼其”这个名字，意为“雷霆之子"; //漆黑暗影
            case 6 -> "主阿，你要我们吩咐火从天上降下来，烧灭他们，像以利亚所作的吗？"; //爆燃领主
            case 10 -> "那就让我们同去，与他钉死在一起"; //荣耀之名
            case 5 -> "您即是他的儿子，您生来为王！"; //女巫之王
            case 1 -> "凡住在地上、名字从创世以来没有记在被杀之羔羊生命册上的人，都要拜它。";  //憎恶本质
            case 8 -> "又有大雹子从天落在人身上，每一个约重一他连得。为这雹子的灾极大，人就亵渎神。"; //冷冽寒冬
            case 11 -> "那迷惑他们的魔鬼被扔在硫磺的火湖里，就是兽和假先知所在的地方。他们必昼夜受痛苦，直到永永远远。"; //十恶不赦
            case 0 -> "主阿，为什么要向我们显现，不向世人显现呢？"; //不灭重生
            case 12 -> "你当刚强壮胆,不要惊惶，因为我必与你同在。"; //万众一心
            case 13 -> "我不是拣选了你们十二个门徒吗？但你们中间有一个是魔鬼"; //末日终结
            default -> null;
        };

        if (message != null) {
            player.displayClientMessage(Component.literal(message), true);
        }
    }

    private void apollyonTitleSkill(LivingEntity living, int title) {
        if (living == null || living.level().isClientSide) {
            return; // 确保实体存在且在服务端
        }

        Level level = living.level();
        Vec3 pos = living.position();

        if (title == 7 || title == 12) {
            for (Entity entity : affectingEntities) {
                if (!(entity instanceof Player player)) continue;
                if (!player.getCommandSenderWorld().isClientSide) {
                    player.addEffect(new MobEffectInstance(MobEffects.HUNGER, 400, 3));
                    player.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 400, 3));
                }
            }
        }
        if (title == 4 || title == 12) {
            addWitherSkeletonKnight(level, pos, 6);
        }
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
        if (title == 9 || title == 12) {
            for (Entity entity : affectingEntities) {
                if (!(entity instanceof Player player)) continue;
                if (!player.getCommandSenderWorld().isClientSide) {
                    player.addEffect(new MobEffectInstance(EffectRegister.SCARED.get(), 100, 0));
                }
            }
        }
        if (title == 3 || title == 12) {
            createEffectClouds(level, living, 10);
        }
        if (title == 6 || title == 12) {
            fireBomb(level,pos,living);
        }
        if (title == 10 || title == 12) {
            for (Entity entity : affectingEntities) {
                if (!(entity instanceof Player player)) continue;
                if (!player.getCommandSenderWorld().isClientSide) {
                    player.invulnerableTime = 0;
                }
            }
        }
        if (title == 5 || title == 12) {
            for (Entity entity : affectingEntities) {
                if (!(entity instanceof Player player)) continue;
                if (!player.getCommandSenderWorld().isClientSide) {
                    player.addEffect(new MobEffectInstance(GoetyEffects.ENDER_FLUX.get(), 100, 0));
                }
            }
        }
        if (title == 1 || title == 12) {
            for (Entity entity : affectingEntities) {
                if (!(entity instanceof Mob mob)) continue;
                mob.setTarget(living.getLastHurtMob());
            }
        }
        if (title == 8 || title == 12) {
            for (Entity entity : affectingEntities) {
                repelEntity(entity);
            }
        }
        if (title == 11 || title == 12) {
            summonWither(level, living);
        }
        if (title == 0 || title == 12) {
            living.heal(5);
            living.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 400, 3));
        }
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
            // Generate positions in a sphere around the entity
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

            level.addFreshEntity(cloud); // Add the cloud to the world
            clouds.add(cloud); // Add to our list
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

    }

    private void summonWither(Level level,LivingEntity holder) {
        WitherServant wither = new WitherServant(ModEntityType.WITHER_SERVANT.get(), level);
        wither.setTrueOwner(holder);
        double angle = Math.PI * 2 * 4 / 12; // 圆形分布
        double dx = Math.cos(angle) * 8;
        double dz = Math.sin(angle) * 8;
        for (int i = 0; i < 4; i++) {
            wither.moveTo(
                    holder.getX() + dx,
                    holder.getY(),
                    holder.getZ() + dz,
                    holder.getYRot(),
                    holder.getXRot()
            );

            level.addFreshEntity(wither);
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
