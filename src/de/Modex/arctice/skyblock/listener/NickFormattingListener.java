package de.Modex.arctice.skyblock.listener;

import de.Modex.arctice.skyblock.commands.nick;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class NickFormattingListener implements Listener {

    @EventHandler
    public void on(PlayerDeathEvent e) {
        String message = e.getDeathMessage();
        StringBuilder builder = new StringBuilder();
        String[] str = message.split(" ");
        str[0] = nick.getNick(Bukkit.getPlayer(str[0])) + "§r";

        for (String s : str)
            builder.append(s).append(" ");

        e.setDeathMessage(builder.toString());
    }

}
