package de.Modex.arctice.skyblock.afk;

import de.Modex.arctice.skyblock.commands.nick;
import de.Modex.arctice.skyblock.names.PrefixHandler;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.ServerLoadEvent;

public class AFKListener implements Listener {

    @EventHandler
    public void on(AFKEvent e) {
        e.getPlayer().setSleepingIgnored(true);

        if (nick.nicknames.containsKey(e.getPlayer().getUniqueId()))
            PrefixHandler.updatePrefix(e.getPlayer(), "§l§cAFK §8● §8" + nick.stripColorCodes(nick.nicknames.get(e.getPlayer().getUniqueId())), true);
        else
            PrefixHandler.updatePrefix(e.getPlayer(), "§l§cAFK §8● §8" + e.getPlayer().getName(), true);
    }

    @EventHandler
    public void on(AFKReturnEvent e) {
        e.getPlayer().setSleepingIgnored(false);

        if (nick.nicknames.containsKey(e.getPlayer().getUniqueId()))
            PrefixHandler.updatePrefix(e.getPlayer(), nick.nicknames.get(e.getPlayer().getUniqueId()));
        else
            PrefixHandler.updatePrefix(e.getPlayer());
    }

    @EventHandler
    public void on(PlayerQuitEvent e) {
        e.getPlayer().setSleepingIgnored(false);
    }

    @EventHandler
    public void on(PlayerJoinEvent e) {
        if (AFKManager.fromUUID(e.getPlayer().getUniqueId()) == null)
            AFKManager.afkPlayers.add(new AFKPlayerWrapper(e.getPlayer(), 0, e.getPlayer().getLocation()));
        else
            AFKManager.fromUUID(e.getPlayer().getUniqueId()).setAfkTime(0);
    }

    @EventHandler
    public void on(ServerLoadEvent e) {
        Bukkit.getOnlinePlayers().forEach(player -> {
            if (AFKManager.fromUUID(player.getUniqueId()) == null)
                AFKManager.afkPlayers.add(new AFKPlayerWrapper(player, 0, player.getLocation()));
        });
    }
}
