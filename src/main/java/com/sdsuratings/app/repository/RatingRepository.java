package com.sdsuratings.app.repository;

import com.sdsuratings.app.model.Rating;

import java.util.List;

public interface RatingRepository {
    void create(Rating rating);

    List<Rating> findAllByProfessorId(int id);

    List<Rating> findAllByCourseByProfessorId(int id, String course);
}
