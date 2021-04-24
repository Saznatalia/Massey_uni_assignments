// Sazanakova, Natalia, 21005371, Assignment 2, 159.234
/* Movie Concrete Class*/
package com.demo.java;

public class Movie extends LibraryItem {
    private String Director;

    // Constructor
    public Movie(String title, int id, int year, String status, String director) {
        super(title, id, year, status);
        this.Director = director;
    }

    // Getter
    public String getDirector() {
        return Director;
    }

    @Override
    public String toString() {
        return String.format(super.toString() + "Director: %s%nMax number of days for borrowing: %d", getDirector(), getMaxDays());
    }

    @Override
    public String getType() {
        return "Movie";
    }

    @Override
    public int getMaxDays() {
        return 7;
    }

}
