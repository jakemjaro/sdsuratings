package com.sdsuratings.app.repository;

import com.sdsuratings.app.model.Rating;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RatingRepositoryImpl implements RatingRepository {
    public void create(Rating rating) {
        return;
    }

    public List<Rating> findAllByProfessorName(String name) {
        return null;
    }

    public List<Rating> findAllByCourseByProfessorName(String name, String course) {
        return null;
    }
}
