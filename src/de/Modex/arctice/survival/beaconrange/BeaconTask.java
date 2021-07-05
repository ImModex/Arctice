package de.Modex.arctice.survival.beaconrange;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Beacon;
import org.bukkit.scheduler.BukkitRunnable;

public class BeaconTask extends BukkitRunnable {
    @Override
    public void run() {
        BeaconEffectListener.beacons.forEach(beacon -> {
            if (beacon.getWorld().getBlockAt(beacon.getLocation()).getType() != Material.BEACON) {
                BeaconEffectListener.beacons.remove(beacon);
            } else {
                Beacon b = (Beacon) beacon.getWorld().getBlockAt(beacon.getLocation()).getState();
                Bukkit.getOnlinePlayers().forEach(player -> {
                    if (player.getWorld().getEnvironment() == b.getWorld().getEnvironment()) {
                        if (player.getLocation().distance(b.getLocation()) <= maxDistance(b.getTier())) {
                            if (b.getPrimaryEffect() != null)
                                player.addPotionEffect(b.getPrimaryEffect());
                            if (b.getSecondaryEffect() != null)
                                player.addPotionEffect(b.getSecondaryEffect());
                        }
                    }
                });
            }
        });
    }

    private int maxDistance(int level) {
        switch (level) {
            case 1:
                return 20 * 5;
            case 2:
                return 30 * 5;
            case 3:
                return 40 * 5;
            case 4:
                return 50 * 5;
        }

        return 0;
    }
}
