package com.reallylastone.quiz.exercise.core;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Entity
public abstract class Exercise {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    public Long id;
}
