package com.reallylastone.cli.util;

public class Constants {
    public static final String TOKEN_STORAGE_FILE = "token-storage";
    public static final String STORAGE_TEMPLATE = """
            AccessToken: {access_token}
            RefreshToken: {refresh_token}
            """;
}
