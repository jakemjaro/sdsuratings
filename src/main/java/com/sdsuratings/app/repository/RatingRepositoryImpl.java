package com.sdsuratings.app.repository;

import com.sdsuratings.app.model.Rating;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
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
        String sqlQuery = "INSERT INTO ratings (professor_id, quality, difficulty, course, grade, date_published, description, accessibility, workload, class_type) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        jdbcClient.sql(sqlQuery)
                .params(rating.getProfessorId(), rating.getQuality(), rating.getDifficulty(), rating.getCourse(), rating.getGrade(), rating.getDatePublished(), rating.getDescription(), rating.getAccessibility(), rating.getWorkload(), rating.getClassType());
    }

    @Override
    public List<Rating> findAllByProfessorId(int professorId) {
        String sqlQuery = "SELECT * FROM ratings WHERE professor_id = ?";

        return jdbcClient.sql(sqlQuery)
                .param(professorId)
                .query(Rating.class)
                .list();
    }

    @Override
    public List<Rating> findAllByCourseByProfessorId(int professorId, String course) {
        String sqlQuery = "SELECT * FROM ratings WHERE (professor_id = ?) AND (course = ?)";

        return jdbcClient.sql(sqlQuery)
                .params(professorId, course)
                .query(Rating.class)
                .list();
    }

    @Override
    public void deleteById(int id) {
        String sqlQuery = "DELETE FROM ratings WHERE id = ?";

        jdbcClient.sql(sqlQuery)
                .param(id);
    }

    @Override
    public void update(Rating rating) {
        String sqlQuery = "UPDATE ratings SET quality = ?, difficulty = ?, course = ?, grade = ?, date_published = ?, description = ?, accessibility = ?, workload = ?, class_type = ?";

        jdbcClient.sql(sqlQuery)
                .params(rating.getQuality(), rating.getDifficulty(), rating.getCourse(), rating.getGrade(), rating.getDatePublished(), rating.getDescription(), rating.getAccessibility(), rating.getWorkload(), rating.getClassType());
    }
}
