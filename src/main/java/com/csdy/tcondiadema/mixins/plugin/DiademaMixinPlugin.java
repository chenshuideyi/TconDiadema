package com.csdy.tcondiadema.mixins.plugin;

import com.csdy.tcondiadema.TconDiadema;
import net.minecraftforge.fml.ModList;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DiademaMixinPlugin implements IMixinConfigPlugin {


    @Override
    public void onLoad(String mixinPackage) {
    }

    @Override
    public String getRefMapperConfig() {
        return "csdy.refmap.json";
    }

    private static final String REVELATION_CORE_CLASS = "com.mega.revelationfix.Revelationfix";

    private static final Set<String> CONDITIONAL_MIXINS = new HashSet<>(Arrays.asList(
            "com.csdy.tcondiadema.mixins.ApollyonMixin"
    ));


    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        if (CONDITIONAL_MIXINS.contains(mixinClassName)) {
            boolean isLoaded = classExists(REVELATION_CORE_CLASS);
            if (isLoaded) System.out.println("[匠魂领域MixinPlugin] " + mixinClassName + " 加载决策: " + true + " 亚伯伦这下牛逼了");
            else System.out.println("[匠魂领域MixinPlugin] " + mixinClassName + " 加载决策: " + false + " 亚伯伦这下不牛逼了");
            return isLoaded;
        }
        return true;
    }

    private static boolean classExists(String className) {
        try {
            Class.forName(className, false, DiademaMixinPlugin.class.getClassLoader());
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

//    @Override
//    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
//        if (CONDITIONAL_MIXINS_FOR_GOETY.contains(mixinClassName)) {
//            boolean loaded = TconDiadema.IS_GOETY_REVELATION_LOADED;
//            System.out.println("[MixinPlugin] 最终加载决策: " + loaded);
//            System.out.println("[MixinPlugin] 最终加载决策: " + loaded);
//            System.out.println("[MixinPlugin] 最终加载决策: " + loaded);
//            System.out.println("[MixinPlugin] 最终加载决策: " + loaded);
//            return loaded;
//        }
//        return true;
//    }

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
