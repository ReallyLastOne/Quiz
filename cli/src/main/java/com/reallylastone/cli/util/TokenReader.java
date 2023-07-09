package com.reallylastone.cli.util;

import com.reallylastone.cli.external.model.AuthenticationResponse;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import static com.reallylastone.cli.util.Constants.TOKEN_STORAGE_FILE;

public class TokenReader {
    public AuthenticationResponse read() throws IOException {

        try (BufferedReader reader = new BufferedReader(new FileReader(TOKEN_STORAGE_FILE))) {
            return new AuthenticationResponse(extractToken(reader.readLine()), extractToken(reader.readLine()), "bearer");
        }
    }

    private String extractToken(String line) {
        return line.split(":")[1].trim();
    }
}
