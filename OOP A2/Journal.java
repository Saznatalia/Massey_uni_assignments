// Sazanakova, Natalia, 21005371, Assignment 2, 159.234
/* Journal Concrete Class*/
package com.demo.java;

public class Journal extends LibraryItem {
    private int Volume;
    private int JournalNumber;

    // Constructor
    public Journal(String title, int id, int year, String status, int volume, int journalNumber) {
        super(title, id, year, status);
        if (volume <= 0) throw new IllegalArgumentException("Volume must be a positive number");
        this.Volume = volume;
        if (journalNumber <= 0) throw new IllegalArgumentException("Number must be a positive number");
        this.JournalNumber = journalNumber;
    }

    // Getters
    public int getVolume() {
        return Volume;
    }

    public int getJournalNumber() {
        return JournalNumber;
    }

    @Override
    public String toString() {
        return String.format(super.toString() + "Volume: %s%nNumber: %s%nMax number of days for borrowing: %d",
                getVolume(), getJournalNumber(), getMaxDays());
    }

    @Override
    public String getType() {
        return "Journal";
    }

    @Override
    public int getMaxDays() {
        return 14;
    }
}
