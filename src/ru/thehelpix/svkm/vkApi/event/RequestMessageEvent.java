package ru.thehelpix.svkm.vkApi.event;

import ru.thehelpix.svkm.vkApi.message.RequestMessage;

public class RequestMessageEvent extends VkEvent {
    private boolean cancel = false;

    private RequestMessage message;

    public RequestMessageEvent(boolean async, RequestMessage message){
        super(async);
        this.message = message;
    }

    public RequestMessage getMessage() {
        return message;
    }

    public boolean isCancel() {
        return cancel;
    }

    public void setCancel(boolean cancel) {
        this.cancel = cancel;
    }
}
