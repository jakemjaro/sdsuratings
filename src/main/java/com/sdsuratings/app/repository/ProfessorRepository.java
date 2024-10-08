package com.sdsuratings.app.repository;

import com.sdsuratings.app.model.Professor;

import java.util.List;

public interface ProfessorRepository {
    void create(Professor professor);

    boolean exists(Professor professor);

    List<Professor> findAll();

    Professor findById(int id);

    List<Professor> findByNameContainsLimited(String sequence, int limit);

    List<Professor> findAllByNameContains(String sequence);

    List<Professor> findAllSorted(String filterQuery, String departmentQuery);

    List<Professor> findAllOffset(int offset);

    List<Professor> findAllByNameContainsOffset(String sequence, int offset);

    List<Professor> findAllSortedOffset(String filterQuery, String departmentQuery, int offset);
}
