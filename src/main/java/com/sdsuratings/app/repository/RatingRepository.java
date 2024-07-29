package com.sdsuratings.app.repository;

import com.sdsuratings.app.model.Rating;

import java.util.List;

public interface RatingRepository {
    void create(Rating rating);

    List<Rating> findAllByProfessorName(String name);

    List<Rating> findAllByCourseByProfessorName(String name, String course);
}
