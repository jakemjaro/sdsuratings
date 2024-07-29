package com.sdsuratings.app.repository;

import com.sdsuratings.app.model.Professor;

import java.util.List;

public interface ProfessorRepository {
    void create(Professor professor);

    List<Professor> findAll();

    Professor findByName(String name);

    List<Professor> findByNameContainsLimited(String sequence, int limit);

    List<Professor> findAllByNameContains(String sequence);

    int findIdByName(String name);
}
