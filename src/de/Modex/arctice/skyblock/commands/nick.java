package de.Modex.arctice.skyblock.commands;

import de.Modex.arctice.skyblock.names.PrefixHandler;
import de.Modex.arctice.skyblock.utils.CustomCommand;
import de.Modex.arctice.skyblock.utils.Permissions;
import de.Modex.arctice.skyblock.utils.Strings;
import net.md_5.bungee.api.ChatColor;
import de.Modex.arctice.skyblock.utils.Tuple;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import ru.tehkode.permissions.bukkit.PermissionsEx;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class nick extends CustomCommand {

    public static final HashMap<UUID, String> nicknames = new HashMap<>();

    public nick() {
        super(new File("plugins/Arctice/nicks.yml"), "nick");
        loadNicks();
    }

    public void loadNicks() {
        for (String key : getConfig().config().getKeys(false)) {
            Player tmp = Bukkit.getPlayer(UUID.fromString(key));
            if (tmp != null)
                nick(tmp, getConfig().config().getString(key));
            nicknames.put(UUID.fromString(key), getConfig().config().getString(key));
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, String label, String[] args) {
        return false;
    }

    @Override
    public boolean onConsoleCommand(ConsoleCommandSender sender, String label, String[] args) {
        return false;
    }

    @Override
    public boolean onPlayerCommand(Player p, String label, String[] args) {

        if (args.length == 0) {
            switch (unnick(p)) {
                case 2:
                    p.sendMessage(Strings.prefix + "§7You don't have a nickname.");
                    break;
                default:
                    p.sendMessage(Strings.prefix + "§7You cleared your nickname.");
                    break;
            }

        } else if (args.length == 1) {
            String nick = args[0].replaceAll("&", "§");

            if (PermissionsEx.getUser(p).has(Permissions.nick_others)) {
                Player other = Bukkit.getPlayer(nick);
                if (other != null) {
                    switch (unnick(other)) {
                        case 2:
                            p.sendMessage(Strings.prefix + "§7The entered player has no nickname.");
                            break;
                        default:
                            p.sendMessage(Strings.prefix + "§7You cleared " + other.getName() + "§7's nickname.");
                            break;
                    }
                    return true;
                }
            }

            switch (nick(p, nick)) {
                case 1:
                    p.sendMessage(Strings.prefix + "§7Entered nickname is not formatted correctly.");
                    break;
                case 2:
                case 3:
                    p.sendMessage(Strings.prefix + "§cYou cannot chose the name of someone else.");
                    break;
                default:
                    p.sendMessage(Strings.prefix + "§7You set your nickname to: " + Strings.translateHex(nick));
                    break;
            }

        } else if (args.length == 2) {
            if (PermissionsEx.getUser(p).has(Permissions.nick_others)) {
                Player other = Bukkit.getPlayer(args[0]);
                String nick = args[1].replaceAll("&", "§");

                if (other != null) {
                    switch (nick(other, nick)) {
                        case 1:
                            p.sendMessage(Strings.prefix + "§7Entered nickname is not formatted correctly.");
                            break;
                        case 2:
                        case 3:
                            p.sendMessage(Strings.prefix + "§cYou cannot set " + other.getName() + "'s name to the name of someone else.");
                            break;
                        default:
                            p.sendMessage(Strings.prefix + "§7You set " + other.getName() + "§7's nickname to: " + Strings.translateHex(nick));
                            break;
                    }
                } else {
                    p.sendMessage(Strings.prefix + "§cThe entered player does not exist.");
                }
            } else {
                p.sendMessage(Strings.prefix + "§c/nick <name>");
            }
        }
        return true;
    }

    private static boolean isValidColor(String nick) {
        ArrayList<String> colors = new ArrayList<>(Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "0", "a", "b", "c", "d", "e", "f", "k", "l", "m", "n", "o", "r", "x"));
        String[] str = nick.split("§");

        if (!nick.contains("§") || str.length == 0)
            return true;

        for (String s : str) {
            if (s != null && !s.equals("") && !s.equals(" "))
                if (!colors.contains(String.valueOf(s.charAt(0)).toLowerCase()))
                    return false;
        }
        return true;
    }

    public static String stripColorCodes(String nick) {
        if (!nick.contains("§")) return nick;
        String[] str = nick.split("§");
        StringBuilder builder = new StringBuilder();

        for (String s : str) {
            if (s == null || s.equals("") || s.equals(" ")) continue;
            builder.append(s.substring(1));
        }

        return builder.toString();
    }

    private final Pattern pattern = Pattern.compile("#[a-fA-F0-9]{6}");

    private Tuple<String, Integer> replaceHexCodes(String nick) {
        Matcher match = pattern.matcher(nick);
        int matches = 0;
        while (match.find()) {
            matches++;
            String color = nick.substring(match.start(), match.end());
            nick = nick.replace(color, ChatColor.of(color) + "");
            match = pattern.matcher(nick);
        }
        return new Tuple<>(nick, matches);
    }

    private int nick(Player p, String nick) {

        int offset = StringUtils.countMatches(nick, "§") * 2;
        Tuple<String, Integer> result = replaceHexCodes(nick);
        nick = result.a();
        offset += 7 * result.b();

        if (nick.matches("^[a-zA-Z0-9_§]+$") && isValidColor(nick) && (nick.length() - offset) >= 3 && (nick.length() - offset) <= 16) {

            String stripped = stripColorCodes(nick);
            for (Player all : Bukkit.getOnlinePlayers())
                if (!p.getName().equals(all.getName()) && all.getName().equals(stripped))
                    return 2;

            for (String nicks : nicknames.values()) {
                if (stripColorCodes(nicks).equals(stripped))
                    for (UUID uuid : nicknames.keySet()) {
                        if (uuid.equals(p.getUniqueId()) && !stripped.equalsIgnoreCase(nicknames.get(uuid)))
                            return 3;
                    }
            }

            nicknames.remove(p.getUniqueId());
            nicknames.put(p.getUniqueId(), nick);
            p.setDisplayName(nick);
            PrefixHandler.updatePrefix(p, nick);
            getConfig().config().set(p.getUniqueId().toString(), nick);
            getConfig().save();
        } else {
            return 1;
        }
        return 0;
    }

    private int unnick(Player p) {
        if (nicknames.containsKey(p.getUniqueId())) {
            nicknames.remove(p.getUniqueId());
            p.setDisplayName(p.getName());
            PrefixHandler.updatePrefix(p);
            getConfig().config().set(p.getUniqueId().toString(), "§7" + p.getName());
        } else {
            return 2;
        }
        return 0;
    }

    public static String getNick(Player p) {
        if (nicknames.containsKey(p.getUniqueId()))
            return nicknames.get(p.getUniqueId());
        return p.getName();
    }

    public static boolean hasNick(Player p) {
        return nicknames.containsKey(p.getUniqueId());
    }
}
