package de.Modex.arctice.survival.compasstarget;

import de.Modex.arctice.survival.utils.Strings;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class CompassInteractListener implements Listener {

    private final Plugin instance;

    public CompassInteractListener(Plugin plugin) {
        instance = plugin;
    }

    @EventHandler
    public void on(PlayerInteractEntityEvent e) {
        Player p = e.getPlayer();

        if (e.getRightClicked() instanceof Player && p.getInventory().getItemInMainHand().getType().equals(Material.COMPASS) || p.getInventory().getItemInOffHand().getType().equals(Material.COMPASS)) {
            Player target = (Player) e.getRightClicked();

            new BukkitRunnable() {
                @Override
                public void run() {
                    if (Bukkit.getOnlinePlayers().contains(target) && Bukkit.getOnlinePlayers().contains(p)) {
                        p.setCompassTarget(e.getRightClicked().getLocation());
                    } else {
                        System.out.println("Task cancelled");
                        cancel();
                    }
                }
            }.runTaskTimer(instance, 0, 10);

            p.sendMessage(Strings.prefix + "§7Your compass is now tracking: " + ((Player) e.getRightClicked()).getDisplayName());
        }
    }
}
