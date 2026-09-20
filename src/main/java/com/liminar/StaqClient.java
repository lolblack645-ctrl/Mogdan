package com.liminar;

import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StaqClient implements ClientModInitializer {
    public static final String MOD_ID = "staqclient";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitializeClient() {
        LOGGER.info("Staq Client initialized successfully! Liminar-style HUD & GUI loaded.");
        // Здесь в будущем будем инициализировать менеджеры модулей, бинды и ClickGUI
    }
}
