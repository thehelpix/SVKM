package ru.thehelpix.svkm.utils;

import org.bukkit.Bukkit;
import ru.thehelpix.svkm.vkApi.event.ReceiveMessageEvent;

public class LoggerVK {
    public static ReceiveMessageEvent message;

    public LoggerVK(ReceiveMessageEvent msg) {
        message = msg;
    }

    public static void log() {
        Bukkit.getConsoleSender().sendMessage(Color.parse( "&9[VK] &c"+ VKUtils.getUserName(message.getMessage().getFrom())+" &4id"+message.getMessage().getFrom()+"&a : "+message.getMessage().getText()));
    }
}
