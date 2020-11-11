package ru.thehelpix.svkm.vkApi.event;


import ru.thehelpix.svkm.vkApi.message.ReceiveMessage;

public class ReceiveMessageEvent extends VkEvent {

    private ReceiveMessage message;

    public ReceiveMessageEvent(boolean async, ReceiveMessage message){
        super(async);
        this.message = message;
    }

    public ReceiveMessage getMessage() {
        return message;
    }
}
