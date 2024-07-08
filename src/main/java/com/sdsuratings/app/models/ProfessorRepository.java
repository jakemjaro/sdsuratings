package com.sdsuratings.app.models;

import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class ProfessorRepository {
    private List<String> professors;

    public ProfessorRepository() {
        this.professors = new ArrayList(Arrays.asList("Ben Shen", "Patricia Kraft", "Chong Kim"));
    }

    public LinkedList<String> getProfessors(String name) {
        LinkedList<String> matches = new LinkedList<>();

        for (String s : professors) {
            if (s.contains(name)) {
                matches.add(s);
            }
        }

        return matches;
    }
}
