package com.staqclient.bot;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ServerInfo;

import java.util.ArrayList;
import java.util.List;

public class BotManager {
    private static final List<BotData> bots = new ArrayList<>();
    private static int activeBotIndex = -1; // -1 значит управление основным игроком

    public static class BotData {
        public String username;
        public boolean isConnected;

        public BotData(String username) {
            this.username = username;
            this.isConnected = false;
        }
    }

    // Добавить бота и отправить его на текущий сервер, где играет игрок
    public static void addAndConnectBot(String username) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.getCurrentServerEntry() == null) {
            // Если мы не на сервере, подключение невозможно
            return;
        }

        ServerInfo currentServer = client.getCurrentServerEntry();
        BotData newBot = new BotData(username);
        bots.add(newBot);

        // Логика запуска/подключения бота
        newBot.isConnected = true;
        
        // Автоматически переключаемся на нового бота, если нужно
        activeBotIndex = bots.size() - 1;
    }

    public static List<BotData> getBots() {
        return bots;
    }

    public static void setActiveBot(int index) {
        if (index >= -1 && index < bots.size()) {
            activeBotIndex = index;
        }
    }

    public static int getActiveBotIndex() {
        return activeBotIndex;
    }
}

