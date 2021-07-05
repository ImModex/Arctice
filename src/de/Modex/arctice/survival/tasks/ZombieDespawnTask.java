package de.Modex.arctice.survival.tasks;

import de.Modex.arctice.survival.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.scheduler.BukkitRunnable;

public class ZombieDespawnTask {
    public ZombieDespawnTask() {
        new BukkitRunnable() {
            @Override
            public void run() {
                Bukkit.getWorlds().forEach(world -> world.getEntities().forEach(entity -> {
                    if (entity instanceof Zombie && entity.getCustomName() == null) {
                        Zombie zombie = (Zombie) entity;
                        if (zombie.getEquipment() != null && zombie.getEquipment().getItemInMainHand().getType() != Material.AIR && zombie.getNearbyEntities(64, 64, 64).stream().noneMatch(e -> e instanceof Player)) {
                           zombie.remove();
                        }
                    }
                }));
            }
        }.runTaskTimer(Main.instance, 0, 60 * 20);
    }
}
