package de.Modex.arctice.skyblock.listener;

import org.bukkit.entity.Chicken;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;

public class ZombieRiderSpawnListener implements Listener {

    @EventHandler
    public void on(EntitySpawnEvent e) {
        if (e.getEntityType().equals(EntityType.ZOMBIE) && e.getEntity().getVehicle() != null && e.getEntity().getVehicle() instanceof Chicken) {
            e.getEntity().getVehicle().remove();
        }
    }
}
