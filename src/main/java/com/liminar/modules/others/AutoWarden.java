package com.liminar.modules.others;

import com.liminar.modules.Module;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;

public class AutoWarden extends Module {
    private int cooldown = 0;

    public AutoWarden() {
        super("AutoWarden", "Автоматический призыв или активация эффектов вардена в PvP", 0);
    }

    @Override
    public void onTick() {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null || client.interactionManager == null || client.world == null) return;

        if (cooldown > 0) {
            cooldown--;
            return;
        }

        // Проверяем, есть ли враг в близком радиусе (например, 4 блоков для жесткого размена)
        boolean enemyNearby = false;
        for (Entity entity : client.world.getEntities()) {
            if (entity == client.player) continue;
            if (entity instanceof PlayerEntity && client.player.distanceTo(entity) <= 4.0f) {
                enemyNearby = true;
                break;
            }
        }

        if (!enemyNearby) return;

        // Ищем в инвентаре предмет, связанный с варденом (например, уникальный призывник / рог / заряд)
        for (int i = 0; i < 9; i++) {
            ItemStack stack = client.player.getInventory().getStack(i);
            if (stack.isEmpty()) continue;

            String name = stack.getName().getString().toLowerCase();
            if (name.contains("варден") || name.contains("warden") || name.contains("скалк") || name.contains("roar")) {

                int oldSlot = client.player.getInventory().selectedSlot;
                client.player.getInventory().selectedSlot = i;

                // Активируем способность/предмет вардена
                client.interactionManager.interactItem(client.player, Hand.MAIN_HAND);

                client.player.getInventory().selectedSlot = oldSlot;
                cooldown = 40; // Кулдаун, чтобы не спамить вхолостую
                break;
            }
        }
    }
}
