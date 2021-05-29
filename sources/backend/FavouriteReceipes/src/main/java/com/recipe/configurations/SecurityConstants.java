package com.recipe.configurations;

public class SecurityConstants {
    static final String SECRET = "FavouriteRecipe2021ChallengeSecurityKeyTokenCreation";
    static final String TOKEN_PREFIX = "Bearer ";
    static final String HEADER = "Authorization";
    static final String SIGN_UP_URL = "/users/signup";
    static final long EXPIRATION_TIME_IN_MILLISECONDS = 900000L;
}
