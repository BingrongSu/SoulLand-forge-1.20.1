package net.robert.soulland.stat;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.saveddata.SavedData;

import java.util.Map;
import java.util.UUID;

public class ServerData extends SavedData {
    public Map<UUID, PlayerData> playersData;

    public ServerData() {
    }

    public ServerData(CompoundTag nbt) {
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
}
