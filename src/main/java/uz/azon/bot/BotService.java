package uz.azon.bot;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import uz.azon.entity.User;
import uz.azon.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Service
public class BotService {
    @Autowired
    UserRepository userRepository;

    public SendMessage startBot(Update update, Long chatId) {
        SendMessage sendMessage = new SendMessage()
                .setChatId(chatId)
                .setText("Ассалому алайкум ва раҳматуллоҳи ва баракатуҳ!\n" +
                        "\n" +
                        "Ушбу бот AzonTV'нинг \"Қуръони карим мусобақаси 1442\" ҳифз ва тиловат йўналишида иштирок этувчилар учун.\n" +
//                        "Ушбу бот AzonTV'нинг \"Қуръони карим мусобақаси 1442\" ҳифз йўналишида иштирок этувчилар учун.\n" +
                        "\n" +
                        "Мусобақада қатнашиш учун \"Ариза топшириш\" тугмасини босиб рўйхатдан ўтишингизни сўраймиз.\n")
                .setParseMode(ParseMode.MARKDOWN);
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> lists = new ArrayList<>();
        List<InlineKeyboardButton> list = new ArrayList<>();
        InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton("Ариза топшириш");
        inlineKeyboardButton.setCallbackData("#StartAriza");
        list.add(inlineKeyboardButton);
        lists.add(list);
        inlineKeyboardMarkup.setKeyboard(lists);
        sendMessage.setReplyMarkup(inlineKeyboardMarkup);
        return sendMessage;

    }

    public SendMessage getDirectory(Update update, Long chatId) {
        return new SendMessage()
                .setChatId(chatId)
                .setText("Қайси йўналишда иштирок этмоқчисиз?")
                .setReplyMarkup(getDirectoryMarkUp())
                .setParseMode(ParseMode.MARKDOWN);
    }

    public SendMessage getIsm(Update update, Long chatId) {
        return new SendMessage()
                .setChatId(chatId)
                .setText("Исмингиз?")
                .setReplyMarkup(getReplyMarkup())
                .setParseMode(ParseMode.MARKDOWN);
    }

    public SendMessage getFamilya(Update update, Long chatId) {
        SendMessage sendMessage = new SendMessage()
                .setChatId(chatId)
                .setText("Фамилиянгиз?")
                .setParseMode(ParseMode.MARKDOWN);
        sendMessage.setReplyMarkup(getReplyMarkup());
        return sendMessage;
    }

    public SendMessage getAge(Update update, Long chatId) {
        SendMessage sendMessage = new SendMessage()
                .setChatId(chatId)
                .setText("Неча ёшдасиз?")
                .setParseMode(ParseMode.MARKDOWN);
        sendMessage.setReplyMarkup(getReplyMarkup());
        return sendMessage;
    }

    public SendMessage getCity(Update update, Long chatId) {
        SendMessage sendMessage = new SendMessage()
                .setChatId(chatId)
                .setText("Яшаш ҳудудингиз? (Шаҳар, туман, вилоят)")
                .setParseMode(ParseMode.MARKDOWN);
        sendMessage.setReplyMarkup(getReplyMarkup());
        return sendMessage;
    }

    public SendMessage getEduName(Update update, Long chatId) {
        SendMessage sendMessage = new SendMessage()
                .setChatId(chatId)
                .setText("Ўқув даргоҳи номини ёзинг.")
                .setParseMode(ParseMode.MARKDOWN);
        sendMessage.setReplyMarkup(getReplyMarkup());
        return sendMessage;
    }

    public SendMessage getEdu(Update update, Long chatId) {
        SendMessage sendMessage = new SendMessage()
                .setChatId(chatId)
                .setText("Диний муассасада таҳсил олганмисиз?")
                .setParseMode(ParseMode.MARKDOWN);
        sendMessage.setReplyMarkup(getEduName());
        return sendMessage;
    }

    public SendMessage getTelefon(Update update, Long chatId) {
        SendMessage sendMessage = new SendMessage()
                .setChatId(chatId)
                .setText("Боғланиш учун мобил рақам қолдиринг")
                .setParseMode(ParseMode.MARKDOWN);
        sendMessage.setReplyMarkup(getPhone());
        return sendMessage;
    }

