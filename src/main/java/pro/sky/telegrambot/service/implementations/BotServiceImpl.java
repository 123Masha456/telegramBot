package pro.sky.telegrambot.service.implementations;

import com.pengrad.telegrambot.TelegramBot;


import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.model.NotificationTask;
import pro.sky.telegrambot.parser.Parser;
import pro.sky.telegrambot.repositories.NotificationTaskRepository;
import pro.sky.telegrambot.service.interfaces.BotService;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;


@Service
public class BotServiceImpl implements BotService {

    private final TelegramBot telegramBot;
    private final NotificationTaskRepository repository;

    public BotServiceImpl(TelegramBot telegramBot, NotificationTaskRepository repository) {
        this.telegramBot = telegramBot;
        this.repository = repository;
    }

    @Override
    public void addTask(Message message) {
        NotificationTask notificationTask;
        long chatId = message.chat().id();
        SendMessage result;

        try {
            notificationTask = Parser.tryToParse(message.text());
            notificationTask.setChatId(chatId);
        } catch (Exception ex) {
            telegramBot.execute(new SendMessage(chatId, "Wrong format of date/time"));
            return;
        }

        repository.save(notificationTask);
        result = new SendMessage(chatId,
                String.format("OK, %s %s", notificationTask.getDateTime(), notificationTask.getMessage()));
        telegramBot.execute(result);
    }

    @Scheduled(cron = "0 0/1 * * * *")
    public void NotificationByTime() {
        var time = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
        var result = repository.findByDateTime(time);

        for (var element : result) {
            var response = new SendMessage(element.getChatId(), element.getMessage());
            telegramBot.execute(response);
        }
    }
}
