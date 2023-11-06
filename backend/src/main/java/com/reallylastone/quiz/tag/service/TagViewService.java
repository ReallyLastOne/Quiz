package com.reallylastone.quiz.tag.service;

import com.reallylastone.quiz.tag.model.TagCreateRequest;
import com.reallylastone.quiz.tag.model.TagCreateResponse;
import org.springframework.http.ResponseEntity;

public interface TagViewService {
    ResponseEntity<TagCreateResponse> create(TagCreateRequest request);
}
