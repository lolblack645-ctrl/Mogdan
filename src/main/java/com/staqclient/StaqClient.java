package com.staqclient;

import com.staqclient.ui.ClickGuiScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class StaqClient implements ClientModInitializer {
    public static KeyBinding clickGuiKeyBinding;

    @Override
    public void onInitializeClient() {
        // Регистрируем клавишу Right Shift для открытия меню
        clickGuiKeyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.staqclient.clickgui",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_RIGHT_SHIFT,
                "category.staqclient.general"
        ));

        // Слушаем нажатия в игре
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (clickGuiKeyBinding.wasPressed()) {
                client.setScreen(new ClickGuiScreen());
            }
        });
    }
}

