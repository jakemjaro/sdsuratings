package com.sdsuratings.app.repository;

import com.sdsuratings.app.model.Professor;

import java.util.List;

public interface ProfessorRepository {
    void create(Professor professor);

    List<Professor> findAll();

    Professor findById(int id);

    List<Professor> findByNameContainsLimited(String sequence, int limit);

    List<Professor> findAllByNameContains(String sequence);
}
