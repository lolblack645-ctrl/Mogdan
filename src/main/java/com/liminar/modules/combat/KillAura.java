package com.liminar.modules.combat;

import com.liminar.modules.Module;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;

public class KillAura extends Module {
    private final double range = 4.2; // Дистанция удара под анархию

    public KillAura() {
        super("KillAura", "Автоматически бьет врагов вокруг", 0);
    }

    @Override
    public void onTick() {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null || client.world == null) return;
        if (client.player.isDead()) return;

        // Ищем ближайшего игрока в радиусе
        Entity target = null;
        double minDistance = range;

        for (Entity entity : client.world.getEntities()) {
            if (entity == client.player) continue;
            if (entity instanceof PlayerEntity) { // На анархии бьем игроков
                double dist = client.player.distanceTo(entity);
                if (dist <= minDistance) {
                    minDistance = dist;
                    target = entity;
                }
            }
        }

        // Если цель найдена — атаковать
        if (target != null && client.interactionManager != null) {
            // Проверка кулдауна атаки (чтобы удары засчитывались)
            if (client.player.getAttackCooldownProgress(0.5f) >= 1.0f) {
                client.interactionManager.attackEntity(client.player, target);
                client.player.swingHand(Hand.MAIN_HAND);
            }
        }
    }
}
