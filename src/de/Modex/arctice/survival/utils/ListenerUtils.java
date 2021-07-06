package de.Modex.arctice.survival.utils;

import de.Modex.arctice.survival.Main;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

public class ListenerUtils {

    public static void registerListener(Listener listener) {
        Bukkit.getPluginManager().registerEvents(listener, Main.instance);
    }
}
