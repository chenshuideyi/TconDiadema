package com.csdy.tcondiadema.mixins;

import com.Polarice3.Goety.common.entities.boss.Apostle;
import com.csdy.tcondiadema.diadema.DiademaRegister;
import com.csdy.tcondiadema.frames.diadema.Diadema;
import com.csdy.tcondiadema.frames.diadema.movement.FollowDiademaMovement;
import com.mega.revelationfix.common.entity.boss.ApostleServant;
import com.mega.revelationfix.common.init.ModEntities;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraftforge.fml.ModList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import z1gned.goetyrevelation.util.ApollyonAbilityHelper;

import static com.csdy.tcondiadema.TconDiadema.isLoadRevelation;

@Mixin(Apostle.class)
public abstract class ApollyonMixin {

    @Unique
    private Diadema tcondiadema$apollyonDiadema;

    // 辅助方法，用于检查 Goety 是否加载 (如果 Mixin 本身不是条件加载的)
    @Unique
    private boolean tcondiadema$isGoetyRevelationLoaded() {
        return ModList.get().isLoaded("goety_revelation");
    }

    @Inject(method = "<init>", at = @At("RETURN"))
    private void tcondiadema$onApostleInit(EntityType<? extends Apostle> entityType, Level level, CallbackInfo ci) {
        if (!tcondiadema$isGoetyRevelationLoaded()) {
            return;
        }
        Apostle currentApostleInstance = (Apostle) (Object) this;
        if (level.isClientSide) {
            return;
        }

        // 3. 排除特定子类型
        if (entityType.equals(ModEntities.APOSTLE_SERVANT.get())) {
            return;
        }

        this.tcondiadema$apollyonDiadema = DiademaRegister.APOLLYON.get().CreateInstance(new FollowDiademaMovement(currentApostleInstance));

    }

}
