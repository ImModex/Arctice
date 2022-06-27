package de.Modex.arctice.skyblock.silkspawners;

import de.Modex.arctice.skyblock.Main;
import org.bukkit.Material;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Collections;

public class SpawnerBreakListener implements Listener {

    @EventHandler
    public void onBreak(SpawnerBreakEvent e) {
        CreatureSpawner cs = (CreatureSpawner) e.getSpawner().getState();

        ItemStack spawner = new ItemStack(Material.SPAWNER, 1);
        ItemMeta meta = spawner.getItemMeta();

        assert meta != null;

        meta.setLore(Collections.singletonList(getEntityString(cs.getSpawnedType())));
        spawner.setItemMeta(meta);

        e.getSpawner().getLocation().getWorld().dropItemNaturally(e.getSpawner().getLocation(), spawner);
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        if (e.getBlockPlaced().getType().equals(Material.SPAWNER)) {
            if (e.getItemInHand().getItemMeta() != null && e.getItemInHand().getItemMeta().getLore() != null) {
                final EntityType type = EntityType.valueOf(e.getItemInHand().getItemMeta().getLore().get(0).toUpperCase().replaceAll(" ", "_"));

                new BukkitRunnable() {
                    @Override
                    public void run() {
                        CreatureSpawner cs = (CreatureSpawner) e.getBlockPlaced().getState();
                        cs.setSpawnedType(type);
                        cs.update();
                    }
                }.runTaskLater(Main.instance, 1);
            }
        }
    }

    private String getEntityString(EntityType type) {
        String ret;

        ret = type.getName().substring(0, 1).toUpperCase() + type.getName().substring(1);
        ret = ret.replaceAll("_", " ");

        return ret;
    }
}
