package com.csdy.tcondiadema.mixins;

import com.csdy.tcondiadema.diadema.DiademaRegister;
import com.csdy.tcondiadema.frames.diadema.Diadema;
import com.csdy.tcondiadema.frames.diadema.movement.FollowDiademaMovement;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.animal.horse.SkeletonHorse;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.monster.WitherSkeleton;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;



import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.monster.WitherSkeleton;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.csdy.tcondiadema.diadema.wither.WitherDiadema.addWitherSkeleton;

// 假设 DiademaRegister 和 Diadema 以及 FollowDiademaMovement 类存在且定义正确
// import your.package.DiademaRegister;
// import your.package.Diadema;
// import your.package.FollowDiademaMovement;


@Mixin(WitherBoss.class)
public abstract class WitherMixin { // 如果 WitherBoss 有抽象方法，可能需要继承其父类

    @Unique
    private Diadema tcondiadema$witherDiadema; // 改为实例字段，如果每个凋零都有自己的Diadema

    @Unique
    private boolean tcondiadema$skeletonsSpawned = false;

    // Diadema 的初始化可以保留在构造函数中，因为它可能与凋零实例本身紧密相关
    @Inject(method = "<init>", at = @At("RETURN"))
    private void tcondiadema$onWitherInit_diademaSetup(EntityType<? extends WitherBoss> entityType, Level level, CallbackInfo ci) {
        if (level.isClientSide) return;
        WitherBoss wither = (WitherBoss) (Object) this;
        this.tcondiadema$witherDiadema = DiademaRegister.WITHER.get().CreateInstance(new FollowDiademaMovement(wither));
    }

    // 在凋零的AI步骤中生成骷髅，确保其位置更准确，并且只生成一次
    @Inject(method = "customServerAiStep", at = @At("HEAD")) // 或者 "tick"
    private void tcondiadema$onWitherFirstTick_spawnSkeletons(CallbackInfo ci) {
        WitherBoss wither = (WitherBoss) (Object) this;
        Level level = wither.level();

        if (level.isClientSide || this.tcondiadema$skeletonsSpawned) {
            return;
        }

        // 获取凋零当前更稳定的位置
//        Vec3 witherPos = wither.position(); // 或者 wither.getX(), wither.getY(), wither.getZ()
//        addWitherSkeleton(level, witherPos, 12);
        this.tcondiadema$skeletonsSpawned = true; // 标记已生成，防止重复
    }
}