    public SendMessage getFinished(Update update, Long chatId) {
        return new SendMessage()
                .setChatId(chatId)
                .setReplyMarkup(new ReplyKeyboardRemove())
                .setText("Сизни аризангиз қабул қилинди. \n" +
                        "\n" +
                        "Мусобақага саралаш ... кун давомида тасодифий аниқлаш дастурлари орқали амалга оширилади ва унинг жавоблари ҳар ҳафта мобайнида АзонТВ'нинг ижтимоий тармоқларида эълон қилиб борилади.\n" +
                        "\n" +
                        "Эслатиб ўтамиз, шахсга тегишли маълумотлар хавфсизлиги компания томонидан таъминланади.\n")
                .setParseMode(ParseMode.MARKDOWN);
    }

    public SendMessage getFinish(Update update, Long chatId) {
        return new SendMessage()
                .setChatId(chatId)
                .setText("Сизнинг аризангиз аллақачон қабул қилинган.")
                .setParseMode(ParseMode.MARKDOWN);
    }

    public SendMessage getConfirm(Update update, Long chatId, User user) {
        SendMessage sendMessage = new SendMessage()
                .setChatId(chatId)
                .setText("Иштирокчи маълумоти: " +
                        "\n" +
                        "ФИШ: " + user.getFirstName() + " " + user.getLastName() +
                        "\n" +
                        "Йўналиш: " + (user.isRead() ? " Тиловат " : " Ҳифз ") +
                        "\n" +
                        "Ёш: " + user.getAge() +
                        "\n" +
                        "Яшаш жойингиз: " + user.getCity() +
                        "\n" +
                        "Телефон рақамингиз: " + user.getPhoneNumber() +
                        "\n" +
                        "Ўқув даргоҳи: " + (user.getEdu() != null && !user.getEdu().isEmpty() && user.getEdu().length() > 0 ? user.getEdu() : "йўқ") +
                        "\n" +
                        "Маълумотларингиз тўғрими?" +
                        "\n")
                .setParseMode(ParseMode.MARKDOWN);
        sendMessage.setReplyMarkup(confirm());
        return sendMessage;
    }

    public SendMessage error(Update update, Long chatId) {
        SendMessage sendMessage = new SendMessage()
                .setChatId(chatId)
                .setText("Хатолик! Қайтада уриниб кўринг.")
                .setParseMode(ParseMode.MARKDOWN);
        sendMessage.setReplyMarkup(getReplyMarkup());
        return sendMessage;
    }

    public SendMessage cancel(Update update, Long chatId) {
        SendMessage sendMessage = new SendMessage()
                .setChatId(chatId)
                .setText("Ростдан ҳам мусобақа рўйхатидан ўчиришни истайсизми?")
                .setParseMode(ParseMode.MARKDOWN);
        sendMessage.setReplyMarkup(cancelOk());
        return sendMessage;
    }

    public SendMessage cancelOk(Update update, Long chatId) {
        SendMessage sendMessage = new SendMessage()
                .setChatId(chatId)
                .setText("Мусобақада қантнашишни бекор қилдингиз. Маълумотларингиз рўйҳатдан ўчирилди!")
                .setParseMode(ParseMode.MARKDOWN);
        sendMessage.setReplyMarkup(new ReplyKeyboardRemove());
        return sendMessage;
    }

