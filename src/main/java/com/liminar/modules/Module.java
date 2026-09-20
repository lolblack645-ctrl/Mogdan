package com.liminar.modules;

public class Module {
    private String name;
    private String description;
    private int key;
    private boolean toggled;

    public Module(String name, String description, int key) {
        this.name = name;
        this.description = description;
        this.key = key;
        this.toggled = false;
    }

    public void toggle() {
        this.toggled = !this.toggled;
        if (this.toggled) {
            onEnable();
        } else {
            onDisable();
        }
    }

    public void onEnable() {}
    public void onDisable() {}
    public void onTick() {}

    public String getName() { return name; }
    public boolean isToggled() { return toggled; }
}
