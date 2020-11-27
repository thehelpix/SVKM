package ru.thehelpix.svkm.vkEvents;

import ru.thehelpix.svkm.vkApi.VkHandler;
import ru.thehelpix.svkm.vkApi.VkListener;
import ru.thehelpix.svkm.vkApi.event.ReceiveMessageEvent;
import ru.thehelpix.svkm.vkApi.message.ReceiveMessage;
import org.bukkit.Bukkit;
import ru.thehelpix.svkm.Main;
import ru.thehelpix.svkm.utils.*;

public class CommandEvent implements VkListener {

    @VkHandler
    public void onMessage(ReceiveMessageEvent event) {
        if (Integer.parseInt(event.getMessage().getFrom()) < 0) return;
        if (event.getMessage().getText().length() < 1) return;
        new LoggerVK(event);
        String[] args = event.getMessage().getText().split(" ");
        ReceiveMessage sender = event.getMessage();
        int senderId = Integer.parseInt(event.getMessage().getFrom());
        String screenName = VKUtils.getUserScreenName(""+senderId);

        if (args[0].equalsIgnoreCase("помощь") || args[0].equalsIgnoreCase("help")) {
            LoggerVK.log();
            sender.sendMessage(screenName+", привет! Я бот SVKM, проще говоря бот-консоль" +
                    "\nУровень доступа: "+Utils.getPermLevel(senderId)+Utils.getPermLevelCmd(senderId));
        }

        if (args[0].equalsIgnoreCase("!restart")) {
            LoggerVK.log();
            if (!ConfigUtils.isAdmin(senderId) || ConfigUtils.getLevelAdmin(senderId) < 100) {
                sender.sendMessage(screenName+", У Вас недостаточно прав чтобы перезагрузить сервер!");
                return;
            }
            sender.sendMessage(screenName+", сервер перезагружается!");
            Bukkit.spigot().restart();
        }

        if (args[0].equalsIgnoreCase("!reload")) {
            LoggerVK.log();
            if (!ConfigUtils.isAdmin(senderId) || ConfigUtils.getLevelAdmin(senderId) < 100) {
                sender.sendMessage(screenName+", У Вас недостаточно прав чтобы перезагрузить сервер!");
                return;
            }
            sender.sendMessage(screenName+", сервер релоаднут!");
            Main.getInstance().getServer().reload();
        }

        if (args[0].equalsIgnoreCase("!кик")) {
            LoggerVK.log();
            if (!ConfigUtils.isAdmin(senderId) || ConfigUtils.getLevelAdmin(senderId) < 50) {
                sender.sendMessage(screenName+", У Вас недостаточно прав чтобы кикнуть игрока!");
                return;
            }

            if (args.length > 1) {
                if (ConfigUtils.isWhitePlayer(args[1])) {
                    sender.sendMessage(screenName+", игрока "+args[1]+" нельзя кикинуть!");
                    return;
                }
                if (!Utils.isPlayer(args[1])) {
                    sender.sendMessage(screenName+", такого игрока на сервере нету!");
                    return;
                }
                if (args.length > 2) {
                    StringBuilder reason = new StringBuilder(args[2]);
                    for (int ii = 3; ii != args.length; ++ii) {
                        reason.append(" ").append(args[ii]);
                    }
                    Utils.kickPlayer(args[1], reason.toString());
                    sender.sendMessage(screenName+", Вы кикнули игрока "+args[1]+" из сервера!\nПо причине: "+reason);
                    return;
                }
                Utils.kickPlayer(args[1], "Причина не указана!");
                sender.sendMessage(screenName+", Вы кикнули игрока "+args[1]+" из сервера!");
                return;
            }
            sender.sendMessage(screenName+", использование: !кик ник [причина]");
        }

        if (args[0].equalsIgnoreCase("cmd")) {
            LoggerVK.log();
            if (!ConfigUtils.isAdmin(senderId) || ConfigUtils.getLevelAdmin(senderId) < 100) {
                sender.sendMessage(screenName+", У Вас недостаточно прав!");
                return;
            }
            if (args.length > 1) {
                StringBuilder command = new StringBuilder(args[1]);
                for (int ii = 2; ii != args.length; ++ii) {
                    command.append(" ").append(args[ii]);
                }
                if (!ConfigUtils.isColonUsing()) {
                    if (Utils.isColonCmd(String.valueOf(command))) {
                        sender.sendMessage(screenName+", команды с двоеточием заблокированы!");
                        return;
                    }
                }
                if (String.valueOf(command).equalsIgnoreCase("svkm")) {
                    sender.sendMessage(screenName+", нельзя использовать команды бота ркона в вк!");
                    return;
                }
                String[] argsCmd = String.valueOf(command).split(" ");
                if (argsCmd.length > 0) {
                    if (ConfigUtils.isBanCmd(argsCmd[0])) {
                        sender.sendMessage(screenName+", команда /"+argsCmd[0]+" заблокирована!");
                        return;
                    }
                }
                if (ConfigUtils.isBanCmd(String.valueOf(command))) {
                    sender.sendMessage(screenName+", команда /"+command+" заблокирована!");
                    return;
                }
                SilentConsole.sendCommand(event.getMessage(), command.toString());
                sender.sendMessage(screenName+", команда выполнена!");
                return;
            }
            sender.sendMessage(screenName+", использование: cmd [команда].");
        }
    }
}
