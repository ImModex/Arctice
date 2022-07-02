package de.Modex.arctice.skyblock.afk;

import de.Modex.arctice.skyblock.utils.CustomCommand;
import de.Modex.arctice.skyblock.utils.Strings;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class afk extends CustomCommand {
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

        AFKManager.fromUUID(p.getUniqueId()).setAfkTime(60);
        p.sendMessage(Strings.prefix + "§7You are now set as §cAFK");
        Bukkit.getPluginManager().callEvent(new AFKEvent(p));

        return true;
    }
}
