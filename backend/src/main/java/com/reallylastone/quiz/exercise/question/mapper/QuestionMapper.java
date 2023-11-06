package com.reallylastone.quiz.exercise.question.mapper;

import com.reallylastone.quiz.exercise.question.model.Question;
import com.reallylastone.quiz.exercise.question.model.QuestionAddResponse;
import com.reallylastone.quiz.exercise.question.model.QuestionCreateRequest;
import com.reallylastone.quiz.exercise.question.model.QuestionView;
import com.reallylastone.quiz.tag.model.Tag;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Mapper(imports = { Collections.class, List.class, Stream.class, Collectors.class, Random.class })
public interface QuestionMapper {
    @Mapping(source = "question", target = "answers", qualifiedByName = "answersMapMethod")
    QuestionView mapToView(Question question);

    @Named("answersMapMethod")
    default List<String> toView(Question question) {
        List<String> collect = Stream
                .concat(Stream.concat(question.getWrongAnswers().stream(), question.getCorrectAnswers().stream()),
                        Stream.of(question.getCorrectAnswer()))
                .toList();

        return Stream.concat(question.getWrongAnswers().stream(), Stream.of(question.getCorrectAnswer())).toList()
                .stream().collect(Collectors.collectingAndThen(Collectors.toList(), collected -> {
                    Collections.shuffle(collected);
                    return collected.stream();
                })).limit(collect.size()).toList();
    }

    default List<Tag> mapToTags(List<String> value) {
        if (value == null)
            return new ArrayList<>();

        return value.stream().map(s -> {
            Tag tag = new Tag();
            tag.setName(s);
            return tag;
        }).toList();
    }

    @Mapping(target = "correctAnswer", expression = "java(request.correctAnswers().get(0))")
    @Mapping(target = "correctAnswers", expression = "java(request.correctAnswers().subList(1, request.correctAnswers().size()))")
    @Mapping(target = "multipleCorrectAnswers", expression = "java(request.correctAnswers().size() > 1)")
    @Mapping(target = "wrongAnswers", source = "wrongAnswers")
    Question mapToEntity(QuestionCreateRequest request);

    @Mapping(target = "correctAnswers", expression = "java(Stream.concat(question.getCorrectAnswers().stream(), "
            + "Stream.of(question.getCorrectAnswer())).toList())")
    @Mapping(target = "tags", expression = "java(mapToNames(question.getTags()))")
    QuestionAddResponse mapToResponse(Question question);

    default List<String> mapToNames(List<Tag> tags) {
        return tags.stream().map(Tag::getName).toList();
    }
}
