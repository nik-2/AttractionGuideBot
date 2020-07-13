package by.nikita.attractions.entity;

import by.nikita.attractions.constant.Constant;
import by.nikita.attractions.service.CityService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import javax.annotation.PostConstruct;

/**
 * The type Bot.
 */
@Component
public class Bot extends TelegramLongPollingBot {

    private static final Logger LOGGER = LogManager.getLogger(Bot.class);
    private CityService cityService;

    /**
     * Instantiates a new Bot.
     *
     * @param cityService the city service
     */
    @Autowired
    public Bot(CityService cityService) {
        this.cityService = cityService;
    }

    /**
     * Register bot.
     */
    @PostConstruct
    public void registerBot() {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(new Bot(cityService));
        } catch (TelegramApiRequestException e) {
            e.printStackTrace();
        }
    }


    /**
     * Method for receiving messages.
     *
     * @param update Contains a message from the user.
     */
    @Override
    public void onUpdateReceived(Update update) {
        String userMessage = update.getMessage().getText();
        String responseMessage;
        if (userMessage.equals("/start")) {
            responseMessage = "Давай начнём, просто введи название города, который тебя интересует...";
        } else {
            responseMessage = cityService.getInfo(userMessage);
        }
        sendMessage(update.getMessage().getChatId().toString(), responseMessage);
    }

    /**
     * Send message.
     *
     * @param chatId  the chat id
     * @param message the string, who need send like message
     */
    public synchronized void sendMessage(String chatId, String message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(false);
        sendMessage.enableMarkdownV2(false);
        sendMessage.setChatId(chatId);
        sendMessage.setText(message);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            LOGGER.log(Level.ERROR, "Exception: " + e.toString());
        }
    }


    /**
     * The method returns the name of the bot, access during registration.
     *
     * @return bot name
     */
    @Override
    public String getBotUsername() {
        return Constant.BOT_USERNAME;
    }


    /**
     * The method returns a token bot for communication with the Telegram server.
     * bot token return
     */
    @Override
    public String getBotToken() {
        return Constant.BOT_TOKEN;
    }
}