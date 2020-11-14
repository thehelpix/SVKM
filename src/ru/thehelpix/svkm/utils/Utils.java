package ru.thehelpix.svkm.utils;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.thehelpix.svkm.Main;

import java.util.regex.Pattern;

public class Utils {

    public static Boolean isPlayer(String p) {
        for (Player players : Bukkit.getOnlinePlayers()) {
            return players.getName().equalsIgnoreCase(p);
        }
        return false;
    }

    public static Boolean isInt(String text) {
        return Pattern.matches("[0-9]+", text);
    }

    public static Boolean isLevelSet(int level) {
        return (level == 50 || level == 100);
    }

    public static void kickPlayer(String p, String reason) {
        Bukkit.getScheduler().runTask(Main.getInstance(), () -> {
            for (Player players : Bukkit.getOnlinePlayers()) {
                if (players.getName().equalsIgnoreCase(p)) {
                    players.kickPlayer(Color.parse("&9[ВК]\n&cВас кикнули по причине: &4"+reason));
                    System.out.println();
                }
            }
        });
    }

    public static Boolean isMinLevel(int id) {
        System.out.println(ConfigUtils.getLevelAdmin(id));
        if (ConfigUtils.getLevelAdmin(id) == 50) {
            return true;
        }
        if (ConfigUtils.getLevelAdmin(id) == 100) {
            return true;
        }
        return false;
    }

    public static String getPermLevel(int id) {
        if (!ConfigUtils.isAdmin(id)) {
            return "нету";
        }
        if (!isMinLevel(id)) {
            return "некорректный лвл!";
        }
        return ConfigUtils.getLevelAdmin(id)+" лвл";
    }

    public static Boolean isColonCmd(String cmd) {
        return cmd.contains(":");
    }

    public static void changeColonUsing(CommandSender sender) {
        if (ConfigUtils.isColonUsing()) {
            ConfigUtils.setColonUsing(false);
            sender.sendMessage(Color.cmd("&aВы включили блокировку двоеточий в командах!"));
        } else {
            ConfigUtils.setColonUsing(true);
            sender.sendMessage(Color.cmd("&2Вы выключили блокировку двоеточий в команде!"));
        }
    }

    public static String getPermLevelCmd(int id) {
        if (!ConfigUtils.isAdmin(id)) {
            return "";
        }
        if (ConfigUtils.getLevelAdmin(id) == 50) {
            return "\n\nТвои права:" +
                    "\n!кик ник (причина) - кик игрока";
        }
        if (ConfigUtils.getLevelAdmin(id) == 100) {
            return "\n\nТвои права:" +
                    "\n!кик ник (причина) - кик игрока" +
                    "\n!reload - релоад сервера" +
                    "\n!restart - рестарт сервера" +
                    "\ncmd команда - выполнение команды";
        }
        return "";
    }
}
