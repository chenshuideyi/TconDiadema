package com.csdy.tcondiadema.diadema.gula;


import com.csdy.tcondiadema.TconDiadema;
import com.csdy.tcondiadema.diadema.api.ranges.SphereDiademaRange;
import com.csdy.tcondiadema.frames.diadema.Diadema;
import com.csdy.tcondiadema.frames.diadema.DiademaType;
import com.csdy.tcondiadema.frames.diadema.movement.DiademaMovement;
import com.csdy.tcondiadema.frames.diadema.range.DiademaRange;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;

@Mod.EventBusSubscriber(modid = TconDiadema.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class GulaDiadema extends Diadema {
    static final double RADIUS = 8;
    private final Entity core = getCoreEntity();

    public GulaDiadema(DiademaType type, DiademaMovement movement) {
        super(type, movement);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override protected void removed() {
        MinecraftForge.EVENT_BUS.unregister(this);
    }

    private final SphereDiademaRange range = new SphereDiademaRange(this,RADIUS);

    @Override
    public @NotNull DiademaRange getRange() {
        return range;
    }

    @SubscribeEvent
    public void gula(LivingDeathEvent e) {
        if (!(core instanceof Player player)) return;
        LivingEntity living = e.getEntity();
        if (this.affectingEntities.contains(living)) {

            AttributeInstance maxHealthAttr = player.getAttribute(Attributes.MAX_HEALTH);
            double originalMaxHealth = maxHealthAttr.getBaseValue();
            double reducedMaxHealth = originalMaxHealth + 1;
            maxHealthAttr.setBaseValue(reducedMaxHealth);

            AttributeInstance AttackAttr = player.getAttribute(Attributes.ATTACK_DAMAGE);
            double originalAttack = AttackAttr.getBaseValue();
            double reducedAttack = originalAttack + 1;
            AttackAttr.setBaseValue(reducedAttack);

            player.heal(10);
            player.getFoodData().setFoodLevel(player.getFoodData().getFoodLevel()+5);
            player.getFoodData().setSaturation(player.getFoodData().getSaturationLevel()+5);
        }
    }
}
