package com.staqclient;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;
import com.staqclient.ui.ClickGuiScreen; // Убедись, что импортирован правильный экран

public class StaqClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player != null) {
                long window = client.getWindow().getHandle();
                if (InputUtil.isKeyPressed(window, GLFW.GLFW_KEY_RIGHT_SHIFT)) {
                    if (client.currentScreen == null) {
                        client.setScreen(new ClickGuiScreen());
                    }
                }
            }
        });
    }
}

