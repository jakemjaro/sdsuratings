package com.sdsuratings.app.model;

public class Professor {
    private static int running_id = 1;

    private int id;
    private String firstName, lastName;
    private String department;
    private String averageQuality, averageDifficulty;

    public Professor(int id, String firstName, String lastName, String department) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.department = department;
        this.averageQuality = "--";
        this.averageDifficulty = "--";
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public String getDepartment() {
        return department;
    }

    public String getAverageQuality() {
        return this.averageQuality;
    }

    public void setAverageQuality(double averageQuality) {
        if (averageQuality >= 0.0) {
            this.averageQuality = String.format("%.2f", averageQuality);
        }
    }

    public String getAverageDifficulty() {
        return this.averageDifficulty;
    }

    public void setAverageDifficulty(double averageDifficulty) {
        if (averageDifficulty >= 0.0) {
            this.averageDifficulty = String.format("%.2f", averageDifficulty);
        }
    }
}
