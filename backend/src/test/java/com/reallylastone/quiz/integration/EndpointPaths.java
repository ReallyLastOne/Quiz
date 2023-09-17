package com.reallylastone.quiz.integration;

public class EndpointPaths {
    public static class Authentication {
        public static final String BASE = "/api/v1/auth";
        public static final String REGISTER_PATH = BASE + "/register";
        public static final String AUTH_PATH = BASE + "/authenticate";
        public static final String REFRESH_PATH = BASE + "/refresh";
        public static final String CSRF_PATH = BASE + "/csrf";
        public static final String ME_PATH = BASE + "/me";
    }

    public static class QuizGame {
        public static final String BASE = "/api/v1/game/quiz";
        public static final String START_GAME_PATH = BASE + "/start";
        public static final String NEXT_QUESTION_PATH = BASE + "/next";
        public static final String ANSWER_QUESTION_PATH = BASE + "/answer";
        public static final String STOP_GAME_PATH = BASE + "/stop";
    }

    public static class TranslationGame {
        public static final String BASE = "/api/v1/game/translation";
        public static final String START_GAME_PATH = BASE + "/start";
        public static final String NEXT_PHRASE_PATH = BASE + "/next";
        public static final String ANSWER_PHRASE_PATH = BASE + "/answer";
        public static final String STOP_GAME_PATH = BASE + "/stop";
    }

    public static class Phrase {
        public static final String BASE = "/api/v1/exercises/phrases";
    }

    public static class Question {
        public static final String BASE = "/api/v1/exercises/questions";
    }
}
