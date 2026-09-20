package com.liminar.modules.others;

import com.liminar.modules.Module;
import net.minecraft.client.MinecraftClient;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.slot.SlotActionType;

public class ChestStealer extends Module {
    private int delayTimer = 0;

    public ChestStealer() {
        super("ChestStealer", "Автоматически забирает все вещи из открытого сундука", 0);
    }

    @Override
    public void onTick() {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null || client.interactionManager == null) return;

        // Проверяем, открыт ли у игрока инвентарь сундука (контейнер)
        if (client.player.currentScreenHandler instanceof GenericContainerScreenHandler) {
            GenericContainerScreenHandler container = (GenericContainerScreenHandler) client.player.currentScreenHandler;

            // Добавляем небольшую задержку (тиков), чтобы сервер не кикнул за быстрый лутинг
            delayTimer++;
            if (delayTimer < 3) return; 
            delayTimer = 0;

            // Проходим по слотам сундука (верхняя часть контейнера) и быстро перемещаем предметы
            int containerRows = container.getRows() * 9;
            for (int i = 0; i < containerRows; i++) {
                if (!container.getSlot(i).hasStack()) continue;

                // Быстрый клик (Shift + Click) для перекладывания вещи в инвентарь
                client.interactionManager.clickSlot(
                    container.syncId,
                    i,
                    0,
                    SlotActionType.QUICK_MOVE,
                    client.player
                );
                break; // Лутаем по одному предмету за такт во избежание детекта
            }
        }
    }
}
