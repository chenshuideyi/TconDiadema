package com.csdy.tcondiadema.frames.diadema;

import com.csdy.tcondiadema.frames.CsdyRegistries;
import com.csdy.tcondiadema.modifier.DiademaModifier;
import net.minecraftforge.registries.IForgeRegistry;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;


public class DiademaUtils {

    public static Collection<DiademaType> getAllDiademaTypes() {
        IForgeRegistry<DiademaType> registry = CsdyRegistries.DIADEMA_TYPES_REG.get();
        return registry.getValues();
    }

    public static void killAllDiadema() {
        // 先禁用领域创建
        DiademaModifier.setDiademaCreationEnabled(false);

        try {
            IForgeRegistry<DiademaType> registry = CsdyRegistries.DIADEMA_TYPES_REG.get();
            Collection<DiademaType> allTypes = registry.getValues();

            allTypes.stream()
                    .flatMap(type -> type.getInstances().stream())
                    .filter(Diadema::isAlive)
                    .forEach(Diadema::kill);

            System.out.println("csdy一共杀掉了" + allTypes + "个");

            // 同时清理 DiademaModifier 中的缓存
            clearDiademaModifierCache();

        } finally {
            // 可选：在一段时间后重新启用领域创建
            // 或者保持禁用状态直到需要时再启用
        }
    }

    /**
     * 清理 DiademaModifier 中的缓存
     */
    private static void clearDiademaModifierCache() {
        try {
            Field diademasField = DiademaModifier.class.getDeclaredField("Diademas");
            diademasField.setAccessible(true);
            @SuppressWarnings("unchecked")
            Map<UUID, Diadema> diademas = (Map<UUID, Diadema>) diademasField.get(null);
            diademas.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
