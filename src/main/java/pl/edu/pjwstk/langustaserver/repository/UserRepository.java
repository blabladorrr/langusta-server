package pl.edu.pjwstk.langustaserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.pjwstk.langustaserver.model.User;

public interface UserRepository extends JpaRepository<User, String> {
    User getUserById(String id);
    User saveUser(User user);
}
