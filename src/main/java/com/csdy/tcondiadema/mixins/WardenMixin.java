package com.csdy.tcondiadema.mixins;

import com.csdy.tcondiadema.diadema.DiademaRegister;
import com.csdy.tcondiadema.frames.diadema.Diadema;
import com.csdy.tcondiadema.frames.diadema.movement.FollowDiademaMovement;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({Warden.class})
public class WardenMixin {

    @Unique
    private static Diadema tcondiadema$wardenDiadema;

    @Inject(method = "<init>", at = @At("RETURN"))
    private void onWardenInit(EntityType<? extends Warden> entityType, Level level, CallbackInfo ci) {
        if (level.isClientSide) return;
        Warden warden = (Warden)(Object)this;
        tcondiadema$wardenDiadema = DiademaRegister.WARDEN.get().CreateInstance(new FollowDiademaMovement(warden));
    }
}
