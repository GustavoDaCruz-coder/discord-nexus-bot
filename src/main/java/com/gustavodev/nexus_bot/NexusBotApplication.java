package com.gustavodev.nexus_bot;

import com.gustavodev.nexus_bot.listener.MessageListener;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;

import javax.security.auth.login.LoginException;

public class NexusBotApplication {
    public static void main(String[] args) throws LoginException {

        JDABuilder.createDefault(System.getenv("TOKEN"), GatewayIntent.GUILD_MESSAGES)
                .addEventListeners(new MessageListener())
                .build();
    }
}
