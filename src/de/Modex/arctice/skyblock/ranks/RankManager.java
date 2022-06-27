package de.Modex.arctice.skyblock.ranks;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class RankManager {

    private final static HashMap<String, Rank> ranks = new LinkedHashMap<>();

    public static HashMap<String, Rank> getRanks() {
        return ranks;
    }

    static {
        ranks.put("Owner", new Rank("Owner", "§4§lOwner §8● §7", 1));
        ranks.put("Staff", new Rank("Staff", "§6Staff §8● §7", 2));
        ranks.put("Player", new Rank("Player", "§7", 3));
    }
}
