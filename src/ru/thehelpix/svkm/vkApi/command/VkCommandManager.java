package ru.thehelpix.svkm.vkApi.command;

import ru.thehelpix.svkm.vkApi.Group;
import ru.thehelpix.svkm.vkApi.event.CommandCompleteEvent;
import ru.thehelpix.svkm.vkApi.message.ReceiveMessage;

import java.util.ArrayList;
import java.util.List;

public class VkCommandManager {

    private List<VkCommand> commands = new ArrayList<VkCommand>();

    private String symbol;

    private Group group;

    public VkCommandManager(Group group){
        this.group = group;
    }

    public void register(VkCommand command){
        this.commands.add(command);
    }

    public void unregister(VkCommand command){
        this.commands.remove(command);
    }

    @SuppressWarnings("deprecation")
    public void execute(String command, ReceiveMessage message, String[] args){
        new Thread(){
            @Override
            public void run() {
                VkCommand cmd = new VkCommand(command);
                if(commands.contains(cmd)){
                    cmd = commands.get(commands.indexOf(cmd));
                    String label = null;
                    if(command.equals(cmd.getCommand())){
                        label = cmd.getCommand();
                    }else{
                        for (String alias : cmd.getAliases()) {
                            if(alias.equalsIgnoreCase(command)){
                                label = alias;
                            }
                        }
                    }
                    CommandCompleteEvent event = new CommandCompleteEvent(true, message, cmd, args);
                    group.notifyListeners(event);
                    if(!event.isCancel()){
                        cmd.execute(message, label, args);
                    }
                }
                stop();
            }
        }.start();
    }
}
