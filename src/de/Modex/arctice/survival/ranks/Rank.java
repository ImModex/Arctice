package de.Modex.arctice.survival.ranks;

public class Rank {

    private String name;
    private String prefix;
    private int ladder;

    private Rank() {}

    public Rank(String name, String prefix, int ladder) {
        this.name = name;
        this.prefix = prefix;
        this.ladder = ladder;
    }

    public String getName() {
        return name;
    }

    public String getPrefix() {
        return prefix;
    }

    public int getLadder() {
        return ladder;
    }
}
