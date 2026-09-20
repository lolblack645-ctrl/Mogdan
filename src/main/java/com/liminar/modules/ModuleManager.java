package com.liminar.modules;

import com.liminar.modules.combat.*;
import com.liminar.modules.movement.*;
import com.liminar.modules.render.*;
import com.liminar.modules.others.*;
import com.liminar.modules.bots.*;

import java.util.ArrayList;
import java.util.List;

public class ModuleManager {
    private static final List<Module> modules = new ArrayList<>();

    public static void init() {
        // Боевые
        modules.add(new KillAura());
        modules.add(new AutoTotem());
        modules.add(new AutoSwap());
        modules.add(new AimAssist());
        modules.add(new TriggerBot());

        // Движение
        modules.add(new TargetStrafe());
        modules.add(new Speed());

        // Рендер
        modules.add(new HUD());
        modules.add(new ESP());
        modules.add(new Brightness());
        modules.add(new TargetESP());
        modules.add(new Ambience());

        // Остальные
        modules.add(new ChestStealer());
        modules.add(new FTHelper());
        modules.add(new AutoWarden());

        // Боты
        modules.add(new W1ldBot());
    }

    public static List<Module> getModules() {
        return modules;
    }

    public static Module getModuleByName(String name) {
        for (Module m : modules) {
            if (m.getName().equalsIgnoreCase(name)) {
                return m;
            }
        }
        return null;
    }
}

