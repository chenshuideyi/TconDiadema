package com.csdy.tcondiadema.mixins;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.monster.WitherSkeleton;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;

@Mixin(Mob.class)
public abstract class SetTargetMixin {
    @Inject(method = "setTarget", at = @At("HEAD"), cancellable = true)
    private void onSetTarget(@Nullable LivingEntity pLivingEntity, CallbackInfo cir) {
        Mob currentMob = (Mob) (Object) this;
        if (currentMob instanceof WitherSkeleton) {
            if (pLivingEntity instanceof WitherBoss) {
                cir.cancel();
            }
        }
    }
}
