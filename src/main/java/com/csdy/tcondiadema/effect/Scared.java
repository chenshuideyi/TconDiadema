package com.csdy.tcondiadema.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class Scared extends MobEffect {

    private static final String SPEED_MODIFIER_UUID = "7107DE5E-7CE8-4030-940E-514C1F160890"; // 唯一标识符
    private static final double SPEED_REDUCTION = -0.8;

    private static final String ATTACK_DAMAGE_MODIFIER_UUID = "7107DE5E-7CE8-4030-940E-514C1F160890";
    private static final double ATTACK_DAMAGE_REDUCTION = -0.5;

    public Scared() {
        super(MobEffectCategory.HARMFUL, 0);
        this.addAttributeModifier(Attributes.MOVEMENT_SPEED, SPEED_MODIFIER_UUID, SPEED_REDUCTION, AttributeModifier.Operation.MULTIPLY_TOTAL);
        this.addAttributeModifier(Attributes.ATTACK_DAMAGE, ATTACK_DAMAGE_MODIFIER_UUID, ATTACK_DAMAGE_REDUCTION, AttributeModifier.Operation.MULTIPLY_TOTAL);
    }

/*    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        if (entity instanceof ServerPlayer player) {
            player.setShiftKeyDown(true); // 强制设置为潜行状态
        }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true; // 每 tick 都执行 applyEffectTick
    }

    @SubscribeEvent
    public static void Scared(TickEvent.PlayerTickEvent event) {
        if (event.player.hasEffect(EffectRegister.SCARED.get())) event.player.setShiftKeyDown(true);
    }*/
}

