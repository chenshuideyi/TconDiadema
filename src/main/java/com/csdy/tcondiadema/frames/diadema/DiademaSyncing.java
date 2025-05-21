package com.csdy.tcondiadema.frames.diadema;


import com.csdy.tcondiadema.TconDiadema;
import com.csdy.tcondiadema.frames.CsdyRegistries;
import com.csdy.tcondiadema.frames.diadema.packets.DiademaCreatedPacket;
import com.csdy.tcondiadema.frames.diadema.packets.DiademaRemovedPacket;
import com.csdy.tcondiadema.frames.diadema.packets.DiademaUpdatePacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

/// 用于处理双端同步的工具类，一般情况下除了初始化以外应该完全不需要和这里交互。
@Mod.EventBusSubscriber(modid = TconDiadema.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class DiademaSyncing {
    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(TconDiadema.MODID, "diadema"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );


    public static void Init() {
        DistExecutor.unsafeRunWhenOn(Dist.DEDICATED_SERVER, () -> () -> {
            int packetId = 0;
            CHANNEL.registerMessage(
                    packetId++,
                    DiademaCreatedPacket.class,
                    DiademaCreatedPacket::encode,
                    DiademaCreatedPacket::decode,
                    (o1, o2) -> {},
                    Optional.of(NetworkDirection.PLAY_TO_CLIENT)
            );
            CHANNEL.registerMessage(
                    packetId++,
                    DiademaRemovedPacket.class,
                    DiademaRemovedPacket::encode,
                    DiademaRemovedPacket::decode,
                    (o1, o2) -> {},
                    Optional.of(NetworkDirection.PLAY_TO_CLIENT)
            );
            CHANNEL.registerMessage(
                    packetId++,
                    DiademaUpdatePacket.class,
                    DiademaUpdatePacket::encode,
                    DiademaUpdatePacket::decode,
                    (o1, o2) -> {},
                    Optional.of(NetworkDirection.PLAY_TO_CLIENT)
            );
        });

        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
            int packetId = 0;
            CHANNEL.registerMessage(
                    packetId++,
                    DiademaCreatedPacket.class,
                    DiademaCreatedPacket::encode,
                    DiademaCreatedPacket::decode,
                    DiademaSyncing::handleCreation,
                    Optional.of(NetworkDirection.PLAY_TO_CLIENT)
            );
            CHANNEL.registerMessage(
                    packetId++,
                    DiademaRemovedPacket.class,
                    DiademaRemovedPacket::encode,
                    DiademaRemovedPacket::decode,
                    DiademaSyncing::handleRemoval,
                    Optional.of(NetworkDirection.PLAY_TO_CLIENT)
            );
            CHANNEL.registerMessage(
                    packetId++,
                    DiademaUpdatePacket.class,
                    DiademaUpdatePacket::encode,
                    DiademaUpdatePacket::decode,
                    DiademaSyncing::handleUpdate,
                    Optional.of(NetworkDirection.PLAY_TO_CLIENT)
            );
        });
    }


    // sync logic
    private static final Map<Long, ClientDiadema> instances = new HashMap<>();

    @OnlyIn(Dist.CLIENT)
    public static void handleCreation(DiademaCreatedPacket packet, Supplier<NetworkEvent.Context> network) {
        network.get().enqueueWork(() -> {
            var type = CsdyRegistries.CLIENT_DIADEMA_TYPES_REG.get().getValue(packet.diadema());
            if (type == null) return;
            instances.put(packet.instanceId(), type.CreateClientInstance());
        });
        network.get().setPacketHandled(true);
    }

    @OnlyIn(Dist.CLIENT)
    private static void handleRemoval(DiademaRemovedPacket packet, Supplier<NetworkEvent.Context> network) {
        network.get().enqueueWork(() -> {
            var instance = instances.remove(packet.instanceId());
            if (instance != null) instance.remove();
        });
        network.get().setPacketHandled(true);
    }

    @OnlyIn(Dist.CLIENT)
    private static void handleUpdate(DiademaUpdatePacket packet, Supplier<NetworkEvent.Context> network) {
        network.get().enqueueWork(() -> {
            var instance = instances.getOrDefault(packet.instanceId(), null);
            if (instance != null) instance.update(packet);
        });
        network.get().setPacketHandled(true);
    }


    // event handlers
    @SubscribeEvent @OnlyIn(Dist.CLIENT)
    public static void onClientLoggingOut(ClientPlayerNetworkEvent.LoggingOut e) {
        instances.values().forEach(ClientDiadema::remove);
        instances.clear();
    }
}
