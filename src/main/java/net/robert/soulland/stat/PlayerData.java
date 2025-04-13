package net.robert.soulland.stat;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.server.ServerLifecycleHooks;

import java.util.UUID;

public class PlayerData {
    public UUID playerUuid;
    public double maxSoulPower;
    public double soulPower;
    public double maxSpiritPower;
    public double spiritPower;

    public PlayerData(UUID uuid) {
        playerUuid = uuid;
        maxSoulPower = 0d;
        soulPower = 0d;
        maxSpiritPower = 0d;
        spiritPower = 0d;
    }

    public PlayerData(CompoundTag nbt) {
        playerUuid = nbt.getUUID("playerUuid");
        maxSoulPower = nbt.getDouble("maxSoulPower");
        soulPower = nbt.getDouble("soulPower");
        maxSpiritPower = nbt.getDouble("maxSpiritPower");
        spiritPower = nbt.getDouble("spiritPower");
    }

    public CompoundTag toNbtCompound() {
        CompoundTag nbt = new CompoundTag();
        nbt.putUUID("playerUuid", playerUuid);
        nbt.putDouble("maxSoulPower", maxSoulPower);
        nbt.putDouble("soulPower", soulPower);
        nbt.putDouble("maxSpiritPower", maxSpiritPower);
        nbt.putDouble("spiritPower", spiritPower);
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
}
