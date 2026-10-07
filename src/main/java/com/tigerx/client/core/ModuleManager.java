package com.tigerx.client.core;

import com.tigerx.client.modules.visual.ChestESP;
import java.util.ArrayList;
import java.util.List;

public class ModuleManager {
    private final List<Module> modules = new ArrayList<>();

    public ModuleManager() {
        registerModules();
    }

    private void registerModules() {
        register(new ChestESP());
    }

    public List<Module> getModules() {
        return modules;
    }

    public Module getModule(String name) {
        for (Module m : modules) {
            if (m.getName().equalsIgnoreCase(name)) return m;
        }
        return null;
    }

    public void register(Module module) {
        modules.add(module);
    }
}
