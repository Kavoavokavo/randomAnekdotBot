package com.egomosha.bots.anekdot.randomanekdotbot.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import com.egomosha.bots.anekdot.randomanekdotbot.bot.AnekdotBot;

@Configuration
public class AnekdotBotConfiguration {


    @Bean
    public static TelegramBotsApi telegramBotsApi(AnekdotBot anekdotBot) throws TelegramApiException {
        var api = new TelegramBotsApi(DefaultBotSession.class);
        api.registerBot(anekdotBot);
        return api;
    }


}