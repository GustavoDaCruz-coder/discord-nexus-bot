package com.gustavodev.nexus_bot.service;

import com.gustavodev.nexus_bot.util.Constants;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.io.FileReader;
import java.io.FileWriter;

import static com.gustavodev.nexus_bot.util.AccountUtil.*;

@RequiredArgsConstructor
public class AccountService {

    @SneakyThrows
    public void addAccount(String fullMessage, MessageReceivedEvent messageReceivedEvent) {
        CSVWriter csvWriter = new CSVWriter(new FileWriter(Constants.DATA_DIRECTORY, true));

        messageReceivedEvent.getMessage().addReaction(Emoji.fromUnicode("U+1F600")).queue();
        messageReceivedEvent.getChannel().sendMessage("Iniciando cadastro!").queue();

        String[] line = {encrypt(getTextByIndex(fullMessage, 1)),
                encrypt(getTextByIndex(fullMessage, 2)),
                encrypt(getTextByIndex(fullMessage, 3))};

        csvWriter.writeNext(line);
        csvWriter.close();

        messageReceivedEvent.getChannel().sendMessage("Cadastro realidado com sucesso!").queue();
    }

    @SneakyThrows
    public void findAccount(MessageReceivedEvent messageReceivedEvent) {
        CSVReader csvReader = new CSVReader(new FileReader(Constants.DATA_DIRECTORY));
        String[] line;

        while ((line = csvReader.readNext()) != null) {
            for (String info: line){
                messageReceivedEvent.getChannel().sendMessage(decrypt(info)).queue();

            }
        }
    }
}
