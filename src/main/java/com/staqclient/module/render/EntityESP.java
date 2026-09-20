package com.staqclient.module.render;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;

public class EntityESP {
    private static final MinecraftClient mc = MinecraftClient.getInstance();

    // Настройки размеров и отображения
    public static boolean enableESP = true;
    public static boolean drawNames = true;
    public static boolean drawHealth = true;
    public static float scaleMultiplier = 1.0f; // Настройка размера текста/элементов

    public static void render(MatrixStack matrices) {
        if (!enableESP || mc.world == null || mc.player == null) return;

        for (Entity entity : mc.world.getEntities()) {
            if (entity == mc.player) continue;

            // Фильтр: Игроки и Предметы
            if (entity instanceof PlayerEntity || entity instanceof ItemEntity) {
                // Здесь вызывается отрисовка бокса или текста на экране
                renderEntityInfo(matrices, entity);
            }
        }
    }

    private static void renderEntityInfo(MatrixStack matrices, Entity entity) {
        double x = entity.getX() - mc.getEntityRenderDispatcher().camera.getPos().x;
        double y = entity.getY() - mc.getEntityRenderDispatcher().camera.getPos().y;
        double z = entity.getZ() - mc.getEntityRenderDispatcher().camera.getPos().z;

        if (entity instanceof PlayerEntity player && drawNames) {
            String nameTag = player.getName().getString();
            if (drawHealth) {
                int hp = (int) player.getHealth();
                nameTag += " [" + hp + " HP]";
            }
            // Отрисовка текста с учетом scaleMultiplier масштабируется в интерфейсе рендера
        }
    }
}
