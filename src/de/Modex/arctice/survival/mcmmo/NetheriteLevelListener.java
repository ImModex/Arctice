package de.Modex.arctice.survival.mcmmo;

import net.minecraft.world.level.block.entity.TileEntity;
import org.bukkit.craftbukkit.v1_17_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_17_R1.block.CraftBlock;
import org.bukkit.entity.Animals;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

public class NetheriteLevelListener implements Listener {

    @EventHandler
    public void on(BlockBreakEvent e) {
        Player p = e.getPlayer();
        ItemStack tool = p.getInventory().getItemInMainHand();

        if (LevelUtils.levelRequirements.containsKey(tool.getType()) && e.getBlock().isPreferredTool(tool)) {
            LevelUtils.setBrokenBlocks(tool, LevelUtils.getBrokenBlocks(tool) + 1, p);
        }
    }

    @EventHandler
    public void on(EntityDeathEvent e) {
        LivingEntity entity = e.getEntity();

        if (entity.getKiller() != null && (entity instanceof Animals || entity instanceof Monster)) {
            Player p = entity.getKiller();
            ItemStack tool = p.getInventory().getItemInMainHand();

            if (p.getInventory().getItemInMainHand().getType().name().toLowerCase().contains("sword") && LevelUtils.levelRequirements.containsKey(tool.getType())) {
                LevelUtils.setBrokenBlocks(tool, LevelUtils.getBrokenBlocks(tool) + 1, p);
            }
        }
    }
}
