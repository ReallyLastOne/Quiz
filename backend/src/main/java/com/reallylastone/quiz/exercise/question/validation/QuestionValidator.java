package com.reallylastone.quiz.exercise.question.validation;

import com.reallylastone.quiz.exercise.question.model.Question;
import com.reallylastone.quiz.tag.repository.TagRepository;
import com.reallylastone.quiz.user.model.Role;
import com.reallylastone.quiz.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;

@Service
@RequiredArgsConstructor
public class QuestionValidator implements Validator {
    private final TagRepository tagRepository;
    private final SpringValidatorAdapter validatorAdapter;

    @Override
    public boolean supports(Class<?> clazz) {
        return Question.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Question question = (Question) target;

        if (validatorAdapter != null) {
            validatorAdapter.validate(question, errors);
        }

        if (!UserService.getCurrentUser().getRoles().contains(Role.ADMIN)) {
            // TODO: resolve key and value from messages.properties - now it doesn't work as expected
            errors.reject("question.global.permission", "Only users with ADMIN role can create global questions");
        }

        if (question.getTags() != null) {
            for (int i = 0; i < question.getTags().size(); i++) {
                String fieldPath = "tags[" + i + "]";
                if (!tagRepository.existsByName(question.getTags().get(i).getName())) {
                    errors.rejectValue(fieldPath, "invalid.tags.value",
                            "not existing tag: %s".formatted(question.getTags().get(i).getName()));
                }
            }
        }
    }
}
