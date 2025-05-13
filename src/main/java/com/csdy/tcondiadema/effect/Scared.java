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

}

