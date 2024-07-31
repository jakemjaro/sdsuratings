package com.sdsuratings.app.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Rating {
    private static int running_id = 0;

    private int id, professorId;
    private double quality, difficulty;
    private String course;
    private char grade;
    private LocalDate date;
    private String description;

    public Rating(int professorId, double quality, double difficulty, String course, char grade, LocalDate date, String description) {
        this.id = running_id++;
        this.professorId = professorId;
        this.quality = quality;
        this.difficulty = difficulty;
        this.course = course;
        this.grade = grade;
        this.date = date;
        this.description = description;
    }

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

    public LocalDate getDate() {
        return this.date;
    }

    public String getDescription() {
        return description;
    }

    public String getFormattedDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy");
        return this.date.format(formatter);
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

