package com.reallylastone.quiz.exercise.question.service;

import com.reallylastone.quiz.exercise.question.model.Question;
import com.reallylastone.quiz.exercise.question.repository.QuestionRepository;
import com.reallylastone.quiz.exercise.question.validation.QuestionValidator;
import com.reallylastone.quiz.tag.model.Tag;
import com.reallylastone.quiz.tag.repository.TagRepository;
import com.reallylastone.quiz.util.validation.ValidationErrorsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
@Slf4j
public class QuestionServiceImpl implements QuestionService {
    private final QuestionRepository questionRepository;
    private final QuestionValidator questionValidator;
    private final TagRepository tagRepository;

    @Override
    public Optional<Question> findById(Long id) {
        return questionRepository.findById(id);
    }

    @Override
    public Page<Question> findAll(Pageable page) {
        if (page.getPageSize() > 100)
            page = PageRequest.of(page.getPageNumber(), 100);

        return questionRepository.findAll(page);
    }

    @Override
    public Question findRandomQuestion() {
        long count = questionRepository.count();
        if (count == 0) {
            String message = "No questions in database, can not draw one";
            log.info(message);
            throw new IllegalStateException(message);
        }
        int toPick = ThreadLocalRandom.current().nextInt((int) count);
        Page<Question> questionPage = questionRepository.findAll(PageRequest.of(toPick, 1));
        Question question = null;
        if (questionPage.hasContent()) {
            question = questionPage.getContent().get(0);
        }

        return question;
    }

    @Override
    public Question findRandomQuestion(List<Long> idsExcluded) {
        if (idsExcluded.isEmpty())
            return findRandomQuestion();

        return questionRepository.findByIdNotIn(idsExcluded).orElseThrow(() -> new IllegalStateException(
                "No questions in database that do not have ids %s ".formatted(idsExcluded)));
    }

    @Override
    public Question create(Question question) {
        validate(question);

        // attach tag entities to current session
        List<Tag> tags = question.getTags().stream().map(tag -> tagRepository.findByName(tag.getName()))
                .filter(Optional::isPresent).map(Optional::get).toList();
        question.setTags(tags);

        return questionRepository.save(question);
    }

    private void validate(Question question) {
        Errors errors = new BeanPropertyBindingResult(question, "PhraseCreateRequest");
        questionValidator.validate(question, errors);

        if (errors.hasErrors()) {
            throw new ValidationErrorsException(errors);
        }
    }

}
