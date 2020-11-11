package ru.thehelpix.svkm.vkApi.event;

public class VkEvent {

    private boolean async = false;

    public VkEvent(){
    }

    public VkEvent(boolean async){
        this.async = async;
    }

    public boolean isAsync() {
        return async;
    }
}
