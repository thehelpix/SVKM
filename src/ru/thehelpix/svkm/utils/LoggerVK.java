package ru.thehelpix.svkm.utils;

import ru.thehelpix.svkm.vkApi.event.ReceiveMessageEvent;

public class LoggerVK {
    public static ReceiveMessageEvent message;

    public LoggerVK(ReceiveMessageEvent msg) {
        message = msg;
    }

    public static void log() {
        Color.message("&c"+ VKUtils.getUserName(message.getMessage().getFrom())+" &4id"+message.getMessage().getFrom()+"&a : "+message.getMessage().getText());
    }
}
