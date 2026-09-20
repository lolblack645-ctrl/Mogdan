package com.liminar.modules.others;

import com.liminar.modules.Module;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;

public class FTHelper extends Module {
    private int useCooldown = 0;

    public FTHelper() {
        super("FTHelper", "Автоматизация уникальных предметов и способностей для FunTime", 0);
    }

    @Override
    public void onTick() {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null || client.interactionManager == null) return;

        // Управляем кулдауном для предотвращения мгновенного спама пакетов на сервер
        if (useCooldown > 0) {
            useCooldown--;
            return;
        }

        // Пример логики поиска и использования уников из хотбара (паладин, ассасин, фаерболы и т.д.)
        for (int i = 0; i < 9; i++) {
            ItemStack stack = client.player.getInventory().getStack(i);
            if (stack.isEmpty()) continue;

            String itemName = stack.getName().getString().toLowerCase();

            // Проверяем наличие уникальных предметов по ключевым словам
            if (itemName.contains("ассасин") || itemName.contains("паладин") || 
                itemName.contains("святая вода") || itemName.contains("радиация") || 
                itemName.contains("фаербол") || itemName.contains("дезориентация") || 
                itemName.contains("пыль маньяка") || itemName.contains("леденящий снежок")) {

                // Переключаемся на слот с предметом
                int oldSlot = client.player.getInventory().selectedSlot;
                client.player.getInventory().selectedSlot = i;

                // Симулируем использование предмета
                client.interactionManager.interactItem(client.player, net.minecraft.util.Hand.MAIN_HAND);

                // Возвращаем слот обратно (или оставляем, в зависимости от ситуации в PvP)
                client.player.getInventory().selectedSlot = oldSlot;

                // Устанавливаем задержку в тиках (например, 20 тиков = 1 секунда)
                useCooldown = 20; 
                break;
            }
        }
    }
}
