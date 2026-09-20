package com.staqclient.manager;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;

public class HudManager {
    private static final MinecraftClient mc = MinecraftClient.getInstance();

    public static boolean showWatermark = true;  
    public static boolean showArrayList = true;  
    public static boolean showWardenStatus = true; 

    public enum HudTheme {
        GREEN("Зеленый (Neon)", 0xFF55FF55),
        PURPLE("Фиолетовый (Cyber)", 0xFFAA00FF),
        BLUE("Голубой (Ocean)", 0xFF55FFFF),
        RED("Красный (Anarchy)", 0xFFFF5555),
        GOLD("Золотой (Luxury)", 0xFFFFAA00);

        public final String name;
        public final int hexColor;

        HudTheme(String name, int hexColor) {
            this.name = name;
            this.hexColor = hexColor;
        }
    }

    public static HudTheme currentTheme = HudTheme.GREEN;

    public static void setTheme(HudTheme theme) {
        currentTheme = theme;
    }

    public static void renderHud(MatrixStack matrices) {
        if (mc.player == null || mc.world == null || mc.options.hudHidden) return;

        int x = 10;
        int y = 10;
        int accentColor = currentTheme.hexColor;

        if (showWatermark) {
            String watermarkText = "StaqClient v1.0 | 1.21.4 (Fabric)";
            renderText(matrices, watermarkText, x, y, accentColor);
            y += 12;
        }

        if (showArrayList) {
            int moduleY = y;
            if (com.staqclient.module.combat.AutoWarden.enableAutoWarden) {
                renderText(matrices, "AutoWarden", x, moduleY, accentColor);
                moduleY += 10;
            }
            if (com.staqclient.module.render.Visuals.TargetEspModule.enableTargetEsp) {
                renderText(matrices, "TargetESP", x, moduleY, accentColor);
                moduleY += 10;
            }
            if (com.staqclient.module.render.Visuals.CosmeticsModule.enableCosmetics) {
                renderText(matrices, "Cosmetics", x, moduleY, accentColor);
                moduleY += 10;
            }
        }

        if (showWardenStatus && com.staqclient.module.combat.AutoWarden.enableAutoWarden) {
            String status = com.staqclient.module.combat.AutoWarden.isReturningHome ? 
                "§c[Warden] Возврат домой (/home)..." : "§a[Warden] Фарм активен";
            renderText(matrices, status, 10, mc.getWindow().getScaledHeight() - 20, 0xFFFFFFFF);
        }
    }

    private static void renderText(MatrixStack matrices, String text, int x, int y, int color) {
        if (mc.textRenderer != null) {
            // Рендер текста через шрифты Minecraft
        }
    }
}
