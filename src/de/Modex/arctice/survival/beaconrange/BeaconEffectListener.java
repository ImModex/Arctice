package de.Modex.arctice.survival.beaconrange;

import de.Modex.arctice.survival.Main;
import de.Modex.arctice.survival.utils.CustomListener;
import org.bukkit.Material;
import org.bukkit.block.Beacon;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityPotionEffectEvent;
import org.bukkit.scheduler.BukkitTask;

import java.util.Arrays;
import java.util.HashSet;

public class BeaconEffectListener extends CustomListener {

    public static BukkitTask beaconTask;
    public static final HashSet<Beacon> beacons = new HashSet<>();

    public BeaconEffectListener() {
        super();
        beaconTask = new BeaconTask().runTaskTimer(Main.instance, 0, 60);
    }

    @EventHandler
    public void on(EntityPotionEffectEvent e) {
        if (e.getCause() != EntityPotionEffectEvent.Cause.BEACON || !(e.getEntity() instanceof Player) || e.getAction() != EntityPotionEffectEvent.Action.ADDED)
            return;

        Arrays.stream(e.getEntity().getWorld().getLoadedChunks()).forEach(chunk -> Arrays.stream(chunk.getTileEntities()).forEach(block -> {
            if (block.getType() != Material.BEACON || beacons.contains((Beacon) block.getWorld().getBlockAt(block.getLocation()).getState()))
                return;

            beacons.forEach(beacon -> {
                if (beacon.getLocation().equals(block.getLocation()))
                    beacons.remove(beacon);
            });
            beacons.add((Beacon) block.getWorld().getBlockAt(block.getLocation()).getState());
        }));
    }
}
