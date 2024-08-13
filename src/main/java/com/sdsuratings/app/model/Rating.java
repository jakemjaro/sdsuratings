package com.sdsuratings.app.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Rating {
    private int id, professorId;
    private double quality, difficulty;
    private String course;
    private String grade;
    private String datePublished;
    private String description;
    private double accessibility;
    private String workload;
    private String classType;

    public Rating(int id, int professorId, double quality, double difficulty, String course, String grade, String datePublished, String description,
                  double accessibility, String workload, String classType) {
        this.id = id;
        this.professorId = professorId;
        this.quality = quality;
        this.difficulty = difficulty;
        this.course = course;
        this.grade = grade;
        this.datePublished = datePublished;
        this.description = description;
        this.accessibility = accessibility;
        this.workload = workload;
        this.classType = classType;
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

    public double getAccessibility() { return this.accessibility; }

    public String getWorkload() { return this.workload; }

    public String getClassType() { return this.classType; }

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

