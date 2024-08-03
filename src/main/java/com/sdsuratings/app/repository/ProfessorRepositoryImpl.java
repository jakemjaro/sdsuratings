package com.sdsuratings.app.repository;

import com.sdsuratings.app.model.Professor;
import com.sdsuratings.app.service.RatingService;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProfessorRepositoryImpl implements ProfessorRepository {
    private JdbcClient jdbcClient;
    private RatingRepository ratingRepository;

    public ProfessorRepositoryImpl(JdbcClient jdbcClient, RatingRepository ratingRepository) {
        this.jdbcClient = jdbcClient;
        this.ratingRepository = ratingRepository;
    }

    private void setAverages(Professor professor) {
        professor.setAverageQuality(ratingRepository.getAverageQualityForProfessor(professor.getId()));
        professor.setAverageDifficulty(ratingRepository.getAverageDifficultyForProfessor(professor.getId()));
    }

    @Override
    public void create(Professor professor) {
        jdbcClient.sql("INSERT INTO professors (first_name, last_name, department) VALUES (?, ?, ?)")
                .params(professor.getFirstName(), professor.getLastName(), professor.getDepartment())
                .update();
    }

    @Override
    public List<Professor> findAll() {
        List<Professor> professorList = jdbcClient.sql("SELECT * FROM professors")
                .query(Professor.class)
                .list();

        professorList.forEach(professor -> setAverages(professor));

        return professorList;
    }

    @Override
    public Professor findById(int id) {
        Professor professor = jdbcClient.sql("SELECT * FROM professors WHERE id = ?")
                .param(id)
                .query(Professor.class)
                .single();

        setAverages(professor);

        return professor;
    }

    @Override
    public List<Professor> findByNameContainsLimited(String sequence, int limit) {
        sequence = sequence.trim();

        String[] splitArray = {};
        String sqlQuery = "";

        if (sequence.contains("\s")) {
            splitArray = sequence.split("\s+");
        }

        if (splitArray.length >= 2) {
            sqlQuery = "SELECT * FROM professors WHERE (first_name ILIKE '%" + splitArray[0] + "%' OR last_name ILIKE '%" + splitArray[0] + "%') AND (first_name ILIKE '%" + splitArray[1] + "%' OR last_name ILIKE '%" + splitArray[1] + "%') LIMIT " + limit;
        } else {
            sqlQuery = "SELECT * FROM professors WHERE first_name ILIKE '%" + sequence + "%' OR last_name ILIKE '%" + sequence + "%' LIMIT " + limit;
        }

        List<Professor> professorList = jdbcClient.sql(sqlQuery)
                .query(Professor.class)
                .list();

        professorList.forEach(professor -> setAverages(professor));

        return professorList;
    }

    @Override
    public List<Professor> findAllByNameContains(String sequence) {
        sequence = sequence.trim();

        String[] splitArray = {};
        String sqlQuery = "";

        if (sequence.contains("\s")) {
            splitArray = sequence.split("\s+");
        }

        if (splitArray.length >= 2) {
            sqlQuery = "SELECT * FROM professors WHERE (first_name ILIKE '%" + splitArray[0] + "%' OR last_name ILIKE '%" + splitArray[0] + "%') AND (first_name ILIKE '%" + splitArray[1] + "%' OR last_name ILIKE '%" + splitArray[1] + "%')";
        } else {
            sqlQuery = "SELECT * FROM professors WHERE first_name ILIKE '%" + sequence + "%' OR last_name ILIKE '%" + sequence + "%'";
        }

        List<Professor> professorList = jdbcClient.sql(sqlQuery)
                .query(Professor.class)
                .list();

        professorList.forEach(professor -> setAverages(professor));

        return professorList;
    }
}
