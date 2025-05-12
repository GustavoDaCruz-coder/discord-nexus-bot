package com.gustavodev.nexus_bot.service;

import com.gustavodev.nexus_bot.util.MessageUtil;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.awt.*;
import java.time.Instant;

@RequiredArgsConstructor
public class ServerService {

    public void handle(String fullMessage, MessageReceivedEvent messageReceivedEvent) {
        String subCommand = MessageUtil.getSubCommandFromMessage(fullMessage);

        switch (subCommand) {
            case "iniciar": startServer(fullMessage, messageReceivedEvent);
        }
    }

    public void startServer(String fullMessage, MessageReceivedEvent messageReceivedEvent) {
        MessageEmbed teste = new EmbedBuilder()
                .setTitle("SERVIDOR DE TESTE INICIADO")
                .setThumbnail("https://i.postimg.cc/k4KvLRRJ/rami-m-valheim-logo.jpg")
                .setDescription("Sua aplicação foi inciada e ja esta liberada")
                .setColor(Color.GREEN)
                .addField("IP: ", "123.456.666", true)
                .addField("Porta: ", "8080", true)
                .setFooter("Se encontrar algum problema chama nois")
                .setAuthor("Gustavo")
                .build();

        MessageEmbed teste1 = new EmbedBuilder()
                .setTitle("SERVIDOR DE TESTE FINALIZADO")
                .setThumbnail("https://i.postimg.cc/k4KvLRRJ/rami-m-valheim-logo.jpg")
                .setDescription("Sua aplicação foi finalizada e ja esta liberada")
                .setColor(Color.RED)
                .addField("IP: ", "123.456.666", false)
                .addField("Porta: ", "8080", false)
                .setFooter("Se encontrar algum problema chama nois")
                .setAuthor("John","https://i.postimg.cc/k4KvLRRJ/rami-m-valheim-logo.jpg")
                .build();

        MessageEmbed teste2 = new EmbedBuilder()
                .setTitle("SERVIDOR DE TESTE NAO INICIADO")
                .setThumbnail("https://i.postimg.cc/k4KvLRRJ/rami-m-valheim-logo.jpg")
                .setDescription("Sua aplicação foi finalizada e ja esta liberada")
                .setColor(Color.GRAY)
                .addField("IP: ", "123.456.666", true)
                .addField("Porta: ", "8080", true)
                .setFooter("Se encontrar algum problema chama nois")
                .setTimestamp(Instant.now())
                .addBlankField(true)
                .appendDescription(fullMessage)
                .setAuthor("john", "https://i.postimg.cc/k4KvLRRJ/rami-m-valheim-logo.jpg", "https://i.postimg.cc/k4KvLRRJ/rami-m-valheim-logo.jpg")
                .build();


        messageReceivedEvent.getChannel().sendMessageEmbeds(teste).queue();
        messageReceivedEvent.getChannel().sendMessageEmbeds(teste1).queue();
        messageReceivedEvent.getChannel().sendMessageEmbeds(teste2).queue();
    }
}
