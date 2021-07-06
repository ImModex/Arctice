package de.Modex.arctice.survival.mcmmo;

import de.Modex.arctice.survival.Main;
import de.Modex.arctice.survival.commands.nick;
import de.Modex.arctice.survival.mcmmo.handlers.*;
import de.Modex.arctice.survival.utils.Strings;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;

public class LevelUtils {

    public static HashMap<Player, FeatureContainer> playerFeatures = new HashMap<>() {{
        Bukkit.getOnlinePlayers().forEach(player -> put(player, new FeatureContainer()));
    }};

    public static HashMap<Material, Double> levelRequirements = new HashMap<>() {{
        put(Material.NETHERITE_AXE, 50000d);
        put(Material.NETHERITE_HOE, 100000d);
        put(Material.NETHERITE_PICKAXE, 100000d);
        put(Material.NETHERITE_SHOVEL, 100000d);
        put(Material.NETHERITE_SWORD, 10000d);
    }};

    public static void levelupTool(double broken_blocks, ItemStack tool, Player p) {
        switch (tool.getType()) {
            case NETHERITE_AXE -> new AxeLevelHandler(broken_blocks, tool, p);
            case NETHERITE_HOE -> new HoeLevelHandler(broken_blocks, tool, p);
            case NETHERITE_PICKAXE -> new PickaxeLevelHandler(broken_blocks, tool, p);
            case NETHERITE_SHOVEL -> new ShovelLevelHandler(broken_blocks, tool, p);
            case NETHERITE_SWORD -> new SwordLevelHandler(broken_blocks, tool, p);
        }
    }

    public static void sendToAll(String message, Sound sound) {
        for (Player all : Bukkit.getOnlinePlayers()) {
            all.sendMessage(message);
            all.playSound(all.getLocation(), sound, 1, 1);
        }
        Bukkit.getConsoleSender().sendMessage(message);
    }

    public static String getToolName(Material tool) {
        if (tool.equals(Material.NETHERITE_AXE))
            return "Netherite Axe";
        else if (tool.equals(Material.NETHERITE_PICKAXE))
            return "Netherite Pickaxe";
        else if (tool.equals(Material.NETHERITE_SHOVEL))
            return "Netherite Shovel";
        else if (tool.equals(Material.NETHERITE_HOE))
            return "Netherite Hoe";
        else if (tool.equals(Material.NETHERITE_SWORD))
            return "Netherite Sword";

        return null;
    }

    public static double getBrokenBlocks(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        double broken_num = 0;

        if (meta != null) {
            NamespacedKey broken = new NamespacedKey(Main.instance, "blocks_broken");
            PersistentDataContainer container = meta.getPersistentDataContainer();
            if (container.has(broken, PersistentDataType.DOUBLE)) {
                broken_num = container.get(broken, PersistentDataType.DOUBLE);
            }
        }

        return broken_num;
    }

    public static void setBrokenBlocks(ItemStack item, double broken_num, Player p) {
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            NamespacedKey broken = new NamespacedKey(Main.instance, "blocks_broken");

            meta.getPersistentDataContainer().set(broken, PersistentDataType.DOUBLE, broken_num);
            item.setItemMeta(meta);

            if (broken_num != 0 && broken_num % (levelRequirements.get(item.getType()) / 10) == 0) {
                levelupTool(broken_num, item, p);
            }
        }
    }

    public static void addEnchantmentForLevel(ItemStack tool, int level, Enchantment enchantment) {
        if (tool.containsEnchantment(enchantment)) {
            if (tool.getEnchantmentLevel(enchantment) < level) {
                tool.addUnsafeEnchantment(enchantment, level);
            }
        } else {
            tool.addUnsafeEnchantment(enchantment, 1);
        }
    }

    public static void announceLevel(ItemStack tool, Player p, String color, int level, Sound sound) {
        if (level == 100) {
            sendToAll(Strings.prefix + "§7Player §6" + nick.getNick(p) + " §7just reached §e§kA §4§lGOD §e§kA §7level with their " + getToolName(tool.getType()) + "!", sound);
            new BukkitRunnable() {
                int count = 0;

                @Override
                public void run() {
                    p.getWorld().strikeLightningEffect(p.getLocation());
                    if (++count > 15)
                        cancel();
                }
            }.runTaskTimer(Main.instance, 0, 5);
        } else {
            sendToAll(Strings.prefix + "§7Player §6" + nick.getNick(p) + " §7just reached level " + color + level + " §7with their " + getToolName(tool.getType()) + "!", sound);
        }
    }

    public static int getLevel(ItemStack tool) {
        if (levelRequirements.get(tool.getType()) != null)
            return (int) Math.floor(getBrokenBlocks(tool) / (levelRequirements.get(tool.getType()) / 10));
        return 0;
    }
}
