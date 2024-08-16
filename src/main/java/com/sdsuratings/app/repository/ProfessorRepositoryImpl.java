package com.sdsuratings.app.repository;

import com.sdsuratings.app.model.Professor;
import com.sdsuratings.app.service.RatingService;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

@Repository
public class ProfessorRepositoryImpl implements ProfessorRepository {
    private JdbcClient jdbcClient;

    public ProfessorRepositoryImpl(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    @Override
    public void create(Professor professor) {
        String sqlQuery = "INSERT INTO professors (first_name, last_name, department) VALUES (?, ?, ?)";

        jdbcClient.sql(sqlQuery)
                .params(professor.getFirstName(), professor.getLastName(), professor.getDepartment())
                .update();
    }

    @Override
    public boolean exists(Professor professor) {
        String sqlQuery = "SELECT * FROM professors WHERE (first_name ILIKE ?) AND (last_name ILIKE ?) AND (department = ?)";

        List<Professor> professorList = jdbcClient.sql(sqlQuery)
                .params(professor.getFirstName(), professor.getLastName(), professor.getDepartment())
                .query(Professor.class)
                .list();

        return !professorList.isEmpty();
    }

    @Override
    public List<Professor> findAll() {
        String sqlQuery = "SELECT * FROM professors ORDER BY last_name ASC";

        return jdbcClient.sql(sqlQuery)
                .query(Professor.class)
                .list();
    }

    @Override
    public List<Professor> findAllSorted(String filterQuery, String departmentQuery) {
        String sqlQuery = "SELECT * FROM professors" + departmentQuery + " ORDER BY " + filterQuery;

        return jdbcClient.sql(sqlQuery)
                .query(Professor.class)
                .list();
    }

    @Override
    public Professor findById(int id) {
        String sqlQuery = "SELECT * FROM professors WHERE id = ?";

        return jdbcClient.sql(sqlQuery)
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
            sqlQuery = "SELECT * FROM professors WHERE (first_name ILIKE '%" + splitArray[0] + "%' OR last_name ILIKE '%" + splitArray[0] + "%') AND (first_name ILIKE '%" + splitArray[1] + "%' OR last_name ILIKE '%" + splitArray[1] + "%') ORDER BY last_name ASC LIMIT " + limit;
        } else {
            sqlQuery = "SELECT * FROM professors WHERE first_name ILIKE '%" + sequence + "%' OR last_name ILIKE '%" + sequence + "%' ORDER BY last_name ASC LIMIT " + limit;
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
            sqlQuery = "SELECT * FROM professors WHERE (first_name ILIKE '%" + splitArray[0] + "%' OR last_name ILIKE '%" + splitArray[0] + "%') AND (first_name ILIKE '%" + splitArray[1] + "%' OR last_name ILIKE '%" + splitArray[1] + "%') ORDER BY last_name ASC";
        } else {
            sqlQuery = "SELECT * FROM professors WHERE first_name ILIKE '%" + sequence + "%' OR last_name ILIKE '%" + sequence + "%' ORDER BY last_name ASC";
        }

        List<Professor> professorList = Collections.emptyList();

        return jdbcClient.sql(sqlQuery)
                .query(Professor.class)
                .list();
    }
}
