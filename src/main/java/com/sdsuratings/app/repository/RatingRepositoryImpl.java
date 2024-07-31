package com.sdsuratings.app.repository;

import com.sdsuratings.app.model.Rating;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RatingRepositoryImpl implements RatingRepository {
    private JdbcClient jdbcClient;

    public RatingRepositoryImpl(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public void create(Rating rating) {
        return;
    }

    public List<Rating> findAllByProfessorId(int id) {
        return null;
    }

    public List<Rating> findAllByCourseByProfessorId(int id, String course) {
        return null;
    }
}
