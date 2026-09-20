package com.liminar.modules.render;

import com.liminar.modules.Module;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;

public class TargetESP extends Module {
    private Entity currentTarget = null;

    public TargetESP() {
        super("TargetESP", "Подсвечивает текущую цель для атаки", 0);
    }

    @Override
    public void onTick() {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.world == null || client.player == null) return;

        // Ищем ближайшего игрока в радиусе 6 блоков (как в KillAura / AimAssist)
        Entity bestTarget = null;
        double minDistance = 6.0;

        for (Entity entity : client.world.getEntities()) {
            if (entity == client.player) continue;
            if (entity instanceof PlayerEntity) {
                double dist = client.player.distanceTo(entity);
                if (dist <= minDistance) {
                    minDistance = dist;
                    bestTarget = entity;
                }
            }
        }

        // Если цель поменялась, обновляем подсветку
        if (currentTarget != bestTarget) {
            if (currentTarget != null && currentTarget.isGlowing()) {
                currentTarget.setGlowing(false); // Снимаем со старой
            }
            currentTarget = bestTarget;
        }

        // Включаем свечение для текущей главной цели
        if (currentTarget != null && !currentTarget.isGlowing()) {
            currentTarget.setGlowing(true);
        }
    }

    @Override
    public void onDisable() {
        super.onDisable();
        // При выключении модуля убираем свечение с текущей цели
        if (currentTarget != null) {
            currentTarget.setGlowing(false);
            currentTarget = null;
        }
    }
}
