package net.robert.soulland.network;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import net.robert.soulland.SoulLand;

public class NetworkHandler {
    private static final String PROTOCOL_VERSION = "1.0";
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(SoulLand.MOD_ID, "main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    private static int packetId = 0;

    public static void register() {
        // 注册服务端->客户端的数据包（ID 0）
        INSTANCE.registerMessage(packetId++, PlayerDataSyncPacket.class,
                PlayerDataSyncPacket::encode,
                PlayerDataSyncPacket::new,
                PlayerDataSyncPacket::handle);

        // 注册客户端->服务端的数据包（ID 1）
//        INSTANCE.registerMessage(packetId++, ClientToServerPacket.class,
//                ClientToServerPacket::encode,
//                ClientToServerPacket::new,
//                ClientToServerPacket::handle);
    }
}
