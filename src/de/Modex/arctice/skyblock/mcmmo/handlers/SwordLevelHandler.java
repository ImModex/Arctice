package de.Modex.arctice.skyblock.mcmmo.handlers;

import de.Modex.arctice.skyblock.mcmmo.LevelUtils;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class SwordLevelHandler extends LevelHandler {

    public SwordLevelHandler(double broken_blocks, ItemStack tool, Player p) {
        super(broken_blocks, tool, p);
    }

    @Override
    public void level(int level) {
        LevelUtils.addEnchantmentForLevel(tool, level, Enchantment.DAMAGE_ALL);
    }

    @Override
    public void level5() {
        tool.addUnsafeEnchantment(Enchantment.DURABILITY, 4);
        LevelUtils.announceLevel(tool, p, "§a", 5, Sound.ENTITY_WITHER_SPAWN);
    }

    @Override
    public void level10() {
        tool.addUnsafeEnchantment(Enchantment.DURABILITY, 5);
        LevelUtils.announceLevel(tool, p, "§d", 10, Sound.ENTITY_WITHER_DEATH);
    }
}
