package de.Modex.arctice.skyblock.afk;

import de.Modex.arctice.skyblock.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AFKManager {

    private final AFKTask afkTask;
    private final AFKReturnTask returnTask;
    protected static final List<AFKPlayerWrapper> afkPlayers = new ArrayList<>();

    public AFKManager() {
        afkTask = new AFKTask();
        afkTask.runTaskTimer(Main.instance, 0, 200);

        returnTask = new AFKReturnTask();
        returnTask.runTaskTimer(Main.instance, 0, 20);
    }

    public AFKTask getAfkTask() {
        return afkTask;
    }

    public AFKReturnTask getReturnTask() {
        return returnTask;
    }

    public static List<UUID> getPlayers() {
        List<UUID> ret = new ArrayList<>();
        afkPlayers.forEach(afkPlayer -> ret.add(afkPlayer.getPlayer()));
        return ret;
    }

    public static Player playerOf(AFKPlayerWrapper afkPlayer) {
        return Bukkit.getPlayer(afkPlayer.getPlayer());
    }

    public static AFKPlayerWrapper fromUUID(UUID uuid) {
        return afkPlayers.stream().filter(afkPlayer -> afkPlayer.getPlayer().equals(uuid)).findFirst().orElse(null);
    }

    public static boolean isAFK(Player p) {
        return fromUUID(p.getUniqueId()).getAfkTime() > 60;
    }
}
