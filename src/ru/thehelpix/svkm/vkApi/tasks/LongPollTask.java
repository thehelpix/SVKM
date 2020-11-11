package ru.thehelpix.svkm.vkApi.tasks;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import ru.thehelpix.svkm.vkApi.Group;
import ru.thehelpix.svkm.vkApi.event.ReceiveMessageEvent;
import ru.thehelpix.svkm.vkApi.message.ReceiveMessage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.TimerTask;

public class LongPollTask extends TimerTask {

    private long ts = 0;
    private Group group;
    private String server, key;

    public LongPollTask(Group group){
        this.group = group;
    }

    public void connect(){
        try{
            String url = "https://api.vk.com/method/groups.getLongPollServer?&need_pts=0&Ip_version=3&group_id=" + group.getId() + "&access_token=" + group.getToken() + "&v=5.103";
            URL obj = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
            connection.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuffer response = new StringBuffer();
            while (in.ready()) {
                response.append(in.readLine());
            }
            connection.disconnect();
            JsonObject responseObject = new Gson().fromJson(response.toString(), JsonObject.class).get("response").getAsJsonObject();
            key = responseObject.get("key").getAsString();
            server = responseObject.get("server").getAsString();
            ts = responseObject.get("ts").getAsLong();
        }catch (IOException ex){
            ex.printStackTrace();
        }
    }

    public void setTs(long ts) {
        this.ts = ts;
    }

    public long getTs() {
        return ts;
    }

    public void checkMessagesOnGroup(){
        try{
            String longUrl = server + "?act=a_check&key=" + key + "&ts=" + ts +"&wait=1&mode=128&version=3&group_id=" + group.getId();
            URL longObj = new URL(longUrl);
            HttpURLConnection con = (HttpURLConnection) longObj.openConnection();
            con.setRequestMethod("GET");
            BufferedReader inLong = new BufferedReader(new InputStreamReader(con.getInputStream()));
            while (inLong.ready()) {
                JsonObject object = new Gson().fromJson(inLong.readLine(), JsonObject.class);
                if(!object.has("failed")){
                    ts = object.get("ts").getAsLong();
                    JsonArray array = object.get("updates").getAsJsonArray();
                    for(int i = 0; i < array.size(); i++){
                        JsonObject obj = array.get(i).getAsJsonObject();
                        if(obj.get("type").getAsString().equalsIgnoreCase("message_new")) {
                            JsonObject objj = obj.get("object").getAsJsonObject();
                            JsonObject msgobj = objj.get("message").getAsJsonObject();
                            String message = new String(msgobj.get("text").getAsString().getBytes(), StandardCharsets.UTF_8);
                            Long peerid = msgobj.get("peer_id").getAsLong();
                            boolean conversation = false;
                            if (peerid > 2000000000) {
                                conversation = true;
                            }
                            String id = msgobj.get("from_id").toString();
                            ReceiveMessage rmessage;
                            if(conversation){
                                rmessage = new ReceiveMessage(id, new String(message.getBytes(), "utf-8"), peerid + "");
                            }else{
                                rmessage = new ReceiveMessage(id, new String(message.getBytes(), "utf-8"));
                            }
                            if(message.startsWith(group.getSymbol())){
                                group.executeCommand(message, rmessage, parseArrayStart(message.split(" "), 1));
                            }
                            group.notifyListeners(new ReceiveMessageEvent(true, rmessage));
                        }
                    }
                }else{
                    if(object.get("failed").getAsInt() == 1){
                        ts = object.get("ts").getAsLong();
                    }else{
                        connect();
                    }
                }
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public String[] parseArrayStart(String[] args, int index){
        if(args.length > index){
            String[] toReturn = new String[args.length - index];
            for (int i = index; i < args.length; i++) {
                toReturn[i - index] = args[i];
            }
            return toReturn;
        }else{
            return new String[0];
        }
    }

    @Override
    public void run() {
        checkMessagesOnGroup();
    }
}
