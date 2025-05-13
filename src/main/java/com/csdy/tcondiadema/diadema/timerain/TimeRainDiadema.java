package com.csdy.tcondiadema.diadema.timerain;



import com.csdy.tcondiadema.diadema.api.ranges.ColumnDiademaRange;
import com.csdy.tcondiadema.frames.diadema.Diadema;
import com.csdy.tcondiadema.frames.diadema.DiademaType;
import com.csdy.tcondiadema.frames.diadema.movement.DiademaMovement;
import com.csdy.tcondiadema.frames.diadema.range.DiademaRange;
import lombok.NonNull;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import slimeknights.tconstruct.library.tools.item.armor.ModifiableArmorItem;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;

import java.util.HashSet;
import java.util.Set;

public class TimeRainDiadema extends Diadema {
    static final double RADIUS = 8,HUP = 8, HDOWN = -8;
    private Entity entity = getEntity();

    private static final Set<Entity> WhiteList = new HashSet<>();

    private final ColumnDiademaRange range = new ColumnDiademaRange(this, RADIUS, HDOWN, HUP);

    public TimeRainDiadema(DiademaType type, DiademaMovement movement) {
        super(type, movement);
        if (entity != null) WhiteList.add(entity); //加白名单
    }

    @Override protected void removed() {
        if (getEntity() != null) WhiteList.remove(getEntity()); //去白名单
    }

    @Override
    public @NonNull DiademaRange getRange() {
        return range;
    }

    @Override
    protected void perTick() {
        for (Entity entity : affectingEntities) {
            if (!(entity instanceof LivingEntity target)) continue;
            if (WhiteList.contains(target)) continue;
            if (!hasBlockAbove(target, 8)) {
                target.setHealth(target.getMaxHealth()*0.0005f);
                reduceArmorDurability(target);
            }
        }
    }

private void reduceArmorDurability(LivingEntity living) {
    boolean hasAnyArmor = false; // 有任何护甲
    boolean allTinkersArmorBroken = true; // 所有匠魂护甲都损坏

    // 遍历护甲槽
    for (ItemStack armor : living.getArmorSlots()) {
        if (!armor.isEmpty()) {
            hasAnyArmor = true; // 有护甲
            // 检查是否是匠魂护甲
            if (armor.getItem() instanceof ModifiableArmorItem) {
                IToolStackView toolStack = ToolStack.from(armor);
                if (!toolStack.isBroken()) {
                    // 减少耐久,每刻5
                    armor.hurt(5, living.getRandom(), null);
                    allTinkersArmorBroken = false; // 至少有一件未损坏
                }
            }
        }
    }
    //俩即死
    if (!hasAnyArmor) {
        living.setHealth(0);
        return;
    }
    if (allTinkersArmorBroken) {
        living.setHealth(0);
    }
}

    private boolean hasBlockAbove(LivingEntity living, int height) {
        Level level = living.level();
        BlockPos pos = living.blockPosition();

        // 从目标头顶开始，向上检查height格(8)
        for (int i = 1; i <= height; i++) {
            BlockPos abovePos = pos.above(i);
            if (!level.getBlockState(abovePos).isAir()) {
                return true;
            }
        }
        return false;
    }
}
