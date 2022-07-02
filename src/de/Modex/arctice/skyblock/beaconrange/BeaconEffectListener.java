package de.Modex.arctice.skyblock.beaconrange;

import de.Modex.arctice.skyblock.Main;
import de.Modex.arctice.skyblock.utils.Config;
import net.minecraft.server.level.ChunkMapDistance;
import net.minecraft.server.level.ChunkProviderServer;
import net.minecraft.server.level.TicketType;
import net.minecraft.world.level.ChunkCoordIntPair;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Beacon;
import org.bukkit.craftbukkit.v1_19_R1.CraftWorld;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPotionEffectEvent;
import org.bukkit.event.server.ServerLoadEvent;
import org.bukkit.scheduler.BukkitTask;

import java.io.File;
import java.lang.reflect.Field;
import java.util.*;

public class BeaconEffectListener implements Listener {

    public static BukkitTask beaconTask;
    public static final HashSet<Beacon> beacons = new HashSet<>();
    public static final Config config = new Config(new File("plugins/Arctice/beacons.yml"));

    public BeaconEffectListener() {
        super();
        beaconTask = new BeaconTask().runTaskTimer(Main.instance, 0, 20 * 10);
    }

    @EventHandler
    public void on(ServerLoadEvent e) {
        if (!config.contains("beacons")) return;
        List<Location> beaconLocations = (List<Location>) config.config().get("beacons");
        for (int i = 0; i < beaconLocations.size(); i++) {
            registerBeacon(Set.of(beaconLocations.get(i).getChunk()));
        }
    }

    @EventHandler
    public void on(EntityPotionEffectEvent e) {
        if (e.getCause() != EntityPotionEffectEvent.Cause.BEACON || !(e.getEntity() instanceof Player player) || e.getNewEffect() == null || e.getAction() != EntityPotionEffectEvent.Action.ADDED || player.getActivePotionEffects().stream().anyMatch(potionEffect -> potionEffect.getType().equals(e.getNewEffect().getType())))
            return;

        Location loc = e.getEntity().getLocation();
        Set<Chunk> chunks = new HashSet<>();
        for (int x = loc.getBlockX() - 51; x <= loc.getBlockX() + 51; x += 16) {
            for (int z = loc.getBlockZ() - 51; z <= loc.getBlockZ() + 51; z += 16) {
                chunks.add(loc.getWorld().getChunkAt(x >> 4, z >> 4));
            }
        }

        registerBeacon(chunks);
    }

    private void registerBeacon(Set<Chunk> chunks) {
        chunks.forEach(chunk -> Arrays.stream(chunk.getTileEntities()).forEach(block -> {
            if (block.getType() != Material.BEACON || beacons.contains((Beacon) block.getWorld().getBlockAt(block.getLocation()).getState()))
                return;

            beacons.forEach(beacon -> {
                if (beacon.getLocation().equals(block.getLocation()))
                    beacons.remove(beacon);
            });
            beacons.add((Beacon) block.getWorld().getBlockAt(block.getLocation()).getState());
            List<Location> beaconLocations = (config.contains("beacons")) ? (ArrayList) config.config().getList("beacons") : new ArrayList<>();
            if (!beaconLocations.contains(block.getLocation()))
                beaconLocations.add(block.getLocation());
            config.set("beacons", beaconLocations);

            try {
                Field chunkProviderField = ChunkProviderServer.class.getDeclaredField("c");
                chunkProviderField.setAccessible(true);
                ChunkMapDistance chunkDistanceManager = (ChunkMapDistance) chunkProviderField.get(((CraftWorld) block.getWorld()).getHandle().k());


                for (int x = block.getChunk().getX() - 2; x <= block.getChunk().getX() + 2; x++) {
                    for (int z = block.getChunk().getZ() - 2; z <= block.getChunk().getZ() + 2; z++) {
                        chunkDistanceManager.addRegionTicketAtDistance(TicketType.PLUGIN_TICKET, new ChunkCoordIntPair(x, z), 1, Main.instance);
                        block.getWorld().getChunkAt(x, z);
                    }
                }

            } catch (NoSuchFieldException | IllegalAccessException ex) {
                throw new RuntimeException(ex);
            }


        }));
    }
}
