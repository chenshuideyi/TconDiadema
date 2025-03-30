package com.csdy.tcondiadema.network;


import com.csdy.tcondiadema.ModMain;
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

@Mod.EventBusSubscriber(modid = ModMain.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ParticleSyncing {
    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(ModMain.MODID, "particle"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );


    public static void Init() {
        DistExecutor.unsafeRunWhenOn(Dist.DEDICATED_SERVER, () -> () -> {
            int packetId = 0;
            CHANNEL.registerMessage(
                    packetId++,
                    SonicBoomPacket.class,
                    SonicBoomPacket::encode,
                    SonicBoomPacket::decode,
                    (o1, o2) -> {},
                    Optional.of(NetworkDirection.PLAY_TO_CLIENT)
            );
            CHANNEL.registerMessage(
                    packetId++,
                    AvaritaPacket.class,
                    AvaritaPacket::encode,
                    AvaritaPacket::decode,
                    (o1, o2) -> {},
                    Optional.of(NetworkDirection.PLAY_TO_CLIENT)
            );
        });

        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
            int packetId = 0;
            CHANNEL.registerMessage(
                    packetId++,
                    SonicBoomPacket.class,
                    SonicBoomPacket::encode,
                    SonicBoomPacket::decode,
                    SonicBoomPacket::handle,
                    Optional.of(NetworkDirection.PLAY_TO_CLIENT)
            );
            CHANNEL.registerMessage(
                    packetId++,
                    AvaritaPacket.class,
                    AvaritaPacket::encode,
                    AvaritaPacket::decode,
                    AvaritaPacket::handle,
                    Optional.of(NetworkDirection.PLAY_TO_CLIENT)
            );
        });
    }
}
