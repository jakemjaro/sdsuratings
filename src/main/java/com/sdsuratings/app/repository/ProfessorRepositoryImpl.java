package com.sdsuratings.app.repository;

import com.sdsuratings.app.model.Professor;
import com.sdsuratings.app.service.RatingService;
import com.sdsuratings.app.util.Constants;
import org.apache.tomcat.util.bcel.Const;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.yaml.snakeyaml.scanner.Constant;

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

        if (sequence.contains("\s")) {
            splitArray = sequence.split("\s+");
        }

        List<Professor> professorList = Collections.emptyList();

        if (splitArray.length >= 2) {
            String sqlQuery = "SELECT * FROM professors WHERE (first_name ILIKE ? OR last_name ILIKE ?) AND (first_name ILIKE ? OR last_name ILIKE ?) ORDER BY last_name ASC LIMIT ?";
            String firstSequence = "%" + splitArray[0] + "%";
            String secondSequence = "%" + splitArray[1] + "%";

            professorList = jdbcClient.sql(sqlQuery)
                    .params(firstSequence, firstSequence, secondSequence, secondSequence, limit)
                    .query(Professor.class)
                    .list();
        } else {
            String sqlQuery = "SELECT * FROM professors WHERE first_name ILIKE ? OR last_name ILIKE ? ORDER BY last_name ASC LIMIT ?";
            sequence = "%" + sequence + "%";

            professorList = jdbcClient.sql(sqlQuery)
                    .params(sequence, sequence, limit)
                    .query(Professor.class)
                    .list();
        }

        return professorList;
    }

    @Override
    public List<Professor> findAllByNameContains(String sequence) {
        sequence = sequence.trim();
        String[] splitArray = {};

        if (sequence.contains("\s")) {
            splitArray = sequence.split("\s+");
        }

        List<Professor> professorList = Collections.emptyList();

        if (splitArray.length >= 2) {
            String sqlQuery = "SELECT * FROM professors WHERE (first_name ILIKE ? OR last_name ILIKE ?) AND (first_name ILIKE ? OR last_name ILIKE ?) ORDER BY last_name ASC";
            String firstSequence = "%" + splitArray[0] + "%";
            String secondSequence = "%" + splitArray[1] + "%";

             professorList = jdbcClient.sql(sqlQuery)
                    .params(firstSequence, firstSequence, secondSequence, secondSequence)
                    .query(Professor.class)
                    .list();
        } else {
            String sqlQuery = "SELECT * FROM professors WHERE first_name ILIKE ? OR last_name ILIKE ? ORDER BY last_name ASC";
            sequence = "%" + sequence + "%";

            professorList = jdbcClient.sql(sqlQuery)
                    .params(sequence, sequence)
                    .query(Professor.class)
                    .list();
        }

        return professorList;
    }

    @Override
    public List<Professor> findAllSorted(String filterQuery, String departmentQuery) {
        String sqlQuery = "SELECT * FROM professors" + departmentQuery + " ORDER BY " + filterQuery;

        return jdbcClient.sql(sqlQuery)
                .query(Professor.class)
                .list();
    }

    @Override
    public List<Professor> findAllOffset(int offset) {
        String sqlQuery = "SELECT * FROM professors ORDER BY last_name ASC LIMIT ? OFFSET ?";

        return jdbcClient.sql(sqlQuery)
                .params(Constants.OFFSET_FACTOR, offset)
                .query(Professor.class)
                .list();
    }

    @Override
    public List<Professor> findAllByNameContainsOffset(String sequence, int offset) {
        sequence = sequence.trim();

        String[] splitArray = {};
        String sqlQuery = "";

        if (sequence.contains("\s")) {
            splitArray = sequence.split("\s+");
        }

        if (splitArray.length >= 2) {
            sqlQuery = "SELECT * FROM professors WHERE (first_name ILIKE '%" + splitArray[0] + "%' OR last_name ILIKE '%" + splitArray[0] + "%') AND (first_name ILIKE '%" + splitArray[1] + "%' OR last_name ILIKE '%" + splitArray[1] + "%') ORDER BY last_name ASC LIMIT ? OFFSET ?";
        } else {
            sqlQuery = "SELECT * FROM professors WHERE first_name ILIKE '%" + sequence + "%' OR last_name ILIKE '%" + sequence + "%' ORDER BY last_name ASC LIMIT ? OFFSET ?";
        }

        List<Professor> professorList = Collections.emptyList();

        return jdbcClient.sql(sqlQuery)
                .params(Constants.OFFSET_FACTOR, offset)
                .query(Professor.class)
                .list();
    }

    @Override
    public List<Professor> findAllSortedOffset(String filterQuery, String departmentQuery, int offset) {
        String sqlQuery = "SELECT * FROM professors" + departmentQuery + " ORDER BY " + filterQuery + " LIMIT ? OFFSET ?";

        return jdbcClient.sql(sqlQuery)
                .params(Constants.OFFSET_FACTOR, offset)
                .query(Professor.class)
                .list();
    }
}
