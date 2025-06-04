package com.csdy.tcondiadema.diadema.fakeapollyon;


import com.csdy.tcondiadema.diadema.api.ranges.SphereDiademaRange;
import com.csdy.tcondiadema.frames.diadema.Diadema;
import com.csdy.tcondiadema.frames.diadema.DiademaType;
import com.csdy.tcondiadema.frames.diadema.movement.DiademaMovement;
import com.csdy.tcondiadema.frames.diadema.range.DiademaRange;
import com.csdy.tcondiadema.item.register.ItemRegister;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemHandlerHelper;
import org.jetbrains.annotations.NotNull;
import z1gned.goetyrevelation.util.ApollyonAbilityHelper;

public class FakeApollyonDiadema extends Diadema {
    static final double RADIUS = 6;
    private final Entity holder = getCoreEntity();

    public FakeApollyonDiadema(DiademaType type, DiademaMovement movement) {
        super(type, movement);
    }

    private final SphereDiademaRange range = new SphereDiademaRange(this,RADIUS);

    @Override
    public @NotNull DiademaRange getRange() {
        return range;
    }

    @Override
    protected void perTick() {
        ServerLevel level = getLevel();

        // 只在服务端执行，且确保 entity 是 LivingEntity
        if (level.isClientSide() || !(holder instanceof Player player)) {
            return;
        }

        if (player.tickCount % 400 == 0) {
            ItemHandlerHelper.giveItemToPlayer(player, new ItemStack(ItemRegister.POOP.get()));
        }

    }
}
