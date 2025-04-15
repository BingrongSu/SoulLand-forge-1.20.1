package net.robert.soulland.network;

import net.minecraft.client.Minecraft;
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
    private boolean isRequest;

    public ShowedRingsSyncPacket(List<Double> years, long showedTick, Player player, boolean isRequest) {
        this.years = years;
        this.player = player;
        this.showedTick = showedTick;
        this.isRequest = isRequest;
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
        isRequest = buf.readBoolean();
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(years.size());
        years.forEach(buf::writeDouble);
        buf.writeUUID(player.getUUID());
        buf.writeLong(showedTick);
        buf.writeBoolean(isRequest);
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
                if (packet.isRequest) {
                    System.out.println("Server Received the request \n~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                    List<Double> years = DataCache.globalData.getPlayerData(packet.player).getShowingSoulRing();
                    long tick = DataCache.globalData.getPlayerData(packet.player).showedTick;
                    DataCache.returnYearsData2Client(ctx.get().getSender(), packet.player, years, tick);
                    System.out.println(years);
                    System.out.println(tick);
                } else {
                    System.out.println("Server Received the change \n~~~~~~~~~~~~~~~~~~~~~~~~~~~");

//                    assert Minecraft.getInstance().level != null;
                    for (Player player1 : ctx.get().getSender().level().players()) {
                        DataCache.returnYearsData2Client(player1, packet.player, packet.years, packet.showedTick);
                    }
                    System.out.println(packet.player);
                    System.out.println(packet.years);
                    System.out.println(packet.showedTick);
                }
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
