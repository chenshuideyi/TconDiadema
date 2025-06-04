package com.csdy.tcondiadema.mixins.plugin;

import net.minecraftforge.fml.ModList;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.Set;

import java.util.Arrays;
import java.util.HashSet;

public class DiademaMixinPlugin implements IMixinConfigPlugin {

    private static final String TARGET_MOD_ID = "goety_revelation";
    private boolean isGoetyRevelationLoaded = false;

    @Override
    public void onLoad(String mixinPackage) {
        // 在 Mixin 配置加载时检查目标 Mod 是否存在
        this.isGoetyRevelationLoaded = ModList.get().isLoaded(TARGET_MOD_ID);
        if (!this.isGoetyRevelationLoaded) {
            // 你可以在这里打印一条日志信息
            System.out.println("[YourModID] " + TARGET_MOD_ID + " not found. ApollyonMixin will not be applied.");
        } else {
            System.out.println("[YourModID] " + TARGET_MOD_ID + " found. ApollyonMixin will be conditionally applied.");
        }
    }

    @Override
    public String getRefMapperConfig() {
        return null; // 通常不需要，除非你有特殊的 refmap需求
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        // targetClassName 是被 Mixin 修改的目标类的全限定名
        // mixinClassName 是 Mixin 类的全限定名

        // 我们只关心 ApollyonMixin
        // 注意：mixinClassName 可能是 "com.yourmodid.mixin.ApollyonMixin"
        // 你可能需要根据你的实际包名调整
        if (mixinClassName.equals("com.csdy.tcondiadema.mixins.ApollyonMixin")) { // <--- 重要：确保这里的类名和包名与你的 ApollyonMixin 完全一致
            return this.isGoetyRevelationLoaded; // 如果 Mod 加载了，则应用此 Mixin，否则不应用
        }

        // 对于此插件不关心的其他 Mixin（如果有的话），默认允许应用
        return true;
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {
        // 通常不需要实现具体逻辑
    }

    @Override
    public List<String> getMixins() {
        return null; // 通常不需要，除非你想动态添加 Mixins 列表
    }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
        // 在 Mixin 应用到目标类之前调用
    }

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
        // 在 Mixin 应用到目标类之后调用
    }
}
