package com.reallylastone.cli.util;

import com.reallylastone.cli.external.model.AuthenticationResponse;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import static com.reallylastone.cli.util.Constants.STORAGE_TEMPLATE;
import static com.reallylastone.cli.util.Constants.TOKEN_STORAGE_FILE;

public class TokenWriter {

    public void store(AuthenticationResponse response) {
        // TODO: some kind of operating system file lock to prevent concurrent access and malformed data creation,
        //  as the application may be run in multiple instances.
        // TODO: it would be nice to change storage from plain text to some encrypted approach

        try (FileOutputStream outputStream = new FileOutputStream(TOKEN_STORAGE_FILE);
             OutputStreamWriter writer = new OutputStreamWriter(outputStream)) {
            writer.write(templateFrom(response));
        } catch (IOException e) {
            System.err.println("[BACKGROUND TASK] Could not write retrieved tokens to file " + TOKEN_STORAGE_FILE);
        }

    }

    private String templateFrom(AuthenticationResponse response) {
        return STORAGE_TEMPLATE
                .replace("{access_token}", response.accessToken())
                .replace("{refresh_token}", response.refreshToken());
    }
}
