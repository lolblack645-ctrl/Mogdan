package com.staqclient;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;
import com.staqclient.bot.BotsScreen;

public class StaqClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // Регистрация открытия меню ботов по нажатию правого Shift
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player != null && client.currentScreen == null) {
                long window = client.getWindow().getHandle();
                if (InputUtil.isKeyPressed(window, GLFW.GLFW_KEY_RIGHT_SHIFT)) {
                    client.setScreen(new BotsScreen());
                }
            }
        });
    }
}

