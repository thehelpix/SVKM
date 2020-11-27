package ru.thehelpix.svkm;

import org.bukkit.Bukkit;
import org.spigotmc.Metrics;
import ru.thehelpix.svkm.utils.VKUtils;
import ru.thehelpix.svkm.vkApi.Group;
import org.bukkit.plugin.java.JavaPlugin;
import ru.thehelpix.svkm.commands.SVKMCmd;
import ru.thehelpix.svkm.utils.Color;
import ru.thehelpix.svkm.vkEvents.CommandEvent;

import java.io.IOException;

public class Main extends JavaPlugin {
    private static Main plugin;
    public final Group vk = new Group(getConfig().getString("vk.group_id"), getConfig().getString("vk.token"));

    @Override
    public void onEnable() {
        saveDefaultConfig();
        plugin = this;
        try {
            Metrics metrics = new Metrics();
            metrics.start();
        } catch (IOException e) {
            // Failed to submit the stats :-(
        }
        if (!VKUtils.isTrueToken()) {
            Color.log("&4Ваш токен не действителен!");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }
        vk.build();
        vk.addListener(new CommandEvent());
        getCommand("svkm").setExecutor(new SVKMCmd());
        Color.log("&2Плагин включён!");
    }

    @Override
    public void onDisable() {
        if (VKUtils.isTrueToken()) {
            vk.stop();
        }
        Color.log("&aПлагин выключен!");
    }

    public static Main getInstance() {
        return plugin;
    }
}
