package com.gustavodev.nexus_bot.listener;

import com.gustavodev.nexus_bot.handler.CommandHandler;
import com.gustavodev.nexus_bot.util.Constants;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

@RequiredArgsConstructor
public class MessageListener extends ListenerAdapter {

    public MessageListener() {
        this.commandHandler = new CommandHandler();
    }

    private final CommandHandler commandHandler;

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent messageReceivedEvent) {
        var message = messageReceivedEvent.getMessage().getContentRaw();

        if (!messageReceivedEvent.getAuthor().isBot() && message.toLowerCase(Locale.ROOT).startsWith(Constants.PREFIX)) {
            commandHandler.handle(messageReceivedEvent);
        } else if (!messageReceivedEvent.getAuthor().isBot()) {
            commandHandler.accountInRegister(messageReceivedEvent);
        }
    }
}
