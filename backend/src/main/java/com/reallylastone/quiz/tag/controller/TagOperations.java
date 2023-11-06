package com.reallylastone.quiz.tag.controller;

import com.reallylastone.quiz.tag.model.TagCreateRequest;
import com.reallylastone.quiz.tag.model.TagCreateResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface TagOperations {
    @PostMapping
    ResponseEntity<TagCreateResponse> create(@RequestBody @Valid TagCreateRequest request);
}
