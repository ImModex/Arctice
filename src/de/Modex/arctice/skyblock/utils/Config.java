package de.Modex.arctice.skyblock.utils;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class Config {

    private File file;
    private YamlConfiguration config;

    public Config(String fileName) {
        File configFile = new File(fileName);
        if (!configFile.exists()) {
            try {
                configFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        file = configFile;
        config = YamlConfiguration.loadConfiguration(file);
    }

    public Config(File file) {
        if (file != null) {
            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            this.file = file;
            config = YamlConfiguration.loadConfiguration(file);
        }
    }

    public File getFile() {
        return file;
    }

    public YamlConfiguration config() {
        return config;
    }

    public void set(String path, Object value) {
        config.set(path, value);
        save();
    }

    public boolean contains(String path) {
        return config.contains(path);
    }
    public void save() {
        try {
            config.save(getFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
