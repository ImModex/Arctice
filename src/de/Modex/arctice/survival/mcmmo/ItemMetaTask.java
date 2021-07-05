package de.Modex.arctice.survival.mcmmo;

import de.Modex.arctice.survival.Main;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Arrays;

public class ItemMetaTask {
    public ItemMetaTask() {
        new BukkitRunnable() {
            @Override
            public void run() {
                Bukkit.getOnlinePlayers().forEach(player -> player.getInventory().forEach(item -> {
                    if (item != null && LevelUtils.levelRequirements.containsKey(item.getType()) && item.getItemMeta() != null) {
                        ItemMeta meta = item.getItemMeta();
                        NamespacedKey broken = new NamespacedKey(Main.instance, "blocks_broken");
                        PersistentDataContainer container = meta.getPersistentDataContainer();

                        if (container.has(broken, PersistentDataType.DOUBLE)) {
                            double broken_num = container.get(broken, PersistentDataType.DOUBLE);
                            ArrayList<String> lore;
                            String level = "Level: " + LevelUtils.getLevel(item);
                            if(LevelUtils.getLevel(item) == 100)
                                level = "Level: GOD";
                            if (item.getType().name().toLowerCase().contains("sword")) {
                                lore = new ArrayList<>(Arrays.asList("Enemies Killed: " + (int) Math.ceil(broken_num), level));
                            } else {
                                lore = new ArrayList<>(Arrays.asList("Blocks Broken: " + (int) Math.ceil(broken_num), level));
                            }
                            meta.setLore(lore);
                            item.setItemMeta(meta);
                        }
                    }
                }));
            }
        }.runTaskTimerAsynchronously(Main.instance, 0, 200);
    }
}
