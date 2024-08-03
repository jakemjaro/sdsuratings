package com.sdsuratings.app.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Rating {
    private static int running_id = 1;

    private int id, professorId;
    private double quality, difficulty;
    private String course;
    private String grade;
    private String datePublished;
    private String description;

    public Rating(int professorId, double quality, double difficulty, String course, String grade, String datePublished, String description) {
        this.id = running_id++;
        this.professorId = professorId;
        this.quality = quality;
        this.difficulty = difficulty;
        this.course = course;
        this.grade = grade;
        this.datePublished = datePublished;
        this.description = description;
    }

    public int getId() {
        return this.id;
    }

    public int getProfessorId() {
        return this.professorId;
    }

    public double getQuality() {
        return this.quality;
    }

    public double getDifficulty() {
        return this.difficulty;
    }

    public String getCourse() {
        return this.course;
    }

    public String getGrade() {
        return this.grade;
    }

    public String getDatePublished() {
        return this.datePublished;
    }

    public String getDescription() {
        return this.description;
    }

    public String getBackgroundColor() {
        if (this.quality <= 2) {
            return BackgroundColors.RED.className;
        } else if (this.quality == 3) {
            return BackgroundColors.YELLOW.className;
        } else {
            return BackgroundColors.GREEN.className;
        }
    }
}

