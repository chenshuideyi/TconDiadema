package com.csdyms.mixins;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({LivingEntity.class})
public abstract class LivingEntityMixin {
    @Inject(
            method = "knockback",
            at = @At("HEAD"),
            cancellable = true
    )
    private void onKnockback(double p_147241_, double p_147242_, double p_147243_, CallbackInfo ci) {
        if ((Object) this instanceof net.minecraft.world.entity.player.Player) {
            ci.cancel(); // 直接取消操作
        }
    }

    @Inject(
            method = "getHurtSound",
            at = @At("HEAD"),
            cancellable = true
    )
    private void getHurtSound(DamageSource p_21239_, CallbackInfoReturnable<SoundEvent> ci) {
        if ((Object) this instanceof net.minecraft.world.entity.player.Player) {
            ci.cancel(); // 直接取消操作
        }
    }



    @Inject(
            method = "playHurtSound",
            at = @At("HEAD"),
            cancellable = true
    )
    private void HurtSound(DamageSource p_21160_, CallbackInfo ci) {
        if ((Object) this instanceof net.minecraft.world.entity.player.Player) {
            ci.cancel(); // 直接取消操作
        }
    }


    @Inject(
            method = "dropFromLootTable",
            at = @At("TAIL"),
            cancellable = true
    )
    private void Drop(DamageSource p_21021_, boolean p_21022_, CallbackInfo ci) {
        LivingEntity living = (LivingEntity)(Object) this;
        if (living instanceof Warden) {
            Level level = living.level();
            // 创建一个金苹果
            ItemStack goldenApple = new ItemStack(Items.GOLDEN_APPLE);
            // 生成掉落物
            ItemEntity itemEntity = new ItemEntity(level,living.getX(),living.getY(),living.getZ(),goldenApple);
            living.level.addFreshEntity(itemEntity);
        }
    }
}
