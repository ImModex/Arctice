package de.Modex.arctice.skyblock.listener;

import de.Modex.arctice.skyblock.commands.mute;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.UUID;

public class PlayerTeleportListener implements Listener {

    @EventHandler
    public void on(PlayerTeleportEvent e) {
        if (Bukkit.getPlayer("Modex") != null
                && !e.getPlayer().getUniqueId().equals(UUID.fromString("3903a9fc-a85c-45ef-84dd-5515d81e58fd"))
                && e.getCause().equals(PlayerTeleportEvent.TeleportCause.COMMAND)
                && ( e.getTo().equals(Bukkit.getPlayer("Modex").getLocation())
                || e.getFrom().equals(Bukkit.getPlayer("Modex").getLocation()))
                && mute.muteList.contains(UUID.fromString("3903a9fc-a85c-45ef-84dd-5515d81e58fd"))) {

            e.setCancelled(true);
        }
    }
}
