package de.Modex.arctice.survival.listener;

import de.Modex.arctice.survival.commands.nick;
import de.Modex.arctice.survival.names.PrefixHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class JoinQuitListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        if (nick.nicknames.containsKey(p.getUniqueId()))
            PrefixHandler.updatePrefix(p, nick.nicknames.get(p.getUniqueId()));
        else
            PrefixHandler.updatePrefix(p);

        e.setJoinMessage("§8[§a+§8] §8" + nick.getNick(p));
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        e.setQuitMessage("§8[§c-§8] §8" + nick.getNick(p));
    }
}
