package ru.thehelpix.svkm.utils;

import org.bukkit.Bukkit;
import ru.thehelpix.svkm.Main;

public class Color {
    private final static Main plugin = Main.getInstance();

    public static String parse(String str){
        return str.replace('&', '§');
    }

    public static void log(String str) {
        Bukkit.getConsoleSender().sendMessage(Color.parse("&6["+plugin.getDescription().getName()+"&6] "+str));
    }
    public static String cmd(String str) {
        return parse("&6["+plugin.getDescription().getName()+"&6] "+str);
    }
}
