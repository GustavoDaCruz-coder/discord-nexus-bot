package com.gustavodev.nexus_bot.service;

import com.gustavodev.nexus_bot.model.Account;
import com.gustavodev.nexus_bot.util.Constants;
import com.gustavodev.nexus_bot.util.MessageUtil;
import com.gustavodev.nexus_bot.util.StatusByRegistration;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.io.FileReader;
import java.io.FileWriter;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import static com.gustavodev.nexus_bot.util.Constants.TITLE_ACCOUNT_MESSAGE;
import static com.gustavodev.nexus_bot.util.StatusByRegistration.*;

@RequiredArgsConstructor
public class AccountService {

    private final Map<String, StatusByRegistration> statusByRegistration = new HashMap<>();
    private final Map<String, Account> accountMap = new HashMap<>();

    public void handle(String fullMessage, MessageReceivedEvent messageReceivedEvent) {
        var subComand = MessageUtil.getSubCommandFromMessage(fullMessage);

        switch (subComand) {
            case "criar":
                addAccount(fullMessage, messageReceivedEvent);
                break;
            case "listar":
                findAccount(messageReceivedEvent);
                break;
            case "desativar":
                deleteAccount(messageReceivedEvent);
                break;
            case "atualizar":
                updateAccount(fullMessage, messageReceivedEvent);
                break;
        }

    }

    @SneakyThrows
    public void addAccount(String fullMessage, MessageReceivedEvent messageReceivedEvent) {
        CSVWriter csvWriter = new CSVWriter(new FileWriter(Constants.DATA_DIRECTORY, true));
        String userId = messageReceivedEvent.getAuthor().getId();

        if (!statusByRegistration.containsKey(userId)) {
            statusByRegistration.put(userId, WAITING_PLATAFORM);
            accountMap.put(userId, new Account());
            messageReceivedEvent.getChannel().sendMessage("\uD83D\uDD10 Vamos começar o cadastro!\n\nQual a plataforma do serviço?").queue();
            return;
        }

        StatusByRegistration status = statusByRegistration.get(userId);
        Account account = accountMap.get(userId);

        switch (status) {
            case WAITING_PLATAFORM:
                account.setPlataform(fullMessage);
                statusByRegistration.put(userId, WAITING_DESCRTIPTION);
                messageReceivedEvent.getChannel().addReactionById(messageReceivedEvent.getMessageId(), Emoji.fromUnicode("\uD83D\uDC4D")).queue();
                messageReceivedEvent.getChannel().sendMessage("\uD83D\uDCC3 Agora vamos precisar de uma descrição!\nDescreva sua conta:").queue();
                break;
            case WAITING_DESCRTIPTION:
                account.setDescription(fullMessage);
                statusByRegistration.put(userId, WAITING_USERNAME);
                messageReceivedEvent.getChannel().addReactionById(messageReceivedEvent.getMessageId(), Emoji.fromUnicode("\uD83D\uDC4D")).queue();
                messageReceivedEvent.getChannel().sendMessage("\uD83E\uDDD1 Agora nos informe o nome de usuário da conta: ").queue();
                break;
            case WAITING_USERNAME:
                account.setUsername(fullMessage);
                statusByRegistration.put(userId, WAITING_PASSWORD);
                messageReceivedEvent.getChannel().addReactionById(messageReceivedEvent.getMessageId(), Emoji.fromUnicode("\uD83D\uDC4D")).queue();
                messageReceivedEvent.getChannel().sendMessage("\uD83D\uDD11 Agora precisamos saber qual a senha: ").queue();
                break;
            case WAITING_PASSWORD:
                account.setPassword(fullMessage);
                statusByRegistration.put(userId, WAITING_MFA);
                messageReceivedEvent.getChannel().addReactionById(messageReceivedEvent.getMessageId(), Emoji.fromUnicode("\uD83D\uDC4D")).queue();
                messageReceivedEvent.getChannel().sendMessage("\uD83D\uDCF1 Precisamos saber esta conta possui MFA ? (Sim ou Não)").queue();
                break;
            case WAITING_MFA:
                account.setMfa(fullMessage.equalsIgnoreCase("Sim"));
                account.setActive(true);
                account.setOwner(messageReceivedEvent.getAuthor().getName());
                account.setId(UUID.randomUUID().toString());

                String[] line = {account.getPlataform(),
                        account.getUsername(),
                        account.getPassword(),
                        account.getMfa().toString(),
                        account.getOwner(),
                        account.getDescription(),
                        account.getActive().toString(),
                        account.getId()};

                csvWriter.writeNext(line);
                csvWriter.close();

                accountMap.remove(userId);
                statusByRegistration.remove(userId);

                MessageEmbed messageEmbed = new EmbedBuilder()
                        .setTitle(TITLE_ACCOUNT_MESSAGE)
                        .setDescription(account.getDescription())
                        .addField("Plataforma: ", account.getPlataform(), false)
                        .addField("Nome de usuario:", account.getUsername(), true)
                        .addField("Senha: ", account.getPassword(), true)
                        .addField("MFA: ", account.getMfa().toString(), true)
                        .setFooter("ID: " + account.getId())
                        .build();

                messageReceivedEvent.getChannel().sendMessageEmbeds(messageEmbed).queue();
                break;
        }
    }


    @SneakyThrows
    public void findAccount(MessageReceivedEvent messageReceivedEvent) {
        CSVReader csvReader = new CSVReader(new FileReader(Constants.DATA_DIRECTORY));
        String[] line;

        while ((line = csvReader.readNext()) != null) {
            if (Boolean.parseBoolean(line[6])) {
                MessageEmbed messageEmbed = new EmbedBuilder()
                        .setTitle("CONTA")
                        .setDescription(line[5])
                        .addField("Plataforma: ", line[0], false)
                        .addField("Nome de usuario:", line[1], true)
                        .addField("Senha: ", line[2], true)
                        .addField("MFA: ", line[3], true)
                        .setFooter("ID: " + line[7])
                        .build();

                messageReceivedEvent.getChannel().sendMessageEmbeds(messageEmbed).queue();
            }
        }
    }

    public void deleteAccount(MessageReceivedEvent messageReceivedEvent) {
        messageReceivedEvent.getChannel().sendMessage("Excluindo com sucesso!").queue();
    }

    public void updateAccount(String fullMessage, MessageReceivedEvent messageReceivedEvent) {
        messageReceivedEvent.getChannel().sendMessage("Atualizando com sucesso!").queue();
    }

    public boolean inRegister(String userId) {
        return statusByRegistration.containsKey(userId);
    }
}
