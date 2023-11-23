package com.reallylastone.quiz.game.quiz.model;

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
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@Table(name = "quiz_highscore_view")
@Getter
public class HighscoreQuizEntry {
    @Id
    @JsonIgnore
    // id needed for entity but not needed in JSON response
    private Long id;
    private String nickname;
    private int sessionCount;
    private int questionCount;
    private int correctAnswers;
}
