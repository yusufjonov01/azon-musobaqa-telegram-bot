package uz.azon.repository;

import uz.azon.entity.User;
import uz.azon.entity.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByChatId(Long chatId);

    List<User> findAllByStatusAndRead(Status status, boolean read);
}
