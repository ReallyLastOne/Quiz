package com.reallylastone.quiz.util;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class Messages {
    private final MessageSource messageSource;

    public String getMessage(String code, @Nullable Object... args) {
        return messageSource.getMessage(code, args, Locale.getDefault());
    }

}
