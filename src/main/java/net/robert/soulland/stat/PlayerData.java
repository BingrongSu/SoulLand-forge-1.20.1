package net.robert.soulland.stat;

import net.minecraft.nbt.CompoundTag;

import java.util.UUID;

public class PlayerData {
    public UUID playerUuid;
    public double maxSoulPower;
    public double soulPower;
    public double maxSpiritPower;
    public double spiritPower;

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
}
