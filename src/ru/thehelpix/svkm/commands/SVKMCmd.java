package ru.thehelpix.svkm.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.thehelpix.svkm.utils.Color;
import ru.thehelpix.svkm.utils.ConfigUtils;
import ru.thehelpix.svkm.utils.Utils;
import ru.thehelpix.svkm.utils.VKUtils;

public class SVKMCmd implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String arg, String[] args) {
        if (sender instanceof Player) {
            sender.sendMessage(Color.cmd("&cУ Вас недостаточно прав! Пройдите в консоль."));
            return true;
        }

        if (args.length == 0 || args[0].equalsIgnoreCase("help")) {
            sender.sendMessage(Color.cmd("&2Использование:"));
            sender.sendMessage(Color.parse("&2/%cmd% add &9(idvk) &7- Добавление администратора в конфиг.").replace("%cmd%", cmd.getName()));
            sender.sendMessage(Color.parse("&2/%cmd% remove &9(idvk) &7- Удаление администратора из конфига.").replace("%cmd%", cmd.getName()));
            sender.sendMessage(Color.parse("&2/%cmd% setlevel &9(idvk) &7- Установка уровня прав администратору.").replace("%cmd%", cmd.getName()));
            sender.sendMessage(Color.parse("&2/%cmd% info &9(idvk) &7- Информация об администраторе по id.").replace("%cmd%", cmd.getName()));
            sender.sendMessage(Color.parse("&2/%cmd% bancmd &8(команда) &7- Блокировка команды.").replace("%cmd%", cmd.getName()));
            sender.sendMessage(Color.parse("&2/%cmd% unbancmd &8(команда) &7- Разблокировка команды.").replace("%cmd%", cmd.getName()));
            sender.sendMessage(Color.parse("&2/%cmd% addwhiteplayer &8(ник) &7- Добавление игрока в список запрещёных киков.").replace("%cmd%", cmd.getName()));
            sender.sendMessage(Color.parse("&2/%cmd% removewhiteplayer &8(ник) &7- Удаление игрока из списка запрещёных киков.").replace("%cmd%", cmd.getName()));
            sender.sendMessage(Color.parse("&2/%cmd% reload &7- Перезагрузка конфига.").replace("%cmd%", cmd.getName()));
            sender.sendMessage(Color.parse("&2/%cmd% colonuse &7- Вкл/Выкл двоеточия в командах.").replace("%cmd%", cmd.getName()));
            return true;
        }

        if (args[0].equalsIgnoreCase("addwhiteplayer")) {
            if (args.length > 1) {
                String nick = args[1];
                if (ConfigUtils.isWhitePlayer(nick)) {
                    sender.sendMessage(Color.cmd("&cИгрок &4"+nick+"&c уже добавлен в список!"));
                    return true;
                }
                ConfigUtils.addWhitePlayer(nick);
                sender.sendMessage(Color.cmd("&2Вы добавили игрока &a"+nick+"&2 в список запрещёных киков!"));
                return true;
            }
            sender.sendMessage(Color.cmd("&2Использование: &a/%cmd% addwhiteplayer (ник)").replace("%cmd%", cmd.getName()));
            return true;
        }

        if (args[0].equalsIgnoreCase("removewhiteplayer")) {
            if (args.length > 1) {
                String nick = args[1];
                if (!ConfigUtils.isWhitePlayer(nick)) {
                    sender.sendMessage(Color.cmd("&cИгрок &4"+nick+"&c не найден в списке!"));
                    return true;
                }
                ConfigUtils.removeWhitePlayer(nick);
                sender.sendMessage(Color.cmd("&2Вы удалили игрока &a"+nick+"&2 из списка запрещёных киков!"));
                return true;
            }
            sender.sendMessage(Color.cmd("&2Использование: &a/%cmd% removewhiteplayer (ник)").replace("%cmd%", cmd.getName()));
            return true;
        }

        if (args[0].equalsIgnoreCase("reload")) {
            ConfigUtils.reloadConfig();
            sender.sendMessage(Color.cmd("&2Вы перезагрузили конфиг!"));
            return true;
        }

        if (args[0].equalsIgnoreCase("colonuse")) {
            Utils.changeColonUsing(sender);
            return true;
        }

        if (args[0].equalsIgnoreCase("bancmd")) {
            if (args.length > 1) {
                StringBuilder command = new StringBuilder(args[1]);
                for (int ii = 2; ii != args.length; ++ii) {
                    command.append(" ").append(args[ii]);
                }
                if (ConfigUtils.isBanCmd(String.valueOf(command))) {
                    sender.sendMessage(Color.cmd("&cКоманда &4/"+command+"&c уже есть в чёрном списке!"));
                    return true;
                }
                ConfigUtils.addBanCmd(String.valueOf(command));
                sender.sendMessage(Color.cmd("&2Вы добавили команду &c/"+command+"&2 в чёрный список ркона."));
                return true;
            }
            sender.sendMessage(Color.cmd("&2Использование: &a/%cmd% bancmd (команда).").replace("%cmd%", cmd.getName()));
            return true;
        }

