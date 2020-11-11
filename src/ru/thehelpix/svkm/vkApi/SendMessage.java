package ru.thehelpix.svkm.vkApi;


import ru.thehelpix.svkm.vkApi.message.ReceiveMessage;
import ru.thehelpix.svkm.vkApi.message.RequestMessage;
import ru.thehelpix.svkm.Main;

public class SendMessage {

    private static ReceiveMessage mess;
    private static final Group gr = Main.getInstance().vk;

    public SendMessage(ReceiveMessage message) {
        mess = message;
    }

    public static void send(String message) {
        new RequestMessage(mess.isConversation() ? mess.getConversation() : mess.getFrom(), message).send(gr);
    }
}
