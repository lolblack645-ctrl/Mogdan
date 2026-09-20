package com.staqclient;

import com.staqclient.manager.CommandManager;
import com.staqclient.manager.HudManager;
import com.staqclient.ui.CustomMainMenuScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.message.v1.ClientSendMessageEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.TitleScreen;
import org.lwjgl.glfw.GLFW;

public class StaqClientClient implements ClientModInitializer {
    private static boolean wasRightShiftPressed = false;

    @Override
    public void onInitializeClient() {
        System.out.println("[StaqClient] Initializing client components...");

        HudRenderCallback.EVENT.register((drawContext, tickDelta) -> {
            HudManager.renderHud(drawContext.getMatrices());
        });

        ClientSendMessageEvents.ALLOW_CHAT.register(message -> {
            if (CommandManager.handleChatCommand(message)) {
                return false;
            }
            return true;
        });

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.getWindow() == null) return;

            if (client.currentScreen instanceof TitleScreen) {
                client.setScreen(new CustomMainMenuScreen());
            }

            long window = client.getWindow().getHandle();
            boolean isPressed = GLFW.glfwGetKey(window, GLFW.GLFW_KEY_RIGHT_SHIFT) == GLFW.GLFW_PRESS;

            if (isPressed && !wasRightShiftPressed) {
                if (client.currentScreen == null) {
                    client.setScreen(new CustomMainMenuScreen());
                } else if (client.currentScreen instanceof CustomMainMenuScreen) {
                    client.setScreen(null);
                }
            }
            wasRightShiftPressed = isPressed;
        });

        System.out.println("[StaqClient] Client successfully initialized!");
    }
}
