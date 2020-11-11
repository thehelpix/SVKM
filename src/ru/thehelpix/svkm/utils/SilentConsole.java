package ru.thehelpix.svkm.utils;

import ru.thehelpix.svkm.vkApi.message.ReceiveMessage;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.conversations.Conversation;
import org.bukkit.conversations.ConversationAbandonedEvent;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.plugin.Plugin;
import ru.thehelpix.svkm.Main;

import java.util.Set;

public class SilentConsole implements ConsoleCommandSender {
    private final ConsoleCommandSender sender = Bukkit.getConsoleSender();
    private final ReceiveMessage message;

    public static void sendCommand(ReceiveMessage message, String cmd) {
        Bukkit.getScheduler().runTask(Main.getInstance(), () -> {
            Bukkit.dispatchCommand(new SilentConsole(message), cmd);
        });
    }

    public SilentConsole(ReceiveMessage message) {
        this.message = message;
    }

    @Override
    public void sendMessage(String s) {
        message.sendMessage(ChatColor.stripColor(s));
    }

    @Override
    public void sendMessage(String[] strings) {
        message.sendMessage(String.join(" ", strings));
    }


    public CommandSender.Spigot spigot() {
        return null;
    }

    public boolean isConversing() {
        return false;
    }

    public void acceptConversationInput(String s) {}

    public boolean beginConversation(Conversation conversation) {
        return false;
    }

    public void abandonConversation(Conversation conversation) {}

    public void abandonConversation(Conversation conversation, ConversationAbandonedEvent conversationAbandonedEvent) {}

    public void sendRawMessage(String s) {}

    public Server getServer() {
        return this.sender.getServer();
    }

    public String getName() {
        return this.sender.getName();
    }

    public boolean isPermissionSet(String name) {
        return this.sender.isPermissionSet(name);
    }

    public boolean isPermissionSet(Permission perm) {
        return this.sender.isPermissionSet(perm);
    }

    public boolean hasPermission(String name) {
        return this.sender.hasPermission(name);
    }

    public boolean hasPermission(Permission perm) {
        return this.sender.hasPermission(perm);
    }

    public PermissionAttachment addAttachment(Plugin plugin, String name, boolean value) {
        return this.sender.addAttachment(plugin, name, value);
    }

    public PermissionAttachment addAttachment(Plugin plugin) {
        return this.sender.addAttachment(plugin);
    }

    public PermissionAttachment addAttachment(Plugin plugin, String name, boolean value, int ticks) {
        return this.sender.addAttachment(plugin, name, value, ticks);
    }

    public PermissionAttachment addAttachment(Plugin plugin, int ticks) {
        return this.sender.addAttachment(plugin, ticks);
    }

    public void removeAttachment(PermissionAttachment attachment) {
        this.sender.removeAttachment(attachment);
    }

    public void recalculatePermissions() {
        this.sender.recalculatePermissions();
    }

    public Set<PermissionAttachmentInfo> getEffectivePermissions() {
        return this.sender.getEffectivePermissions();
    }

    public boolean isOp() {
        return this.sender.isOp();
    }

    public void setOp(boolean value) {
        this.sender.setOp(value);
    }
}
