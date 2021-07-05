package de.Modex.arctice.survival.listener;

import de.Modex.arctice.survival.commands.mute;
import de.Modex.arctice.survival.ranks.Rank;
import de.Modex.arctice.survival.ranks.RankManager;
import de.Modex.arctice.survival.utils.Permissions;
import de.Modex.arctice.survival.utils.Strings;
import net.md_5.bungee.api.ChatColor;
import net.minecraft.util.Tuple;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import ru.tehkode.permissions.bukkit.PermissionsEx;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PlayerChatListener implements Listener {


    @EventHandler(priority = EventPriority.LOWEST)
    public void on(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        String msg = e.getMessage();

        if (mute.getMuteList().contains(p)) {
            e.setCancelled(true);
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
            all.sendMessage(message);
        }
        Bukkit.getConsoleSender().sendMessage(message);
    }
}
