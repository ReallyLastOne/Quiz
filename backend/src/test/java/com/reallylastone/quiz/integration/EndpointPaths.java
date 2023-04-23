package com.reallylastone.quiz.integration;

public class EndpointPaths {
    public static class Authentication {
        public static final String BASE = "/api/v1/auth";
        public static final String REGISTER_PATH = BASE + "/register";
        public static final String AUTH_PATH = BASE + "/authenticate";
        public static final String REFRESH_PATH = BASE + "/refresh";
    }

    public static class QuizGame {
        public static final String BASE = "/api/v1/game/quiz";
        public static final String START_GAME_PATH = BASE + "/start";
        public static final String NEXT_QUESTION_PATH = BASE + "/next";
        public static final String ANSWER_QUESTION_PATH = BASE + "/answer";

    }
}
