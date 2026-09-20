package com.staqclient;

import com.staqclient.ui.ClickGuiScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;

public class StaqClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // Регистрируем клиентскую команду /gui
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
            dispatcher.register(ClientCommandManager.literal("gui")
                .executes(context -> {
                    context.getSource().getClient().execute(() -> {
                        context.getSource().getClient().setScreen(new ClickGuiScreen());
                    });
                    return 1;
                })
            );
        });
    }
}

