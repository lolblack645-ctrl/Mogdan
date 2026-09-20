package com.liminar.modules.combat;

import com.liminar.modules.Module;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.Items;
import net.minecraft.item.SwordItem;

public class AutoSwap extends Module {

    public AutoSwap() {
        super("AutoSwap", "Автоматически переключает на меч/оружие при атаке", 0);
    }

    @Override
    public void onTick() {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null || client.interactionManager == null) return;

        // Пример логики: если в руках не меч, а рядом есть враг / зажата атака, 
        // можно автоматически переключить слот на лучший меч из горячего инвентаря (слоты 0-8).
        boolean holdingSword = client.player.getMainHandStack().getItem() instanceof SwordItem;

        if (!holdingSword) {
            for (int i = 0; i < 9; i++) {
                if (client.player.getInventory().getStack(i).getItem() instanceof SwordItem) {
                    client.player.getInventory().selectedSlot = i;
                    break;
                }
            }
        }
    }
}
