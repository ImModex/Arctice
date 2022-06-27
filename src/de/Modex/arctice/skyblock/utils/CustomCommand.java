package de.Modex.arctice.skyblock.utils;

import de.Modex.arctice.skyblock.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.io.File;

public abstract class CustomCommand extends Configurable implements CommandExecutor, Listener {

    protected CustomCommand(String... commands) {
        this(null, commands);
    }

    protected CustomCommand(File config, String... commands) {
        super(new Config(config));
        for (String command : commands) {
            Main.instance.getCommand(command).setExecutor(this);
        }
        Bukkit.getPluginManager().registerEvents(this, Main.instance);
    }

    public abstract boolean onCommand(CommandSender sender, String label, String[] args);

    public abstract boolean onConsoleCommand(ConsoleCommandSender sender, String label, String[] args);

    public abstract boolean onPlayerCommand(Player p, String label, String[] args);

    @Override
    public final boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        boolean r1 = false;
        boolean r2 = false;
        r2 = onCommand(sender, label, args);
        if (sender instanceof Player)
            r1 = onPlayerCommand((Player) sender, label, args);
        else if (sender instanceof ConsoleCommandSender)
            r1 = onConsoleCommand((ConsoleCommandSender) sender, label, args);
        return r1 && r2;
    }
}
