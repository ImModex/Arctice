package de.Modex.arctice.skyblock.commands;

import de.Modex.arctice.skyblock.Main;
import de.Modex.arctice.skyblock.utils.CustomCommand;
import de.Modex.arctice.skyblock.utils.Strings;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class chunk extends CustomCommand {
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

        p.sendMessage(Strings.prefix + "§7There are §a" + p.getLocation().getChunk().getPluginChunkTickets().size() + " §7chunk tickets registered here.");

        return true;
    }
}
