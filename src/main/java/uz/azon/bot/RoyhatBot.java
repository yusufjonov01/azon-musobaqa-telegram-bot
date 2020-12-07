package uz.azon.bot;

import uz.azon.entity.User;
import uz.azon.entity.enums.Status;
import uz.azon.repository.UserRepository;
import uz.azon.service.ExcelService;
import uz.azon.service.Payload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class RoyhatBot extends TelegramLongPollingBot {
    @Autowired
    BotService botService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ExcelService excelService;

    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        String text = "";
        Long chatId = null;
        if (update.getCallbackQuery() != null && update.getCallbackQuery().getData().length() > 0) {
            text = update.getCallbackQuery().getData();
            chatId = update.getCallbackQuery().getMessage().getChatId();
        } else if (update.getMessage() != null && update.getMessage().getText() != null && update.getMessage().getText().length() > 0) {
            text = update.getMessage().getText();
            chatId = Objects.requireNonNull(update.getMessage()).getChatId();
        } else if (update.getMessage() != null && update.getMessage().getContact() != null) {
            text = update.getMessage().getContact().getPhoneNumber();
            chatId = Objects.requireNonNull(update.getMessage()).getChatId();
        }
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        Long finalChatId = chatId;
        User user = userRepository.findByChatId(chatId).orElseGet(() -> new User(finalChatId, Status.START));
        if (text != null) {
            switch (text) {
                case "⬅️ Орқага":
                    switch (user.getStatus()) {
                        case ISM:
                            try {
                                user.setStatus(Status.DIRECTORY);
                                userRepository.save(user);
                                sendMessage = botService.getDirectory(update, finalChatId);
                                execute(sendMessage);
                            } catch (TelegramApiException e) {
                                e.printStackTrace();
                            }
                            break;
                        case FAMILYA:
                            try {
                                user.setStatus(Status.ISM);
                                userRepository.save(user);
                                sendMessage = botService.getIsm(update, finalChatId);
                                execute(sendMessage);
                            } catch (TelegramApiException e) {
                                e.printStackTrace();
                            }
                            break;
                        case YOSH:
                            try {
                                user.setStatus(Status.FAMILYA);
                                userRepository.save(user);
                                sendMessage = botService.getFamilya(update, finalChatId);
                                execute(sendMessage);
                            } catch (TelegramApiException e) {
                                e.printStackTrace();
                            }
                            break;
                        case HUDUD:
                            try {
                                user.setStatus(Status.YOSH);
                                userRepository.save(user);
                                sendMessage = botService.getAge(update, finalChatId);
                                execute(sendMessage);
                            } catch (TelegramApiException e) {
                                e.printStackTrace();
                            }
                            break;
                        case EDU:
                            try {
                                user.setStatus(Status.HUDUD);
                                userRepository.save(user);
                                sendMessage = botService.getCity(update, finalChatId);
                                execute(sendMessage);
                            } catch (TelegramApiException e) {
                                e.printStackTrace();
                            }
                            break;
                        case EDU_NAME:
                            try {
                                user.setStatus(Status.EDU);
                                userRepository.save(user);
                                sendMessage = botService.getEdu(update, finalChatId);
                                execute(sendMessage);
                            } catch (TelegramApiException e) {
                                e.printStackTrace();
                            }
                            break;
                        case TELEFON:
                            try {
                                user.setStatus(Status.EDU);
                                userRepository.save(user);
                                sendMessage = botService.getEdu(update, finalChatId);
                                execute(sendMessage);
                            } catch (TelegramApiException e) {
                                e.printStackTrace();
                            }
                            break;
                        case CONFIRM:
                            try {
                                user.setStatus(Status.TELEFON);
                                userRepository.save(user);
                                sendMessage = botService.getTelefon(update, finalChatId);
                                execute(sendMessage);
                            } catch (TelegramApiException e) {
                                e.printStackTrace();
                            }
                            break;
                        default:
                    }
                    break;
                case "❌ Бекор қилиш":
                    try {
                        if (!user.getStatus().equals(Status.FINISHED)) {
                            userRepository.deleteById(user.getId());
                            sendMessage = botService.cancelOk(update, finalChatId);
                            execute(sendMessage);
                        } else {
                            sendMessage = botService.cancel(update, finalChatId);
                            execute(sendMessage);
                        }
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                    break;
                case "Тасдиқлайман":
                    try {
                        if (user.getStatus().equals(Status.FINISHED)) {
                            userRepository.deleteById(user.getId());
                            sendMessage = botService.cancelOk(update, finalChatId);
                            execute(sendMessage);
                        }
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                    break;
                case "Ҳа":
                    try {
                        if (user.getStatus().equals(Status.CONFIRM)) {
                            user.setStatus(Status.FINISHED);
                            userRepository.save(user);
                            sendMessage = botService.getFinished(update, finalChatId);
                            execute(sendMessage);
                        } else if (user.getStatus().equals(Status.EDU)) {
                            user.setStatus(Status.EDU_NAME);
                            userRepository.save(user);
                            sendMessage = botService.getEduName(update, finalChatId);
                            execute(sendMessage);
                        }
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                    break;
                case "Йўқ":
                    try {
                        if (user.getStatus().equals(Status.EDU)) {
                            user.setStatus(Status.TELEFON);
                            userRepository.save(user);
                            sendMessage = botService.getTelefon(update, finalChatId);
                            execute(sendMessage);
                        }
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                    break;
                case "ec17fd48-fab5-4284":
                    try {
                        List<User> allByStatus = userRepository.findAllByStatusAndRead(Status.FINISHED, true);
                        for (int j = 0; j < 2; j++) {
                            if (j == 1)
                                allByStatus = userRepository.findAllByStatusAndRead(Status.FINISHED, false);
                            List<Payload> userPayloads = new ArrayList<>();
                            for (int i = 0; i < allByStatus.size(); i++) {
                                userPayloads.add(new Payload(
                                        String.valueOf(i + 1),
                                        allByStatus.get(i).getFirstName(),
                                        allByStatus.get(i).getLastName(),
                                        allByStatus.get(i).getCity(),
                                        allByStatus.get(i).getPhoneNumber(),
                                        String.valueOf(allByStatus.get(i).getAge()),
                                        allByStatus.get(i).getCreatedAt(),
                                        allByStatus.get(i).getEdu(),
                                        allByStatus.get(i).isRead()
                                ));
                            }
                            File file = excelService.generateExel(userPayloads, j == 0);
                            SendDocument sendDocument = new SendDocument();
                            sendDocument.setChatId(chatId);
                            sendDocument.setDocument(file);
                            execute(sendDocument);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case "/start":
                    try {
                        if (user.getId() != null && user.getStatus().equals(Status.FINISHED)) {
                            sendMessage = botService.getFinish(update, finalChatId);
                        } else {
                            user.setStatus(Status.START_ARIZA);
                            userRepository.save(user);
                            sendMessage = botService.startBot(update, finalChatId);
                        }
                        execute(sendMessage);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                    break;
                case "Ҳифз":
                    try {
                        if (user.getId() != null && user.getStatus().equals(Status.DIRECTORY)) {
                            user.setRead(false);
                            user.setStatus(Status.ISM);
                            userRepository.save(user);
                            sendMessage = botService.getIsm(update, finalChatId);
                        } else {
                            user.setStatus(Status.START_ARIZA);
                            userRepository.save(user);
                            sendMessage = botService.startBot(update, finalChatId);
                        }
                        execute(sendMessage);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                    break;
                case "Тиловат":
                    try {
                        if (user.getId() != null && user.getStatus().equals(Status.DIRECTORY)) {
                            user.setRead(true);
                            user.setStatus(Status.ISM);
                            userRepository.save(user);
                            sendMessage = botService.getIsm(update, finalChatId);
                        } else {
                            user.setStatus(Status.START_ARIZA);
                            userRepository.save(user);
                            sendMessage = botService.startBot(update, finalChatId);
                        }
                        execute(sendMessage);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    switch (user.getStatus()) {
                        case START:
                            try {
                                user.setStatus(Status.START_ARIZA);
                                userRepository.save(user);
                                sendMessage = botService.startBot(update, finalChatId);
                                execute(sendMessage);
                            } catch (TelegramApiException e) {
                                e.printStackTrace();
                            }
                            break;
                        case START_ARIZA:
                            if (text.equals("#StartAriza")) {
                                try {
                                    user.setStatus(Status.DIRECTORY);
                                    userRepository.save(user);
                                    sendMessage = botService.getDirectory(update, finalChatId);
                                    execute(sendMessage);
                                } catch (TelegramApiException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                try {
                                    user.setStatus(Status.START_ARIZA);
                                    userRepository.save(user);
                                    sendMessage = botService.startBot(update, finalChatId);
                                    execute(sendMessage);
                                } catch (TelegramApiException e) {
                                    e.printStackTrace();
                                }
                            }
                            break;
                        case DIRECTORY:
                            break;
                        case ISM:
                            try {
                                if (!text.equals("#StartAriza") && user.getStatus().equals(Status.ISM)) {
                                    user.setFirstName(text);
                                    user.setStatus(Status.FAMILYA);
                                    userRepository.save(user);
                                    sendMessage = botService.getFamilya(update, finalChatId);
                                    execute(sendMessage);
                                }
                            } catch (TelegramApiException e) {
                                e.printStackTrace();
                            }
                            break;
                        case FAMILYA:
                            try {
                                user.setLastName(text);
                                user.setStatus(Status.YOSH);
                                userRepository.save(user);
                                sendMessage = botService.getAge(update, finalChatId);
                                execute(sendMessage);
                            } catch (TelegramApiException e) {
                                e.printStackTrace();
                            }
                            break;
                        case YOSH:
                            try {
                                user.setAge(Integer.parseInt(text));
                                user.setStatus(Status.HUDUD);
                                userRepository.save(user);
                                sendMessage = botService.getCity(update, finalChatId);
                                execute(sendMessage);
                            } catch (Exception e) {
                                try {
                                    sendMessage = botService.getAge(update, finalChatId);
                                    execute(sendMessage);
                                } catch (TelegramApiException k) {
                                    k.printStackTrace();
                                }
                            }
                            break;
                        case HUDUD:
                            try {
                                user.setCity(text);
                                user.setStatus(Status.EDU);
                                userRepository.save(user);
                                sendMessage = botService.getEdu(update, finalChatId);
                                execute(sendMessage);
                            } catch (TelegramApiException e) {
                                e.printStackTrace();
                            }
                            break;
                        case EDU:
//                            try {
//                                user.setEdu(text);
//                                user.setStatus(Status.TELEFON);
//                                userRepository.save(user);
//                                sendMessage = botService.getTelefon(update, finalChatId);
//                                execute(sendMessage);
//                            } catch (TelegramApiException e) {
//                                e.printStackTrace();
//                            }
                            break;
                        case EDU_NAME:
                            try {
                                user.setEdu(text);
                                user.setStatus(Status.TELEFON);
                                userRepository.save(user);
                                sendMessage = botService.getTelefon(update, finalChatId);
                                execute(sendMessage);
                            } catch (TelegramApiException e) {
                                e.printStackTrace();
                            }
                            break;
                        case TELEFON:
                            try {
                                if (update.getMessage().getContact() != null) {
                                    user.setPhoneNumber(update.getMessage().getContact().getPhoneNumber());
                                    user.setStatus(Status.CONFIRM);
                                    userRepository.save(user);
                                    sendMessage = botService.getConfirm(update, finalChatId, user);
                                } else {
                                    sendMessage = botService.getTelefon(update, finalChatId);
                                }
                                execute(sendMessage);
                            } catch (TelegramApiException e) {
                                e.printStackTrace();
                            }
                            break;
                        case CONFIRM:
                            try {
                                sendMessage = botService.getConfirm(update, finalChatId, user);
                                execute(sendMessage);
                            } catch (TelegramApiException e) {
                                e.printStackTrace();
                            }
                            break;
                        case FINISHED:
                            try {
                                userRepository.save(user);
                                sendMessage = botService.getFinish(update, finalChatId);
                                execute(sendMessage);
                            } catch (TelegramApiException e) {
                                e.printStackTrace();
                            }
                            break;
                        default:
                            try {
                                sendMessage = botService.error(update, finalChatId);
                                execute(sendMessage);
                            } catch (TelegramApiException e) {
                                e.printStackTrace();
                            }
                    }
            }
        } else {
            switch (user.getStatus()) {
                case START:
                    try {
                        user.setStatus(Status.START_ARIZA);
                        userRepository.save(user);
                        sendMessage = botService.startBot(update, finalChatId);
                        execute(sendMessage);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                    break;
                case START_ARIZA:
                    try {
                        user.setStatus(Status.DIRECTORY);
                        userRepository.save(user);
                        sendMessage = botService.getDirectory(update, finalChatId);
                        execute(sendMessage);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                    break;
                case DIRECTORY:
//                    try {
//                        user.setFirstName(text);
//                        user.setStatus(Status.ISM);
//                        userRepository.save(user);
//                        sendMessage = botService.getIsm(update, finalChatId);
//                        execute(sendMessage);
//                    } catch (TelegramApiException e) {
//                        e.printStackTrace();
//                    }
                    break;
                case ISM:
                    try {
                        user.setFirstName(text);
                        user.setStatus(Status.FAMILYA);
                        userRepository.save(user);
                        sendMessage = botService.getFamilya(update, finalChatId);
                        execute(sendMessage);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                    break;
                case FAMILYA:
                    try {
                        user.setLastName(text);
                        user.setStatus(Status.YOSH);
                        userRepository.save(user);
                        sendMessage = botService.getAge(update, finalChatId);
                        execute(sendMessage);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                    break;
                case YOSH:
                    try {
                        user.setAge(Integer.parseInt(text));
                        user.setStatus(Status.HUDUD);
                        userRepository.save(user);
                        sendMessage = botService.getCity(update, finalChatId);
                        execute(sendMessage);
                    } catch (TelegramApiException e) {
                        try {
                            sendMessage = botService.getAge(update, finalChatId);
                            execute(sendMessage);
                        } catch (TelegramApiException k) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case HUDUD:
                    try {
                        user.setCity(text);
                        user.setStatus(Status.EDU);
                        userRepository.save(user);
                        sendMessage = botService.getEdu(update, finalChatId);
                        execute(sendMessage);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                    break;
                case EDU:
//                    try {
//                        user.setEdu(text);
//                        user.setStatus(Status.EDU_NAME);
//                        userRepository.save(user);
//                        sendMessage = botService.getEduName(update, finalChatId);
//                        execute(sendMessage);
//                    } catch (TelegramApiException e) {
//                        e.printStackTrace();
//                    }
                    break;
                case EDU_NAME:
//                    try {
//                        user.setStatus(Status.TELEFON);
//                        userRepository.save(user);
//                        sendMessage = botService.getTelefon(update, finalChatId);
//                        execute(sendMessage);
//                    } catch (TelegramApiException e) {
//                        e.printStackTrace();
//                    }
                    break;
                case TELEFON:
                    try {
                        if (update.getMessage().getContact() != null) {
                            user.setPhoneNumber(update.getMessage().getContact().getPhoneNumber());
                            user.setStatus(Status.CONFIRM);
                            userRepository.save(user);
                            sendMessage = botService.getFinished(update, finalChatId);
                        } else {
                            sendMessage = botService.getTelefon(update, finalChatId);
                        }
                        execute(sendMessage);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                    break;
                case CONFIRM:
                    try {
                        userRepository.save(user);
                        sendMessage = botService.getConfirm(update, finalChatId, user);
                        execute(sendMessage);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                    break;
                case FINISHED:
                    try {
                        userRepository.save(user);
                        sendMessage = botService.getFinish(update, finalChatId);
                        execute(sendMessage);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    try {
                        sendMessage = botService.error(update, finalChatId);
                        execute(sendMessage);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
            }
        }
    }

    @Override
    public String getBotUsername() {
        return null;
    }

    @Override
    public String getBotToken() {
        return null;
    }

}
