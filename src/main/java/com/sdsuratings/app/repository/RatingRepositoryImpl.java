package com.sdsuratings.app.repository;

import com.sdsuratings.app.model.Rating;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.OptionalDouble;

@Repository
public class RatingRepositoryImpl implements RatingRepository {
    private JdbcClient jdbcClient;

    public RatingRepositoryImpl(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    @Override
    public void create(Rating rating) {
        jdbcClient.sql("INSERT INTO ratings (professor_id, quality, difficulty, course, grade, date_published, description, accessibility, workload, classtype) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")
                .params(rating.getProfessorId(), rating.getQuality(), rating.getDifficulty(), rating.getCourse(), rating.getGrade(), rating.getDatePublished(), rating.getDescription(), rating.getAccessibility(), rating.getWorkload(), rating.getClassType())
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
