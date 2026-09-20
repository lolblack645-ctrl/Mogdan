package com.staqclient.module.movement;

import com.staqclient.module.Module;
import net.minecraft.client.MinecraftClient;

public class Flight extends Module {
    public Flight() {
        super("Flight", "Allows you to fly", Category.MOVEMENT);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player != null) {
            client.player.getAbilities().allowFlying = true;
            client.player.getAbilities().flying = true;
        }
    }

    @Override
    public void onDisable() {
        super.onDisable();
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player != null && !client.player.getAbilities().creativeMode) {
            client.player.getAbilities().allowFlying = false;
            client.player.getAbilities().flying = false;
        }
    }
}

