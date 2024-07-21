package com.sdsuratings.app.repository;

import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class ProfessorRepository {
    private List<String> professors;

    public ProfessorRepository() {
        this.professors = new ArrayList(Arrays.asList("Ben Shen", "Patricia Kraft", "Chong Kim", "Ethan Banegas",
                "Krishna Ramamoorthy", "Vanessa Castro", "Dillon Chapman", "John Love", "Jason Crane", "Matthew Rowe"));
    }

    public LinkedList<String> getMatchingProfessors(String name) {
        LinkedList<String> matches = new LinkedList<>();

        for (String p : professors) {
            if (p.toLowerCase().contains(name.toLowerCase())) {
                matches.add(p);
            }
        }

        return matches;
    }

    public LinkedList<String> getMatchingProfessors(String name, int amount) {
        LinkedList<String> matches = new LinkedList<>();
        int count = 0;

        for (String p : professors) {
            if (p.toLowerCase().contains(name.toLowerCase())) {
                matches.add(p);
                count++;
            }

            if (count == amount) {
                break;
            }
        }

        return matches;
    }

    public List<String> getAllProfessors() {
        return professors;
    }
}
