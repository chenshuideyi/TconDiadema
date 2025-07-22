package com.csdy.tcondiadema.mixins;

import com.Polarice3.Goety.common.entities.boss.Apostle;
import com.csdy.tcondiadema.diadema.DiademaRegister;
import com.csdy.tcondiadema.frames.diadema.Diadema;
import com.csdy.tcondiadema.frames.diadema.movement.FollowDiademaMovement;
import com.mega.revelationfix.common.init.ModEntities;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import z1gned.goetyrevelation.util.ApollyonAbilityHelper;

@Mixin({Warden.class})
public class WardenMixin extends Mob {

    @Unique
    private Diadema tcondiadema$wardenDiadema;

    @Unique
    private boolean tcondiadema$diademaInitialized = false; // 标记 Diadema 是否已初始化

    protected WardenMixin(EntityType<? extends Mob> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void tcondiadema$onTickCheckAndInitializeDiadema(CallbackInfo ci) {
        if (this.level().isClientSide || this.tcondiadema$diademaInitialized) {
            return;
        }

        Warden warden = (Warden) (Object) this;

        tcondiadema$wardenDiadema = DiademaRegister.WARDEN.get().CreateInstance(new FollowDiademaMovement(warden));

        this.tcondiadema$diademaInitialized = true;
    }
}
///狗操的刷怪笼
//    @Inject(method = "<init>", at = @At("RETURN"))
//    private void onWardenInit(EntityType<? extends Warden> entityType, Level level, CallbackInfo ci) {
//        if (level.isClientSide) return;
//
//        Warden warden = (Warden)(Object)this;
//
//        tcondiadema$wardenDiadema = DiademaRegister.WARDEN.get().CreateInstance(new FollowDiademaMovement(warden));
//        System.out.println("已添加一个领域"+ this.getClass().getName());
//    }

