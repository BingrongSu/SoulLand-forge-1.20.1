package net.robert.soulland.event;

import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.robert.soulland.SoulLand;
import net.robert.soulland.stat.DataCache;
import net.robert.soulland.stat.PlayerData;
import net.robert.soulland.stat.SoulLandData;
import net.robert.soulland.stat.StatManager;

import java.util.Objects;

public class ModServerEvents {

    public static void register() {
        SoulLand.LOGGER.info("Registering Mod Server Events.");
    }

    @Mod.EventBusSubscriber(modid = "soulland", bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class ServerEventsHandler {

        @SubscribeEvent
        public static void serverStarted(ServerStartedEvent event) {
            MinecraftServer server = event.getServer();
            DataCache.globalData = StatManager.getData(Objects.requireNonNull(server));
            SoulLand.LOGGER.info("Load Soul Land Data.");
        }

        @SubscribeEvent
        public static void playerJoined(PlayerEvent.PlayerLoggedInEvent event) {
            Player player = event.getEntity();
            player.sendSystemMessage(player.getDisplayName());
            PlayerData playerData = DataCache.globalData.getPlayerData(player);
            player.sendSystemMessage(Component.literal("Max Soul Power: %.2f".formatted(playerData.maxSoulPower)));
            player.sendSystemMessage(Component.literal("Soul Power: %.2f".formatted(playerData.soulPower)));
            player.sendSystemMessage(Component.literal("Max Spirit Power: %.2f".formatted(playerData.maxSpiritPower)));
            player.sendSystemMessage(Component.literal("Spirit Power: %.2f".formatted(playerData.spiritPower)));
            SoulLandData.syncPlayerData((ServerPlayer) player, playerData);
        }
    }
}
