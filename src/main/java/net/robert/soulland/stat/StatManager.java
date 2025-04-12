package net.robert.soulland.stat;

import net.minecraft.server.MinecraftServer;
import net.robert.soulland.SoulLand;

public class StatManager {
    public static SoulLandData getData(MinecraftServer server) {
        return server.overworld().getDataStorage().computeIfAbsent(
                SoulLandData::new,
                SoulLandData::new,
                SoulLand.MOD_ID
        );
    }
}
