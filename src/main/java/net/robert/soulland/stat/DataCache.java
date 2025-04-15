package net.robert.soulland.stat;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.PacketDistributor;
import net.robert.soulland.network.NetworkHandler;
import net.robert.soulland.network.ShowedRingsSyncPacket;

import java.util.*;

public class DataCache {
    public static SoulLandData globalData;      // Server use only
    public static PlayerData clientPlayerData;  // Client use only
    public static Map<UUID, List<Double>> clientPlayersShowedRings = new HashMap<>();   // Client use only. Use to show the soul ring of player.
    public static Map<UUID, Long> clientPlayersShowedTicks = new HashMap<>();

    public static List<Double> getPlayerShowedRings(Player player) {
        return clientPlayersShowedRings.getOrDefault(player.getUUID(), new ArrayList<>());
    }

    public static void setPlayerShowedRings(Player player, List<Double> years) {
        clientPlayersShowedRings.put(player.getUUID(), years);
    }

    public static void setPlayerShowedTick(Player player, long tick) {
        clientPlayersShowedTicks.put(player.getUUID(), tick);
    }

    public static long getPlayerShowedTick(Player player) {
        return clientPlayersShowedTicks.getOrDefault(player.getUUID(), 0L);
    }

    public static void returnYearsData2Client(Player player, List<Double> years, long showedTick) {
        NetworkHandler.INSTANCE.send(
                PacketDistributor.PLAYER.with(() -> (ServerPlayer) player), new ShowedRingsSyncPacket(years, showedTick, player)
        );
    }
}
