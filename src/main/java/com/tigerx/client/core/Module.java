package com.tigerx.client.core;

public abstract class Module {
    private final String name;
    private final String description;
    private final Category category;
    private boolean enabled;
    private int keybind;

    public Module(String name, String description, Category category, int defaultKey) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.keybind = defaultKey;
        this.enabled = false;
    }

    public String getName() { return name; }
    public String getDescription() { return description; }
    public Category getCategory() { return category; }
    public boolean isEnabled() { return enabled; }
    public int getKeybind() { return keybind; }

    public void setKeybind(int key) { this.keybind = key; }

    public void toggle() {
        this.enabled = !this.enabled;
        if (this.enabled) onEnable();
        else onDisable();
    }

    public void onEnable() {}
    public void onDisable() {}

    public enum Category {
        COMBAT, VISUAL, MOVEMENT, WORLD, MISC
    }
}
