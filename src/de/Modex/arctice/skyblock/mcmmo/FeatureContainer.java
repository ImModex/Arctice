package de.Modex.arctice.skyblock.mcmmo;

public class FeatureContainer {

    private boolean timberMode;

    public FeatureContainer() {
        timberMode = false;
    }

    public boolean isTimberMode() {
        return timberMode;
    }

    public FeatureContainer setTimberMode(boolean timberMode) {
        this.timberMode = timberMode;
        return this;
    }
}
