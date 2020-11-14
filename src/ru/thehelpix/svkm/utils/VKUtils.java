package ru.thehelpix.svkm.utils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import ru.thehelpix.svkm.Main;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class VKUtils {
    private final static Main plugin = Main.getInstance();
    private static final String token = plugin.vk.getToken();

    public static String getUserScreenName(String id){
        try{
            String urlApi = "https://api.vk.com/method/users.get?user_ids="+id+"&v=5.71&access_token="+token;
            URL longObj = new URL(urlApi);
            HttpURLConnection con = (HttpURLConnection) longObj.openConnection();
            con.setRequestMethod("GET");
            BufferedReader inLong = new BufferedReader(new InputStreamReader(con.getInputStream()));
            while (inLong.ready()) {
                JsonObject object = new Gson().fromJson(inLong.readLine(), JsonObject.class);
                JsonArray array = object.get("response").getAsJsonArray();
                for(int i = 0; i < array.size(); i++) {
                    JsonObject obj = array.get(i).getAsJsonObject();
                    String first_name = obj.get("first_name").getAsString();
                    int uuid = obj.get("id").getAsInt();
                    return "[id"+uuid+"|"+first_name+"]";
                }
            }
            return null;
        }catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }

    public static String getUserName(String id){
        try{
            String urlApi = "https://api.vk.com/method/users.get?user_ids="+id+"&v=5.71&access_token="+token;
            URL longObj = new URL(urlApi);
            HttpURLConnection con = (HttpURLConnection) longObj.openConnection();
            con.setRequestMethod("GET");
            BufferedReader inLong = new BufferedReader(new InputStreamReader(con.getInputStream()));
            while (inLong.ready()) {
                JsonObject object = new Gson().fromJson(inLong.readLine(), JsonObject.class);
                JsonArray array = object.get("response").getAsJsonArray();
                for(int i = 0; i < array.size(); i++) {
                    JsonObject obj = array.get(i).getAsJsonObject();
                    return obj.get("first_name").getAsString();
                }
            }
            return null;
        }catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }

    public static Boolean isTrueToken(){
        try{
            String urlApi = "https://api.vk.com/method/users.get?access_token="+token;
            URL longObj = new URL(urlApi);
            HttpURLConnection con = (HttpURLConnection) longObj.openConnection();
            con.setRequestMethod("GET");
            BufferedReader inLong = new BufferedReader(new InputStreamReader(con.getInputStream()));
            while (inLong.ready()) {
                JsonObject object = new Gson().fromJson(inLong.readLine(), JsonObject.class);
                JsonObject obj = (JsonObject) object.get("error");
                String error_code = String.valueOf(obj.get("error_code"));
                return !error_code.equals("5");
            }
            return false;
        }catch (Exception ex){
            ex.printStackTrace();
            return false;
        }
    }

    public static String getUserFullName(int id){
        try{
            String urlApi = "https://api.vk.com/method/users.get?user_ids="+id+"&v=5.71&access_token="+token;
            URL longObj = new URL(urlApi);
            HttpURLConnection con = (HttpURLConnection) longObj.openConnection();
            con.setRequestMethod("GET");
            BufferedReader inLong = new BufferedReader(new InputStreamReader(con.getInputStream()));
            while (inLong.ready()) {
                JsonObject object = new Gson().fromJson(inLong.readLine(), JsonObject.class);
                JsonArray array = object.get("response").getAsJsonArray();
                for(int i = 0; i < array.size(); i++) {
                    JsonObject obj = array.get(i).getAsJsonObject();
                    String first_name = obj.get("first_name").getAsString();
                    String last_name = obj.get("last_name").getAsString();
                    return first_name+" "+last_name;
                }
            }
            return null;
        }catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }
}
