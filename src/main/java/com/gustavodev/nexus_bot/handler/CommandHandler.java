package com.gustavodev.nexus_bot.handler;

import com.gustavodev.nexus_bot.service.AccountService;
import com.gustavodev.nexus_bot.util.MessageUtil;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class CommandHandler {

    private final AccountService accountService;

    public CommandHandler() {
        this.accountService = new AccountService();
    }

    public void handle(MessageReceivedEvent messageReceivedEvent) {
        var fullMessage = messageReceivedEvent.getMessage().getContentRaw();
        var command = MessageUtil.getCommandFromMessage(fullMessage);
        var jda = messageReceivedEvent.getJDA();

        try {
            switch (command) {
                case "criar": accountService.addAccount(fullMessage, messageReceivedEvent);
                case "listar": accountService.findAccount(messageReceivedEvent);
            }
        } catch (RuntimeException exception) {
            MessageEmbed messageEmbed = new EmbedBuilder().setTitle("Erro!").setDescription(exception.getMessage()).build();
        }
    }
}
