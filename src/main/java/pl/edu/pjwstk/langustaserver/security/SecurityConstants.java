package pl.edu.pjwstk.langustaserver.security;

public class SecurityConstants {
    public static final String SECRET_KEY = "secret";
    public static final int TOKEN_EXPIRATION = 7200000; // 2hrs
    public static final String BEARER = "Bearer ";
    public static final String AUTHORIZATION = "Authorization";
    public static final String REGISTER_PATH = "/auth/register";
}
