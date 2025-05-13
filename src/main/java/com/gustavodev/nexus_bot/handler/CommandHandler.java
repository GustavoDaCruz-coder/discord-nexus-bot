package com.gustavodev.nexus_bot.handler;

import com.gustavodev.nexus_bot.service.AccountService;
import com.gustavodev.nexus_bot.service.ServerService;
import com.gustavodev.nexus_bot.util.MessageUtil;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class CommandHandler {

    private final ServerService serverService;
    private final AccountService accountService;

    public CommandHandler() {
        this.serverService = new ServerService();
        this.accountService = new AccountService();
    }

    public void handle(MessageReceivedEvent messageReceivedEvent) {
        var fullMessage = messageReceivedEvent.getMessage().getContentRaw();
        var command = MessageUtil.getCommandFromMessage(fullMessage);

        messageReceivedEvent.getChannel().addReactionById(messageReceivedEvent.getMessageId(), Emoji.fromUnicode("\uD83D\uDC4D")).queue();

        switch (command) {
            case "conta":
                accountService.handle(fullMessage, messageReceivedEvent);
                break;
            case "servidor":
                serverService.handle(fullMessage, messageReceivedEvent);
                break;
            default:
                messageReceivedEvent.getChannel().sendMessage("!Ajuda para mais informações!").queue();
        }
    }

    public void accountInRegister(MessageReceivedEvent messageReceivedEvent) {
        var fullMessage = messageReceivedEvent.getMessage().getContentRaw();
        if (accountService.inRegister(messageReceivedEvent.getAuthor().getId())) {
            accountService.addAccount(fullMessage, messageReceivedEvent);
        }
    }
}