package pl.edu.pjwstk.langustaserver.model;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

public class User {
    private Integer ID;
    private String email;
    private String username;
    private String pwHash;
    private List<UserRoles> roles;

    //TODO Make password encoding a configuration bean used by UserService
    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public User() {
    }

    public User(Integer ID, String email, String username, String pwHash, List<UserRoles> roles) {
        this.ID = ID;
        this.email = email;
        this.username = username;
        this.pwHash = encoder.encode(pwHash);
        this.roles = roles;
    }

    public boolean isPasswordMatching(String password) {
        return encoder.matches(password, pwHash);
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPwHash() {
        return pwHash;
    }

    public void setPwHash(String pwHash) {
        this.pwHash = pwHash;
    }

}
