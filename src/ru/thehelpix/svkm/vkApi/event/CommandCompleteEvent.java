package ru.thehelpix.svkm.vkApi.event;


import ru.thehelpix.svkm.vkApi.command.VkCommand;
import ru.thehelpix.svkm.vkApi.message.ReceiveMessage;

public class CommandCompleteEvent extends VkEvent {

    private VkCommand command;
    private String[] args;
    private boolean cancel = false;
    private ReceiveMessage message;

    public CommandCompleteEvent(boolean async, ReceiveMessage message, VkCommand command, String args[]){
        super(async);
        this.message = message;
        this.args = args;
        this.command = command;
    }

    public boolean isCancel() {
        return cancel;
    }

    public void setCancel(boolean cancel) {
        this.cancel = cancel;
    }

    public VkCommand getCommand() {
        return command;
    }

    public String[] getArgs() {
        return args;
    }

    public ReceiveMessage getMessage() {
        return message;
    }
}
