package com.reallylastone.quiz.exception;

import java.time.LocalDateTime;

record ErrorMessage(LocalDateTime date, String message) {
}
