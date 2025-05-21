package com.csdy.tcondiadema.diadema.warden;


import com.csdy.tcondiadema.TconDiadema;
import com.csdy.tcondiadema.diadema.api.ranges.HalfSphereDiademaRange;
import com.csdy.tcondiadema.effect.register.EffectRegister;
import com.csdy.tcondiadema.frames.diadema.Diadema;
import com.csdy.tcondiadema.frames.diadema.DiademaType;
import com.csdy.tcondiadema.frames.diadema.movement.DiademaMovement;
import com.csdy.tcondiadema.frames.diadema.range.DiademaRange;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

// 如你所见，这个是领域的服务端类型，带个Client的是客户端类型，一般而言两个都要重写一份。然后拿去注册
@Mod.EventBusSubscriber(modid = TconDiadema.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class WardenDiadema extends Diadema {
    static final double RADIUS = 4;

    public static final Set<Entity> WhiteList = new HashSet<>();

    public WardenDiadema(DiademaType type, DiademaMovement movement) {
        super(type, movement);

        if (getCoreEntity() != null) WhiteList.add(getCoreEntity()); //加白名单
    }

    @Override protected void removed() {
        if (getCoreEntity() != null) WhiteList.remove(getCoreEntity()); //去白名单
    }

    private final HalfSphereDiademaRange range = new HalfSphereDiademaRange(this, RADIUS);

    @Override public @NotNull DiademaRange getRange() {
        return range;
    }

    // 大部分逐帧事件（比如把所有范围内实体丢进虚空）就都可以写在这里，有方法能获取所有影响到的方块和实体
    // 需要针对实体进出的时间点的就监听事件，参考那俩事件处理器。
    @Override protected void perTick() {
        for (Entity entity : affectingEntities) {
            if (!(entity instanceof LivingEntity target)) continue;
            if (!(WhiteList.contains(target))) {
                target.addEffect(new MobEffectInstance(EffectRegister.SCARED.get(), 100, 0));
            }
        }
    }


    @Override protected void onEntityEnter(Entity entity) {
        if (entity instanceof ServerPlayer player) {
            WardenBlindnessEffect.SetEnableTo(player, true);
        }
    }

    @Override
    protected void onEntityExit(Entity entity) {
        if (entity instanceof ServerPlayer player && !WhiteList.contains(player)) {
            WardenBlindnessEffect.SetEnableTo(player, false);
        }

        var core = getCoreEntity();
        if (core == null || entity.equals(core)) return;
        if (entity instanceof LivingEntity living) {
            SonicBoomUtil.performSonicBoom(entity.level, living, core);
            living.removeEffect(EffectRegister.SCARED.get());
        }
    }
}
