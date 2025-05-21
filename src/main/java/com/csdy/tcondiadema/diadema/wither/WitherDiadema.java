package com.csdy.tcondiadema.diadema.wither;

import com.csdy.tcondiadema.ModMain;
import com.csdy.tcondiadema.diadema.DiademaRegister;
import com.csdy.tcondiadema.diadema.api.ranges.SphereDiademaRange;
import com.csdy.tcondiadema.diadema.api.ranges.SquareDiademaRange;
import com.csdy.tcondiadema.effect.register.EffectRegister;
import com.csdy.tcondiadema.frames.diadema.Diadema;
import com.csdy.tcondiadema.frames.diadema.DiademaType;
import com.csdy.tcondiadema.frames.diadema.movement.DiademaMovement;
import com.csdy.tcondiadema.frames.diadema.range.DiademaRange;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.horse.SkeletonHorse;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.WitherSkeleton;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber(modid = ModMain.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class WitherDiadema extends Diadema {
    static final double RADIUS = 12;
    public WitherDiadema(DiademaType type, DiademaMovement movement) {
        super(type, movement);
    }

    private final SquareDiademaRange range = new SquareDiademaRange(this,RADIUS);

    @Override
    public @NotNull DiademaRange getRange() {
        return range;
    }


    private static final int WITHER_SKELETONS_TO_SPAWN = 12;
    private static final int MAX_WITHER_SKELETONS = 12;
    private static final double SPAWN_RADIUS = 12.0; // 检测半径

    @Override
    protected void perTick() {
        var entity = getEntity();
        if (!(entity.tickCount % (30 * 20) == 0)) return;

        Vec3 pos = getPosition();
        ServerLevel level = getLevel();

        // 只在服务端执行
        if (level.isClientSide()) return;

        // 检查范围内的凋零骷髅数量
        int nearbySkeletons = countNearbyWitherSkeletons(level, pos);
        if (nearbySkeletons >= MAX_WITHER_SKELETONS) return;

        // 计算需要生成的数量
        int toSpawn = Math.min(WITHER_SKELETONS_TO_SPAWN, MAX_WITHER_SKELETONS - nearbySkeletons);
        addWitherSkeleton(level, pos,toSpawn);

    }

    // 统计附近的凋零骷髅数量
    private int countNearbyWitherSkeletons(Level level, Vec3 pos) {
        AABB area = new AABB(
                pos.x - SPAWN_RADIUS, pos.y - 5, pos.z - SPAWN_RADIUS,
                pos.x + SPAWN_RADIUS, pos.y + 5, pos.z + SPAWN_RADIUS
        );

        return level.getEntitiesOfClass(
                WitherSkeleton.class,
                area,
                e -> e.isAlive() && !e.isRemoved()
        ).size();
    }

    public static void addWitherSkeleton(Level level, Vec3 witherCenterPos, int count) {
        if (!(level instanceof ServerLevel)) return;


        for (int i = 0; i < count; i++) {
            double offsetX = (level.random.nextDouble() - 0.5) * RADIUS;
            double offsetZ = (level.random.nextDouble() - 0.5) * RADIUS;

            double spawnX = witherCenterPos.x() + offsetX;
            double spawnZ = witherCenterPos.z() + offsetZ;

            int groundY = level.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                    Mth.floor(spawnX),
                    Mth.floor(spawnZ));

            WitherSkeleton witherSkeleton = EntityType.WITHER_SKELETON.create(level);
            if (witherSkeleton == null) continue;

            witherSkeleton.moveTo(
                    spawnX,
                    groundY,
                    spawnZ,
                    level.random.nextFloat() * 360.0f, // 随机朝向
                    0.0f
            );

            witherSkeleton.setPos(spawnX, groundY, spawnZ);

            // 装备全套下界合金装备
            ItemStack netheriteSword = new ItemStack(Items.NETHERITE_SWORD);
            netheriteSword.enchant(Enchantments.SHARPNESS, 5); // 锋利V

            ItemStack shield = new ItemStack(Items.SHIELD);
            shield.enchant(Enchantments.MENDING,1);

            ItemStack netheriteHelmet = new ItemStack(Items.NETHERITE_HELMET);
            netheriteHelmet.enchant(Enchantments.MENDING, 1);

            ItemStack netheriteChestplate = new ItemStack(Items.NETHERITE_CHESTPLATE);
            netheriteChestplate.enchant(Enchantments.MENDING, 1);

            ItemStack netheriteLeggings = new ItemStack(Items.NETHERITE_LEGGINGS);
            netheriteLeggings.enchant(Enchantments.MENDING, 1);

            ItemStack netheriteBoots = new ItemStack(Items.NETHERITE_BOOTS);
            netheriteBoots.enchant(Enchantments.MENDING, 1);

            witherSkeleton.setItemSlot(EquipmentSlot.MAINHAND, netheriteSword);
            witherSkeleton.setItemSlot(EquipmentSlot.OFFHAND, shield);
            witherSkeleton.setItemSlot(EquipmentSlot.HEAD, netheriteHelmet);
            witherSkeleton.setItemSlot(EquipmentSlot.CHEST, netheriteChestplate);
            witherSkeleton.setItemSlot(EquipmentSlot.LEGS, netheriteLeggings);
            witherSkeleton.setItemSlot(EquipmentSlot.FEET, netheriteBoots);

            witherSkeleton.setDropChance(EquipmentSlot.MAINHAND, 1);
            witherSkeleton.setDropChance(EquipmentSlot.OFFHAND, 1);
            witherSkeleton.setDropChance(EquipmentSlot.HEAD, 1);
            witherSkeleton.setDropChance(EquipmentSlot.CHEST, 1);
            witherSkeleton.setDropChance(EquipmentSlot.LEGS, 1);
            witherSkeleton.setDropChance(EquipmentSlot.FEET, 1);

            // 2. 创建骷髅马
            SkeletonHorse skeletonHorse = EntityType.SKELETON_HORSE.create(level);
            if (skeletonHorse == null) {
                level.addFreshEntity(witherSkeleton);
                continue;
            }

            skeletonHorse.moveTo(spawnX, groundY, spawnZ, witherSkeleton.getYRot(), 0.0f); // Y朝向与凋零骷髅一致
            skeletonHorse.setAge(0);
            skeletonHorse.setTamed(true); // 必须设置为驯服状态，非玩家实体才能骑乘

            level.addFreshEntity(skeletonHorse);
            level.addFreshEntity(witherSkeleton);

            boolean didRide = witherSkeleton.startRiding(skeletonHorse, true); // true 表示强制骑乘

            if (!didRide) {
                System.err.println("凋零骷髅骑士发生错误于 " + spawnX + "," + groundY + "," + spawnZ);
            }

        }

        if (count > 0) {
            level.playSound(
                    null, // 在所有客户端播放
                    witherCenterPos.x(), witherCenterPos.y(), witherCenterPos.z(), // 声音从凋零位置发出
                    SoundEvents.WITHER_SPAWN,
                    SoundSource.HOSTILE,
                    1.0f,
                    0.8f // 降低音调，听起来更像召唤小怪
            );
        }
    }

    @SubscribeEvent
    public void witherSkeletonsDeath(LivingDeathEvent e) {
        LivingEntity living = e.getEntity();
        if (DiademaRegister.WITHER.get().isAffected(living)) {
            Level level = living.level();
            AreaEffectCloud cloud = new AreaEffectCloud(level, living.getX(), living.getY(), living.getZ());
            cloud.setOwner(null);
            cloud.setFixedColor(0x000000);
            cloud.setParticle(ParticleTypes.SQUID_INK);
            cloud.setRadius(4.5F);
            cloud.setRadiusOnUse(-0.5F);
            cloud.setRadiusPerTick(-0.005F);
            cloud.setDuration(90);
            cloud.setWaitTime(20);
            cloud.setPotion(Potions.STRONG_HARMING);
            level.addFreshEntity(cloud);
        }

    }
}
