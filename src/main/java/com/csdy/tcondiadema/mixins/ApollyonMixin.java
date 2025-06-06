package com.csdy.tcondiadema.mixins;

import com.Polarice3.Goety.common.entities.boss.Apostle;
import com.csdy.tcondiadema.DiademaConfig;
import com.csdy.tcondiadema.diadema.DiademaRegister;
import com.csdy.tcondiadema.frames.diadema.Diadema;
import com.csdy.tcondiadema.frames.diadema.movement.FollowDiademaMovement;
import com.mega.revelationfix.common.entity.boss.ApostleServant;
import com.mega.revelationfix.common.entity.cultists.HereticServant;
import com.mega.revelationfix.common.init.ModEntities;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraftforge.fml.ModList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import z1gned.goetyrevelation.util.ApollyonAbilityHelper;

import static com.csdy.tcondiadema.diadema.apollyon.ApollyonDiadema.spawnHereticServant;
import static com.csdy.tcondiadema.diadema.apollyon.ApollyonDiadema.spawnMultipleServants;


@Mixin(Apostle.class) // 确保 Apostle 类实现了 ApollyonAbilityHelper (可能通过另一个 Mixin)
public abstract class ApollyonMixin extends LivingEntity { // 确保继承自 Apostle 的正确父类

    protected ApollyonMixin(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Unique
    private Diadema tcondiadema$apollyonDiadema;

    @Unique
    private boolean tcondiadema$diademaInitialized = false; // 标记 Diadema 是否已初始化

    @Unique
    private boolean tcondiadema$isGoetyRevelationLoaded() {
        return ModList.get().isLoaded("goety_revelation");
    }

    // 核心逻辑放在 tick 方法中进行一次性检查
    @Inject(method = "tick", at = @At("HEAD"))
    private void tcondiadema$onTickCheckAndInitializeDiadema(CallbackInfo ci) {
        // 只在服务器端执行，且只执行一次，且依赖 Mod 加载
        if (this.level().isClientSide || this.tcondiadema$diademaInitialized || !tcondiadema$isGoetyRevelationLoaded()) {
            return;
        }

        Apostle currentApostle = (Apostle) (Object) this;


        if (currentApostle.getType().equals(ModEntities.APOSTLE_SERVANT.get())) {
            this.tcondiadema$diademaInitialized = true; // 标记为已处理 (即使是跳过)
            return;
        }


        if (currentApostle instanceof ApollyonAbilityHelper) {
            if (((ApollyonAbilityHelper) currentApostle).allTitlesApostle_1_20_1$isApollyon()) {
                if (DiademaRegister.APOLLYON != null && DiademaRegister.APOLLYON.isPresent()) {
                    this.tcondiadema$apollyonDiadema = DiademaRegister.APOLLYON.get().CreateInstance(
                            new FollowDiademaMovement(currentApostle)
                    );
                }
            }
        }

        this.tcondiadema$diademaInitialized = true;
    }

    @Inject(method = "finalizeSpawn", at = @At("HEAD"))
    private void tcondiadema$finalizeSpawn(ServerLevelAccessor worldIn, DifficultyInstance difficultyIn, MobSpawnType reason,
                                           SpawnGroupData spawnDataIn, CompoundTag dataTag, CallbackInfoReturnable<SpawnGroupData> cir) {

        Apostle currentApostle = (Apostle)(Object)this;

        // 只在服务器端执行
        if (!(worldIn.getLevel() instanceof ServerLevel)) {
            return;
        }

        // 确保是Apollyon才生成仆从
        if (currentApostle instanceof ApollyonAbilityHelper &&
                ((ApollyonAbilityHelper)currentApostle).allTitlesApostle_1_20_1$isApollyon()) {

            if (DiademaConfig.EX_APOLLYON.get()) {
                spawnMultipleServants(currentApostle.level, currentApostle, 12);
            }
            else spawnHereticServant(currentApostle.level, currentApostle, 12);


            MinecraftServer server = (MinecraftServer) worldIn; // 或者通过其他方式获取 server
            if (server != null) {
                // 遍历所有在线玩家
                for (ServerPlayer onlinePlayer : server.getPlayerList().getPlayers()) {
                    // 发送消息（仅限服务端，避免客户端重复执行）
                    onlinePlayer.displayClientMessage(
                            Component.literal("我来并不是叫地上太平，乃是叫地上动刀兵"),
                            true // 是否显示在 ActionBar（true 为 ActionBar，false 为聊天栏）
                    );
                }
            }
        }
    }



}
