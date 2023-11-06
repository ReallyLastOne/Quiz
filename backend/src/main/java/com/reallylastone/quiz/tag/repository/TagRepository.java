package com.reallylastone.quiz.tag.repository;

import com.reallylastone.quiz.tag.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Long> {
    boolean existsByName(String name);

    Optional<Tag> findByName(String name);
}
