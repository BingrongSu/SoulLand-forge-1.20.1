package net.robert.soulland.stat;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.server.ServerLifecycleHooks;

import java.util.*;

public class PlayerData {
    public UUID playerUuid;
    public double maxSoulPower;
    public double soulPower;
    public double maxSpiritPower;
    public double spiritPower;
    public String openedSoulSpirit;
    public Map<String, List<Double>> soulRingYears;

    public PlayerData(UUID uuid) {
        playerUuid = uuid;
        maxSoulPower = 0d;
        soulPower = 0d;
        maxSpiritPower = 0d;
        spiritPower = 0d;
        openedSoulSpirit = "null";
        soulRingYears = new HashMap<>();
    }

    public PlayerData(CompoundTag nbt) {
        playerUuid = nbt.getUUID("playerUuid");
        maxSoulPower = nbt.getDouble("maxSoulPower");
        soulPower = nbt.getDouble("soulPower");
        maxSpiritPower = nbt.getDouble("maxSpiritPower");
        spiritPower = nbt.getDouble("spiritPower");

        openedSoulSpirit = nbt.getString("openedSoulSpirit");
        soulRingYears = new HashMap<>();
        CompoundTag yearsNbt = nbt.getCompound("soulRingYears");
        if (!yearsNbt.getAllKeys().isEmpty())
            yearsNbt.getAllKeys().forEach(key -> {
                CompoundTag yearListNbt = yearsNbt.getCompound(key);
                List<Double> yearList = new ArrayList<>();
                for (int i = 0; yearListNbt.contains(""+i); i++) {
                    yearList.add(yearListNbt.getDouble(""+i));
                }
                soulRingYears.put(key, yearList);
            });
    }

    public CompoundTag toNbtCompound() {
        CompoundTag nbt = new CompoundTag();
        nbt.putUUID("playerUuid", playerUuid);
        nbt.putDouble("maxSoulPower", maxSoulPower);
        nbt.putDouble("soulPower", soulPower);
        nbt.putDouble("maxSpiritPower", maxSpiritPower);
        nbt.putDouble("spiritPower", spiritPower);
        nbt.putString("openedSoulSpirit", openedSoulSpirit);

        CompoundTag yearsNbt = new CompoundTag();
        if (!soulRingYears.isEmpty())
            soulRingYears.forEach((name, list) -> {
                CompoundTag yearListNbt = new CompoundTag();
                for (int i = 0; i < list.size(); i++) {
                    yearListNbt.putDouble(""+i, list.get(i));
                }
                yearsNbt.put(name, yearListNbt);
            });
        nbt.put("soulRingYears", yearsNbt);

        return nbt;
    }

    public ServerPlayer getPlayer() {
        MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
        if (server == null) return null;
        return server.getPlayerList().getPlayer(playerUuid);
    }

    public void setSoulPower(double soulPower) {
        this.soulPower = soulPower;
        System.out.printf("Soul Power set to %.2f\n", this.soulPower);
    }

    public void setMaxSoulPower(double maxSoulPower) {
        this.maxSoulPower = maxSoulPower;
        System.out.printf("Max Soul Power set to %.2f\n", this.maxSoulPower);
    }
}
