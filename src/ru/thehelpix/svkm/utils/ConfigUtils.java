package ru.thehelpix.svkm.utils;

import ru.thehelpix.svkm.Main;

import java.util.List;

public class ConfigUtils {
    private final static Main plugin = Main.getInstance();

    public static Boolean isWhitePlayer(String name) {
        List<String> players = plugin.getConfig().getStringList("white_players");
        return players.contains(name.toLowerCase());
    }

    public static void addWhitePlayer(String name) {
        List<String> players = plugin.getConfig().getStringList("white_players");
        players.add(name.toLowerCase());
        plugin.getConfig().set("white_players", players);
        plugin.saveConfig();
    }

    public static void removeWhitePlayer(String cmd) {
        List<String> players = plugin.getConfig().getStringList("white_players");
        players.remove(cmd.toLowerCase());
        plugin.getConfig().set("white_players", players);
        plugin.saveConfig();
    }

    public static Boolean isColonUsing() {
        return plugin.getConfig().getBoolean("settings.colon");
    }

    public static void setColonUsing(Boolean bool) {
        plugin.getConfig().set("settings.colon", bool);
        plugin.saveConfig();
    }

    public static Boolean isAdmin(int id) {
        return plugin.getConfig().contains("admins."+id);
    }

    public static Boolean isBanCmd(String cmd) {
        List<String> cmds = plugin.getConfig().getStringList("block_cmds");
        return cmds.contains(cmd.toLowerCase());
    }

    public static void addBanCmd(String cmd) {
        List<String> cmds = plugin.getConfig().getStringList("block_cmds");
        cmds.add(cmd.toLowerCase());
        plugin.getConfig().set("block_cmds", cmds);
        plugin.saveConfig();
    }

    public static void removeBanCmd(String cmd) {
        List<String> cmds = plugin.getConfig().getStringList("block_cmds");
        cmds.remove(cmd.toLowerCase());
        plugin.getConfig().set("block_cmds", cmds);
        plugin.saveConfig();
    }

    public static void addAdmin(int id) {
        plugin.getConfig().set("admins."+id, 50);
        plugin.saveConfig();
    }

    public static void setLevelAdmin(int id, int level) {
        plugin.getConfig().set("admins."+id, level);
        plugin.saveConfig();
    }

    public static int getLevelAdmin(int id) {
        return plugin.getConfig().getInt("admins."+id);
    }

    public static void removeAdmin(int p) {
        plugin.getConfig().set("admins."+p, null);
        plugin.saveConfig();
    }

    public static void reloadConfig() {
        plugin.reloadConfig();
    }
}
