package com.liminar.modules.bots;

import com.liminar.modules.Module;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;

public class W1ldBot extends Module {
    private int pathUpdateCooldown = 0;

    public W1ldBot() {
        super("W1ldBot", "Автоматический бот-помощник для преследования и контроля в стиле w1ld", 0);
    }

    @Override
    public void onTick() {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null || client.world == null) return;

        // Ищем ближайшего игрока для преследования/сопровождения
        PlayerEntity target = null;
        double closestDistance = Double.MAX_VALUE;

        for (Entity entity : client.world.getEntities()) {
            if (entity == client.player) continue;
            if (entity instanceof PlayerEntity) {
                double dist = client.player.distanceTo(entity);
                if (dist < closestDistance) {
                    closestDistance = dist;
                    target = (PlayerEntity) entity;
                }
            }
        }

        if (target != null && closestDistance > 3.0 && closestDistance < 25.0) {
            // Поворачиваемся лицом к цели
            client.player.lookAt(net.minecraft.command.argument.EntityAnchorArgumentType.EntityAnchor.EYES, target.getPos());

            // Имитируем движение вперед к цели (простейший путь)
            client.options.forwardKey.setPressed(true);

            // Если цель слишком близко, останавливаемся для атаки
        } else {
            client.options.forwardKey.setPressed(false);
        }
    }

    @Override
    public void onDisable() {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client != null && client.options != null) {
            client.options.forwardKey.setPressed(false);
        }
        super.onDisable();
    }
}
