package de.Modex.arctice.skyblock.listener;

import de.Modex.arctice.skyblock.commands.mute;
import de.Modex.arctice.skyblock.ranks.Rank;
import de.Modex.arctice.skyblock.ranks.RankManager;
import de.Modex.arctice.skyblock.utils.Permissions;
import de.Modex.arctice.skyblock.utils.Strings;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import ru.tehkode.permissions.bukkit.PermissionsEx;

import java.util.HashMap;

public class PlayerChatListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void on(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        String msg = e.getMessage();

        if (mute.muteList.contains(p.getUniqueId())) {
            e.setCancelled(true);
            return;
        }

        if (PermissionsEx.getUser(p).has(Permissions.chat_color)) {
            msg = msg.replaceAll("&", "§");
            msg = Strings.translateHex(msg);
        }

        for (Rank rank : RankManager.getRanks().values()) {
            if (PermissionsEx.getUser(p).inGroup(rank.getName())) {
                sendToAll(p.getDisplayName() + " §7» " + msg);
                break;
            }
        }
        e.setCancelled(true);
    }

    private void sendToAll(String message) {
        for (Player all : Bukkit.getOnlinePlayers()) {
            if (mute.muteList.contains(all.getUniqueId())) return;
            all.sendMessage(message);
        }
        Bukkit.getConsoleSender().sendMessage(message);
    }
}
