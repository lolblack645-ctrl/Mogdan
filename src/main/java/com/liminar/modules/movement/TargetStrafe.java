package com.liminar.modules.movement;

import com.liminar.modules.Module;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;

public class TargetStrafe extends Module {
    private final double strafeDistance = 2.5; // Дистанция кружения вокруг врага
    private boolean strafeDirection = true;   // Направление кружения (вправо/влево)

    public TargetStrafe() {
        super("TargetStrafe", "Автоматически кружит вокруг противника в PvP", 0);
    }

    @Override
    public void onTick() {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null || client.world == null) return;

        // Ищем ближайшего противника
        Entity target = null;
        double minDistance = 6.0;

        for (Entity entity : client.world.getEntities()) {
            if (entity == client.player) continue;
            if (entity instanceof PlayerEntity) {
                double dist = client.player.distanceTo(entity);
                if (dist <= minDistance) {
                    minDistance = dist;
                    target = entity;
                }
            }
        }

        // Если цель найдена, заставляем игрока двигаться по кругу
        if (target != null) {
            double dX = client.player.getX() - target.getX();
            double dZ = client.player.getZ() - target.getZ();
            double angle = Math.atan2(dZ, dX);

            // Меняем направление при столкновении с препятствием или по желанию
            if (client.player.horizontalCollision) {
                strafeDirection = !strafeDirection;
            }

            angle += strafeDirection ? 0.5 : -0.5;

            double targetX = target.getX() + strafeDistance * Math.cos(angle);
            double targetZ = target.getZ() + strafeDistance * Math.sin(angle);

            // Устанавливаем движение в сторону рассчитанной точки
            // (В простейшем варианте через изменение скорости или зажатие стрейф-клавиш)
            double speed = 0.22; // Стандартная скорость бега
            double moveX = targetX - client.player.getX();
            double moveZ = targetZ - client.player.getZ();

            double len = Math.sqrt(moveX * moveX + moveZ * moveZ);
            if (len > 0.01) {
                moveX /= len;
                moveZ /= len;
                client.player.setVelocity(moveX * speed, client.player.getVelocity().y, moveZ * speed);
            }
        }
    }
}
