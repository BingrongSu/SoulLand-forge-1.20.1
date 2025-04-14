package net.robert.soulland.key;

import com.google.common.base.Suppliers;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.robert.soulland.SoulLand;
import org.lwjgl.glfw.GLFW;

import java.util.function.Supplier;


public class ModKeyBinds {
    public static final Supplier<KeyMapping> SWITCH_SOUL_SPIRIT_MAPPING = Suppliers.memoize(() -> new KeyMapping(
            "key.soulland.switch_soul_spirit",
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_X,
            "key.categories.soulland"
    ));

    @Mod.EventBusSubscriber(modid = SoulLand.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class RegisterKeyMapping {
        @SubscribeEvent
        public static void onRegisterKeyMappings(RegisterKeyMappingsEvent event) {
            event.register(SWITCH_SOUL_SPIRIT_MAPPING.get());
            System.out.println("~~~~~~~~~~~~\nRegistering key mapping\n~~~~~~~~~~~~~~~~~~~~~\n\n");
        }
    }
}
