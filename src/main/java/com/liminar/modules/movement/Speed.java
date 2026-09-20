package com.liminar.modules.movement;

import com.liminar.modules.Module;
import net.minecraft.client.MinecraftClient;

public class Speed extends Module {
    private final double speedMultiplier = 1.15; // Коэффициент ускорения

    public Speed() {
        super("Speed", "Увеличивает скорость передвижения", 0);
    }

    @Override
    public void onTick() {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null) return;

        // Проверяем, движется ли игрок вообще (нажаты ли клавиши движения)
        if (client.player.input.movementForward != 0.0f || client.player.input.movementSideways != 0.0f) {
            // Немного подгоняем скорость по осям X и Z
            double velocityX = client.player.getVelocity().x;
            double velocityZ = client.player.getVelocity().z;

            // Если игрок на земле или в полета, увеличиваем горизонтальный импульс
            if (client.player.isOnGround()) {
                client.player.setVelocity(velocityX * speedMultiplier, client.player.getVelocity().y, velocityZ * speedMultiplier);
            }
        }
    }
}
