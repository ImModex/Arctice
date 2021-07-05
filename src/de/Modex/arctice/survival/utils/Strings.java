package de.Modex.arctice.survival.utils;

import net.md_5.bungee.api.ChatColor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Strings {

    public static final String prefix = "§6Arctice §8｜ ";
    public static final String no_perms = prefix + "§cYou don't have the permission to use this command!";

    private final static Pattern pattern = Pattern.compile("#[a-fA-F0-9]{6}");
    public static String translateHex(String message) {
        Matcher match = pattern.matcher(message);
        while(match.find()) {
            String color = message.substring(match.start(), match.end());
            message = message.replace(color, ChatColor.of(color) + "");
            match = pattern.matcher(message);
        }
        return message;
    }
}
