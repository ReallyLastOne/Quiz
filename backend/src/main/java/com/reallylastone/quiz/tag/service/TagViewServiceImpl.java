package com.reallylastone.quiz.tag.service;

import com.reallylastone.quiz.tag.model.TagCreateRequest;
import com.reallylastone.quiz.tag.model.TagCreateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TagViewServiceImpl implements TagViewService {
    private final TagService tagService;

    @Override
    public ResponseEntity<TagCreateResponse> create(TagCreateRequest request) {
        return ResponseEntity.ok(new TagCreateResponse(tagService.create(request).getId()));
    }
}
