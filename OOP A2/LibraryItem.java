// Sazanakova, Natalia, 21005371, Assignment 2, 159.234
/* Library Item Abstract Class*/
package com.demo.java;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public abstract class LibraryItem {
    protected String title;
    protected int id;
    protected int year;
    protected ArrayList<Float> rates = new ArrayList<>();
    protected String status;
    protected Date dueDate;

    // Constructor
    public LibraryItem(String title, int id, int year, String status) {
        this.title = title;
        this.id = id;
        int yearLength = String.valueOf(year).length();
        if (year <= 0 || yearLength != 4) {
            throw new IllegalArgumentException("Year must be a positive 4 digit number");
        }
        this.year = year;
        this.status = status;
    }

    public void addRate(Float newRate) {
        if (newRate < 0 || newRate > 10) {
            throw new IllegalArgumentException("Your rate must be within 0-10 range");
        }
        rates.add(newRate);
    }

    // Getters
    public abstract String getType();

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getYear() {
        return year;
    }

    public float getAverageRating(ArrayList<Float> rates) {
        if (rates.isEmpty()) return 0.0F;
        float sum = 0;
        for (Float rate : rates) {
            sum += rate;
        }
        return sum / (rates.size());
    }

    public int getNumberOfReviews() {
        return rates.size();
    }

    public ArrayList<Float> getRates() {
        return rates;
    }

    public abstract int getMaxDays();

    public String getStatus() {
        return status;
    }

    public String getDueDate() {
        return dateFormat.format(dueDate);
    }

    // Setters
    public void setStatus(String status) {
        this.status = status;
    }

    public void borrow() {
        setStatus("on loan");
        Date currentDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(Calendar.DAY_OF_MONTH, getMaxDays());
        dueDate = calendar.getTime();
    }

    private static final DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");

    @Override
    public String toString() {
        String s = String.format("Type: %s%nTitle: %s%nID: %d%nYear: %d%nAverage rating: %.1f%nNumber of reviewers: %d%nStatus: %s%n",
                getType(), getTitle(), getId(), getYear(), getAverageRating(getRates()), getNumberOfReviews(), getStatus());
        if (!getStatus().equalsIgnoreCase("available")) {
            s += String.format("Due date: %s%n", getDueDate());
        }
        return s;
    }
}
