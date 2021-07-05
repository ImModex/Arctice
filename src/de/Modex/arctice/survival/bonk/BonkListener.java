package de.Modex.arctice.survival.bonk;

import de.Modex.arctice.survival.Main;
import de.Modex.arctice.survival.utils.CustomListener;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Random;

public class BonkListener extends CustomListener {

    private static final ArrayList<Player> bonkCooldown = new ArrayList<>();

    @EventHandler
    public void on(EntityDamageByEntityEvent e) {
        if (e.getEntity() instanceof Player && e.getDamager() instanceof Player) {
            Player p = (Player) e.getDamager();
            Player target = (Player) e.getEntity();

            if (!bonkCooldown.contains(p)) {
                if (p.getInventory().getItemInMainHand().getType().equals(Material.BAMBOO)) {
                    if (p.getInventory().getItemInMainHand().getItemMeta() != null && p.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equalsIgnoreCase("bonk stick")) {
                        bonkCooldown.add(p);

                        Bukkit.getOnlinePlayers().forEach(player -> {
                            String message = "§7" + p.getName() + " bonked " + target.getName() + ", NO HORNY";

                            if (new Random().nextInt(1001) <= 1)
                                message = "§7" + p.getName() + " boned " + target.getName() + ", NO HORNY";
                            player.sendMessage(message);
                            player.playSound(player.getLocation(), Sound.BLOCK_BAMBOO_HIT, 1f, 1.5f);
                        });

                        Bukkit.getConsoleSender().sendMessage("§7" + p.getName() + " bonked " + target.getName() + ", NO HORNY");

                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                bonkCooldown.remove(p);
                            }
                        }.runTaskLaterAsynchronously(Main.instance, 20 * 10);
                    }
                }
            }
        }
    }
}
