package com.csdy.tcondiadema.mixins;

import com.csdy.tcondiadema.diadema.DiademaRegister;
import com.csdy.tcondiadema.frames.diadema.Diadema;
import com.csdy.tcondiadema.frames.diadema.movement.FollowDiademaMovement;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attributes;
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
import org.spongepowered.asm.mixin.*;
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

// 假设 DiademaRegister 和 Diadema 以及 FollowDiademaMovement 类存在且定义正确
// import your.package.DiademaRegister;
// import your.package.Diadema;
// import your.package.FollowDiademaMovement;


@Mixin(WitherBoss.class)
public abstract class WitherMixin extends Mob {

    @Shadow protected abstract float rotlerp(float p_31443_, float p_31444_, float p_31445_);

    protected WitherMixin(EntityType<? extends Mob> entityType, Level level) {
        super(entityType, level);
    }

    @Unique
    private Diadema tcondiadema$witherDiadema;

    @Unique
    private boolean tcondiadema$skeletonsSpawned = false;

    // Diadema 的初始化可以保留在构造函数中，因为它可能与凋零实例本身紧密相关
    @Inject(method = "<init>", at = @At("RETURN"))
    private void tcondiadema$onWitherInit_diademaSetup(EntityType<? extends WitherBoss> entityType, Level level, CallbackInfo ci) {
        if (level.isClientSide) return;
        WitherBoss wither = (WitherBoss) (Object) this;
        Difficulty difficulty = this.level().getDifficulty();
        if(difficulty == Difficulty.EASY) return;
        if (difficulty == Difficulty.HARD) {
            wither.getAttribute(Attributes.MAX_HEALTH).setBaseValue(600.0);
            wither.setHealth(600.0f);
        }
        this.tcondiadema$witherDiadema = DiademaRegister.WITHER.get().CreateInstance(new FollowDiademaMovement(wither));
    }

    @Override
    public Component getDisplayName() {
        Difficulty difficulty = this.level().getDifficulty();
        return switch (difficulty) {
            case EASY -> Component.literal("天真无邪的少女 · 凋灵");
            case NORMAL -> Component.literal("冷酷无情的战士 · 凋灵");
            case HARD -> Component.literal("『冥界主宰』 · 调灵");
            default -> super.getDisplayName();
        };
    }
}