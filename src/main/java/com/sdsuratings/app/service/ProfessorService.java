package com.sdsuratings.app.service;

import com.sdsuratings.app.model.Professor;
import com.sdsuratings.app.repository.ProfessorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfessorService {
    private ProfessorRepository professorRepository;

    public ProfessorService(ProfessorRepository professorRepository) {
        this.professorRepository = professorRepository;
    }

    public void addProfessor(Professor professor) {
        professorRepository.create(professor);
    }

    public List<Professor> getAllProfessors() {
        return professorRepository.findAll();
    }

    public Professor getProfessor(int id) {
        return professorRepository.findById(id);
    }

    public List<Professor> searchProfessorsLimited(String sequence, int limit) {
        return professorRepository.findByNameContainsLimited(sequence, limit);
    }

    public List<Professor> searchProfessors(String sequence) {
        return professorRepository.findAllByNameContains(sequence);
    }
}
