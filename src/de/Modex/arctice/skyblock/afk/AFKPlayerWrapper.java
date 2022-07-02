package de.Modex.arctice.skyblock.afk;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.UUID;

public class AFKPlayerWrapper {

    private UUID player;
    private int afkTime;
    private Location location;
    public AFKPlayerWrapper(Player p, int afkTime, Location location) {
        player = p.getUniqueId();
        this.afkTime = afkTime;
        this.location = location;
    }

    public UUID getPlayer() {
        return player;
    }

    public void setPlayer(UUID player) {
        this.player = player;
    }

    public int getAfkTime() {
        return afkTime;
    }

    public void setAfkTime(int afkTime) {
        this.afkTime = afkTime;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
