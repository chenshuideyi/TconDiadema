package com.csdy.tcondiadema.diadema.warden;

import com.csdy.tcondiadema.network.VisualChannel;
import com.csdy.tcondiadema.network.packets.SonicBoomPacket;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.phys.Vec3;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.PacketDistributor;

public class SonicBoomUtil {

    public static void performSonicBoom(Level level, LivingEntity target, Entity holder) {
        if (target == null) return;
        if (holder == null) return;

        target.hurt(new DamageSource(target.level().registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypes.SONIC_BOOM)), 30);

        Vec3 knockbackDirection = target.position().subtract(target.position()).normalize();
        target.knockback(1.0F, knockbackDirection.x, knockbackDirection.z);

        level.playSound(null, target.getX(), target.getY(), target.getZ(),
                SoundEvents.WARDEN_SONIC_BOOM, SoundSource.HOSTILE, 1.0F, 1.0F);

        VisualChannel.CHANNEL.send(PacketDistributor.NEAR.with(()->new PacketDistributor.TargetPoint(holder.getX(), holder.getY(), holder.getZ(), 128,
                        holder.level.dimension()))
                ,new SonicBoomPacket(holder.position,target.position));

    }
}

