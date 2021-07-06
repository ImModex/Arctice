package de.Modex.arctice.survival.mcmmo;

import de.Modex.arctice.survival.Main;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;

public class CooldownHandler {

    private final static HashMap<Player, ArrayList<CooldownReason>> cooldowns = new HashMap<>();

    public static void startCooldown(Player p, long time, CooldownReason reason, String message) {
        ArrayList<CooldownReason> reasons = cooldowns.get(p);
        if (reasons == null) {
            reasons = new ArrayList<>();
        }
        reasons.add(reason);
        cooldowns.put(p, reasons);

        new BukkitRunnable() {
            @Override
            public void run() {
                ArrayList<CooldownReason> tmp = cooldowns.get(p);
                tmp.remove(CooldownReason.TIMBER);
                cooldowns.put(p, tmp);
                if (message != null)
                    p.sendMessage(message);
            }
        }.runTaskLaterAsynchronously(Main.instance, time);
    }

    public static boolean isTimberCooldown(Player p) {
        return cooldowns.get(p) != null && cooldowns.get(p).contains(CooldownReason.TIMBER);
    }
}
