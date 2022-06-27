package de.Modex.arctice.skyblock.names;

import de.Modex.arctice.skyblock.ranks.Rank;
import de.Modex.arctice.skyblock.ranks.RankManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class PrefixHandler {

    private static Scoreboard sb;
    private static boolean first = true;

    static {
        sb = Bukkit.getScoreboardManager().getNewScoreboard();

        for (Rank rank : RankManager.getRanks().values()) {
            Team team = sb.registerNewTeam("0000" + rank.getLadder() + rank.getName());
            team.setPrefix(rank.getPrefix());
            team.setColor(ChatColor.GRAY);
        }
    }

    public static void setPrefix(Player p, String name) {

        String team = "";
        for (Rank rank : RankManager.getRanks().values()) {
            if (PermissionsEx.getUser(p).inGroup(rank.getName())) {
                team = "0000" + rank.getLadder() + rank.getName();
                p.setDisplayName(rank.getPrefix() + name);
                break;
            }
        }

        if (sb.getTeam(team).hasPlayer(p)) {
            sb.getTeam(team).removePlayer(p);
        }

        p.setPlayerListName(p.getDisplayName());
        sb.getTeam(team).addPlayer(p);

        if (first) {
            System.out.println(sb.getObjectives().toString());
            first = false;
        }

        for (Player all : Bukkit.getOnlinePlayers()) {
            all.setScoreboard(sb);
        }
    }

    public static void updatePrefix(Player p, String name) {
        boolean hasGroups = false;

        for (String rank : RankManager.getRanks().keySet()) {
            if (PermissionsEx.getUser(p).inGroup(rank)) {
                hasGroups = true;
                break;
            }
        }

        if (!hasGroups)
            PermissionsEx.getUser(p).addGroup("Player");

        setPrefix(p, name);
    }

    public static void updatePrefix(Player p) {
        updatePrefix(p, p.getName());
    }
}
