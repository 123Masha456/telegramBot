package pro.sky.telegrambot.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class NotificationTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column(name = "chat_id")
    private long chatId;
    @Column(name = "date_time")
    private LocalDateTime dateTime;
    @Column(name = "message")
    private String message;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getChatId() {
        return chatId;
    }

    public void setChatId(long chatId) {
        this.chatId = chatId;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        NotificationTask notificationTask = (NotificationTask) o;
        return chatId == notificationTask.chatId
                && Objects.equals(dateTime, notificationTask.dateTime)
                && Objects.equals(message, notificationTask.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(chatId, dateTime, message);
    }
}
