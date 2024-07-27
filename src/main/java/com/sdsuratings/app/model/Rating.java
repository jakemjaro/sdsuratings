package com.sdsuratings.app.model;

import java.util.Date;

public class Rating {
    private int id, professorId;
    private double quality, difficulty;
    private String course;
    private char grade;
    private Date date;
    private String description;

    public int getId() {
        return id;
    }

    public int getProfessorId() {
        return professorId;
    }

    public double getQuality() {
        return quality;
    }

    public double getDifficulty() {
        return difficulty;
    }

    public String getCourse() {
        return course;
    }

    public char getGrade() {
        return grade;
    }

    public Date getDate() {
        return this.date;
    }

    public String getDescription() {
        return description;
    }
}

