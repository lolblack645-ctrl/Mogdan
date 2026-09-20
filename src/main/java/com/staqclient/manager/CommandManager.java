package com.staqclient.manager;

public class CommandManager {

    // Вызывается при отправке сообщения игроком в чат
    public static boolean handleChatCommand(String message) {
        if (!message.startsWith(".")) {
            return false; // Это обычное сообщение в чат
        }

        String[] args = message.substring(1).split(" ");
        if (args.length < 2) return false;

        String mainCommand = args[0].toLowerCase(); // "cfg"
        String action = args[1].toLowerCase();      // "save" или "load"
        
        if (mainCommand.equals("cfg")) {
            if (args.length < 3) {
                System.out.println("[StaqClient] Использование: .cfg save/load <название>");
                return true;
            }

            String configName = args[2];

            if (action.equals("save")) {
                ConfigManager.saveConfig(configName);
            } else if (action.equals("load")) {
                ConfigManager.loadConfig(configName);
            }
            return true; // Команда обработана, в чат сервера отправлять не нужно
        }

        return false;
    }
}
