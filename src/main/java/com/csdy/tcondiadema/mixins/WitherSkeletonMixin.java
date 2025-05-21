package com.csdy.tcondiadema.mixins;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.monster.WitherSkeleton;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;

@Mixin(LivingEntity.class)
public class WitherSkeletonMixin {

    @Inject(method = "hurt", at = @At("HEAD"), cancellable = true)
    private void onWitherSkeletonHurt(DamageSource source, float damage, CallbackInfoReturnable<Boolean> cir) {
        LivingEntity currentMob = (LivingEntity) (Object) this;
        if (currentMob instanceof WitherSkeleton) {
            if (source.getDirectEntity() instanceof WitherBoss) {
                cir.cancel();
            }
        }
    }

}
