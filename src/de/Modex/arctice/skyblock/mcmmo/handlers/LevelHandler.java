package de.Modex.arctice.skyblock.mcmmo.handlers;

import de.Modex.arctice.skyblock.mcmmo.LevelUtils;
import de.Modex.arctice.skyblock.utils.Strings;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

public abstract class LevelHandler {

    protected double broken_blocks;
    protected ItemStack tool;
    protected Player p;

    public LevelHandler(double broken_blocks, ItemStack tool, Player p) {
        this.broken_blocks = broken_blocks;
        this.tool = tool;
        this.p = p;
        _level();
    }

    public void _level() {
        int level = LevelUtils.getLevel(tool);
        p.sendMessage(Strings.prefix + "§7Your tool just leveled to level §a" + level);

        if (level == 100)
            levelGod();

        if (level > 10)
            return;

        level(level);
        if (level == 5)
            level5();
        else if (level == 10)
            level10();
    }

    public abstract void level(int level);

    public abstract void level5();

    public abstract void level10();

    private void levelGod() {
        LevelUtils.announceLevel(tool, p, "§4§l", 100, Sound.ENTITY_ENDER_DRAGON_DEATH);
        ItemMeta meta = tool.getItemMeta();

        if (meta != null) {
            Damageable damage = (Damageable) tool.getItemMeta();
            tool.setItemMeta((ItemMeta) damage);
            meta = tool.getItemMeta();
            meta.setUnbreakable(true);
            tool.setItemMeta(meta);
        }

        tool.removeEnchantment(Enchantment.DURABILITY);
        tool.removeEnchantment(Enchantment.MENDING);
    }
}
