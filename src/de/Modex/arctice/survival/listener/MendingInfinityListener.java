package de.Modex.arctice.survival.listener;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;

public class MendingInfinityListener implements Listener {

    @EventHandler
    public void on(PrepareAnvilEvent e) {
        AnvilInventory inventory = e.getInventory();

        if ((inventory.getContents()[0] != null && inventory.getContents()[0].getType().equals(Material.BOW) && inventory.getContents()[0].containsEnchantment(Enchantment.ARROW_INFINITE)) && (inventory.getContents()[1] != null && inventory.getContents()[1].getType().equals(Material.ENCHANTED_BOOK))) {

            if (((EnchantmentStorageMeta) inventory.getContents()[1].getItemMeta()).hasStoredEnchant(Enchantment.MENDING)) {
                ItemStack result = new ItemStack(inventory.getContents()[0]);
                result.addUnsafeEnchantment(Enchantment.MENDING, 1);
                ItemMeta meta = result.getItemMeta();
                meta.setDisplayName(inventory.getRenameText());
                result.setItemMeta(meta);
                inventory.setRepairCost(30);
                e.setResult(result);
            }
        }
    }
}
