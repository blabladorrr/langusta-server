package pl.edu.pjwstk.langustaserver.service;

import org.springframework.stereotype.Service;
import pl.edu.pjwstk.langustaserver.model.User;
import pl.edu.pjwstk.langustaserver.repository.UserRepository;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUser(String id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new RuntimeException("User with id '" + id + "' does not exist in our records");
        }
    }

//    public User saveUser(User user) {
//        return null;
//    }
}
