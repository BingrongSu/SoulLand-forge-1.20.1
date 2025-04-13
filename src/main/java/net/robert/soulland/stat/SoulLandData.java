package net.robert.soulland.stat;

import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraftforge.network.PacketDistributor;
import net.robert.soulland.network.NetworkHandler;
import net.robert.soulland.network.PlayerDataSyncPacket;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SoulLandData extends SavedData {
    public Map<UUID, PlayerData> playersData = new HashMap<>();

    public SoulLandData() {
    }

    public SoulLandData(CompoundTag nbt) {
        load(nbt);
    }

    public void load(CompoundTag nbt) {
        CompoundTag playersDataNbt = nbt.getCompound("playersData");
        playersDataNbt.getAllKeys().forEach((key) ->
                playersData.put(UUID.fromString(key), new PlayerData(playersDataNbt.getCompound(key))));
    }

    @Override
    public CompoundTag save(CompoundTag nbt) {
        CompoundTag playersDataNbt = new CompoundTag();
        for (PlayerData playerData : playersData.values()) {
            playersDataNbt.put(playerData.playerUuid.toString(), playerData.toNbtCompound());
        }
        nbt.put("playersData", playersDataNbt);
        // Save the playersData.

        return nbt;
    }

    public PlayerData getPlayerData(Player player) {
        player.sendSystemMessage(Component.literal("Exist before: %b".formatted(playersData.containsKey(player.getUUID()))));
        return getPlayerData(player.getUUID());
    }

    public PlayerData getPlayerData(UUID uuid) {
        return playersData.getOrDefault(uuid, new PlayerData(uuid));
    }

    public void addSoulPower(Player player, double amount) {
        addSoulPower(player.getUUID(), amount);
    }

    public void addSoulPower(UUID uuid, double amount) {
        System.out.printf("Add Soul Power by %.2f.\n", amount);
        double n = playersData.get(uuid).soulPower + amount;
        playersData.get(uuid).setSoulPower(n);
        PlayerData playerData = playersData.get(uuid);
        syncPlayerData(playerData.getPlayer(), playerData);
        setDirty();
    }

    private void syncPlayerData(ServerPlayer player, PlayerData playerData) {
        NetworkHandler.INSTANCE.send(
                PacketDistributor.PLAYER.with(() -> player), new PlayerDataSyncPacket(playerData)
        );
    }
}
