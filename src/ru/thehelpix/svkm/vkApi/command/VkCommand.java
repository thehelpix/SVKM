package ru.thehelpix.svkm.vkApi.command;



import ru.thehelpix.svkm.vkApi.message.ReceiveMessage;

import java.util.Arrays;

public class VkCommand{

    private String command;

    private String[] aliases;

    private VkCommandExecutor executor;


    public VkCommand(String command){
        this.command = command;
    }

    public VkCommand(String command, String[] aliases) {
        this.command = command;
        this.aliases = aliases;
    }

    public VkCommandExecutor getExecutor() {
        return executor;
    }

    public void setExecutor(VkCommandExecutor executor) {
        this.executor = executor;
    }

    public void execute(ReceiveMessage message, String label, String[] args){
        this.executor.onCommand(message, this, label, args);
    }

    public String getCommand() {
        return command;
    }

    public String[] getAliases() {
        return aliases;
    }


    @Override
    public boolean equals(Object obj) {
        if(obj == this){
            return true;
        }else if(obj instanceof VkCommand){
            VkCommand command = (VkCommand)obj;
            if(command.getCommand().equalsIgnoreCase(this.command)){
                return true;
            }else{
                return command.getAliases() != null && !Arrays.asList(command.getAliases()).isEmpty() &&
                        Arrays.asList(command.getAliases()).contains(this.command);

            }
        }else{
            return false;
        }
    }
}
