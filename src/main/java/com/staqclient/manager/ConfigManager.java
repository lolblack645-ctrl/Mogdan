package com.staqclient.manager;

import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

public class ConfigManager {
    // Путь к папке конфигов в директории клиента
    private static final File CONFIG_DIR = new File("config/staqclient");

    public static void saveConfig(String configName) {
        if (!CONFIG_DIR.exists()) {
            CONFIG_DIR.mkdirs();
        }

        File configFile = new File(CONFIG_DIR, configName + ".txt");
        try (FileWriter writer = new FileWriter(configFile)) {
            // Сохраняем состояние AutoWarden
            writer.write("AutoWarden=" + com.staqclient.module.combat.AutoWarden.enableAutoWarden + "\n");
            
            // Сохраняем визуальные модули
            writer.write("TargetEsp=" + com.staqclient.module.render.Visuals.TargetEspModule.enableTargetEsp + "\n");
            writer.write("Cosmetics=" + com.staqclient.module.render.Visuals.CosmeticsModule.enableCosmetics + "\n");
            writer.write("NoRender=" + com.staqclient.module.render.Visuals.NoRenderModule.enableNoRender + "\n");
            writer.write("AmbienceFog=" + com.staqclient.module.render.Visuals.AmbienceModule.enableFog + "\n");
            writer.write("NightMode=" + com.staqclient.module.render.Visuals.AmbienceModule.enableNightMode + "\n");
            writer.write("Cubes=" + com.staqclient.module.render.Visuals.CubesModule.enableCubes + "\n");

            // Сохраняем HUD темы
            writer.write("HudTheme=" + HudManager.currentTheme.name() + "\n");
            
            System.out.println("[StaqClient] Конфиг '" + configName + "' успешно сохранен!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadConfig(String configName) {
        File configFile = new File(CONFIG_DIR, configName + ".txt");
        if (!configFile.exists()) {
            System.out.println("[StaqClient] Конфиг '" + configName + "' не найден!");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(configFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("=");
                if (parts.length != 2) continue;

                String key = parts[0];
                String value = parts[1];

                switch (key) {
                    case "AutoWarden":
                        com.staqclient.module.combat.AutoWarden.enableAutoWarden = Boolean.parseBoolean(value);
                        break;
                    case "TargetEsp":
                        com.staqclient.module.render.Visuals.TargetEspModule.enableTargetEsp = Boolean.parseBoolean(value);
                        break;
                    case "Cosmetics":
                        com.staqclient.module.render.Visuals.CosmeticsModule.enableCosmetics = Boolean.parseBoolean(value);
                        break;
                    case "NoRender":
                        com.staqclient.module.render.Visuals.NoRenderModule.enableNoRender = Boolean.parseBoolean(value);
                        break;
                    case "AmbienceFog":
                        com.staqclient.module.render.Visuals.AmbienceModule.enableFog = Boolean.parseBoolean(value);
                        break;
                    case "NightMode":
                        com.staqclient.module.render.Visuals.AmbienceModule.enableNightMode = Boolean.parseBoolean(value);
                        break;
                    case "Cubes":
                        com.staqclient.module.render.Visuals.CubesModule.enableCubes = Boolean.parseBoolean(value);
                        break;
                    case "HudTheme":
                        try {
                            HudManager.currentTheme = HudManager.HudTheme.valueOf(value);
                        } catch (IllegalArgumentException ignored) {}
                        break;
                }
            }
            System.out.println("[StaqClient] Конфиг '" + configName + "' успешно загружен!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
