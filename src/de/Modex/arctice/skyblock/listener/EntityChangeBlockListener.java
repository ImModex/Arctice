package de.Modex.arctice.skyblock.listener;

import org.bukkit.entity.Enderman;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;

public class EntityChangeBlockListener implements Listener {

    @EventHandler
    public void on(EntityChangeBlockEvent e) {
        if (e.getEntity() instanceof Enderman)
            e.setCancelled(true);
    }
}
