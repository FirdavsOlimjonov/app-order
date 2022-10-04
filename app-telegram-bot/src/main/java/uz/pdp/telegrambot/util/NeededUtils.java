package uz.pdp.telegrambot.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import uz.pdp.telegrambot.repository.ChatPageStatusRepository;

@AllArgsConstructor
public class NeededUtils {
    @Getter
    private static ChatPageStatusRepository chatRepo;
}
