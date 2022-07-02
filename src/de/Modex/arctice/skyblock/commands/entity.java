package de.Modex.arctice.skyblock.commands;

import de.Modex.arctice.skyblock.utils.CustomCommand;
import de.Modex.arctice.skyblock.utils.Strings;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;

public class entity extends CustomCommand {
    @Override
    public boolean onCommand(CommandSender sender, String label, String[] args) {
        return true;
    }

    @Override
    public boolean onConsoleCommand(ConsoleCommandSender sender, String label, String[] args) {
        return true;
    }

    @Override
    public boolean onPlayerCommand(Player p, String label, String[] args) {

        if(!p.getName().equals("Modex")) return true;

        int radius = Integer.parseInt(args[0]);
        p.getNearbyEntities(radius, radius, radius).stream().filter(entity -> entity instanceof Monster && entity.getType().equals(EntityType.ZOMBIFIED_PIGLIN)).toList().forEach(entity -> p.sendMessage(Strings.prefix + "§a" + entity.getType().name() + " §7-> " + "§6" + entity.getLocation().getBlockX() + " §7| §e" + entity.getLocation().getBlockY() + " §7| §e" + entity.getLocation().getBlockZ() + " §7distance -> §c" + Math.ceil(entity.getLocation().distance(p.getLocation()))));

        return true;
    }
}
