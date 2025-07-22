package com.csdy.tcondiadema.mixins;

import com.csdy.tcondiadema.diadema.DiademaRegister;
import com.csdy.tcondiadema.frames.diadema.Diadema;
import com.csdy.tcondiadema.frames.diadema.movement.FollowDiademaMovement;
import net.minecraft.network.chat.Component;
import net.minecraft.server.bossevents.CustomBossEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.boss.enderdragon.EndCrystal;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EnderDragon.class)
public abstract class EnderDragonMixin extends Mob {

    @Unique
    private Diadema tcondiadema$dragonDiadema;

    protected EnderDragonMixin(EntityType<? extends Mob> p_21368_, Level p_21369_) {
        super(p_21368_, p_21369_);
    }

    @Inject(method = "<init>", at = @At("RETURN"))
    private void tcondiadema$onEnderDragonInit_diademaSetup(EntityType<? extends EnderDragon> entityType, Level level, CallbackInfo ci) {
        if (level.isClientSide) return;
        EnderDragon enderDragon = (EnderDragon)(Object)this;

        enderDragon.getAttribute(Attributes.MAX_HEALTH).setBaseValue(1000);
        enderDragon.setHealth(1000);

        this.tcondiadema$dragonDiadema = DiademaRegister.ENDER_DRAGON.get().CreateInstance(new FollowDiademaMovement(enderDragon));
    }

}