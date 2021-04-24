// Sazanakova, Natalia, 21005371, Assignment 1, 159.234
/* Program stores library items that users can search, borrow or rate */
package com.demo.java;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        ArrayList<LibraryItem> libraryItems = new ArrayList<>();
        displayInfo();
        loadPrintLibrary(libraryItems);
        search(libraryItems);
    }

    // Create displayInfo function that displays details about assignment and student
    private static void displayInfo() {
        System.out.println("------------------------------------------");
        System.out.println("Assignment 2, 159.234 Semester 1 2021");
        System.out.println("Submitted by: Sazanakova, Natalia 21005371");
        System.out.println("------------------------------------------");
    }

    // Load test data from text file and print all items in the library
    private static void loadPrintLibrary(ArrayList<LibraryItem> libraryItems) {
        String status = "available";
        try {
            FileReader reader = new FileReader("library.txt");
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                try {
                    String[] splitString = line.split(",");
                    if (splitString.length < 5) {
                        System.out.println("Invalid record");
                        continue;
                    }
                    // Elements of line common for all library items
                    String type = splitString[0];
                    int id = Integer.parseInt(splitString[1]);
                    String title = splitString[2];
                    int year = Integer.parseInt(splitString[3]);

                    // If record is movie then create movie object and put into movies arraylist
                    if (type.equalsIgnoreCase("Movie") && splitString.length == 5) {
                        String director = splitString[4];
                        Movie movie = new Movie(title, id, year, status, director);
                        libraryItems.add(movie);
                    }
                    // If record is book then create book object and put into books arraylist
                    else if (type.equalsIgnoreCase("Book") && splitString.length == 6) {
                        String author = splitString[4];
                        int pages = Integer.parseInt(splitString[5]);
                        Book book = new Book(title, id, year, status, author, pages);
                        libraryItems.add(book);
                    }
                    // If record is journal then create journal object and put into journals arraylist
                    else if (type.equalsIgnoreCase("Journal") && splitString.length == 6) {
                        int volume = Integer.parseInt(splitString[4]);
                        int number = Integer.parseInt(splitString[5]);
                        Journal journal = new Journal(splitString[2], id, year, status, volume, number);
                        libraryItems.add(journal);
                    }
                    // Invalid record, throw exception
                    else {
                        throw new Exception("Unknown type");
                    }
                } catch (Exception e) {
                    System.out.println("Invalid record");
                }
            }
        } catch (Exception e) {
            System.out.println("Couldn't read the file!");
        }

        // Print all library, i.e. all movies, books and journals
        for (LibraryItem libraryItem : libraryItems) printItem(libraryItem);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Search function
    private static void search(ArrayList<LibraryItem> libraryItems) {
        Scanner myObj = new Scanner(System.in);
        while (true) {
            System.out.println("\nEnter 'q' to quit, enter 'i' to search by ID, or enter any other key to search by phrase in title");
            String resp = myObj.nextLine(); // Reads user input
            if (resp.equalsIgnoreCase("q")) {
                break;
            }
            if (resp.equalsIgnoreCase("i")) {
                searchById(libraryItems);
            } else {
                searchByTitle(libraryItems);
            }
        }
    }

    // Search by ID
    private static void searchById(ArrayList<LibraryItem> libraryItems) {
        while (true) {
            System.out.println("Enter ID to start search, or enter 'b' to go back to choose search method");
            Scanner myObj = new Scanner(System.in);
            String response = myObj.nextLine();
            if (response.equalsIgnoreCase("b")) {
                break;
            }
            if (response.equalsIgnoreCase("")) {
                continue;
            }
            try {
                // Find item by id
                int id = Integer.parseInt(response);
                LibraryItem item = findById(libraryItems, id);
                if (item == null) {
                    System.out.println("Nothing was found, try again");
                } else {
                    printItem(item);
                    // Display selected item and check status if available ask if wants to borrow or rate, if on loan ask if wants to return or rate
                    LibraryItem selectedItem = selectItemById(libraryItems, item);
                    if ((selectedItem.status).equalsIgnoreCase("available")) {
                        borrowRateItem(selectedItem);
                    } else if ((selectedItem.status).equalsIgnoreCase("on loan")) {
                        returnRateItem(selectedItem);
                    }
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Id must be a positive number");
            }
        }
    }

    // Find by id and return item
    private static LibraryItem findById(ArrayList<LibraryItem> libraryItems, int id) {
        for (LibraryItem libraryItem : libraryItems) {
            if (libraryItem.id == id) {
                return libraryItem;
            }
        }
        return null;
    }

    // Display selected item
    private static LibraryItem selectItemById(ArrayList<LibraryItem> libraryItems, LibraryItem item) {
        Scanner myObj = new Scanner(System.in);
        while (true) {
            System.out.println("Enter 'i' to search other item by ID, or enter any other key to select this item");
            String response = myObj.nextLine();
            if (response.equalsIgnoreCase("i")) {
                searchById(libraryItems);
            } else {
                printSelected(item);
                return item;
            }
        }
    }
    // Print selected item
    private static void printSelected(LibraryItem item) {
        System.out.println("Selected item is");
        System.out.println("------------------------------------------");
        System.out.println(item.toString());
        System.out.println("------------------------------------------");
    }

    // Print item
    private static void printItem(LibraryItem item) {
        System.out.println(item.toString());
        System.out.println("------------------------------------------");
    }

    // Search by phrase in the title
    private static void searchByTitle(ArrayList<LibraryItem> libraryItems) {
        Scanner myObj = new Scanner(System.in);
        while (true) {
            System.out.println("Enter phrase in title to start search, or enter 'b' to go back to choose search method");
            String response = myObj.nextLine();
            if (response.equalsIgnoreCase("b")) {
                break;
            }
            if (response.equalsIgnoreCase("")) {
                continue;
            }
            try {
                // Find item by phrase in title
                ArrayList<LibraryItem> results = findByTitle(libraryItems, response);
                if (results.isEmpty()) {
                    System.out.println("Nothing was found, try again");
                } else {
                    for (int i = 0; i < results.size(); i++) {
                        System.out.printf("* %d --------------------------------------\n", i + 1);
                        System.out.println(results.get(i));
                    }
                    // Display selected item
                    LibraryItem selectedItem = selectFromTitle(results);
                    if (selectedItem == null) break;
                    if ((selectedItem.status).equalsIgnoreCase("available")) {
                        borrowRateItem(selectedItem);
                    } else if ((selectedItem.status).equalsIgnoreCase("on loan")) {
                        returnRateItem(selectedItem);
                    }
                    break;
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
    }

    // Find by phrase in title and return item(s)
    private static ArrayList<LibraryItem> findByTitle(ArrayList<LibraryItem> libraryItems, String response) {
        ArrayList<LibraryItem> results = new ArrayList<>();
        response = response.toLowerCase(Locale.ROOT);
        for (LibraryItem libraryItem : libraryItems) {
            if ((libraryItem.title).toLowerCase(Locale.ROOT).contains(response)) {
                results.add(libraryItem);
            }
        }
        return results;
    }

    // Display selected item
    private static LibraryItem selectFromTitle(ArrayList<LibraryItem> results) {
        Scanner myObj = new Scanner(System.in);
        while (true) {
            System.out.println("\nEnter item number to select item, or enter any other key to continue searching");
            String res = myObj.nextLine();
            try {
                int itemNumber = Integer.parseInt(res);
                for (int j = 0; j < results.size(); j++) {
                    if (itemNumber == j + 1) {
                        printSelected(results.get(j));
                        return results.get(j);
                    }
                }
                break;
            } catch (NumberFormatException e) {
                break;
            }
        }
        return null;
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Prompt user for borrow/rate the item
    private static void borrowRateItem(LibraryItem item) {
        Scanner myObj = new Scanner(System.in);
        while (true) {
            System.out.println("Enter 'b' to borrow the item, enter 'a' to rate the item, or enter any other key to restart");
            String response = myObj.nextLine();
            if (response.equalsIgnoreCase("b")) {
                item.borrow();
                System.out.println("The item's due date is " + item.getDueDate() + "\n");
                printItem(item);
                returnRateItem(item);
                break;
            } else if (response.equalsIgnoreCase("a")) {
                rateItem(item);
            } else {
                break;
            }
        }
    }

    // Prompt user for return/rate the item
    private static void returnRateItem(LibraryItem item) {
        Scanner myObj = new Scanner(System.in);
        while (true) {
            System.out.println("Enter 'r' to return the item, enter 'a' to rate the item, or enter any other key to restart");
            String response = myObj.nextLine();
            if (response.equalsIgnoreCase("r")) {
                returnItem(item);
                break;
            } else if (response.equalsIgnoreCase("a")) {
                rateItem(item);
            } else {
                break;
            }
        }
    }

    // Rate the item function
    private static void rateItem(LibraryItem item) {
        Scanner myObj = new Scanner(System.in);
        System.out.println("Please enter your rating (0 - 10)");
        String response = myObj.nextLine();
        try {
            float res = Float.parseFloat(response);
            if (res < 0 || res > 10) {
                System.out.println("Rate should be withing 0 - 10 range, try again");
            } else {
                item.addRate(res);
                float newAverageRate = item.getAverageRating(item.getRates());
                System.out.println("The item's new average rating is " + newAverageRate + "\n");
                printSelected(item);
            }
        } catch (NumberFormatException e) {
            System.out.println("Sorry, your rate should be a number within 0-10 range");
        }
    }

    // Return function
    private static void returnItem(LibraryItem item) {
        item.setStatus("available");
        System.out.println("The item is returned\n");
        printSelected(item);
        borrowRateItem(item);
    }
}



