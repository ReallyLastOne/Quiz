package com.reallylastone.quiz.game.core.translation.model;

import com.reallylastone.quiz.exercise.core.ExerciseState;
import com.reallylastone.quiz.exercise.phrase.model.Phrase;
import com.reallylastone.quiz.exercise.phrase.model.PhraseView;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public record ActiveTranslationGameSessionView(int correctAnswers, int phrasesLeft, PhraseView currentActive) {

    public ActiveTranslationGameSessionView(TranslationGameSession session, Function<Phrase, PhraseView> mapper) {
        this((int) session.getTranslationsAndStatus().values().stream().filter(ExerciseState.CORRECT::equals).count(),
                session.getPhrasesSize() - session.getTranslationsAndStatus().size(),
                getView(session, mapper));
    }

    private static PhraseView getView(TranslationGameSession session, Function<Phrase, PhraseView> mapper) {
        Optional<Map.Entry<Phrase, ExerciseState>> current = session.findCurrent();
        return current.map(questionExerciseStateEntry -> mapper.apply(questionExerciseStateEntry.getKey())).orElse(null);
    }
}
