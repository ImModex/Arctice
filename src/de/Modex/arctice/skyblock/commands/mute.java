package de.Modex.arctice.skyblock.commands;

import de.Modex.arctice.skyblock.utils.CustomCommand;
import de.Modex.arctice.skyblock.utils.Strings;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.UUID;

public class mute extends CustomCommand {

    public static final ArrayList<UUID> muteList = new ArrayList<>();

    @Override
    public boolean onCommand(CommandSender sender, String label, String[] args) {
        return false;
    }

    @Override
    public boolean onConsoleCommand(ConsoleCommandSender sender, String label, String[] args) {
        return false;
    }

    @Override
    public boolean onPlayerCommand(Player p, String label, String[] args) {

        if (muteList.contains(p.getUniqueId())) {
            muteList.remove(p.getUniqueId());
            p.sendMessage(Strings.prefix + "Chat is turned §aon.");
        } else {
            muteList.add(p.getUniqueId());
            p.sendMessage(Strings.prefix + "Chat is turned §coff.");
        }

        return true;
    }
}
