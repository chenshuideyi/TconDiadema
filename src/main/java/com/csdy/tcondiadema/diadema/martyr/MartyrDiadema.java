package com.csdy.tcondiadema.diadema.martyr;

import com.csdy.tcondiadema.TconDiadema;
import com.csdy.tcondiadema.diadema.DiademaRegister;
import com.csdy.tcondiadema.diadema.api.ranges.SelfDiademaRange;
import com.csdy.tcondiadema.frames.diadema.Diadema;
import com.csdy.tcondiadema.frames.diadema.DiademaType;
import com.csdy.tcondiadema.frames.diadema.movement.DiademaMovement;
import com.csdy.tcondiadema.frames.diadema.range.DiademaRange;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;

@Mod.EventBusSubscriber(modid = TconDiadema.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class MartyrDiadema extends Diadema {

    public MartyrDiadema(DiademaType type, DiademaMovement movement) {
        super(type, movement);
    }

    private final SelfDiademaRange range = new SelfDiademaRange(this, getCoreEntity());

    @Override public @NotNull DiademaRange getRange() {
        return range;
    }

    @SubscribeEvent
    public void onMartyrHurt(LivingHurtEvent e) {
        Entity holder = getCoreEntity();
        if (holder == null) return;

        var entity = e.getEntity();
        if (DiademaRegister.MARTYR.get().isAffected(holder)) {
            if (entity != null && entity != holder) {
                holder.hurt(e.getSource(), e.getAmount());
                e.setCanceled(true);
            }
        }
    }

}
