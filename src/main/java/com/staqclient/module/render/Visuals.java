package com.staqclient.module.render;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Box;
import java.util.ArrayList;
import java.util.List;

public class Visuals {
    private static final MinecraftClient mc = MinecraftClient.getInstance();

    // --- Ambience ---
    public static class AmbienceModule {
        public static boolean enableFog = true;
        public static float fogDensity = 1.0f;
        public static float fogStart = 2.0f;
        public static float fogEnd = 20.0f;
        public static boolean enableNightMode = true;
        public static float nightBrightness = 0.15f;
    }

    // --- Cubes ---
    public static class CubesModule {
        public static boolean enableCubes = true;
        public enum CubeType { CUBES, STARS, HEARTS }
        public static CubeType currentType = CubeType.STARS;
        public static int particleCount = 150;
        public static float particleSize = 1.2f;
        public static float particleSpeed = 0.5f;
    }

    // --- Cosmetics ---
    public static class CosmeticsModule {
        public static boolean enableCosmetics = true;
        public static boolean isMenuOpen = false;
        public enum Category { CAPE, BACKPACK, WINGS }
        public static Category currentCategory = Category.CAPE;

        public static class CosmeticItem {
            public String name;
            public Category category;
            public boolean isEquipped;
            public boolean isAnimated;

            public CosmeticItem(String name, Category category, boolean isAnimated) {
                this.name = name;
                this.category = category;
                this.isEquipped = false;
                this.isAnimated = isAnimated;
            }
        }

        public static List<CosmeticItem> availableCosmetics = new ArrayList<>();

        static {
            availableCosmetics.add(new CosmeticItem("Плащ Akvi4 (FunTime YouTuber)", Category.CAPE, true));
            availableCosmetics.add(new CosmeticItem("Плащ Pioneer (FunTime YouTuber)", Category.CAPE, true));
            availableCosmetics.add(new CosmeticItem("Плащ Домер (ReallyWorld YouTuber)", Category.CAPE, true));
            availableCosmetics.add(new CosmeticItem("Черный рюкзак", Category.BACKPACK, false));
            availableCosmetics.add(new CosmeticItem("Белый рюкзак", Category.BACKPACK, false));
            availableCosmetics.add(new CosmeticItem("Серый рюкзак", Category.BACKPACK, false));
            availableCosmetics.add(new CosmeticItem("Белые крылья (Анимированные)", Category.WINGS, true));
            availableCosmetics.add(new CosmeticItem("Черные крылья (Анимированные)", Category.WINGS, true));
            availableCosmetics.add(new CosmeticItem("Крылья Ангела (Анимированные)", Category.WINGS, true));
            availableCosmetics.add(new CosmeticItem("Крылья Демона (Анимированные)", Category.WINGS, true));
        }
    }

    // --- Target ESP ---
    public static class TargetEspModule {
        public static boolean enableTargetEsp = true;
        public static boolean showBox = true;
        public static boolean showNameAndHealth = true;
        public static float boxScaleSize = 1.0f;
        private static LivingEntity currentTarget = null;

        public static void setTarget(LivingEntity target) { currentTarget = target; }
        public static LivingEntity getTarget() { return currentTarget; }
    }

    // --- NoRender ---
    public static class NoRenderModule {
        public static boolean enableNoRender = true;
        public static boolean noNausea = true;
        public static boolean noFireOverlay = true;
        public static boolean noBlackHearts = true;
        public static boolean noTotemSound = true;
        public static boolean noXpSound = true;

        public static boolean cancelNausea() { return enableNoRender && noNausea; }
        public static boolean cancelFireOverlay() { return enableNoRender && noFireOverlay; }
        public static boolean cancelBlackHearts() { return enableNoRender && noBlackHearts; }
        public static boolean cancelTotemSound(String soundId) {
            return enableNoRender && noTotemSound && soundId.contains("item.totem.use");
        }
        public static boolean cancelXpSound(String soundId) {
            return enableNoRender && noXpSound && soundId.contains("entity.experience_orb.pickup");
        }
    }
}
