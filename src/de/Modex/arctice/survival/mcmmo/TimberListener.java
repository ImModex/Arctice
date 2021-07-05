package de.Modex.arctice.survival.mcmmo;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Random;

public class TimberListener implements Listener {

    @EventHandler
    public void on(PlayerMoveEvent e) {
        if (e.getPlayer().getInventory().getItemInMainHand().getType().name().endsWith("_AXE") && LevelUtils.getLevel(e.getPlayer().getInventory().getItemInMainHand()) >= 10) {
            Player p = e.getPlayer();
            if (!p.isSneaking()) {
                p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("§7Timber Mode §aON"));
            } else {
                p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(""));
            }
        }
    }

    @EventHandler
    public void on(BlockBreakEvent e) {
        Player p = e.getPlayer();
        Block b = e.getBlock();

        if (!p.isSneaking() && LevelUtils.getLevel(p.getInventory().getItemInMainHand()) >= 10 && (b.getType().name().contains("LOG") || b.getType().name().contains("STEM"))) {
            breakTree(b, p.getInventory().getItemInMainHand());
        }
    }

    private void breakTree(Block b, ItemStack item) {
        if (item.getItemMeta() instanceof Damageable) {
            Damageable damageable = (Damageable) item.getItemMeta();

            if (item.getType().getMaxDurability() - damageable.getDamage() >= 0) {
                b.breakNaturally();
                if (!item.getItemMeta().isUnbreakable()) {
                    if (item.containsEnchantment(Enchantment.DURABILITY)) {
                        if (new Random().nextInt(100) < (100 / (item.getEnchantmentLevel(Enchantment.DURABILITY) + 1))) {
                            damageable.setDamage(damageable.getDamage() + 1);
                        }
                    } else {
                        damageable.setDamage(damageable.getDamage() + 1);
                    }
                    item.setItemMeta((ItemMeta) damageable);
                }
                Block next = b.getWorld().getBlockAt(b.getX(), b.getY() + 1, b.getZ());
                if (next.getType().name().contains("LOG") || next.getType().name().contains("STEM")) {
                    breakTree(next, item);
                }
            }
        }
    }
}
