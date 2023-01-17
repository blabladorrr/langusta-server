package pl.edu.pjwstk.langustaserver.exception;

import java.util.UUID;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(UUID id) {
        super("The user id '" + id + "' does not exist in our records.");
    }
}