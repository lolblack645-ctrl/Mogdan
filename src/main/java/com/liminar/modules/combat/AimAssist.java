package com.liminar.modules.combat;

import com.liminar.modules.Module;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;

public class AimAssist extends Module {
    private final double range = 4.5; // Радиус работы аим-ассиста
    private final float speed = 4.0f;  // Скорость доворота (плавность)

    public AimAssist() {
        super("AimAssist", "Плавная доводка прицела до противника", 0);
    }

    @Override
    public void onTick() {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null || client.world == null) return;

        // Ищем ближайшего игрока
        Entity target = null;
        double minDistance = range;

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

        // Если цель есть, плавно поворачиваем камеру
        if (target != null) {
            double dX = target.getX() - client.player.getX();
            double dZ = target.getZ() - client.player.getZ();
            double dY = (target.getY() + target.getEyeHeight(target.getPose())) - (client.player.getY() + client.player.getEyeHeight(client.player.getPose()));

            double distanceXZ = Math.sqrt(dX * dX + dZ * dZ);

            // Вычисляем нужные углы (Yaw и Pitch)
            float targetYaw = (float) (Math.atan2(dZ, dX) * (180 / Math.PI)) - 90.0f;
            float targetPitch = (float) (-(Math.atan2(dY, distanceXZ) * (180 / Math.PI)));

            // Плавно подруливаем текущие углы игрока к целевым
            client.player.setYaw(updateAngle(client.player.getYaw(), targetYaw, speed));
            client.player.setPitch(updateAngle(client.player.getPitch(), targetPitch, speed));
        }
    }

    // Функция для плавной интерполяций углов
    private float updateAngle(float current, float target, float maxSpeed) {
        float diff = target - current;
        while (diff < -180.0f) diff += 360.0f;
        while (diff >= 180.0f) diff -= 360.0f;

        if (diff > maxSpeed) diff = maxSpeed;
        if (diff < -maxSpeed) diff = -maxSpeed;

        return current + diff;
    }
}
