package com.sdsuratings.app.service;

import com.sdsuratings.app.model.Professor;
import com.sdsuratings.app.repository.ProfessorRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class ProfessorService {
    private ProfessorRepository professorRepository;
    private RatingService ratingService;

    public ProfessorService(ProfessorRepository professorRepository, RatingService ratingService) {
        this.professorRepository = professorRepository;
        this.ratingService = ratingService;
    }

    private void setAverages(Professor professor) {
        professor.setAverageQuality(ratingService.getAverageQualityForProfessor(professor.getId()));
        professor.setAverageDifficulty(ratingService.getAverageDifficultyForProfessor(professor.getId()));
    }

    public void addProfessor(Professor professor) {
        professorRepository.create(professor);
    }

    public List<Professor> getAllProfessors() {
        List<Professor> professorList = professorRepository.findAll();
        professorList.forEach(professor -> setAverages(professor));
        return professorList;
    }

    public Professor getProfessor(int id) {
        Professor professor = professorRepository.findById(id);
        setAverages(professor);
        return professor;
    }

    public List<Professor> searchProfessorsLimited(String sequence, int limit) {
        if (sequence.contains("\'") || sequence.contains("\"")) {
            return Collections.emptyList();
        }

        List<Professor> professorList = professorRepository.findByNameContainsLimited(sequence, limit);
        professorList.forEach(professor -> setAverages(professor));
        return professorList;
    }

    public List<Professor> searchProfessors(String sequence) {
        if (sequence.contains("\'") || sequence.contains("\"")) {
            return Collections.emptyList();
        }

        List<Professor> professorList = professorRepository.findAllByNameContains(sequence);
        professorList.forEach(professor -> setAverages(professor));
        return professorList;
    }

    public List<Professor> getAllProfessorsSorted(int filter, String department) {
        String filterQuery;
        String departmentQuery = (!department.equals("all")) ? " WHERE department = '" + department + "'" : "";

        if (filter == 1) {
            filterQuery = "first_name ASC";
        } else if (filter == 2) {
            filterQuery = "last_name ASC";
        } else if (filter == 3) {
            filterQuery = "first_name DESC";
        } else {
            filterQuery = "last_name DESC";
        }

        List<Professor> professorList = professorRepository.findAllSorted(filterQuery, departmentQuery);
        professorList.forEach(professor -> setAverages(professor));
        return professorList;
    }
}
