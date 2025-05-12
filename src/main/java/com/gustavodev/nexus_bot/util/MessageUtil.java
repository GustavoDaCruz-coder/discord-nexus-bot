package com.gustavodev.nexus_bot.util;

public class MessageUtil {

    public static String getCommandFromMessage(String fullMessage) {
        var commandWithPrefix = fullMessage.split(" ")[0];
        var command = commandWithPrefix.replace(Constants.PREFIX, "");

        return command;
    }


}
