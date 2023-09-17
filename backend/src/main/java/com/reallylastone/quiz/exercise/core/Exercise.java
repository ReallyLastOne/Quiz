package com.reallylastone.quiz.exercise.core;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.SequenceGenerator;
import lombok.Data;

@Data
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Entity
public abstract class Exercise {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "exercise_generator")
    @SequenceGenerator(name = "exercise_generator", sequenceName = "exercise_seq", allocationSize = 1)
    protected Long id;
}
