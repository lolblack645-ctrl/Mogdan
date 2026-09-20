package com.liminar.config;

import com.liminar.modules.Module;
import com.liminar.modules.ModuleManager;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

public class ConfigManager {
    private static final File configFile = new File(FabricLoader.getInstance().getConfigDir().toFile(), "staq_config.txt");

    public static void saveConfig() {
        try {
            if (!configFile.exists()) {
                configFile.getParentFile().mkdirs();
                configFile.createNewFile();
            }

            FileWriter writer = new FileWriter(configFile);
            for (Module module : ModuleManager.getModules()) {
                writer.write(module.getName() + ":" + module.isToggled() + "\n");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadConfig() {
        if (!configFile.exists()) return;

        try {
            BufferedReader reader = new BufferedReader(new FileReader(configFile));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    String name = parts[0];
                    boolean toggled = Boolean.parseBoolean(parts[1]);

                    Module module = ModuleManager.getModuleByName(name);
                    if (module != null && module.isToggled() != toggled) {
                        module.toggle();
                    }
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
