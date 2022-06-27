package de.Modex.arctice.skyblock.listener;

import de.Modex.arctice.skyblock.Main;
import de.Modex.arctice.skyblock.utils.Strings;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class PlayerDeathListener implements Listener {

    @EventHandler
    public void on(PlayerDeathEvent e) {
        Player p = e.getEntity();
        e.setDeathMessage("§7" + e.getDeathMessage());
        p.sendMessage(Strings.prefix + "§7You died at §a" + (int) p.getLocation().getX() + " §8| §a" + (int) p.getLocation().getY() + " §8| §a" + (int) p.getLocation().getZ() + " §7in the world §e" + p.getLocation().getWorld().getName());
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, () -> p.spigot().respawn(), 2);
    }
}
