package uz.pdp.telegrambot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.telegrambot.entity.ChatPageStatus;

@Repository
public interface ChatPageStatusRepository extends JpaRepository<ChatPageStatus, Long> {

}
