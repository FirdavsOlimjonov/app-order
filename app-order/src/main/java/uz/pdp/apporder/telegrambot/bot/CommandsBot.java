package uz.pdp.apporder.telegrambot.bot;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.starter.SpringWebhookBot;
import uz.pdp.apporder.telegrambot.entity.ChatPageStatus;
import uz.pdp.apporder.telegrambot.payload.StatusEnum;
import uz.pdp.apporder.telegrambot.repository.ChatPageStatusRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommandsBot extends SpringWebhookBot {
    String botPath;
    String botUsername;
    String botToken;
    String oneTimeCode = "";
    @Autowired
    ChatPageStatusRepository repository;

    Map<Long, StatusEnum> longStatusEnumMap = new HashMap<>();

    public CommandsBot(SetWebhook setWebhook) {
        super(setWebhook);
    }

    @Override
    public String getBotUsername() {
        return "maxway_deliverybot";
    }

    @Override
    public String getBotToken() {
        return "5511026470:AAHT-D1EXn447o_DdGh5F0PfrAI-iV9DzSQ";
    }

    @Override
    @SneakyThrows
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        Message updateMessage = update.getMessage();
        Long chatId = updateMessage.getChatId();
        if ( !repository.existsById(chatId)){

            repository.save(new ChatPageStatus(chatId, StatusEnum.STATUS_START));
        }


        ChatPageStatus pageStatus = repository.findById(chatId).get();
        StatusEnum status = pageStatus.getStatus();

        if (status.equals(StatusEnum.STATUS_REGISTERED)) {
            if (updateMessage.hasText()) {
                switch (updateMessage.getText()) {
                    case "ℹ️ Biz haqimizda":
                        return send(updateMessage, "\uD83C\uDF5F Max Way\n" +
                                "☎️ Aloqa markazi: +998712005400", homeMenu());

                    case "\uD83C\uDF89 Aksiya":
                        return send(updateMessage, "Ayni paytda rejalashtirilgan hech qanday aksiyalar yo'q", homeMenu());

                    case "Biz bilan boglaning":
                        return send(updateMessage, "Biz bilan @MaxWaySupport_bot orqali bog'laning yoki +998712005400 raqamiga qo'ng'iroq qiling.", homeMenu());

                    case "\uD83D\uDECD Buyurtma berish":
                        return send(updateMessage, "Kategoriya tanlang \n" +
                                "\n" +
                                "MaxWay menu (https://telegra.ph/Menyu-09-16-8)", orderMenu());

//                         send(updateMessage, "Buyurtmangizni davom ettiring", orderMenu());

                    case "\uD83C\uDFE0 Mening manzillarim":
                        return send(updateMessage, "Mening manzillarim menusi", addressesMenu());

                    case "⚙️ Sozlamalar":
                        return send(updateMessage, "Sozlamalar menusi", settingsMenu());

                }

            }
        } else if (status.equals(StatusEnum.STATUS_CODE_SENT) && updateMessage.hasText()) {

            if (updateMessage.getText().equals(oneTimeCode)) {
                return welcome(updateMessage, pageStatus);
            } else {
                return send(updateMessage, "Kod xato kiritildi", backMenu());
            }
        } else if (update.hasMessage() && updateMessage.hasText()) {
            String messageText = updateMessage.getText();
            Message command = updateMessage;

            switch (messageText) {
                case "/start":
                    return startAnswer(command);

            }
        } else if (update.hasMessage() && updateMessage.hasContact()) {
            Message message = sendCode(updateMessage, pageStatus);
            return send(message, "Telefon raqamingizga yuborilgan sms kodni kiriting. ** Kod: " + oneTimeCode, backMenu());

        }
        return null;
    }

    private Message sendCode(Message updateMessage, ChatPageStatus pageStatus) {
        pageStatus.setStatus(StatusEnum.STATUS_CODE_SENT);
        repository.save(pageStatus);
        Message message = updateMessage;
        String phoneNumber = updateMessage.getContact().getPhoneNumber();
        oneTimeCode = generateAndSendCode(phoneNumber);
        return message;
    }

    private BotApiMethod<?> welcome(Message updateMessage, ChatPageStatus pageStatus) throws TelegramApiException {
        pageStatus.setStatus(StatusEnum.STATUS_REGISTERED);
        repository.save(pageStatus);
        return send(updateMessage, "Xush kelibsiz " + updateMessage.getChat().getFirstName(), homeMenu());
    }


    private ReplyKeyboardMarkup settingsMenu() {
        return null;
    }

    private ReplyKeyboardMarkup addressesMenu() {

        return null;
    }

    private ReplyKeyboardMarkup orderMenu() {

        return null;
    }

    private ReplyKeyboardMarkup homeMenu() {
        KeyboardButton promotionButton = new KeyboardButton("\uD83C\uDF89 Aksiya");
        KeyboardButton aboutButton = new KeyboardButton("ℹ️ Biz haqimizda");
        KeyboardButton contactButton = new KeyboardButton("Biz bilan boglaning");
        KeyboardButton myAddressesButton = new KeyboardButton("\uD83C\uDFE0 Mening manzillarim");
        KeyboardButton settingsButton = new KeyboardButton("⚙️ Sozlamalar");
        KeyboardButton orderButton = new KeyboardButton("\uD83D\uDECD Buyurtma berish");
        KeyboardRow row1 = new KeyboardRow(List.of(orderButton));
        KeyboardRow row2 = new KeyboardRow(List.of(promotionButton, aboutButton));
        KeyboardRow row3 = new KeyboardRow(List.of(contactButton, settingsButton, myAddressesButton));
        ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup(List.of(row1, row2, row3));
        markup.setSelective(true);
        markup.setResizeKeyboard(true);

        return markup;

    }


    private String generateAndSendCode(String phoneNumber) {
        String code = String.valueOf(Math.random() * 1000000).substring(0, 4);
        sendOneTimeCode(phoneNumber, code);
        return code;
    }

    private void sendOneTimeCode(String phoneNumber, String code) {

    }

    @SneakyThrows
    public BotApiMethod<?> startAnswer(Message message) {
        String text = "Ro'yxatdan o'tish uchun telefon raqamingizni";
        return send(message, text, getMenuForStart());
    }

    private BotApiMethod<?> send(Message message, String text1, ReplyKeyboardMarkup buttonMenu) throws TelegramApiException {
        SendMessage markdown = SendMessage.builder()
                .chatId(String.valueOf(message.getChatId()))
                .parseMode("Markdown")
                .text(text1)
                .replyMarkup(buttonMenu)
                .build();
        return markdown;
    }

    private static ReplyKeyboardMarkup getMenuForStart() {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        KeyboardRow keyboardButtons = new KeyboardRow();
        KeyboardButton keyboardButton = new KeyboardButton();
        keyboardButton.setText("Kontaktni ulashing");
        keyboardButton.setRequestContact(true);
        keyboardButtons.add(keyboardButton);
        replyKeyboardMarkup.setKeyboard(List.of(keyboardButtons));
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setSelective(true);
        return replyKeyboardMarkup;
    }

    private ReplyKeyboardMarkup backMenu() {
        ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup();
        KeyboardRow row = new KeyboardRow();
        KeyboardButton button = new KeyboardButton();
        button.setText("⬅️ Orqaga");
        row.add(button);
        markup.setSelective(true);
        markup.setResizeKeyboard(true);
        markup.setKeyboard(List.of(row));
        return markup;
    }


    @Override
    public String getBotPath() {
        return null;
    }
}
