package ru.thehelpix.svkm.vkApi.message;


import ru.thehelpix.svkm.vkApi.SendMessage;

public class ReceiveMessage implements Message {

    private String from, message, conversation;

    public ReceiveMessage(String from, String message){
        this.from = from;
        this.message = message;
    }

    public ReceiveMessage(String from, String message, String conversation){
        this.from = from;
        this.message = message;
        this.conversation = conversation;
    }

    public boolean isConversation(){
        return conversation != null;
    }

    public String getFrom() {
        return from;
    }

    public String getPeerId(){
        return isConversation() ? conversation : from;
    }

    public String getText() {
        return message;
    }

    public void sendMessage(String message) {
        new SendMessage(this);
        SendMessage.send(message);
    }

    public String getConversation() {
        return conversation;
    }
}
