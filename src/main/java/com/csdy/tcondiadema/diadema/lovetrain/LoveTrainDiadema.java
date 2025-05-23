package com.csdy.tcondiadema.diadema.lovetrain;


import com.csdy.tcondiadema.TconDiadema;
import com.csdy.tcondiadema.diadema.DiademaRegister;
import com.csdy.tcondiadema.diadema.api.ranges.SphereDiademaRange;
import com.csdy.tcondiadema.frames.diadema.Diadema;
import com.csdy.tcondiadema.frames.diadema.DiademaType;
import com.csdy.tcondiadema.frames.diadema.movement.DiademaMovement;
import com.csdy.tcondiadema.frames.diadema.range.DiademaRange;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Mod.EventBusSubscriber(modid = TconDiadema.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class LoveTrainDiadema extends Diadema {

    final static double RADIUS = 4;

    public LoveTrainDiadema(DiademaType type, DiademaMovement movement) {
        super(type, movement);
    }
    private final SphereDiademaRange range = new SphereDiademaRange(this, RADIUS);

    @Override
    public @NotNull DiademaRange getRange() {
        return range;
    }

    @SubscribeEvent
    public void onLoveTrainHurt(LivingHurtEvent e) {
        var entity = e.getEntity();
        if (DiademaRegister.LOVE_TRAIN.get().isAffected(entity)) {
            // 移除随机判断，直接寻找目标
            Entity unaffectedTarget = findRandomUnaffectedEntity(entity.level, BlockPos.containing(entity.position()));

            if (unaffectedTarget != null) {
                unaffectedTarget.hurt(e.getSource(), e.getAmount());
                if (getCoreEntity() instanceof Player player) {
                    Component targetName = unaffectedTarget.getDisplayName();
                    player.displayClientMessage(
                            Component.translatable("love_train_text1")
                                    .append(targetName)
                                    .withStyle(ChatFormatting.DARK_RED),
                            false
                    );
                }
                e.setCanceled(true);
            }
        }
    }

    private Entity findRandomUnaffectedEntity(Level world, BlockPos center) {
        List<LivingEntity> candidates = world.getEntitiesOfClass(LivingEntity.class,
                new AABB(-30_000_000, -256, -30_000_000, 30_000_000, 256, 30_000_000),
                target -> target.isAlive()
                        && !DiademaRegister.LOVE_TRAIN.get().isAffected(target)
                        && target.level() == world
        );

        return candidates.isEmpty() ? null :
                candidates.get(world.getRandom().nextInt(candidates.size()));
    }
}
