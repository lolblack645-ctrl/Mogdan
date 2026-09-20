package com.liminar.ui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import java.util.ArrayList;
import java.util.List;

public class HudRenderer {

    // Класс для хранения информации об ивенте
    public static class FuntimeEvent {
        public String name;
        public String anarchyServer;
        public int secondsLeft;

        public FuntimeEvent(String name, String anarchyServer, int secondsLeft) {
            this.name = name;
            this.anarchyServer = anarchyServer;
            this.secondsLeft = secondsLeft;
        }
    }

    public static void renderHud(DrawContext context, float tickDelta) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null || client.world == null) return;

        // 1. Отрисовка ватермарка-капсулы по центру сверху
        renderWatermark(context, client);

        // 2. Отрисовка виджета ивентов Funtime (например, слева сверху или под ватермарком)
        renderFuntimeEvents(context, client);
    }

    private static void renderWatermark(DrawContext context, MinecraftClient client) {
        String playerName = client.player.getName().getString();
        int fps = client.getCurrentFps();
        int ping = 0;
        if (client.getNetworkHandler() != null && client.getNetworkHandler().getPlayerListEntry(client.player.getUuid()) != null) {
            ping = client.getNetworkHandler().getPlayerListEntry(client.player.getUuid()).getLatency();
        }

        String waterMarkText = "👤 " + playerName + "  |  📡 " + ping + "ms  |  ⚡ " + fps + " FPS";

        int screenWidth = client.getWindow().getScaledWidth();
        int textWidth = client.textRenderer.getWidth(waterMarkText);
        int x = (screenWidth - textWidth) / 2;
        int y = 10;

        context.fill(x - 6, y - 4, x + textWidth + 6, y + 12, 0x90000000);
        context.drawText(client.textRenderer, waterMarkText, x, y, 0xFFFFFFFF, true);
    }

    private static void renderFuntimeEvents(DrawContext context, MinecraftClient client) {
        // Список активных/ближайших ивентов (в будущем сюда подключим парсинг или апи)
        List<FuntimeEvent> events = new ArrayList<>();
        events.add(new FuntimeEvent("Дроп-пакет", "Анархия-1", 145)); // 2 минуты 25 секунд
        events.add(new FuntimeEvent("Босс", "Анархия-4", 320));     // 5 минут 20 секунд

        int startX = 10;
        int startY = 30;

        // Заголовок виджета
        context.drawText(client.textRenderer, "§lFuntime Events:", startX, startY, 0xFFFF5555, true);
        startY += 12;

        for (FuntimeEvent event : events) {
            int minutes = event.secondsLeft / 60;
            int seconds = event.secondsLeft % 60;
            String timeFormatted = String.format("%d:%02d", minutes, seconds);

            String eventText = "§f" + event.name + " §7(" + event.anarchyServer + ") §e-> " + timeFormatted;

            // Полупрозрачный фон для строчки ивента
            int textWidth = client.textRenderer.getWidth(eventText);
            context.fill(startX - 2, startY - 2, startX + textWidth + 4, startY + 10, 0x70000000);
            context.drawText(client.textRenderer, eventText, startX, startY, 0xFFFFFFFF, true);

            startY += 14;
        }
    }
}

