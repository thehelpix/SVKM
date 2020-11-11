package ru.thehelpix.svkm.vkApi.message;


import ru.thehelpix.svkm.vkApi.Group;

public class RequestMessage {

    public String to, message;

    public RequestMessage(String to, String message){
        this.to = to;
        this.message = message;
    }

    public String getTo() {
        return to;
    }

    public void send(Group group){
        group.sendMessage(this);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
