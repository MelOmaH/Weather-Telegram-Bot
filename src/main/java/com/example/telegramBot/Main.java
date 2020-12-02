package com.example.telegramBot;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;

public class Main {

    public static void main(String[] args) {


        ApiContextInitializer.init();

        MyBot myBot = new MyBot();

        TelegramBotsApi botsApi = new TelegramBotsApi();

        try {
            botsApi.registerBot(myBot);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
