package net.robert.soulland.event;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.robert.soulland.SoulLand;
import net.robert.soulland.key.ModKeyBinds;
import net.robert.soulland.network.NetworkHandler;
import net.robert.soulland.network.PlayerDataSyncPacket;
import net.robert.soulland.stat.DataCache;

import java.util.ArrayList;
import java.util.List;

public class ModClientEvents {

    public static void register() {
        SoulLand.LOGGER.info("Registering Mod Client Events.");
    }

    @Mod.EventBusSubscriber(modid = "soulland", bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
    public static class ClientEventsHandler {
        @SubscribeEvent
        public static void switchSoulSpiritHandling(TickEvent.ClientTickEvent event) {
            if (event.phase == TickEvent.ClientTickEvent.Phase.END) {
                while (ModKeyBinds.SWITCH_SOUL_SPIRIT_MAPPING.get().consumeClick()) {
                    System.out.println("Clicked");
                    if (!DataCache.clientPlayerData.soulRingYears.isEmpty()) {
                        List<String> soulSpirits = new ArrayList<>(DataCache.clientPlayerData.soulRingYears.keySet());
                        if (DataCache.clientPlayerData.openedSoulSpirit.equals("null")) {
                            DataCache.clientPlayerData.openedSoulSpirit = soulSpirits.get(0);
                        } else if (soulSpirits.get(soulSpirits.size() - 1).equals(DataCache.clientPlayerData.openedSoulSpirit)) {
                            DataCache.clientPlayerData.openedSoulSpirit = "null";
                        } else {
                            int nextIndex = soulSpirits.indexOf(DataCache.clientPlayerData.openedSoulSpirit) + 1;
                            DataCache.clientPlayerData.openedSoulSpirit = soulSpirits.get(nextIndex);
                        }
                        assert Minecraft.getInstance().level != null;
                        DataCache.clientPlayerData.showedTick = Minecraft.getInstance().level.getGameTime();
                        syncPlayerData();
                        Component soulSpiritName = Component.translatable("soulland.soul_spirit."+DataCache.clientPlayerData.openedSoulSpirit);
                        Component message = Component.translatable("soulland.message.switch_soul_spirit").append(soulSpiritName);
                        Minecraft.getInstance().gui.setOverlayMessage(message, false);
                    }
                }
            }
        }
    }

    public static void syncPlayerData() {
        NetworkHandler.INSTANCE.sendToServer(new PlayerDataSyncPacket(DataCache.clientPlayerData));
    }
}
