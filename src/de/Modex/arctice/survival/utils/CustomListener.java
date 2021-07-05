package de.Modex.arctice.survival.utils;

import de.Modex.arctice.survival.Main;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

public abstract class CustomListener implements Listener {

    public CustomListener() {
        Bukkit.getPluginManager().registerEvents(this, Main.instance);
    }
}
