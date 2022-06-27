package de.Modex.arctice.skyblock.mcmmo;

import de.Modex.arctice.skyblock.utils.CustomCommand;
import de.Modex.arctice.skyblock.utils.Permissions;
import de.Modex.arctice.skyblock.utils.Strings;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class level extends CustomCommand {
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

        if (PermissionsEx.getUser(p).has(Permissions.level)) {
            if (args.length == 1) {
               double points = Double.parseDouble(args[0]);
                LevelUtils.setBrokenBlocks(p.getInventory().getItemInMainHand(), points, p);
                p.sendMessage(Strings.prefix + "§7You set your current tool's broken blocks to -> §a" + (int) points);
            } else {
                p.sendMessage(Strings.prefix + "§c/level <points>");
            }
        }

        return true;
    }
}
