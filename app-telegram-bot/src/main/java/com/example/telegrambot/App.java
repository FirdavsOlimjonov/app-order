package com.example.telegrambot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.*;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

// Аннотация, которая объединяет в себя @Configuration, @EnableAutoConfiguration, @ComponentScan
@SpringBootApplication
public class App {
    public static void main(String[] args) {
        // Здесь код написан по заветам
        // https://github.com/rubenlagus/TelegramBots/tree/master/telegrambots-spring-boot-starter
//        try {
//            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
//        } catch (TelegramApiException e) {
//            throw new RuntimeException(e);
//        }


        SpringApplication.run(App.class, args);
    }
}
