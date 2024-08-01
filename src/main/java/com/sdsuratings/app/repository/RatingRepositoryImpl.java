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

    @Override
    public void create(Rating rating) {
        jdbcClient.sql("INSERT INTO ratings (professor_id, quality, difficulty, course, grade, date_published, description) VALUES (?, ?, ?, ?, ?, ?, ?)")
                .params(rating.getProfessorId(), rating.getQuality(), rating.getDifficulty(), rating.getCourse(), rating.getGrade(), rating.getDate(), rating.getDescription())
                .update();
    }

    @Override
    public List<Rating> findAllByProfessorId(int professorId) {
        return jdbcClient.sql("SELECT * FROM ratings WHERE professor_id = ?")
                .param(professorId)
                .query(Rating.class)
                .list();
    }

    @Override
    public List<Rating> findAllByCourseByProfessorId(int professorId, String course) {
        return jdbcClient.sql("SELECT * FROM ratings WHERE (professor_id = ?) AND (course = ?)")
                .params(professorId, course)
                .query(Rating.class)
                .list();
    }
}
