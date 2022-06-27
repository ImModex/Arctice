package de.Modex.arctice.skyblock.utils;

public abstract class Configurable {
    private Config config;

    public Configurable() {
        setConfig(null);
    }

    public Configurable(Config config) {
        setConfig(config);
    }

    public Config getConfig() {
        return config;
    }

    private void setConfig(Config config) {
        this.config = config;
    }
}
