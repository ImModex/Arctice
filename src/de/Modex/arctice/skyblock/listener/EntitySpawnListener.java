package de.Modex.arctice.skyblock.listener;

import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;

public class EntitySpawnListener implements Listener {

    @EventHandler
    public void on(EntitySpawnEvent e) {
        if (e.getEntity() instanceof Monster monster) {
            if (monster.getNearbyEntities(128, 128, 128).stream().noneMatch(entity -> entity instanceof Player))
                e.setCancelled(true);
        }
    }
}
