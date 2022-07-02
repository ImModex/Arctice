package de.Modex.arctice.skyblock.afk;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class AFKTask extends BukkitRunnable {

    @Override
    public void run() {
        Bukkit.getOnlinePlayers().forEach(player -> {
            if (!AFKManager.toPlayerList(AFKManager.afkPlayers).contains(player))
                AFKManager.afkPlayers.add(new AFKPlayerWrapper(player, 0, player.getLocation()));
            else {
                AFKPlayerWrapper afkPlayer = AFKManager.fromUUID(player.getUniqueId());

                if (!AFKManager.playerOf(afkPlayer).getLocation().equals(afkPlayer.getLocation())) {
                    if(AFKManager.isAFK(player))
                        Bukkit.getPluginManager().callEvent(new AFKReturnEvent(AFKManager.playerOf(afkPlayer)));
                } else {
                    int afkTime = afkPlayer.getAfkTime();
                    AFKManager.afkPlayers.get(AFKManager.afkPlayers.indexOf(afkPlayer)).setAfkTime(afkTime + 10);
                    if (afkTime >= 60)
                        Bukkit.getPluginManager().callEvent(new AFKEvent(AFKManager.playerOf(afkPlayer)));
                }
            }
        });
    }
}