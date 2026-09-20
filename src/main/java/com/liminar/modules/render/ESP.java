package com.liminar.modules.render;

import com.liminar.modules.Module;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Box;

public class ESP extends Module {

    public ESP() {
        super("ESP", "Подсвечивает игроков сквозь стены", 0);
    }

    @Override
    public void onTick() {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.world == null || client.player == null) return;

        // Простая логика: делаем игроков светящимися (Glowing effect), 
        // что заставляет клиентскую игру рисовать вокруг них стандартный контур
        for (Entity entity : client.world.getEntities()) {
            if (entity == client.player) continue;

            if (entity instanceof PlayerEntity) {
                // Включаем свечение для игроков в радиусе прорисовки
                if (!entity.isGlowing()) {
                    entity.setGlowing(true);
                }
            }
        }
    }
}
