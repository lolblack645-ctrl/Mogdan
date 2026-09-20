package com.liminar.modules.render;

import com.liminar.modules.Module;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;

public class HUD extends Module {

    public HUD() {
        super("HUD", "Отображает интерфейс и список модулей на экране", 0);
    }

    // Этот метод мы будем вызывать из рендерера игры, чтобы рисовать элементы
    public void render(DrawContext context, float tickDelta) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.options.hudHidden || client.player == null) return;

        TextRenderer textRenderer = client.textRenderer;

        // Рисуем вотермарку клиента в левом верхнем углу
        String watermark = "Staq Client | 1.21.1 | FPS: " + client.getCurrentFps();
        context.drawText(textRenderer, watermark, 4, 4, 0xFF55FF, true);

        // Здесь в будущем можно будет выводить список активных модулей столбиком
    }
}
