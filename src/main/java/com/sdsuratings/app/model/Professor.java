package com.sdsuratings.app.model;

public class Professor {
    private int id;
    private String name;
    private String department;

    public Professor(int id, String name, String department) {
        this.id = id;
        this.name = name;
        this.department = department;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDepartment() {
        return department;
    }
}
