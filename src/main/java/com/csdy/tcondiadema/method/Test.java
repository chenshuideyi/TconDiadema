package com.csdy.tcondiadema.method;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.entity.*;
import net.minecraftforge.common.MinecraftForge;

import java.util.Iterator;
import java.util.Set;
import java.util.UUID;

public class Test {
    public static void KillEntity(Entity target,Player player) {
        if (target != null && !(target instanceof Player)) {
            MinecraftForge.EVENT_BUS.unregister(target);
            Util.setHealth(target,target.level.damageSources.playerAttack(player),0);
            EntityInLevelCallback inLevelCallback = EntityInLevelCallback.NULL;
            target.levelCallback = inLevelCallback;
            target.setLevelCallback(inLevelCallback);
            target.getPassengers().forEach(Entity::stopRiding);
            Entity.RemovalReason reason = Entity.RemovalReason.KILLED;
            target.removalReason = reason;
            target.onClientRemoval();
            target.onRemovedFromWorld();
            target.remove(reason);
            target.setRemoved(reason);
            target.isAddedToWorld = false;
            target.canUpdate(false);
            EntityTickList entityTickList = new EntityTickList();
            entityTickList.remove(target);
            entityTickList.active.clear();
            entityTickList.passive.clear();
            if (target instanceof LivingEntity living) {
                living.getBrain().clearMemories();
                for (String s : living.getTags()) {
                    living.removeTag(s);
                }

                living.invalidateCaps();
                Util.setHealth(target,target.level.damageSources.playerAttack(player),0);
            }


        }

    }
}
