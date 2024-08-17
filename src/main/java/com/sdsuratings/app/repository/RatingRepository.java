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

    List<Rating> findAllByProfessorIdOffset(int professorId, int offset);

    List<Rating> findAllByCourseByProfessorIdOffset(int professorId, String course, int offset);
}
