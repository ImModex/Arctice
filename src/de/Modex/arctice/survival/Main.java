package de.Modex.arctice.survival;

import de.Modex.arctice.survival.beaconrange.BeaconEffectListener;
import de.Modex.arctice.survival.bonk.BonkListener;
import de.Modex.arctice.survival.commands.mute;
import de.Modex.arctice.survival.commands.nick;
import de.Modex.arctice.survival.listener.*;
import de.Modex.arctice.survival.mcmmo.ItemMetaTask;
import de.Modex.arctice.survival.mcmmo.NetheriteLevelListener;
import de.Modex.arctice.survival.mcmmo.TimberListener;
import de.Modex.arctice.survival.mcmmo.level;
import de.Modex.arctice.survival.names.PrefixHandler;
import de.Modex.arctice.survival.silkspawners.BlockBreakListener;
import de.Modex.arctice.survival.silkspawners.SpawnerBreakListener;
import de.Modex.arctice.survival.tasks.ZombieDespawnTask;
import de.Modex.arctice.survival.utils.Strings;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    public static JavaPlugin instance;

    @Override
    public void onEnable() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            PrefixHandler.updatePrefix(p);
        }

        instance = this;

        registerEvents();
        registerCommands();

        System.out.println(Strings.prefix + "Plugin has been loaded!");
    }

    @Override
    public void onDisable() {
        System.out.println(Strings.prefix + "Plugin has been unloaded!");
    }

    private void registerEvents() {
        Bukkit.getPluginManager().registerEvents(new JoinQuitListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerChatListener(), this);
        Bukkit.getPluginManager().registerEvents(new SpawnerBreakListener(), this);
        Bukkit.getPluginManager().registerEvents(new BlockBreakListener(), this);
        Bukkit.getPluginManager().registerEvents(new CreeperExplosionListener(), this);
        Bukkit.getPluginManager().registerEvents(new NickFormattingListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerDeathListener(), this);
        Bukkit.getPluginManager().registerEvents(new ZombieRiderSpawnListener(), this);
        Bukkit.getPluginManager().registerEvents(new FactionScytheListener(), this);
        Bukkit.getPluginManager().registerEvents(new MendingInfinityListener(), this);
        Bukkit.getPluginManager().registerEvents(new BeaconEffectListener(), this);
        Bukkit.getPluginManager().registerEvents(new BonkListener(), this);
        //Bukkit.getPluginManager().registerEvents(new CompassInteractListener(this), this);

        Bukkit.getPluginManager().registerEvents(new NetheriteLevelListener(), this);
        Bukkit.getPluginManager().registerEvents(new TimberListener(), this);
        new ItemMetaTask();

        new ZombieDespawnTask();
    }

    private void registerCommands() {
        getCommand("mute").setExecutor(new mute());
        getCommand("nick").setExecutor(new nick());
        getCommand("level").setExecutor(new level());
    }
}

