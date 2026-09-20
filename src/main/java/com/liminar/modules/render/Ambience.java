package com.liminar.modules.render;

import com.liminar.modules.Module;
import net.minecraft.client.MinecraftClient;

public class Ambience extends Module {

    public Ambience() {
        super("Ambience", "Кастомная погода, время суток и атмосфера", 0);
    }

    @Override
    public void onTick() {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.world == null) return;

        // Пример: фиксируем время суток на середине дня (например, 6000 тиков),
        // чтобы на анархии всегда было светло и не спавнились мобы вокруг
        client.world.setTimeOfDay(6000L);
    }
}
