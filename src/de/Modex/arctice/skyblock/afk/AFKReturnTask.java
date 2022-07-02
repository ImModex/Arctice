package de.Modex.arctice.skyblock.afk;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class AFKReturnTask extends BukkitRunnable {
    @Override
    public void run() {
        AFKManager.afkPlayers.forEach(afkPlayer -> {
            if (AFKManager.playerOf(afkPlayer) != null && !AFKManager.playerOf(afkPlayer).getLocation().equals(afkPlayer.getLocation())) {
                AFKManager.afkPlayers.get(AFKManager.afkPlayers.indexOf(afkPlayer)).setAfkTime(0);
                Bukkit.getPluginManager().callEvent(new AFKReturnEvent(AFKManager.playerOf(afkPlayer)));
            }
        });
    }
}
