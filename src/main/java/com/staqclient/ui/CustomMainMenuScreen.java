package com.staqclient.ui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.gui.screen.world.SelectWorldScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

public class CustomMainMenuScreen extends Screen {
    
    // Список сохраненных аккаунтов для Account Manager
    private static final List<String> accounts = new ArrayList<>();
    private static String currentAccount = "Player_Staq";
    private boolean isAccountManagerOpen = false;
    private String newAccountInput = "";

    static {
        if (accounts.isEmpty()) {
            accounts.add("Player_Staq");
            accounts.add("AnarchyKing_228");
            accounts.add("GriefMaster");
        }
    }

    public CustomMainMenuScreen() {
        super(Text.literal("StaqClient Main Menu"));
    }

    @Override
    protected void init() {
        super.init();
        int centerX = this.width / 2;
        int startY = this.height / 4 + 48;

        if (!isAccountManagerOpen) {
            // Кнопки главного меню
            this.addDrawableChild(ButtonWidget.builder(Text.literal("Одиночная игра"), button -> {
                assert this.client != null;
                this.client.setScreen(new SelectWorldScreen(this));
            }).dimensions(centerX - 100, startY, 200, 20).build());

            this.addDrawableChild(ButtonWidget.builder(Text.literal("Сетевая игра"), button -> {
                assert this.client != null;
                this.client.setScreen(new MultiplayerScreen(this));
            }).dimensions(centerX - 100, startY + 24, 200, 20).build());

            this.addDrawableChild(ButtonWidget.builder(Text.literal("Account Manager"), button -> {
                isAccountManagerOpen = true;
                this.clearAndInit();
            }).dimensions(centerX - 100, startY + 48, 200, 20).build());

            this.addDrawableChild(ButtonWidget.builder(Text.literal("Выход из игры"), button -> {
                assert this.client != null;
                this.client.scheduleStop();
            }).dimensions(centerX - 100, startY + 72, 200, 20).build());
        } else {
            // Кнопки внутри Account Manager
            this.addDrawableChild(ButtonWidget.builder(Text.literal("Случайный аккаунт"), button -> {
                if (!accounts.isEmpty()) {
                    int idx = (int) (Math.random() * accounts.size());
                    currentAccount = accounts.get(idx);
                }
            }).dimensions(centerX - 100, startY, 200, 20).build());

            this.addDrawableChild(ButtonWidget.builder(Text.literal("Назад"), button -> {
                isAccountManagerOpen = false;
                this.clearAndInit();
            }).dimensions(centerX - 100, startY + 50, 200, 20).build());
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        // 1. Рисуем сине-желтый фон (градиент)
        context.fillGradient(0, 0, this.width, this.height, 0xFF002244, 0xFFFFCC00);

        // 2. Имитация рисунка кота, смотрящего на город (текстово-графический арт или заготовка)
        context.drawText(this.textRenderer, "(=^..^=) [ Кот смотрит на ночной город ]", 20, 20, 0xFFFFFFFF, true);
        
        // Рисуем силуэт города внизу экрана
        context.fill(0, this.height - 60, this.width, this.height, 0xFF111111);
        context.fill(50, this.height - 120, 90, this.height - 60, 0xFF222222);
        context.fill(120, this.height - 150, 180, this.height - 60, 0xFF222222);
        context.fill(this.width - 150, this.height - 140, this.width - 100, this.height - 60, 0xFF222222);

        // Если открыт Account Manager, показываем текущий аккаунт
        if (isAccountManagerOpen) {
            context.drawCenteredTextWithShadow(this.textRenderer, "Текущий аккаунт: " + currentAccount, this.width / 2, this.height / 4, 0xFF55FF55);
            
            int offsetY = this.height / 4 + 85;
            context.drawText(this.textRenderer, "Доступные аккаунты:", this.width / 2 - 100, offsetY, 0xFFFFFF00, true);
            offsetY += 12;
            for (String acc : accounts) {
                String prefix = acc.equals(currentAccount) ? "§a> " : "§7- ";
                context.drawText(this.textRenderer, prefix + acc, this.width / 2 - 100, offsetY, 0xFFFFFFFF, false);
                offsetY += 10;
            }
        } else {
            // Ватермарка на главном меню
            context.drawText(this.textRenderer, "StaqClient v1.0 - Fabric 1.21.4", 10, this.height - 20, 0xFFFFFFFF, true);
        }

        super.render(context, mouseX, mouseY, delta);
    }
}
