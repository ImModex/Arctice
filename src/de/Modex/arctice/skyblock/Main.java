package de.Modex.arctice.skyblock;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import de.Modex.arctice.skyblock.afk.AFKListener;
import de.Modex.arctice.skyblock.afk.AFKManager;
import de.Modex.arctice.skyblock.afk.afk;
import de.Modex.arctice.skyblock.beaconrange.BeaconEffectListener;
import de.Modex.arctice.skyblock.commands.*;
import de.Modex.arctice.skyblock.gravitycontrol.GravityListener;
import de.Modex.arctice.skyblock.listener.*;
import de.Modex.arctice.skyblock.names.PrefixHandler;
import de.Modex.arctice.skyblock.silkspawners.BlockBreakListener;
import de.Modex.arctice.skyblock.silkspawners.SpawnerBreakListener;
import de.Modex.arctice.skyblock.utils.Strings;
import org.bukkit.*;
import org.bukkit.craftbukkit.v1_19_R1.CraftServer;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;

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

        Bukkit.getWorlds().forEach(world -> world.getEntities().stream().filter(entity -> entity instanceof Enderman).filter(enderman -> ((Enderman) enderman).getCarriedBlock() != null && enderman.getCustomName() == null).forEach(Entity::remove));
        /*
        File output = null;
        try {
            output = new File("seeds.json");
            if (!output.exists())
                output.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (true) {
            ArrayList<String> results = new ArrayList<>();
            long time = System.currentTimeMillis();
            WorldCreator wc = new WorldCreator(Integer.toString(new Random().nextInt(10000000)));
            wc.environment(World.Environment.NORMAL);
            wc.type(WorldType.NORMAL);
            System.out.println(Strings.prefix + "Starting world generation");
            World world = wc.createWorld();
            System.out.println(Strings.prefix + "Done generating world " + world.getName());

            System.out.println(Strings.prefix + "Looking for village...");
            Location locVil = world.locateNearestStructure(world.getSpawnLocation(), StructureType.VILLAGE, 100, false);
            double distanceVil = locVil == null ? -1 : locVil.distance(world.getSpawnLocation());
            System.out.println(Strings.prefix + "Village distance " + distanceVil);
            results.add("Village -> " + distanceVil);

            System.out.println(Strings.prefix + "Looking for mansion...");
            Location locMans = world.locateNearestStructure(world.getSpawnLocation(), StructureType.WOODLAND_MANSION, 100, false);
            double distanceMans = locMans == null ? -1 : locMans.distance(world.getSpawnLocation());
            System.out.println(Strings.prefix + "Mansion distance " + distanceMans);
            results.add("Mansion -> " + distanceMans);

            System.out.println(Strings.prefix + "Looking for monument...");
            Location locMonu = world.locateNearestStructure(world.getSpawnLocation(), StructureType.OCEAN_MONUMENT, 100, false);
            double distanceMonu = locMonu == null ? -1 : locMonu.distance(world.getSpawnLocation());
            System.out.println(Strings.prefix + "Monument distance " + distanceMonu);
            results.add("Monument -> " + distanceMonu);

            System.out.println(Strings.prefix + "Checking all biomes...");
            Location loc = world.getSpawnLocation();
            int z, x;
            ArrayList<String> biomes = new ArrayList<>();
            for (z = (int) loc.getZ() + 1000; z > loc.getZ() - 1000; z -= 16) {
                for (x = (int) loc.getX() + 1000; x > loc.getX() - 1000; x -= 16) {
                    String b = world.getBiome(x, 0, z).name();
                    if (!biomes.contains(b))
                        biomes.add(b);
                }
            }

            System.out.println(Strings.prefix + "Found " + biomes.size() + " biomes.");
            StringBuilder builder = new StringBuilder();
            biomes.forEach(biome -> builder.append(biome).append(", "));
            builder.delete(builder.length() - 1, builder.length());
            System.out.println(Strings.prefix + builder);
            System.out.println(Strings.prefix + "Took " + (System.currentTimeMillis() - time) / 1000 + "s");
            results.forEach(System.out::println);
            System.out.println("Seed -> " + world.getSeed());

            System.out.println("\n\n");
            Bukkit.unloadWorld(world, false);
            String[] entries = world.getWorldFolder().list();
            for (String s : entries) {
                new File(world.getWorldFolder().getPath(), s).delete();
            }
            world.getWorldFolder().delete();

            if (biomes.size() < 35 || distanceVil == -1) continue;
            JsonObject json = new JsonObject();
            json.addProperty("seed", world.getSeed());
            json.addProperty("villageDistance", distanceVil);
            json.addProperty("mansionDistance", distanceMans);
            json.addProperty("monumentDistance", distanceMonu);
            JsonArray arr = new JsonArray();
            biomes.forEach(arr::add);
            json.add("biomes", arr);
            json.addProperty("biomeCount", biomes.size());
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            try (PrintWriter out = new PrintWriter(new FileWriter(output, true))) {
                out.append(gson.toJson(json));
                out.append('\n');
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

         */
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
        //Bukkit.getPluginManager().registerEvents(new ZombieRiderSpawnListener(), this);
        Bukkit.getPluginManager().registerEvents(new FactionScytheListener(), this);
        Bukkit.getPluginManager().registerEvents(new MendingInfinityListener(), this);
        Bukkit.getPluginManager().registerEvents(new BeaconEffectListener(), this);
        //Bukkit.getPluginManager().registerEvents(new BonkListener(), this);
        Bukkit.getPluginManager().registerEvents(new VillagerTradeListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerTeleportListener(), this);

        //Bukkit.getPluginManager().registerEvents(new NetheriteLevelListener(), this);
        //Bukkit.getPluginManager().registerEvents(new TimberListener(), this);
        //new ItemMetaTask();

        //new ZombieDespawnTask();
        Bukkit.getPluginManager().registerEvents(new VillagerTradeListener(), this);
        //Bukkit.getPluginManager().registerEvents(new de.Modex.arctice.skyblock.listener.BlockBreakListener(), this);
        //Bukkit.getPluginManager().registerEvents(new LootGenerateListener(), this);

        Bukkit.getPluginManager().registerEvents(new PrepareItemEnchantListener(), this);
        Bukkit.getPluginManager().registerEvents(new EnchantItemListener(), this);

        Bukkit.getPluginManager().registerEvents(new ItemRepairListener(), this);
        Bukkit.getPluginManager().registerEvents(new EntityChangeBlockListener(), this);

        Bukkit.getPluginManager().registerEvents(new EntitySpawnListener(), this);

        Bukkit.getPluginManager().registerEvents(new GravityListener(), this);

        Bukkit.getPluginManager().registerEvents(new AFKListener(), this);
        AFKManager manager = new AFKManager();
    }

    private void registerCommands() {
        getCommand("mute").setExecutor(new mute());
        getCommand("nick").setExecutor(new nick());
        getCommand("spawnable").setExecutor(new spawnable());
        getCommand("spawnable").setTabCompleter(new spawnable());
        getCommand("entity").setExecutor(new entity());
        getCommand("chunk").setExecutor(new chunk());
        getCommand("afk").setExecutor(new afk());
        //getCommand("level").setExecutor(new level());
    }
}

