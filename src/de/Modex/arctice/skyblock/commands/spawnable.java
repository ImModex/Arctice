package de.Modex.arctice.skyblock.commands;

import de.Modex.arctice.skyblock.utils.CustomCommand;
import de.Modex.arctice.skyblock.utils.ReflectionUtils;
import de.Modex.arctice.skyblock.utils.Strings;
import net.minecraft.core.BlockPosition;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.PacketPlayOutBlockChange;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class spawnable extends CustomCommand {

    @Override
    public boolean onCommand(CommandSender sender, String label, String[] args) {
        return false;
    }

    @Override
    public boolean onConsoleCommand(ConsoleCommandSender sender, String label, String[] args) {
        return false;
    }

    private static List<Material> nonSpawnable = new ArrayList<>(List.of(Material.TORCH, Material.GLOWSTONE, Material.SHROOMLIGHT, Material.DIRT_PATH, Material.FARMLAND, Material.SNOW, Material.LEVER, Material.LAVA, Material.WATER, Material.BEDROCK, Material.AIR, Material.CHEST, Material.CAVE_AIR, Material.VOID_AIR, Material.FIRE));

    static {
        for (int i = 0; i < Material.values().length; i++) {
            if (Material.values()[i].name().contains("GLASS") || Material.values()[i].name().contains("LEAVES") || Material.values()[i].name().contains("SLAB") || Material.values()[i].name().contains("CARPET") || Material.values()[i].name().contains("BUTTON") || Material.values()[i].name().contains("PRESSURE") || Material.values()[i].name().contains("RAIL") || Material.values()[i].name().contains("FENCE") || Material.values()[i].name().contains("STAIR") || Material.values()[i].name().contains("WALL"))
                nonSpawnable.add(Material.values()[i]);
        }
    }

    private static boolean inUse = false;

    @Override
    public boolean onPlayerCommand(Player p, String label, String[] args) {

        if(inUse) return true;

        inUse = true;
        double radius = Integer.parseInt(args[0]);
        radius = radius > 128 ? 128 : radius;
        radius = radius < 0 ? 0 : radius;
        int num = 0;
        int blocksConsidered = 0;

        List<Packet<?>> packets = new ArrayList<>();

        for (int x = (int) (p.getLocation().getBlockX() - radius); x <= (int) (p.getLocation().getBlockX() + radius); x++) {
            for (int y = 0; y <= 255; y++) {
                for (int z = (int) (p.getLocation().getBlockZ() - radius); z <= (int) (p.getLocation().getBlockZ() + radius); z++) {
                    if (!nonSpawnable.contains(p.getWorld().getBlockAt(x, y, z).getType()) && (p.getWorld().getBlockAt(x, y + 1, z).getType() == Material.AIR || p.getWorld().getBlockAt(x, y + 1, z).getType() == Material.CAVE_AIR || p.getWorld().getBlockAt(x, y + 1, z).getType() == Material.VOID_AIR))
                        num++;
                    blocksConsidered++;

                    if (x == (int) (p.getLocation().getBlockX() - radius) || x == (int) (p.getLocation().getBlockX() + radius) || z == (int) (p.getLocation().getBlockZ() - radius) || z == (int) (p.getLocation().getBlockZ() + radius)) {
                        packets.add(new PacketPlayOutBlockChange(new BlockPosition(x, y, z), Blocks.dy.m()));
                    }
                }
            }
        }

        ReflectionUtils.sendPacket(p, packets.toArray());
        packets.clear();
        p.sendMessage(Strings.prefix + "§c" + num + " §7blocks are spawnable! §a" + blocksConsidered + " §7blocks taken into consideration. Using square formula");
        num = 0;
        blocksConsidered = 0;

        double square = Math.pow(radius, 2);


        for (int x = (int) (p.getLocation().getBlockX() - radius); x <= (int) (p.getLocation().getBlockX() + radius); x++) {
            for (int y = 0; y <= 255; y++) {
                for (int z = (int) (p.getLocation().getBlockZ() - radius); z <= (int) (p.getLocation().getBlockZ() + radius); z++) {
                    double distance = Math.pow((p.getLocation().getBlockZ() - z), 2) + Math.pow(p.getLocation().getBlockX() - x, 2);
                    if (distance <= square) {
                        if (!nonSpawnable.contains(p.getWorld().getBlockAt(x, y, z).getType()) && (p.getWorld().getBlockAt(x, y + 1, z).getType() == Material.AIR || p.getWorld().getBlockAt(x, y + 1, z).getType() == Material.CAVE_AIR || p.getWorld().getBlockAt(x, y + 1, z).getType() == Material.VOID_AIR)) {
                            num++;
                            packets.add(new PacketPlayOutBlockChange(new BlockPosition(x, y + 1, z), Blocks.eQ.m()));
                        }

                        blocksConsidered++;
                    }

                    if ((distance > Math.pow(radius - 1, 2)) && (distance < Math.pow(radius + 1, 2))) {
                        packets.add(new PacketPlayOutBlockChange(new BlockPosition(x, y, z), Blocks.dD.m()));
                    }
                }
            }
        }

        ReflectionUtils.sendPacket(p, packets.toArray());
        packets.clear();

        p.sendMessage(Strings.prefix + "§c" + num + " §7blocks are spawnable! §a" + blocksConsidered + " §7blocks taken into consideration. Using circle formula");

        num = 0;
        blocksConsidered = 0;
        for (int x = (int) (p.getLocation().getBlockX() - radius); x <= (int) (p.getLocation().getBlockX() + radius); x++) {
            for (int y = (int) (p.getLocation().getBlockY() - radius); y <= (int) (p.getLocation().getBlockY() + radius); y++) {
                for (int z = (int) (p.getLocation().getBlockZ() - radius); z <= (int) (p.getLocation().getBlockZ() + radius); z++) {
                    double distance = Math.pow((p.getLocation().getBlockZ() - z), 2) + Math.pow(p.getLocation().getBlockX() - x, 2) + Math.pow(p.getLocation().getBlockY() - y, 2);

                    if ((distance > Math.pow(radius - 1, 2)) && (distance < Math.pow(radius + 1, 2))) {
                        packets.add(new PacketPlayOutBlockChange(new BlockPosition(x, y, z), Blocks.dF.m()));
                    }


                    if (distance <= square) {
                        if (!nonSpawnable.contains(p.getWorld().getBlockAt(x, y, z).getType()) && (p.getWorld().getBlockAt(x, y + 1, z).getType() == Material.AIR || p.getWorld().getBlockAt(x, y + 1, z).getType() == Material.CAVE_AIR || p.getWorld().getBlockAt(x, y + 1, z).getType() == Material.VOID_AIR)) {
                            num++;
                        }
                        blocksConsidered++;
                    }
                }
            }
        }

        ReflectionUtils.sendPacket(p, packets.toArray());
        p.sendMessage(Strings.prefix + "§c" + num + " §7blocks are spawnable! §a" + blocksConsidered + " §7blocks taken into consideration. Using spherical formula");

        inUse = false;
        return true;
    }
}
