package uz.pdp.telegrambot.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.pdp.telegrambot.payload.StatusEnum;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChatPageStatus {
    @Id
    Long chatId;

    StatusEnum status;
}
