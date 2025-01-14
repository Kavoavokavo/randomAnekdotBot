package com.egomosha.bots.anekdot.randomanekdotbot.bot;


import com.egomosha.bots.anekdot.randomanekdotbot.Anekdot;
import com.egomosha.bots.anekdot.randomanekdotbot.Repository.AnekdotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Document;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.File;
import java.nio.file.Files;
import java.util.List;

@Component
public class AnekdotBot extends TelegramLongPollingBot {

    private final AnekdotRepository anekdotRepository;

    public AnekdotBot(@Value("${telegram.bot.token}") String botToken, AnekdotRepository anekdotRepository) {
        super(botToken);
        this.anekdotRepository = anekdotRepository;
    }

    public long lastId;

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String text = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();
            String stringChatId = String.valueOf(chatId);

            if(text.startsWith("/outputAnekdot")){
                try {
                    long id = Long.parseLong(text.split("/")[2]);
                    String result = anekdotRepository.findAnekdotById(id).getContent();
                    sendMessage(new SendMessage(stringChatId, result));

                } catch (NumberFormatException e) {
                    SendMessage error = new SendMessage(stringChatId, "Invalid input");
                }
            }
            else if (text.equals("/randomAnekdot")){
                Anekdot anekdot = anekdotRepository.findRandomAnekdot();
                if (anekdot != null) {
                        {if (anekdot.getId() == lastId){
                            anekdot = anekdotRepository.findRandomAnekdot();
                            lastId = anekdot.getId();
                        }
                    }
                    System.out.println(anekdot.getId());
                    lastId = anekdot.getId();
                    sendMessage(new SendMessage(stringChatId, anekdot.getContent()));
                } else {
                    sendMessage(new SendMessage(stringChatId, "Упс! Похоже, что у нас нет анекдотов."));
                }
            }
            else if(text.equals("/allAnekdots")){
                List<Anekdot> allAnekdots = anekdotRepository.findAll();
                for(int i = 0; i < allAnekdots.size(); i++){
                    sendMessage(new SendMessage(stringChatId, allAnekdots.get(i).getContent()));
                }
            }
            else if(text.startsWith("/addAnekdot")){
                String anekdotContent = text.split("/ ")[1];
                anekdotRepository.addAnekdot(anekdotContent);
            }
            else if(text.startsWith("/removeAnekdot")){
                long id = Long.parseLong(text.split("/")[2]);
                Anekdot anekdot = anekdotRepository.findAnekdotById(id);
                anekdotRepository.delete(anekdot);
                sendMessage(new SendMessage(stringChatId, "Анедкот под номером " + text.split("/")[2] + " был успешно удалён"));
            }
            else if(text.equals("/help")){
                sendMessage(new SendMessage(stringChatId, "Список команд для взаимодействия:\n/allAnekdots - выдать все анекдоты\n/addAnekdot/ текст анекдота - добавить анекдот()\n/removeAnekdot/номер анекдота - удалить анекдот под нужным номером\n/outputAnekdot/номер анекдота - выдать анекдот под нужным номером\n/randomAnekdot - выдать случайный анекдот\n/putJson/ваш json(одно необходимое поле - content)\n/examples - примеры, уточняющие как писать команды"));
            }
            else if(text.equals("/examples")){
                sendMessage(new SendMessage(stringChatId, "Примеры:\n/outputAnekdot/2 - выдаёт анедкот под номером 2\n/removeAnekdot/2\n/addAnekdot -Доктор, меня все игнорируют. -Следующий!\n/putJson/{\"content\": \"Текст вашего анекдота здесь.\"}"));
            }
            else if(text.startsWith("/putJson")){
                String jsonContent = text.split("/")[2];
                ObjectMapper objectMapper = new ObjectMapper();
                Anekdot anekdot = null;
                try {
                    anekdot = objectMapper.readValue(jsonContent, Anekdot.class);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
                anekdotRepository.save(anekdot);
            }
            System.out.println(text);
        }

    }

    private void sendMessage(SendMessage sendMessage) {
        try {
            execute(sendMessage);
        } catch (Exception e) {
            System.err.println("Ошибка при отправке сообщения: " + e.getMessage());
        }
    }

    @Override
    public String getBotUsername() {
        return "AnekdotusLab1_bot";
    }
}