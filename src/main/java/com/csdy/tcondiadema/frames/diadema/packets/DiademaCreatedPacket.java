package com.csdy.tcondiadema.frames.diadema.packets;

import com.csdy.tcondiadema.frames.CsdyRegistries;
import com.csdy.tcondiadema.frames.diadema.DiademaType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

import java.util.Objects;

public record DiademaCreatedPacket(ResourceLocation diadema, long instanceId) {

    public DiademaCreatedPacket(DiademaType type, long instanceId) {
        this(Objects.requireNonNull(CsdyRegistries.DIADEMA_TYPES_REG.get().getKey(type)), instanceId);
    }

    public static void encode(DiademaCreatedPacket packet, FriendlyByteBuf buf) {
        buf.writeResourceLocation(packet.diadema);
        buf.writeLong(packet.instanceId);
    }

    public static DiademaCreatedPacket decode(FriendlyByteBuf buf) {
        var type = CsdyRegistries.DIADEMA_TYPES_REG.get().getValue(buf.readResourceLocation());
        var instanceId = buf.readLong();
        return new DiademaCreatedPacket(type, instanceId);
    }
}
