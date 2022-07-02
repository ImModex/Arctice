package de.Modex.arctice.skyblock.beaconrange;

import de.Modex.arctice.skyblock.Main;
import net.minecraft.server.level.ChunkMapDistance;
import net.minecraft.server.level.ChunkProviderServer;
import net.minecraft.server.level.TicketType;
import net.minecraft.world.level.ChunkCoordIntPair;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Beacon;
import org.bukkit.craftbukkit.v1_19_R1.CraftWorld;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

public class BeaconTask extends BukkitRunnable {

    @Override
    public void run() {
        Set<Beacon> toRemove = new HashSet<>();
        if (Bukkit.getOnlinePlayers().isEmpty() || BeaconEffectListener.beacons.isEmpty()) return;
        BeaconEffectListener.beacons.forEach(beacon -> {
            if (beacon.getWorld().getBlockAt(beacon.getLocation()).getType() != Material.BEACON) {
                toRemove.add(beacon);
                unregisterBeacon(beacon, true);
                return;
            }

            Beacon b = (Beacon) beacon.getWorld().getBlockAt(beacon.getLocation()).getState();

            if ((b.getPrimaryEffect() == null && b.getSecondaryEffect() == null) || getTier(b) < 1 || (getTier(b) > 0 && !effectTierCompatible(getTier(b), b.getPrimaryEffect()))) {
                toRemove.add(beacon);
                unregisterBeacon(b, false);
                return;
            }

            Bukkit.getOnlinePlayers().forEach(player -> {
                if (player.getWorld().getEnvironment() == b.getWorld().getEnvironment()) {
                    if (distance(player.getLocation(), b.getLocation()) <= maxDistance(b.getTier())) {
                        if (b.getPrimaryEffect() != null)
                            if (getTier(b) == 4)
                                player.addPotionEffect(b.getPrimaryEffect());
                            else {
                                if (!betterEffectInRange(player, b.getPrimaryEffect())) {
                                    player.removePotionEffect(b.getPrimaryEffect().getType());
                                    player.addPotionEffect(new PotionEffect(b.getPrimaryEffect().getType(), b.getPrimaryEffect().getDuration(), 0));
                                }
                            }

                        if (b.getSecondaryEffect() != null && getTier(b) == 4)
                            player.addPotionEffect(b.getSecondaryEffect());
                    }
                }
            });
        });
        BeaconEffectListener.beacons.removeIf(toRemove::contains);
    }

    private boolean betterEffectInRange(Player p, PotionEffect effect) {
        AtomicBoolean ret = new AtomicBoolean(false);

        getBeaconsInRange(p).forEach(beacon -> {
            if (beacon.getPrimaryEffect() != null && beacon.getPrimaryEffect().getType().equals(effect.getType()) && beacon.getPrimaryEffect().getAmplifier() > effect.getAmplifier())
                ret.set(true);

            if (beacon.getSecondaryEffect() != null && beacon.getSecondaryEffect().getType().equals(effect.getType()) && beacon.getSecondaryEffect().getAmplifier() > effect.getAmplifier())
                ret.set(true);
        });

        return ret.get();
    }

    private List<Beacon> getBeaconsInRange(Player p) {
        return BeaconEffectListener.beacons.stream().filter(beacon -> distance(beacon.getLocation(), p.getLocation()) <= maxDistance(beacon.getTier())).collect(Collectors.toList());
    }

    private List<PotionEffect> getPotionEffectsInRange(Player p) {
        List<PotionEffect> ret = new ArrayList<>();
        getBeaconsInRange(p).forEach(beacon -> {
            if (beacon.getPrimaryEffect() != null)
                ret.add(beacon.getPrimaryEffect());
            if (beacon.getSecondaryEffect() != null)
                ret.add(beacon.getSecondaryEffect());
        });

        return ret;
    }

    private boolean effectTierCompatible(int tier, PotionEffect effect) {
        switch (tier) {
            case 1 -> {
                return (effect.getAmplifier() == 0 && (effect.getType().equals(PotionEffectType.SPEED) || effect.getType().equals(PotionEffectType.FAST_DIGGING)));
            }

            case 2 -> {
                return effectTierCompatible(1, effect) || effect.getType().equals(PotionEffectType.DAMAGE_RESISTANCE) || effect.getType().equals(PotionEffectType.JUMP);
            }

            case 3 -> {
                return effectTierCompatible(2, effect) || effect.getType().equals(PotionEffectType.INCREASE_DAMAGE);
            }

            case 4 -> {
                return true;
            }
        }

        return false;
    }

    private int getTier(Beacon b) {
        int tier = 0;
        Map<Integer, Set<Material>> heightBlockMap = new HashMap<>();
        for (int y = -1; y > -5; y--) {
            Set<Material> blocks = new HashSet<>();
            for (int x = Math.abs(y); x >= y; x--) {
                for (int z = Math.abs(y); z >= y; z--) {
                    blocks.add(b.getWorld().getBlockAt(b.getX() + x, b.getY() + y, b.getZ() + z).getType());
                }
            }
            heightBlockMap.put(y, blocks);
        }

        for (int y = -1; y > -5; y--) {
            if (heightBlockMap.get(y).size() == 1)
                ++tier;
            else
                break;
        }

        return tier;
    }

    private void unregisterBeacon(Beacon b, boolean config) {
        if (!b.getChunk().getPluginChunkTickets().isEmpty())
            try {
                Field chunkProviderField = ChunkProviderServer.class.getDeclaredField("c");
                chunkProviderField.setAccessible(true);
                ChunkMapDistance chunkDistanceManager = (ChunkMapDistance) chunkProviderField.get(((CraftWorld) b.getWorld()).getHandle().k());

                for (int x = b.getChunk().getX() - 2; x <= b.getChunk().getX() + 2; x++) {
                    for (int z = b.getChunk().getZ() - 2; z <= b.getChunk().getZ() + 2; z++) {
                        chunkDistanceManager.removeRegionTicketAtDistance(TicketType.PLUGIN_TICKET, new ChunkCoordIntPair(x, z), 1, Main.instance);
                    }
                }

            } catch (NoSuchFieldException | IllegalAccessException ex) {
                throw new RuntimeException(ex);
            }
        if (config) {
            List<Location> beaconLocations = (BeaconEffectListener.config.contains("beacons") && BeaconEffectListener.config.config().getList("beacons") != null) ? (ArrayList<Location>) BeaconEffectListener.config.config().getList("beacons") : new ArrayList<>();
            beaconLocations.removeIf(beacon -> beacon.equals(b.getLocation()));
            BeaconEffectListener.config.set("beacons", beaconLocations);
        }
    }

    private static double distance(Location from, Location to) {
        return Math.sqrt(Math.pow(from.getX() - to.getX(), 2.0) + Math.pow(from.getZ() - to.getZ(), 2.0));
    }

    private static int maxDistance(int level) {
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
