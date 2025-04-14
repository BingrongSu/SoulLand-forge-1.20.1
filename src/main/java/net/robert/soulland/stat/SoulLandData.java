package net.robert.soulland.stat;

import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraftforge.network.PacketDistributor;
import net.robert.soulland.helper.MathHelper;
import net.robert.soulland.network.NetworkHandler;
import net.robert.soulland.network.PlayerDataSyncPacket;

import java.util.ArrayList;
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
        if (!playersData.containsKey(uuid)) {
            playersData.put(uuid, new PlayerData(uuid));
        }
        return playersData.get(uuid);
    }

    public void setPlayerData(Player player, PlayerData playerData) {
        setPlayerData(player.getUUID(), playerData);
    }

    public void setPlayerData(UUID uuid, PlayerData playerData) {
        playersData.get(uuid).load(playerData.toNbtCompound());
        setDirty();
    }

    public void addSoulPower(Player player, double amount) {
        addSoulPower(player.getUUID(), amount);
    }

    public void addSoulPower(UUID uuid, double amount) {
        System.out.printf("Add Soul Power by %.2f.\n", amount);
        double n = Math.min(getPlayerData(uuid).soulPower + amount, getPlayerData(uuid).maxSoulPower);
        playersData.get(uuid).setSoulPower(n);
        PlayerData playerData = playersData.get(uuid);
        syncPlayerData(playerData.getPlayer(), playerData);
        playerData.getPlayer().sendSystemMessage(Component.literal("Current soul power: %.2f".formatted(n)));
        setDirty();
    }

    public void addMaxSoulPower(Player player, double amount) {
        addMaxSoulPower(player.getUUID(), amount);
    }

    public void addMaxSoulPower(UUID uuid, double amount) {
        System.out.printf("Add Max Soul Power by %.2f.\n", amount);
        double n = getPlayerData(uuid).maxSoulPower + amount;
        playersData.get(uuid).setMaxSoulPower(n);
        PlayerData playerData = playersData.get(uuid);
        syncPlayerData(playerData.getPlayer(), playerData);
        playerData.getPlayer().sendSystemMessage(Component.literal("Current max soul power: %.2f".formatted(n)));
        setDirty();
    }

    public void syncPlayerData(ServerPlayer player) {
        PlayerData playerData = playersData.get(player.getUUID());
        syncPlayerData(player, playerData);
    }

    public static void syncPlayerData(ServerPlayer player, PlayerData playerData) {
        NetworkHandler.INSTANCE.send(
                PacketDistributor.PLAYER.with(() -> player), new PlayerDataSyncPacket(playerData)
        );
    }

    public void appendSoulSpirit(ServerPlayer player, String soulSpirit) {
        if (!getPlayerData(player).soulRingYears.containsKey(soulSpirit)) {
            playersData.get(player.getUUID()).soulRingYears.put(soulSpirit, new ArrayList<>());
            player.sendSystemMessage(Component.literal("Gain Soul Spirit: " + soulSpirit + "!"));
            setDirty();
            syncPlayerData(player);
        } else {
            player.sendSystemMessage(Component.literal("Already have that soul spirit!"));
        }
    }

    public void awaken(ServerPlayer player) {
        UUID uuid = player.getUUID();
        assert Minecraft.getInstance().level != null;
        int initialLevel = MathHelper.getInitialLevel(Minecraft.getInstance().level.getGameTime());
        double initialSoulPower = MathHelper.level2SoulPower(initialLevel);
        addMaxSoulPower(uuid, initialSoulPower);
        addSoulPower(uuid, initialSoulPower);
        player.sendSystemMessage(Component.literal("Awaken!"));
        player.sendSystemMessage(Component.literal("Initial soul power level: " + initialLevel));
        player.sendSystemMessage(Component.literal("Initial soul power: " + initialSoulPower));
    }

    // TODO 武魂觉醒相关：觉醒台，水晶球、武魂觉醒事件;
    // TODO 添加武魂：九宝琉璃塔；武魂切换
    // TODO 魂力升级瓶颈设置（每十级）
    // TODO 添加魂环Item，魂环显示及同步
    // TODO 吸收魂环后升级、增加更多魂力
    // TODO 觉醒武魂后获得成就：千分之一 -- 据说，不到千分之一的人觉醒武魂后拥有魂力；百年难遇 -- ；天选之子，
    // TODO 吸收魂环成就
}
