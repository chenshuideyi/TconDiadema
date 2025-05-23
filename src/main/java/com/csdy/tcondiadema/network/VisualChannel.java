package com.csdy.tcondiadema.network;


import com.csdy.tcondiadema.TconDiadema;
import com.csdy.tcondiadema.diadema.warden.WardenBlindnessEffect;
import com.csdy.tcondiadema.network.packets.AvaritaPacket;
import com.csdy.tcondiadema.network.packets.SonicBoomPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.Optional;

@Mod.EventBusSubscriber(modid = TconDiadema.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class VisualChannel {
    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(TconDiadema.MODID, "visual"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    private static int packetId = 0;

    private static int nextId() {
        return packetId++;
    }

    public static void Init() {
        // 分端注册避免炸服务端
        DistExecutor.unsafeRunWhenOn(Dist.DEDICATED_SERVER, () -> () -> {
            CHANNEL.registerMessage(
                    nextId(),
                    SonicBoomPacket.class,
                    SonicBoomPacket::encode,
                    SonicBoomPacket::decode,
                    (o1, o2) -> {
                    },
                    Optional.of(NetworkDirection.PLAY_TO_CLIENT)
            );
            CHANNEL.registerMessage(
                    nextId(),
                    AvaritaPacket.class,
                    AvaritaPacket::encode,
                    AvaritaPacket::decode,
                    (o1, o2) -> {
                    },
                    Optional.of(NetworkDirection.PLAY_TO_CLIENT)
            );
        });

        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
            CHANNEL.registerMessage(
                    nextId(),
                    SonicBoomPacket.class,
                    SonicBoomPacket::encode,
                    SonicBoomPacket::decode,
                    SonicBoomPacket::handle,
                    Optional.of(NetworkDirection.PLAY_TO_CLIENT)
            );
            CHANNEL.registerMessage(
                    nextId(),
                    AvaritaPacket.class,
                    AvaritaPacket::encode,
                    AvaritaPacket::decode,
                    AvaritaPacket::handle,
                    Optional.of(NetworkDirection.PLAY_TO_CLIENT)
            );
        });

        // 以下是无需分端注册的
        CHANNEL.registerMessage(
                nextId(),
                WardenBlindnessEffect.Packet.class,
                WardenBlindnessEffect.Packet::encode,
                WardenBlindnessEffect.Packet::decode,
                WardenBlindnessEffect.Packet::handle,
                Optional.of(NetworkDirection.PLAY_TO_CLIENT)
        );
    }
}
