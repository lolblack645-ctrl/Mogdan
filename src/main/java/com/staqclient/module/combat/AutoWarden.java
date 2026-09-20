package com.staqclient.module.combat;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.WardenEntity;
import java.util.ArrayList;
import java.util.List;

public class AutoWarden {
    private static final MinecraftClient mc = MinecraftClient.getInstance();

    public static boolean enableAutoWarden = true;
    public static double safeDistance = 10.0D;

    // Строгие лимиты инвентаря под фарм вардена
    public static final int REQUIRED_SPEED_POTIONS = 1;
    public static final int REQUIRED_INVIS_POTIONS = 1;
    public static final int REQUIRED_CARROTS = 4;

    // Списки анархий (до 5 точек лута, до 1 дома)
    public static List<String> wardenLootAnarchies = new ArrayList<>();
    public static String wardenHomeAnarchy = null;

    // Флаг состояния: false - фармим/ходим по точкам, true - кончились ресурсы, летим домой
    public static boolean isReturningHome = false;

    public static void onTick() {
        if (!enableAutoWarden || mc.player == null || mc.world == null) return;

        // 1. Контроль ресурсов в инвентаре
        if (!hasRequiredResources() && !isReturningHome) {
            isReturningHome = true;
            moveToHomeAnarchy();
            return;
        }

        // 2. Если ресурсы на месте, ищем Вардена и отходим через Baritone
        if (!isReturningHome) {
            for (Entity entity : mc.world.getEntities()) {
                if (entity instanceof WardenEntity warden) {
                    if (mc.player.distanceTo(warden) < safeDistance) {
                        triggerBaritoneEscape();
                    }
                }
            }
        }
    }

    private static boolean hasRequiredResources() {
        int speedCount = 0;
        int invisCount = 0;
        int carrotCount = 0;

        // Сканируем инвентарь игрока
        for (int i = 0; i < mc.player.getInventory().size(); i++) {
            var stack = mc.player.getInventory().getStack(i);
            if (stack.isEmpty()) continue;

            String itemId = stack.getItem().toString().toLowerCase();
            if (itemId.contains("potion") && itemId.contains("swiftness")) {
                speedCount += stack.getCount();
            } else if (itemId.contains("potion") && itemId.contains("invisibility")) {
                invisCount += stack.getCount();
            } else if (itemId.contains("carrot")) {
                carrotCount += stack.getCount();
            }
        }

        // Проверяем наличие ровно нужного количества
        return speedCount >= REQUIRED_SPEED_POTIONS && 
               invisCount >= REQUIRED_INVIS_POTIONS && 
               carrotCount >= REQUIRED_CARROTS;
    }

    private static void triggerBaritoneEscape() {
        // Вызов API Baritone для автоматического ухода от Хранителя
        // Baritone.getApi().getCustomGoalProcess().setGoal(...)
    }

    private static void moveToHomeAnarchy() {
        if (wardenHomeAnarchy != null && mc.player != null && mc.player.networkHandler != null) {
            // Телепортация на анархию-дом через командную строку сервера
            mc.player.networkHandler.sendChatMessage("/home " + wardenHomeAnarchy);
            
            // Примечание: после пополнения ресурсов из сундука/бочки 
            // необходимо переключить флаг обратно: isReturningHome = false;
        }
    }

    // Обработка чат-команд .warden add loot / home
    public static void handleCommand(String[] args) {
        if (args.length < 3) return;
        String action = args[1];
        String anarchyName = args[2];

        if (action.equalsIgnoreCase("loot")) {
            if (wardenLootAnarchies.size() < 5) {
                wardenLootAnarchies.add(anarchyName); // Сохраняем до 5 точек анархий для лута
            }
        } else if (action.equalsIgnoreCase("home")) {
            wardenHomeAnarchy = anarchyName; // Строго 1 точка дома
        }
    }
}

