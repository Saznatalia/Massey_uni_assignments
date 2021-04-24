// Sazanakova, Natalia, 21005371, Assignment 2, 159.234
/* Book Concrete Class*/
package com.demo.java;

public class Book extends LibraryItem {
    private String Author;
    private int PagesNumber;

    // Constructor
    public Book(String title, int id, int year, String status, String author, int pagesNumber) {
        super(title, id, year, status);
        this.Author = author;
        if (pagesNumber <= 0) {
            throw new IllegalArgumentException("Pages must be a positive number");
        }
        this.PagesNumber = pagesNumber;
    }

    // Getters
    public String getAuthor() {
        return Author;
    }

    public int getPagesNumber() {
        return PagesNumber;
    }

    @Override
    public String toString() {
        return String.format(super.toString() + "Author: %s%nNumber of pages: %s%nMax number of days for borrowing: %d",
                getAuthor(), getPagesNumber(), getMaxDays());
    }

    @Override
    public String getType() {
        return "Book";
    }

    @Override
    public int getMaxDays() {
        return 28;
    }

}
