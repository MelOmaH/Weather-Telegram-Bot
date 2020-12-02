package com.example.telegramBot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;

public class MyBot extends TelegramLongPollingBot {

    private static final String TOKEN = "someToken";
    private static final String USERNAME = "IlliaDemBot";


    public MyBot() {

    }

    public void sendMsg(Message message, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setReplyToMessageId(message.getMessageId());
        sendMessage.setText(text);
        sendMessage.enableHtml(true);
        try {
            setButtons(sendMessage);
            execute(sendMessage);

        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }


    public void onUpdateReceived(Update update) {
        Model model = new Model();
        Message message = update.getMessage();
        if (message != null && message.hasText()) {

            switch (message.getText()) {
                case "/start":
                    sendMsg(message, "Hello, I can show You current weather in each city that you want. Please Enter the city name.");
                    break;
                default:
                    try {
                        sendMsg(message, Weather.getWeather(message.getText(), model));
                    } catch (IOException e) {
                        sendMsg(message, "This place is not found.");
                    }
            }
        }
    }

    public void setButtons(SendMessage sendMessage) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);
    }


    public String getBotUsername() {
        return USERNAME;
    }

    public String getBotToken() {
        return TOKEN;
    }
}
