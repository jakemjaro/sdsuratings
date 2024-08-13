package com.sdsuratings.app.repository;

import com.sdsuratings.app.model.Rating;

import java.util.List;

public interface RatingRepository {
    void create(Rating rating);

    List<Rating> findAllByProfessorId(int professorId);

    List<Rating> findAllByCourseByProfessorId(int professorId, String course);

    Rating findById(int id);

    void deleteById(int id);

    void update(Rating rating);
}
