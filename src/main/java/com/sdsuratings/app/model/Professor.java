package com.sdsuratings.app.model;

public class Professor {
    private static int running_id = 0;

    private int id;
    private String firstName, lastName;
    private String department;

    public Professor(String firstName, String lastName, String department) {
        this.id = running_id++;
        this.firstName = firstName;
        this.lastName = lastName;
        this.department = department;
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
}
