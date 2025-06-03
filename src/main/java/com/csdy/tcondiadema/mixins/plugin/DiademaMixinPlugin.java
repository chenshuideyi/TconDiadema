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

    // 目标 Mod ID
    private static final String GOETY_REVELATION_MOD_ID = "goety_revelation";

    private static final Set<String> CONDITIONAL_MIXINS_FOR_GOETY = new HashSet<>(Arrays.asList(
            "com.csdy.tcondiadema.mixins.ApollyonMixin"
    ));

    private boolean goetyRevelationLoaded = false;

    @Override
    public void onLoad(String mixinPackage) {
        this.goetyRevelationLoaded = ModList.get().isLoaded(GOETY_REVELATION_MOD_ID);

        if (this.goetyRevelationLoaded) {
            System.out.println("[DiademaMixinPlugin] 启示录已被加载");
        } else {
            System.out.println("[DiademaMixinPlugin] 未发现启示录，亚伯伦相关已禁用");
        }
    }

    @Override
    public String getRefMapperConfig() {
        return "csdy.refmap.json";
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        if (CONDITIONAL_MIXINS_FOR_GOETY.contains(mixinClassName)) {
            if (!this.goetyRevelationLoaded) {
                System.out.println("[DiademaMixinPlugin] 加载了" + mixinClassName + " for target " + targetClassName + " because Goety Revelation is not loaded.");
            }
            return this.goetyRevelationLoaded;
        }
        return true;
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {

    }

    @Override
    public List<String> getMixins() {
        return null;
    }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
    }

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
    }
}
