package com.csdy.tcondiadema.diadema.projecte;


import com.csdy.tcondiadema.diadema.api.ranges.SphereDiademaRange;
import com.csdy.tcondiadema.frames.diadema.Diadema;
import com.csdy.tcondiadema.frames.diadema.DiademaType;
import com.csdy.tcondiadema.frames.diadema.movement.DiademaMovement;
import com.csdy.tcondiadema.frames.diadema.range.DiademaRange;
import moze_intel.projecte.api.capabilities.PECapabilities;
import moze_intel.projecte.gameObjs.registries.PEDamageTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;

public class ProjectEDiadema extends Diadema {
    static final double RADIUS = 8;
    private final Entity entity = getCoreEntity();

    public ProjectEDiadema(DiademaType type, DiademaMovement movement) {
        super(type, movement);
    }

    private final SphereDiademaRange range = new SphereDiademaRange(this,RADIUS);

    @Override
    public @NotNull DiademaRange getRange() {
        return range;
    }

    @Override protected void perTick() {
        if (!(entity instanceof Player player)) return;
        for (Entity entity : affectingEntities) {
            DamageSource src = PEDamageTypes.BYPASS_ARMOR_PLAYER_ATTACK.source(player);
            if (!(entity instanceof LivingEntity)) continue;
            if (!entity.equals(player)){
                entity.invulnerableTime = 0;
                entity.hurt(src,1000);
                if (player instanceof ServerPlayer serverPlayer) {
                    player.getCapability(PECapabilities.KNOWLEDGE_CAPABILITY).ifPresent(knowledge -> {
                        BigInteger currentEMC = knowledge.getEmc();
                        BigInteger emcToAdd = new BigInteger("1000");
                        BigInteger newemc = currentEMC.add(emcToAdd);
                        knowledge.setEmc(newemc);
                        knowledge.syncEmc(serverPlayer);
                    });
                }
            }
        }
    }
}
