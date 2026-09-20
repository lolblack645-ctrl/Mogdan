package com.staqclient.module.combat;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;

public class KillAura {
    private static final MinecraftClient mc = MinecraftClient.getInstance();

    // Настройки фильтров и функций
    public static boolean enableAura = true;
    public static boolean targetPlayers = true;
    public static boolean targetMobs = true;
    public static boolean targetAnimals = true;
    public static boolean targetInvisibles = true;
    public static boolean targetNaked = true;
    public static boolean throughWalls = false; // Бить через стену (вкл/выкл)

    public static void onTick() {
        if (!enableAura || mc.player == null || mc.world == null) return;

        // Не бьем, если открыт инвентарь или любой контейнер
        if (mc.currentScreen != null) return;

        for (Entity entity : mc.world.getEntities()) {
            if (entity == mc.player || !(entity instanceof LivingEntity living)) continue;

            // Фильтры целей
            if (living instanceof PlayerEntity player) {
                if (!targetPlayers) continue;
                if (!targetInvisibles && player.isInvisible()) continue;
            } else if (living instanceof MobEntity) {
                if (!targetMobs) continue;
            } else if (living instanceof AnimalEntity) {
                if (!targetAnimals) continue;
            }

            // Проверка: бить ли через стену
            if (!throughWalls && !mc.player.canSee(living)) {
                continue;
            }

            // Логика атаки (удары, ротации, криты)
            attack(living);
            break;
        }
    }

    private static void attack(LivingEntity target) {
        // Здесь выполняется отправка пакета атаки и ротации под FunTime
        mc.interactionManager.attackEntity(mc.player, target);
        mc.player.swingHand(net.minecraft.util.Hand.MAIN_HAND);
    }
}

