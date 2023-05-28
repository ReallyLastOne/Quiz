package com.reallylastone.quiz.exercise.phrase.repository;

import com.reallylastone.quiz.exercise.phrase.model.Phrase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface PhraseRepository extends JpaRepository<Phrase, Long> {

    List<Phrase> findByOwnerId(Long ownerId);
}
