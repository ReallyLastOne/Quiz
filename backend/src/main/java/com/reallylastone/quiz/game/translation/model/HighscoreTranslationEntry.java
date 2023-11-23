package com.reallylastone.quiz.game.translation.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Immutable;

@Entity
@Immutable
@Cache(usage= CacheConcurrencyStrategy.READ_ONLY)
@Table(name = "translation_highscore_view")
@Getter
public class HighscoreTranslationEntry {
    @Id
    @JsonIgnore
    private Long id;
    private String nickname;
    private int sessionCount;
    private int phraseCount;
    private int correctPhrases;
}
