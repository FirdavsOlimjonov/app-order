package uz.pdp.telegrambot.bot;


import uz.pdp.telegrambot.entity.User;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.meta.api.objects.Location;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

// Аннотация @Component необходима, чтобы наш класс распознавался Spring, как полноправный Bean
@Component
// Наследуемся от TelegramLongPollingBot - абстрактного класса Telegram API
public class Bot extends TelegramWebhookBot {
    // Аннотация @Value позволяет задавать значение полю путем считывания из application.yaml

    private Message requestMessage = new Message();
    private final SendMessage response = new SendMessage();
    private final UserService userService;

    @Value("${bot.name}")
    private String botUsername;

    @Value("${bot.token}")
    private String botToken;

    @SneakyThrows
    public Bot(TelegramBotsApi telegramBotsApi, UserService userService) {
        this.userService = userService;
        try {
            telegramBotsApi.registerBot(this, new SetWebhook());
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
        Location location;
    }

    /* Перегружаем метод интерфейса LongPollingBot
    Теперь при получении сообщения наш бот будет отвечать сообщением Hi!
     */
    @SneakyThrows
    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update request) {

        requestMessage = request.getMessage();

        response.setChatId(requestMessage.getChatId().toString());

        var entity = new User(
                0, requestMessage.getChat().getUserName(),
                requestMessage.getText());

        if(requestMessage.getText().equals("\uD83C\uDDFA\uD83C\uDDFF O'zbek"));

        return null;

//        if (requestMessage.getText().equals("/start"))
//            defaultMsg(response, "Напишите команду для показа списка мыслей: \n " + "/idea - показать мысли");
//        else if (requestMessage.getText().equals("/idea"))
//            onIdea(response);
//        else
//            defaultMsg(response, "Я записал вашу мысль :) \n ");
//
//        log.info("Working, text[{}]", requestMessage.getText());
//
//        if (requestMessage.getText().startsWith("/")) {
//            entity.setStartWord("команда: ");
//            producerService.sendMessage(entity);
//        } else {
//            entity.setStartWord("мысль: ");
//            producerService.sendMessage(entity);
//            userService.insert(entity);
//        }

        return null;
    }

//    private void onIdea(SendMessage response) throws TelegramApiException {
//        if (userService.getUserList().isEmpty()) {
//            defaultMsg(response, "В списке нет мыслей. \n");
//        } else {
//            defaultMsg(response, "Вот список ваших мыслей: \n");
//            for (User txt : userService.getUserList()) {
//                response.setText(txt.toString());
//                execute(response);
//            }
//        }
//    }

    /**
     * Шабонный метод отправки сообщения пользователю
     *
     * @param response - метод обработки сообщения
     * @param msg - сообщение
     */
    private void defaultMsg(SendMessage response, String msg) throws TelegramApiException {
        response.setText(msg);
        execute(response);
    }

    // Геттеры, которые необходимы для наследования от TelegramLongPollingBot
    public String getBotUsername() {
        return botUsername;
    }

    public String getBotToken() {
        return botToken;
    }

    @Override
    public String getBotPath() {
        return null;
    }
}
