package pl.edu.pjwstk.langustaserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.pjwstk.langustaserver.model.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> getUserById(UUID id);
    Optional<User> findByUsername(String username);
    User save(User user);
}
