package com.sdsuratings.app.model;

public enum BackgroundColors {
    GREEN("green-background"),
    YELLOW("yellow-background"),
    RED("red-background");

    final String className;

    BackgroundColors(String className) {
        this.className  = className;
    }
}
