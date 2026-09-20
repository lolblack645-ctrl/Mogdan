package com.staqclient;

import com.staqclient.ui.ClickGuiScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public class StaqClient implements ClientModInitializer {
    public static KeyBinding clickGuiKeyBinding;

    @Override
    public void onInitializeClient() {
        clickGuiKeyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.staqclient.clickgui",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_P,
                "category.staqclient.general"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (clickGuiKeyBinding.wasPressed()) {
                if (client.player != null) {
                    client.player.sendMessage(Text.literal("§a[StaqClient] Key pressed! Opening GUI..."), false);
                }
                client.setScreen(new ClickGuiScreen());
            }
        });
    }
}

