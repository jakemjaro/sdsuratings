package com.sdsuratings.app.repository;

import com.sdsuratings.app.model.Rating;

import java.util.List;

public interface RatingRepository {
    void create(Rating rating);

    List<Rating> findAllByProfessorId(int professorId);

    List<Rating> findAllByCourseByProfessorId(int professorId, String course);

    void deleteById(int id);

    void update(Rating rating);
}
