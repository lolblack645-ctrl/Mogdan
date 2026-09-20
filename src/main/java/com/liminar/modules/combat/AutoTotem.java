package com.liminar.modules.combat;

import com.liminar.modules.Module;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.Items;
import net.minecraft.screen.slot.SlotActionType;

public class AutoTotem extends Module {

    public AutoTotem() {
        super("AutoTotem", "Автоматически берет тотем в левую руку", 0);
    }

    @Override
    public void onTick() {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null || client.interactionManager == null) return;

        // Проверяем, что в левой руке НЕ тотем
        boolean hasTotemInOffhand = client.player.getOffHandStack().isOf(Items.TOTEM_OF_UNDYING);

        if (!hasTotemInOffhand) {
            // Ищем тотем в инвентаре (слоты с 9 по 44 — основной инвентарь)
            int slot = -1;
            for (int i = 9; i < 45; i++) {
                if (client.player.getInventory().getStack(i).isOf(Items.TOTEM_OF_UNDYING)) {
                    slot = i;
                    break;
                }
            }

            // Если тотем найден в инвентаре, перемещаем его в офхенд (слот 40)
            if (slot != -1) {
                int targetSlot = slot < 9 ? slot + 36 : slot;
                client.interactionManager.clickSlot(
                    client.player.currentScreenHandler.syncId,
                    targetSlot,
                    40, // Идентификатор для левой руки / офхенда
                    SlotActionType.SWAP,
                    client.player
                );
            }
        }
    }
}

