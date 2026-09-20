package com.liminar.modules.render;

import com.liminar.modules.Module;
import net.minecraft.client.MinecraftClient;

public class Brightness extends Module {
    private double oldGamma = 1.0;

    public Brightness() {
        super("Brightness", "Максимальная яркость и ночное видение", 0);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.options != null) {
            // Сохраняем текущую гамму и выкручиваем на максимум (например, 100.0)
            oldGamma = client.options.getGamma().getValue();
            client.options.getGamma().setValue(100.0);
        }
    }

    @Override
    public void onDisable() {
        super.onDisable();
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.options != null) {
            // Возвращаем стандартную гамму при выключении
            oldGamma = client.options.getGamma().getValue();
        }
    }
}

