package com.sdsuratings.app.repository;

import com.sdsuratings.app.model.Professor;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProfessorRepositoryImpl implements ProfessorRepository {
    private JdbcClient jdbcClient;

    public ProfessorRepositoryImpl(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    @Override
    public void create(Professor professor) {
        jdbcClient.sql("INSERT INTO professors (first_name, last_name, department) VALUES (?, ?, ?)")
                .params(professor.getFirstName(), professor.getLastName(), professor.getDepartment())
                .update();
    }

    @Override
    public List<Professor> findAll() {
        return jdbcClient.sql("SELECT * FROM professors")
                .query(Professor.class)
                .list();
    }

    @Override
    public Professor findById(int id) {
        return jdbcClient.sql("SELECT * FROM professors WHERE id = ?")
                .param(id)
                .query(Professor.class)
                .single();
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

        return jdbcClient.sql(sqlQuery)
                .query(Professor.class)
                .list();
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

        return jdbcClient.sql(sqlQuery)
                .query(Professor.class)
                .list();
    }
}
