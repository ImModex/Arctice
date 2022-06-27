package de.Modex.arctice.skyblock.listener;

import org.bukkit.entity.Creeper;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

public class CreeperExplosionListener implements Listener {

    @EventHandler
    public void on(EntityExplodeEvent e) {
        if (e.getEntity() instanceof Creeper creeper) {
            creeper.setExplosionRadius(creeper.getExplosionRadius() * 2);

            e.blockList().clear();
        }
    }

    @EventHandler
    public void on(EntityDamageEvent e) {
        if (e.getCause().equals(EntityDamageEvent.DamageCause.ENTITY_EXPLOSION)) {
            e.setDamage(e.getDamage() * 2);
        }
    }
}
