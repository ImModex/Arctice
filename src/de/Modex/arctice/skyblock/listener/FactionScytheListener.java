package de.Modex.arctice.skyblock.listener;

import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.Directional;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class FactionScytheListener implements Listener {

    private static final ArrayList<Material> harvestable = new ArrayList<>(List.of(Material.POTATOES, Material.CARROTS, Material.WHEAT, Material.NETHER_WART, Material.COCOA));
    private static final ArrayList<Material> seeds = new ArrayList<>(List.of(Material.POTATO, Material.CARROT, Material.WHEAT_SEEDS, Material.NETHER_WART, Material.COCOA_BEANS));

    @EventHandler
    public void on(BlockBreakEvent e) {
        ItemStack hoe = e.getPlayer().getInventory().getItemInMainHand();
        if (hoe.getType().name().toLowerCase().contains("hoe")) {
            Material block = e.getBlock().getType();
            if (harvestable.contains(block)) {
                Collection<ItemStack> drops = e.getBlock().getDrops(hoe, e.getPlayer());
                for (ItemStack drop : drops) {
                    if (seeds.contains(drop.getType())) {
                        if (drop.getAmount() == 1)
                            drops.remove(drop);
                        else
                            drop.setAmount(drop.getAmount() - 1);
                        break;
                    }
                }

                BlockState bs = e.getBlock().getState();
                Directional direction = null;
                if (bs.getBlockData() instanceof Directional) {
                    direction = (Directional) bs.getBlockData();
                }

                e.getPlayer().getInventory().addItem(drops.toArray(ItemStack[]::new));
                e.getBlock().setType(Material.AIR);
                e.getBlock().setType(block);

                if (direction != null) {
                    ((Directional) bs.getBlockData()).setFacing(direction.getFacing());
                }

                if (bs.getBlockData() instanceof Ageable) {
                    Ageable ageable = (Ageable) bs.getBlockData();
                    ageable.setAge(0);
                    bs.setBlockData(ageable);
                }
                bs.update();
                e.setCancelled(true);
            }
        }
    }
}
