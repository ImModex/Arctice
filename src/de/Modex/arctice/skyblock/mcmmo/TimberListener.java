package de.Modex.arctice.skyblock.mcmmo;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Random;

public class TimberListener implements Listener {

    @EventHandler
    public void on(PlayerJoinEvent e) {
        if (!LevelUtils.playerFeatures.containsKey(e.getPlayer().getUniqueId())) {
            LevelUtils.playerFeatures.put(e.getPlayer().getUniqueId(), new FeatureContainer());
        }
    }

    @EventHandler
    public void on(PlayerItemHeldEvent e) {
        Player p = e.getPlayer();
        if (p.getInventory().getItem(e.getNewSlot()) != null && p.getInventory().getItem(e.getNewSlot()).getType().equals(Material.NETHERITE_AXE) && LevelUtils.getLevel(p.getInventory().getItem(e.getNewSlot())) >= 10) {
            if (LevelUtils.playerFeatures.get(p.getUniqueId()).isTimberMode()) {
                p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("§7Timber Mode §aON"));
            } else {
                p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("§7Timber Mode §cOFF"));
            }
        }
    }

    @EventHandler
    public void on(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if (!CooldownHandler.isTimberCooldown(p)) {
            if (LevelUtils.getLevel(p.getInventory().getItemInMainHand()) >= 10 && (e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK))) {
                if (!LevelUtils.playerFeatures.get(p.getUniqueId()).isTimberMode()) {
                    LevelUtils.playerFeatures.put(p.getUniqueId(), LevelUtils.playerFeatures.get(p.getUniqueId()).setTimberMode(true));
                    p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("§7Timber Mode §aON"));
                } else {
                    LevelUtils.playerFeatures.put(p.getUniqueId(), LevelUtils.playerFeatures.get(p.getUniqueId()).setTimberMode(false));
                    p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("§7Timber Mode §cOFF"));
                }
                CooldownHandler.startCooldown(p, 60, CooldownReason.TIMBER, null);
            }
        }
    }

    @EventHandler
    public void on(BlockBreakEvent e) {
        Player p = e.getPlayer();
        Block b = e.getBlock();

        if (LevelUtils.playerFeatures.get(p.getUniqueId()).isTimberMode() && LevelUtils.getLevel(p.getInventory().getItemInMainHand()) >= 10 && (b.getType().name().contains("LOG") || b.getType().name().contains("STEM"))) {
            breakTree(b, p.getInventory().getItemInMainHand(), p);
        }
    }

    private void breakTree(Block b, ItemStack item, Player p) {
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
                    breakTree(next, item, p);
                    LevelUtils.setBrokenBlocks(item, LevelUtils.getBrokenBlocks(item) + 1, p);
                }
            }
        }
    }
}
