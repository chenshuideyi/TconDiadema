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
        // 确保这里的 MOD_ID 是正确的 Goety Mod ID
        return ModList.get().isLoaded("goety_revelation");
    }

    // 核心判断逻辑：判断当前 Apostle 实例是否为 Apollyon
//    @Unique
//    private boolean tcondiadema$isThisInstanceApollyon() {
//        Apostle self = (Apostle) (Object) this;
//        if (self instanceof ApollyonAbilityHelper) {
//            return ((ApollyonAbilityHelper) self).allTitlesApostle_1_20_1$isApollyon();
//        } else {
//            System.out.println("[TconDiadema Mixin WARNING] Apostle instance does not implement ApollyonAbilityHelper. Cannot determine if it's an Apollyon type. Apostle class: " + self.getClass().getName());
//            return false;
//        }
//    }

    @Inject(method = "<init>", at = @At("RETURN"))
    private void tcondiadema$onApostleInit(EntityType<? extends Apostle> entityType, Level level, CallbackInfo ci) {
        Apostle currentApostleInstance = (Apostle) (Object) this;

        System.out.println("[TconDiadema Mixin DEBUG] Apostle <init> RETURN triggered for: " + entityType + " on " + (level.isClientSide ? "CLIENT" : "SERVER"));

        // 1. 检查依赖 Mod (如果 Mixin 本身不是被 DiademaMixinPlugin 条件加载的)
        if (!tcondiadema$isGoetyRevelationLoaded()) {
            System.out.println("[TconDiadema Mixin DEBUG] Goety Revelation not loaded, skipping Diadema creation in Apostle.");
            return;
        }

        // 2. 只在服务器端处理
        if (level.isClientSide) {
            System.out.println("[TconDiadema Mixin DEBUG] Client side, skipping Diadema creation.");
            return;
        }

        // 3. 排除特定子类型
        if (entityType.equals(ModEntities.APOSTLE_SERVANT.get())) {
            System.out.println("[TconDiadema Mixin DEBUG] Instance is ApostleServant, skipping Diadema creation.");
            return;
        }

        // 4. 判断当前 Apostle 实例是否是 "Apollyon"

        System.out.println("[TconDiadema Mixin DEBUG] Apostle instance IS Apollyon type. Creating Diadema.");
        // 确保 DiademaRegister.APOLLYON 是条件注册的，并且已成功注册
        if (DiademaRegister.APOLLYON != null && DiademaRegister.APOLLYON.isPresent()) {
            this.tcondiadema$apollyonDiadema = DiademaRegister.APOLLYON.get().CreateInstance(new FollowDiademaMovement(currentApostleInstance));
            System.out.println("[TconDiadema Mixin DEBUG] Apollyon Diadema created and assigned.");
        } else {
            System.out.println("[TconDiadema Mixin ERROR] DiademaRegister.APOLLYON is null or not present! Cannot create Diadema for Apollyon. This usually means the DiademaType was not registered because Goety Revelation was thought to be unloaded, or there's an issue with its registration.");
        }

    }

    // ... 其他可能的注入点 (tick, save, load, remove) 来管理 tcondiadema$apollyonDiadema ...
}
