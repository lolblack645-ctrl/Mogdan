package com.doesclient.event;

import com.doesclient.ui.ClientMenuScreen;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class KeyInputHandler {
    public static KeyBinding openMenuKey;

    public static void registerKeyInputs() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            // Проверяем, нажата ли наша клавиша Right Shift
            while (openMenuKey.wasPressed()) {
                // Открываем наше меню
                client.setScreen(new ClientMenuScreen());
            }
        });
    }

    public static void register() {
        // Регистрируем клавишу Right Shift (GLFW.GLFW_KEY_RIGHT_SHIFT)
        openMenuKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.doesclient.open_menu",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_RIGHT_SHIFT,
                "category.doesclient.main"
        ));

        registerKeyInputs();
    }
}
