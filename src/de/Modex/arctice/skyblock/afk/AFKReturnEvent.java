package de.Modex.arctice.skyblock.afk;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class AFKReturnEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    private final Player player;

    public AFKReturnEvent(Player p) {
        player = p;
    }

    public Player getPlayer() {
        return player;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
}
