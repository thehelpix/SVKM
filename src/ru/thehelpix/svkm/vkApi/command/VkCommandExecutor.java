package ru.thehelpix.svkm.vkApi.command;

import ru.thehelpix.svkm.vkApi.message.ReceiveMessage;

public interface VkCommandExecutor {

    void onCommand(ReceiveMessage message, VkCommand cmd, String label, String[] args);
}
