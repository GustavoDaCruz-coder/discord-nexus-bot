package com.gustavodev.nexus_bot.util;

public class MessageUtil {

    public static String getCommandFromMessage(String fullMessage) {
        var commandWithPrefix = fullMessage.split(" ")[0];
        return commandWithPrefix.replace(Constants.PREFIX, "");
    }

    public static String getSubCommandFromMessage(String fullMessage) {
        var commandWithPrefix = fullMessage.split(" ")[1];
        return commandWithPrefix.replace(Constants.PREFIX, "");
    }
}