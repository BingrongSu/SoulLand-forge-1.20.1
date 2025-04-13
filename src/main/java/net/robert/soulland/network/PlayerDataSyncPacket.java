package net.robert.soulland.network;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import net.robert.soulland.stat.DataCache;
import net.robert.soulland.stat.PlayerData;

import java.util.Objects;
import java.util.function.Supplier;

public class PlayerDataSyncPacket {
    private final PlayerData playerData;

    public PlayerDataSyncPacket(PlayerData playerData) {
        this.playerData = playerData;
    }

    public PlayerDataSyncPacket(FriendlyByteBuf buf) {
        this.playerData = new PlayerData(Objects.requireNonNull(buf.readNbt()));
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeNbt(playerData.toNbtCompound());
    }

    public static void handle(PlayerDataSyncPacket packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            if (ctx.get().getDirection() == NetworkDirection.PLAY_TO_CLIENT) {  // When Client received the packet
                System.out.println("Client Received the packet \n~~~~~~~~~~~~~~~~~~~~~~~~~~~\n\n");
                DataCache.clientPlayerData = packet.playerData;
                System.out.println("Client -> Soul power set to " + DataCache.clientPlayerData.soulPower);
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
