package com.csdy.tcondiadema.frames.diadema.packets;

import io.netty.buffer.Unpooled;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;

public record DiademaUpdatePacket(long instanceId, ResourceLocation dimension, Vec3 position, byte[] customData) {
    public static void encode(DiademaUpdatePacket packet, FriendlyByteBuf buf) {
        buf.writeLong(packet.instanceId);
        buf.writeResourceLocation(packet.dimension);
        buf.writeDouble(packet.position.x);
        buf.writeDouble(packet.position.y);
        buf.writeDouble(packet.position.z);
        buf.writeBytes(packet.customData);
    }

    public static DiademaUpdatePacket decode(FriendlyByteBuf buf) {
        var instanceId = buf.readLong();
        var dimension = buf.readResourceLocation();
        var position = new Vec3(buf.readDouble(), buf.readDouble(), buf.readDouble());
        var customData = new byte[buf.readableBytes()];
        buf.getBytes(buf.readerIndex(), customData);
        return new DiademaUpdatePacket(instanceId, dimension, position, customData);
    }

    public FriendlyByteBuf getCustomData() {
        return new FriendlyByteBuf(Unpooled.wrappedBuffer(customData));
    }
}
