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

    public void create(Professor professor) {
        return;
    }

    public List<Professor> findAll() {
        return null;
    }

    public Professor findById(int id) {
        return null;
    }

    public List<Professor> findByNameContainsLimited(String sequence, int limit) {
        return null;
    }

    public List<Professor> findAllByNameContains(String sequence) {
        return null;
    }
}
