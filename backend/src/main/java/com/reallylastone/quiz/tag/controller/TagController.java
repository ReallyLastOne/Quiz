package com.reallylastone.quiz.tag.controller;

import com.reallylastone.quiz.tag.model.TagCreateRequest;
import com.reallylastone.quiz.tag.model.TagCreateResponse;
import com.reallylastone.quiz.tag.service.TagViewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/exercises/questions/tags")
public class TagController implements TagOperations {
    private final TagViewService tagViewService;

    @Override
    public ResponseEntity<TagCreateResponse> create(TagCreateRequest request) {
        return tagViewService.create(request);
    }
}
