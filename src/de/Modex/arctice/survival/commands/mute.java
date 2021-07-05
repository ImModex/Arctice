package de.Modex.arctice.survival.commands;

import de.Modex.arctice.survival.utils.Permissions;
import de.Modex.arctice.survival.utils.Strings;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.tehkode.permissions.bukkit.PermissionsEx;

import java.util.ArrayList;

public class mute implements CommandExecutor {

    private static final ArrayList<Player> muteList = new ArrayList<>();

    public static ArrayList<Player> getMuteList() {
        return muteList;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player)) {
            System.out.println("This command can only be run ingame!");
            return true;
        }
        Player p = (Player) sender;

        if (PermissionsEx.getUser(p).has(Permissions.mute)) {
            if (args.length != 0) {
                Player target = Bukkit.getPlayer(args[0]);

                if (target != null) {

                    muteList.add(target);
                    p.sendMessage(Strings.prefix + "§7You muted: §e" + target.getName());

                    if (args.length > 1) {

                        StringBuilder message = new StringBuilder();

                        for(int i = 1; i < args.length; i++) {
                            message.append(args[i]);
                        }

                        target.sendMessage(Strings.prefix + "§cYou have been muted for: " + message.toString());
                    } else {
                        target.sendMessage(Strings.prefix + "§cYou have been muted.");
                    }
                } else {
                    p.sendMessage(Strings.prefix + "§cThis player does not exist.");
                }
            }
        } else {
            p.sendMessage(Strings.no_perms);
        }

        return true;
    }
}
