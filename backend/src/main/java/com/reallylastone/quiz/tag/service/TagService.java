package com.reallylastone.quiz.tag.service;

import com.reallylastone.quiz.tag.model.Tag;
import com.reallylastone.quiz.tag.model.TagCreateRequest;

public interface TagService {
    Tag create(TagCreateRequest request);
}
