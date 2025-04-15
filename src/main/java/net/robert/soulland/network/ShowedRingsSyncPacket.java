package net.robert.soulland.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.server.ServerLifecycleHooks;
import net.robert.soulland.stat.DataCache;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class ShowedRingsSyncPacket {
    private Player player;
    private long showedTick = 0L;
    private List<Double> years = new ArrayList<>();

    public ShowedRingsSyncPacket(List<Double> years, long showedTick, Player player) {
        this.years = years;
        this.player = player;
        this.showedTick = showedTick;
    }

    public ShowedRingsSyncPacket(FriendlyByteBuf buf) {
        int n = buf.readInt();
        for (int i = 0; i < n; i++) {
            years.add(buf.readDouble());
        }
        MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
        if (server == null) return;
        player =  server.getPlayerList().getPlayer(buf.readUUID());
        showedTick = buf.readLong();
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(years.size());
        years.forEach(buf::writeDouble);
        buf.writeUUID(player.getUUID());
        buf.writeLong(showedTick);
    }

    public static void handle(ShowedRingsSyncPacket packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            if (ctx.get().getDirection() == NetworkDirection.PLAY_TO_CLIENT) {  // When Client received the packet
                System.out.println("Client Received the packet \n~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
                DataCache.setPlayerShowedRings(packet.player, packet.years);
                DataCache.setPlayerShowedTick(packet.player, packet.showedTick);
                System.out.println(DataCache.getPlayerShowedRings(packet.player));
                System.out.println(DataCache.getPlayerShowedTick(packet.player));
                System.out.println();
            } else if (ctx.get().getDirection() == NetworkDirection.PLAY_TO_SERVER) {
                System.out.println("Server Received the request \n~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
                List<Double> years = DataCache.globalData.getPlayerData(packet.player).getShowingSoulRing();
                long tick = DataCache.globalData.getPlayerData(packet.player).showedTick;
                DataCache.returnYearsData2Client(packet.player, years, tick);
                System.out.println(years);
                System.out.println(tick);
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
