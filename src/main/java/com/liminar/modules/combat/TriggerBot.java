package com.liminar.modules.combat;

import com.liminar.modules.Module;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;

public class TriggerBot extends Module {

    public TriggerBot() {
        super("TriggerBot", "Автоматически бьет игрока под прицелом", 0);
    }

    @Override
    public void onTick() {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null || client.interactionManager == null) return;
        if (client.player.isDead()) return;

        // Проверяем, куда смотрит игрок
        HitResult hit = client.crosshairTarget;
        if (hit != null && hit.getType() == HitResult.Type.ENTITY) {
            EntityHitResult entityHit = (EntityHitResult) hit;

            // Если под прицелом игрок
            if (entityHit.getEntity() instanceof PlayerEntity) {
                // Проверяем кулдаун атаки
                if (client.player.getAttackCooldownProgress(0.5f) >= 1.0f) {
                    client.interactionManager.attackEntity(client.player, entityHit.getEntity());
                    client.player.swingHand(Hand.MAIN_HAND);
                }
            }
        }
    }
}
