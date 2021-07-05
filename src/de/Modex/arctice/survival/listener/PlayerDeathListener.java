package de.Modex.arctice.survival.listener;

import de.Modex.arctice.survival.utils.Strings;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathListener implements Listener {

    @EventHandler
    public void on(PlayerDeathEvent e) {
        Player p = e.getEntity();
        e.setDeathMessage("§7" + e.getDeathMessage());
        e.setDroppedExp((int) Math.ceil(p.getTotalExperience() * 0.25));
        p.sendMessage(Strings.prefix + "§7You died at §a" + (int) p.getLocation().getX() + " §8| §a" + (int) p.getLocation().getY() + " §8| §a" + (int) p.getLocation().getZ() + " §7in the world §e" + p.getLocation().getWorld().getName());
    }
}