        if (args[0].equalsIgnoreCase("unbancmd")) {
            if (args.length > 1) {
                StringBuilder command = new StringBuilder(args[1]);
                for (int ii = 2; ii != args.length; ++ii) {
                    command.append(" ").append(args[ii]);
                }
                if (!ConfigUtils.isBanCmd(String.valueOf(command))) {
                    sender.sendMessage(Color.cmd("&cКоманды &4/"+command+"&c нету в чёрном списке!"));
                    return true;
                }
                ConfigUtils.removeBanCmd(String.valueOf(command));
                sender.sendMessage(Color.cmd("&2Вы удалили команду &c/"+command+"&2 из чёрного списка ркона."));
                return true;
            }
            sender.sendMessage(Color.cmd("&2Использование: &a/%cmd% unbancmd (команда).").replace("%cmd%", cmd.getName()));
            return true;
        }

        if (args[0].equalsIgnoreCase("add")) {
            if (args.length > 1) {
                if (!Utils.isInt(args[1])) {
                    sender.sendMessage(Color.cmd("&сidvk должен состоять из чисел!"));
                    return true;
                }
                int id = Integer.parseInt(args[1]);
                String getName = VKUtils.getUserFullName(id);
                if (ConfigUtils.isAdmin(id)) {
                    sender.sendMessage(Color.cmd("&cТакой администратор уже есть в конфиге!"));
                    return true;
                }
                ConfigUtils.addAdmin(id);
                sender.sendMessage(Color.cmd("&2Администратор &9"+getName+" @id"+id+"&2 добавлен в конфиг!"));
                return true;
            }
            sender.sendMessage(Color.cmd("&2Использование: &a/%cmd% add &9(idvk)&2.").replace("%cmd%", cmd.getName()));
            return true;
        }

        if (args[0].equalsIgnoreCase("setlevel")) {
            if (args.length > 1) {
                if (!Utils.isInt(args[1])) {
                    sender.sendMessage(Color.cmd("&сidvk должен состоять из чисел!"));
                    return true;
                }
                int id = Integer.parseInt(args[1]);
                String getName = VKUtils.getUserFullName(id);
                if (!ConfigUtils.isAdmin(id)) {
                    sender.sendMessage(Color.cmd("&cАдминистратор &4"+getName+"&c не найден!"));
                    return true;
                }
                if (args.length > 2) {
                    if (!Utils.isInt(args[2])) {
                        sender.sendMessage(Color.cmd("&сУровень должен состоять из чисел!"));
                        return true;
                    }
                    int level = Integer.parseInt(args[2]);
                    if (!Utils.isLevelSet(level)) {
                        sender.sendMessage(Color.cmd("&cВыберите один из уровней: 50 или 100!"));
                        return true;
                    }
                    ConfigUtils.setLevelAdmin(id, level);
                    sender.sendMessage(Color.cmd("&aВы установили уровень доступа администратору &2"+getName+"&a на &2"+level+"&a!"));
                    return true;
                }
                sender.sendMessage(Color.cmd("&2Использование: &a/%cmd% setlevel &9"+id+" &a(level)").replace("%cmd%", cmd.getName()));
                return true;
            }
            sender.sendMessage(Color.cmd("&2Использование: &a/%cmd% setlevel &9(idvk) &a(level)").replace("%cmd%", cmd.getName()));
            return true;
        }

        if (args[0].equalsIgnoreCase("remove")) {
            if (args.length > 1) {
                if (!Utils.isInt(args[1])) {
                    sender.sendMessage(Color.cmd("&сidvk должен состоять из чисел!"));
                    return true;
                }
                int id = Integer.parseInt(args[1]);
                String getName = VKUtils.getUserFullName(id);
                if (!ConfigUtils.isAdmin(id)) {
                    sender.sendMessage(Color.cmd("&cТакого администратора нету в конфиге!"));
                    return true;
                }
                ConfigUtils.removeAdmin(id);
                sender.sendMessage(Color.cmd("&2Администратор &9"+getName+" @id"+id+"&2 удалён из конфига!"));
                return true;
            }
            sender.sendMessage(Color.cmd("&2Использование: &a/%cmd% remove &9(idvk)&2").replace("%cmd%", cmd.getName()));
            return true;
        }

        if (args[0].equalsIgnoreCase("info")) {
            if (args.length > 1) {
                if (!Utils.isInt(args[1])) {
                    sender.sendMessage(Color.cmd("&сidvk должен состоять из чисел!"));
                    return true;
                }
                int id = Integer.parseInt(args[1]);
                String getName = VKUtils.getUserFullName(id);
                if (!ConfigUtils.isAdmin(id)) {
                    sender.sendMessage(Color.cmd("&cАдминистратор "+getName+" не найден!"));
                    return true;
                }
                int level = ConfigUtils.getLevelAdmin(id);
                sender.sendMessage(Color.cmd("&2Информация про администратора &9"+getName+"&2:"));
                sender.sendMessage(Color.parse("&aУровень доступа: &2"+level));
                return true;
            }
            sender.sendMessage(Color.cmd("&2Использование: &a/%cmd% info &9(idvk)").replace("%cmd%", cmd.getName()));
            return true;
        }

        sender.sendMessage(Color.cmd("&c/%cmd% help.").replace("%cmd%", cmd.getName()));
        return true;
    }
}
