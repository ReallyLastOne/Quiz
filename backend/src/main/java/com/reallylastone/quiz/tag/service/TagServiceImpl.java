package com.reallylastone.quiz.tag.service;

import com.reallylastone.quiz.tag.model.Tag;
import com.reallylastone.quiz.tag.model.TagCreateRequest;
import com.reallylastone.quiz.tag.repository.TagRepository;
import com.reallylastone.quiz.user.model.Role;
import com.reallylastone.quiz.user.service.UserService;
import com.reallylastone.quiz.util.validation.ValidationErrorsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

@RequiredArgsConstructor
@Service
public class TagServiceImpl implements TagService {
    private final TagRepository tagRepository;

    @Override
    public Tag create(TagCreateRequest request) {
        Errors errors = validate(request);
        if (errors.hasErrors()) {
            throw new ValidationErrorsException(errors);
        }

        Tag tag = new Tag();
        tag.setName(request.name());
        tag.setDescription(request.description());

        return tagRepository.save(tag);
    }

    private Errors validate(TagCreateRequest request) {
        Errors errors = new BeanPropertyBindingResult(request, "TagCreateRequest");

        if (!UserService.getCurrentUser().getRoles().contains(Role.ADMIN)) {
            errors.reject("tag.global.permission", "Only users with ADMIN role can create tags");
        }

        if (tagRepository.existsByName(request.name())) {
            errors.rejectValue("name", "tag.name.exists", "Tag with name: %s already exists".formatted(request.name()));
        }

        return errors;
    }
}
