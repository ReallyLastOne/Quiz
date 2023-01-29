package com.reallylastone.quiz.exercise.translation.repository;

import com.reallylastone.quiz.exercise.translation.model.Translation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface TranslationRepository extends JpaRepository<Translation, Long> {

    @Query(value = "SELECT * FROM TRANSLATION T INNER JOIN TRANSLATION_MAP TM ON T.ID = TM.TRANSLATION_ID WHERE TM.TRANSLATION IN :translations",
            nativeQuery = true)
    List<Translation> getByTranslationValues(Collection<String> translations);
}
