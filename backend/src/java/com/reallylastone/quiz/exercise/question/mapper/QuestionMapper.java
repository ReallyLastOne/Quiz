package com.reallylastone.quiz.exercise.question.mapper;

import com.reallylastone.quiz.exercise.question.model.Question;
import com.reallylastone.quiz.exercise.question.model.QuestionView;
import org.mapstruct.Mapper;

@Mapper
public interface QuestionMapper {
    QuestionView mapToView(Question question);
}
