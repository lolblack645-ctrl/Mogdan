package com.doesclient;

import com.doesclient.event.KeyInputHandler;
import com.doesclient.hud.HudRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DoesClient implements ClientModInitializer {
    public static final String MOD_ID = "doesclient";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitializeClient() {
        LOGGER.info("DoesClient successfully initialized!");
        
        // Регистрируем HUD
        HudRenderCallback.EVENT.register(new HudRenderer());
        
        // Регистрируем клавишу открытия меню (Right Shift)
        KeyInputHandler.register();
    }
}
