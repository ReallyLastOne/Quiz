package com.reallylastone.quiz.exercise.question.mapper;

import com.reallylastone.quiz.exercise.question.model.Question;
import com.reallylastone.quiz.exercise.question.model.QuestionView;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Mapper(imports = {Collections.class, List.class, Stream.class, Collectors.class, Random.class})
public interface QuestionMapper {
    @Mapping(source = "question", target = "answers", qualifiedByName = "answersMapMethod")
    QuestionView mapToView(Question question);

    @Named("answersMapMethod")
    default List<String> toView(Question question) {
        List<String> collect = Stream.concat(question.getWrongAnswers().stream(), Stream.of(question.getCorrectAnswer())).toList();

        return Stream.concat(question.getWrongAnswers().stream(), Stream.of(question.getCorrectAnswer())).toList().stream()
                .collect(Collectors.collectingAndThen(Collectors.toList(), collected -> {
                    Collections.shuffle(collected);
                    return collected.stream();
                }))
                .limit(collect.size())
                .toList();
    }
}
