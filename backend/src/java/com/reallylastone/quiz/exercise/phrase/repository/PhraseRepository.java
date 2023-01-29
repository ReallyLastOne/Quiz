package com.reallylastone.quiz.exercise.phrase.repository;

import com.reallylastone.quiz.exercise.phrase.model.Phrase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface PhraseRepository extends JpaRepository<Phrase, Long> {

    @Query(value = "SELECT * FROM PHRASE p INNER JOIN TRANSLATION_MAP tm ON t.ID = tm.TRANSLATION_ID WHERE tm.TRANSLATION IN :translations",
            nativeQuery = true)
    List<Phrase> getByTranslationValues(Collection<String> translations);
}
