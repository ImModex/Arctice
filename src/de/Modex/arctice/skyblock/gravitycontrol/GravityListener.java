package de.Modex.arctice.skyblock.gravitycontrol;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.FallingBlock;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.Vector;

import java.util.Set;

public class GravityListener implements Listener {

    final double horizontalCoefficient = 1.46D;
    final double verticalCoefficient = -2.4D;
    private static final Set<Vector> DIRECTIONS = Set.of(
            new Vector(0, 0, -1),
            new Vector(1, 0, 0),
            new Vector(0, 0, 1),
            new Vector(-1, 0, 0)
    );

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntityChangeBlock(final EntityChangeBlockEvent event) {
        if (event.getTo() == Material.AIR || !(event.getEntity() instanceof final FallingBlock falling)) {
            return;
        }

        final BoundingBox boundingBox = falling.getBoundingBox().expand(-0.01D);

        for (Vector direction : DIRECTIONS) {
            final Location loc = event.getBlock().getLocation().add(direction);
            if (!loc.getWorld().isChunkLoaded(loc.getBlockX() >> 4, loc.getBlockZ() >> 4)) {
                continue;
            }

            final Block block = loc.getBlock();
            if (block.getType() != Material.END_PORTAL || !block.getBoundingBox().overlaps(boundingBox)) {
                continue;
            }

            final Vector velocity = falling.getVelocity();

            if (velocity.getX() == 0 && velocity.getZ() == 0) {
                loc.getWorld().spawnFallingBlock(falling.getLocation().add(direction.getX() * 0.25D, 0.05D, direction.getZ() * 0.25D), falling.getBlockData());
            } else {
                final Vector endVelocity = velocity
                        .setX(velocity.getX() * horizontalCoefficient)
                        .setY(velocity.getY() * verticalCoefficient)
                        .setZ(velocity.getZ() * horizontalCoefficient);

                falling.getWorld().spawnFallingBlock(
                        falling.getLocation().add(direction.getX() * 0.25D, direction.getY() * 0.25D, direction.getZ() * 0.25D),
                        falling.getBlockData()
                ).setVelocity(endVelocity);
            }
        }
    }
}