    public ReplyKeyboardMarkup getEduName() {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        KeyboardRow keyboardRow = new KeyboardRow();
        KeyboardButton keyboardButton = new KeyboardButton();
        keyboardButton.setText("⬅️ Орқага");
        KeyboardButton keyboardButton2 = new KeyboardButton();
        keyboardButton2.setText("❌ Бекор қилиш");
        keyboardRow.add(keyboardButton);
        keyboardRow.add(keyboardButton2);
        KeyboardRow keyboardRow2 = new KeyboardRow();
        KeyboardButton keyboardButton3 = new KeyboardButton();
        keyboardButton3.setText("Ҳа");
        KeyboardButton keyboardButton4 = new KeyboardButton();
        keyboardButton4.setText("Йўқ");
        keyboardRow2.add(keyboardButton3);
        keyboardRow2.add(keyboardButton4);
        List<KeyboardRow> keyboardRowList = new ArrayList<>();
        keyboardRowList.add(keyboardRow2);
        keyboardRowList.add(keyboardRow);
        replyKeyboardMarkup.setKeyboard(keyboardRowList);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);
        return replyKeyboardMarkup;
    }

    public ReplyKeyboardMarkup getReplyMarkup() {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        KeyboardRow keyboardRow = new KeyboardRow();
        KeyboardButton keyboardButton = new KeyboardButton();
        keyboardButton.setText("⬅️ Орқага");
        KeyboardButton keyboardButton2 = new KeyboardButton();
        keyboardButton2.setText("❌ Бекор қилиш");
        keyboardRow.add(keyboardButton);
        keyboardRow.add(keyboardButton2);
        List<KeyboardRow> keyboardRowList = new ArrayList<>();
        keyboardRowList.add(keyboardRow);
        replyKeyboardMarkup.setKeyboard(keyboardRowList);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);
        return replyKeyboardMarkup;
    }

    public ReplyKeyboardMarkup getDirectoryMarkUp() {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        KeyboardRow keyboardRow = new KeyboardRow();
        KeyboardButton keyboardButton = new KeyboardButton();
        keyboardButton.setText("Ҳифз");
        KeyboardButton keyboardButton2 = new KeyboardButton();
        keyboardButton2.setText("Тиловат");
        keyboardRow.add(keyboardButton);
        keyboardRow.add(keyboardButton2);
        List<KeyboardRow> keyboardRowList = new ArrayList<>();
        keyboardRowList.add(keyboardRow);
        replyKeyboardMarkup.setKeyboard(keyboardRowList);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);
        return replyKeyboardMarkup;
    }

    public ReplyKeyboardMarkup confirm() {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        KeyboardRow keyboardRow = new KeyboardRow();
        KeyboardButton keyboardButton1 = new KeyboardButton();
        keyboardButton1.setText("Ҳа");
        keyboardRow.add(keyboardButton1);
        KeyboardButton keyboardButton2 = new KeyboardButton();
        keyboardButton2.setText("⬅️ Орқага");
        keyboardRow.add(keyboardButton2);
        List<KeyboardRow> keyboardRowList = new ArrayList<>();
        keyboardRowList.add(keyboardRow);
        replyKeyboardMarkup.setKeyboard(keyboardRowList);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);
        return replyKeyboardMarkup;
    }


    public ReplyKeyboardMarkup getReplyMarkup3() {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        KeyboardRow keyboardRow = new KeyboardRow();
        List<KeyboardRow> keyboardRowList = new ArrayList<>();
        keyboardRowList.add(keyboardRow);
        replyKeyboardMarkup.setKeyboard(keyboardRowList);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);
        return replyKeyboardMarkup;
    }

    public ReplyKeyboardMarkup cancelOk() {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        KeyboardRow keyboardRow = new KeyboardRow();
        KeyboardButton keyboardButton2 = new KeyboardButton();
        keyboardButton2.setText("Тасдиқлайман");
        KeyboardButton keyboardButton1 = new KeyboardButton();
        keyboardButton2.setText("⬅️ Орқага");
        keyboardRow.add(keyboardButton2);
        keyboardRow.add(keyboardButton1);
        List<KeyboardRow> keyboardRowList = new ArrayList<>();
        keyboardRowList.add(keyboardRow);
        replyKeyboardMarkup.setKeyboard(keyboardRowList);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);
        return replyKeyboardMarkup;
    }


    public ReplyKeyboardMarkup getPhone() {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        KeyboardRow keyboardRow1 = new KeyboardRow();
        KeyboardButton keyboardButton1 = new KeyboardButton();
        keyboardButton1.setText("Телефон рақамни юбориш");
        keyboardButton1.setRequestContact(true);

        KeyboardRow keyboardRow = new KeyboardRow();
        KeyboardButton keyboardButton = new KeyboardButton();
        keyboardButton.setText("⬅️ Орқага");

        KeyboardButton keyboardButton2 = new KeyboardButton();
        keyboardButton2.setText("❌ Бекор қилиш");
        keyboardRow1.add(keyboardButton1);
        keyboardRow.add(keyboardButton);
        keyboardRow.add(keyboardButton2);
        List<KeyboardRow> keyboardRowList = new ArrayList<>();
        keyboardRowList.add(keyboardRow1);
        keyboardRowList.add(keyboardRow);
        replyKeyboardMarkup.setKeyboard(keyboardRowList);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);
        return replyKeyboardMarkup;
    }
}
