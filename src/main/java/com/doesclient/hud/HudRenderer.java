package com.doesclient.hud;

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;

public class HudRenderer implements HudRenderCallback {

    @Override
    public void onHudRender(DrawContext context, RenderTickCounter tickCounter) {
        MinecraftClient client = MinecraftClient.getInstance();
        
        if (client != null && !client.options.hudHidden && client.player != null) {
            TextRenderer textRenderer = client.textRenderer;
            
            // Получаем координаты игрока
            int x = (int) client.player.getX();
            int y = (int) client.player.getY();
            int z = (int) client.player.getZ();
            
            // Строки в стиле System DLC
            String clientName = "§bDoesClient §7[1.21.8]";
            String fpsText = "§fFPS: §a" + client.getCurrentFps();
            String coordsText = "§fXYZ: §7" + x + ", " + y + ", " + z;
            
            // Рисуем элементы друг под другом в левом верхнем углу
            int startX = 4;
            int startY = 4;
            int spacing = 10;
            
            context.drawText(textRenderer, clientName, startX, startY, 0xFFFFFFFF, true);
            context.drawText(textRenderer, fpsText, startX, startY + spacing, 0xFFFFFFFF, true);
            context.drawText(textRenderer, coordsText, startX, startY + (spacing * 2), 0xFFFFFFFF, true);
        }
    }
}
