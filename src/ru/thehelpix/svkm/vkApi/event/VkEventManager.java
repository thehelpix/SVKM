package ru.thehelpix.svkm.vkApi.event;


import ru.thehelpix.svkm.vkApi.VkHandler;
import ru.thehelpix.svkm.vkApi.VkListener;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VkEventManager {

    private List<VkListener> listeners = new ArrayList<VkListener>();

    public VkEventManager(){
    }

    public void addListener(VkListener listener){
        this.listeners.add(listener);
    }

    public void notifyListeners(VkEvent ev){
        for (VkListener listener : listeners){
            for (Method method : listener.getClass().getDeclaredMethods()){
                if(method.isAnnotationPresent(VkHandler.class)){
                    List<Class<?>> classes = Arrays.asList(method.getParameterTypes());
                    if(classes.contains(ev.getClass())){
                        try{
                            method.invoke(listener, ev);
                        }catch (Exception ex){
                            ex.printStackTrace();
                        }
                    }
                }
            }
        }
    }
}
