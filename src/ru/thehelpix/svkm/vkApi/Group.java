package ru.thehelpix.svkm.vkApi;


import ru.thehelpix.svkm.vkApi.command.VkCommand;
import ru.thehelpix.svkm.vkApi.command.VkCommandManager;
import ru.thehelpix.svkm.vkApi.event.RequestMessageEvent;
import ru.thehelpix.svkm.vkApi.event.VkEvent;
import ru.thehelpix.svkm.vkApi.event.VkEventManager;
import ru.thehelpix.svkm.vkApi.message.ReceiveMessage;
import ru.thehelpix.svkm.vkApi.message.RequestMessage;
import ru.thehelpix.svkm.vkApi.tasks.LongPollTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Timer;

public class Group {

    private String id, token;
    private VkEventManager eventManager;
    private VkCommandManager commandManager;
    private LongPollTask longPollTask;
    private Timer timer;
    private String symbol = "!";

    public Group(String id, String token){
        this.id = id;
        this.token = token;
    }

    public Group(String id, String token, String symbol){
        this.id = id;
        this.token = token;
        this.symbol = symbol;
    }

    public void build(){
        eventManager = new VkEventManager();
        commandManager = new VkCommandManager(this);
        longPollTask = new LongPollTask(this);
        longPollTask.connect();
        timer = new Timer();
        timer.scheduleAtFixedRate(longPollTask, 0, 1000);
    }

    public void stop(){
        longPollTask.cancel();
        timer.cancel();
    }

    public void notifyListeners(VkEvent event){
        eventManager.notifyListeners(event);
    }

    public void addListener(VkListener listener){
        eventManager.addListener(listener);
    }

    public void executeCommand(String command, ReceiveMessage message, String[] args){
        commandManager.execute(command, message, args);
    }

    public void registerCommand(VkCommand command){
        commandManager.register(command);
    }

    public void unregisterCommand(VkCommand command){
        commandManager.unregister(command);
    }

    @SuppressWarnings("deprecation")
    public void sendMessage(RequestMessage message){
        new Thread(){
            @Override
            public void run() {
                RequestMessageEvent event = new RequestMessageEvent(true, message);
                eventManager.notifyListeners(event);
                if(!event.isCancel()){
                    String id = message.getTo();
                    String mess = message.getMessage();
                    try{
                        String Url = "https://api.vk.com/method/messages.send?&random_id=0&peer_id=" + id + "&message=" + URLEncoder.encode(mess, "utf-8") + "&access_token=" + token + "&v=5.103";
                        URL Obj = new URL(Url);
                        HttpURLConnection con = (HttpURLConnection) Obj.openConnection();
                        con.setRequestMethod("GET");
                        BufferedReader inLong = new BufferedReader(new InputStreamReader(con.getInputStream()));
                        while (inLong.ready()) {
                            con.disconnect();
                        }
                    }catch (IOException ex){
                        ex.printStackTrace();
                    }
                }
                stop();
            }
        }.start();
    }

    public String getId() {
        return id;
    }

    public String getToken() {
        return token;
    }

    public String getSymbol() {
        return symbol;
    }
}
