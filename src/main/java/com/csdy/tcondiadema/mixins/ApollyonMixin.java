package com.csdy.tcondiadema.mixins;

import com.Polarice3.Goety.common.entities.boss.Apostle;
import com.csdy.tcondiadema.diadema.DiademaRegister;
import com.csdy.tcondiadema.frames.diadema.Diadema;
import com.csdy.tcondiadema.frames.diadema.movement.FollowDiademaMovement;
import com.mega.revelationfix.common.init.ModEntities;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.csdy.tcondiadema.TconDiadema.isLoadRevelation;


@Mixin(
        value = Apostle.class,
        priority = 1000
)
public class ApollyonMixin {

    @Unique
    private static Diadema tcondiadema$apollyonDiadema;

    @Inject(method = "<init>", at = @At("RETURN"))
    private void onApollyonInit(EntityType<? extends Apostle> entityType, Level level, CallbackInfo ci) {
        System.out.println("[DEBUG] Mixin triggered for: " + entityType);
        if (!isLoadRevelation()) {
            System.out.println("[DEBUG] Revelation not loaded, skipping");
            return;
        }
        if (level.isClientSide) {
            System.out.println("[DEBUG] Client side, skipping");
            return;
        }
        if (entityType.equals(ModEntities.APOSTLE_SERVANT.get())) {
            System.out.println("[DEBUG] ApostleServant detected, skipping");
            return;
        }
        System.out.println("[DEBUG] Creating Diadema for Apostle");
        tcondiadema$apollyonDiadema = DiademaRegister.APOLLYON.get().CreateInstance(new FollowDiademaMovement((Apostle)(Object)this));
    }
}
