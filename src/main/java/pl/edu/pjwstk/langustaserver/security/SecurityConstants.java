package pl.edu.pjwstk.langustaserver.security;

public final class SecurityConstants {
    public static final String SECRET_KEY = "secret";
    public static final int TOKEN_EXPIRATION = 7200000; // 2hrs
    public static final String BEARER = "Bearer ";
    public static final String AUTHORIZATION = "Authorization";
    public static final String REGISTER_PATH = "/api/auth/register";
    public static final String RECIPES_GET_BY_ID = "/recipes/get/**";
    public static final String RECIPES_GET_PUBLIC = "/recipes/get/public";

    private SecurityConstants() {

    }
}
