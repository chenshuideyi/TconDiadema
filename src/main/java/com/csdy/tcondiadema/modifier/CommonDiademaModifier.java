package com.csdy.tcondiadema.modifier;

import com.csdy.tcondiadema.frames.diadema.DiademaType;

import java.util.function.Supplier;

/// 创建一个简单的领域强化。<br/>
/// 用于注册时可以直接像这样用：<br/>
/// {@code MODIFIERS.register("gula", CommonDiademaModifier.Create(DiademaRegister.GULA));}
public class CommonDiademaModifier extends DiademaModifier {
    public CommonDiademaModifier(Supplier<DiademaType> getType) {
        this.getType = getType;
    }

    public static Supplier<CommonDiademaModifier> Create(Supplier<DiademaType> getType) {
        return () -> new CommonDiademaModifier(getType);
    }

    private final Supplier<DiademaType> getType;

    @Override protected DiademaType getDiademaType() {
        return getType.get();
    }
}
