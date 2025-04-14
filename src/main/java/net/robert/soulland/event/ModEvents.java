package net.robert.soulland.event;

import net.minecraftforge.eventbus.api.IEventBus;

public class ModEvents {
    public static void register(IEventBus eventBus) {
        ModMiscEvents.register();
        ModServerEvents.register();
        ModClientEvents.register();
    }
}
