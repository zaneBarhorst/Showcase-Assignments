package com.lendingcatalog;

import com.lendingcatalog.model.*;
import com.lendingcatalog.util.FileStorageService;
import com.lendingcatalog.util.exception.FileStorageException;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Array;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class App {

    // The regex string to split the Strings in the dataset.
    private static final String FIELD_DELIMITER = "\\|";
    private static final String FILE_BASE_PATH = "src/main/resources/";

    private final Scanner keyboard = new Scanner(System.in);

    private Map<String, List<CatalogItem>> catalog = new HashMap<>();

    public static void main(String[] args) throws FileStorageException, FileNotFoundException {

        App app = new App();
        app.initialize();
        app.run();
    }

    private void initialize() throws FileStorageException, FileNotFoundException { //Initialize method.
        // Requirement: Data transformation

        String filePath = "src/main/resources/members.dat"; //Creates a string that holds the relative path to the members.dat file.
        List<String> outputList = FileStorageService.readContentsOfFile(filePath); //Creates a list that will hold hte contents of the file provided.


        for(int i = 0; i < outputList.size(); i++){ //This for loop will run until every item in the outputList has been reached.
            String currentLine = outputList.get(i); //Sets a string to the current iteration of the list.
            String[] currentLineSplit = currentLine.split(FIELD_DELIMITER); //Splits the current line into a string array utilizing the FIELD_DELIMITER.
            String firstName = currentLineSplit[0]; //Sets a string to hold the first index in the string array.
            String lastName = currentLineSplit[1]; //Sets a string to hold the second index in the string array.
            String fileName = currentLineSplit[2]; //Sets a string to hold the third index in the string array.
            Member member = new Member(firstName, lastName); //Creating a new member object.
            List<CatalogItem> memberItems = processCatalogItem(FILE_BASE_PATH + fileName); //Creating a list by utilizing the processCatalogItem method.
            catalog.put(member.toString(), memberItems); //Puts the current member and their items into a map.
        }


    }


    private List<CatalogItem> processCatalogItem(String filePath) throws FileNotFoundException { //ProcessCatalogItem method.
        List<CatalogItem> memberItems = new ArrayList<>(); //Creating a return list.
        File currentFile = new File(filePath); //Creating a new file object.
        Scanner fileScanner = new Scanner(currentFile); //Creating a new Scanner object to read through the file.

        while(fileScanner.hasNextLine()){ //This loop will run until the scanner has reached the end of the file.
            String currentLine = fileScanner.nextLine(); //Creating a string to hold the current line the loop is on.

            if(currentLine.isEmpty()){ //If statement checks to see if the current line is empty.
                System.out.println("Empty line encountered.");
                continue; //Skip this line.
            }

            String[] currentLineData = currentLine.split(FIELD_DELIMITER); //Creating a string array to hold the currentLine variable split by the FIELD_DELIMITER.

            if(currentLineData.length < 4 || currentLineData.length > 4){ //If statement checks to see if the currentLineData array is less than or greater than the 4 required indexes.
                System.out.println("Array length is not equal to 4");
                continue; //Skip this line.
            }

            if(currentLineData[0].equals("book")){ //If the first index in currentLineData is book.
                LocalDate convertedDate = LocalDate.parse(currentLineData[3]);
                Book book = new Book(currentLineData[1], currentLineData[2], convertedDate);
                book.registerItem();
                memberItems.add(book);
            }else if(currentLineData[0].equals("movie")){ //If the first index in currentLineData is movie.
                LocalDate convertedDate = LocalDate.parse(currentLineData[3]);
                Movie movie = new Movie(currentLineData[1], currentLineData[2], convertedDate);
                movie.registerItem();
                memberItems.add(movie);
            }else if(currentLineData[0].equals("tool")){ //Else (first index in currentLineData is tool)
                Tool tool = new Tool(currentLineData[1], currentLineData[2], Integer.parseInt(currentLineData[3]));
                tool.registerItem();
                memberItems.add(tool);
            }else{
                System.out.println("Unrecognized type found");
            }
        }
        return memberItems;
    }

    private void run() {

        if (catalog.isEmpty()) {
            System.out.println("Catalog must not empty for application to run");
            return;
        }

        while (true) {
            // Main menu loop
            printMainMenu();
            int mainMenuSelection = promptForMenuSelection("Please choose an option: ");
            if (mainMenuSelection == 1) {
                // Display data and subsets loop
                while (true) {
                    printDataAndSubsetsMenu();
                    int dataAndSubsetsMenuSelection = promptForMenuSelection("Please choose an option: ");
                    if (dataAndSubsetsMenuSelection == 1) {
                        displayFullCatalog(catalog);
                    } else if (dataAndSubsetsMenuSelection == 2) {
                        displayUsersForItemDisplay(catalog);
                        String userName = promptForString("Enter name: ");
                        displayUserItems(catalog, userName);
                    } else if (dataAndSubsetsMenuSelection == 0) {
                        break;
                    }
                }
            }
            else if (mainMenuSelection == 2) {
                while (true) {
                    printSearchMenu();
                    int searchMenuSelection = promptForMenuSelection("Please choose an option: ");
                    if (searchMenuSelection == 1) {
                        // Search by name/title
                        String searchName = promptForString("Enter name: ");
                        displayMatchesByName(catalog, searchName);
                    } else if (searchMenuSelection == 2) {
                        // Search by creator
                        String searchCreator = promptForString("Enter creator: ");
                        displayMatchesByCreator(catalog, searchCreator);
                    } else if (searchMenuSelection == 3) {
                        // Search by publish/release year
                        int searchYear = promptForYear("Enter date (YYYY): ");
                        displayMatchesByYear(catalog, searchYear);
                    } else if (searchMenuSelection == 0) {
                        break;
                    }
                }
            } else if (mainMenuSelection == 0) {
                break;
            }
        }
    }


    // UI methods

    private void printMainMenu() {
        System.out.println("1: Display catalog");
        System.out.println("2: Search catalog");
        System.out.println("0: Exit");
        System.out.println();
    }

    private void printDataAndSubsetsMenu() {
        System.out.println("1: Display full catalog");
        System.out.println("2: Display all items from a user");
        System.out.println("0: Return to main menu");
        System.out.println();
    }

    private void printSearchMenu() {
        System.out.println("1: Search items by name");
        System.out.println("2: Search items by creator");
        System.out.println("3: Search by year");
        System.out.println("0: Return to main menu");
        System.out.println();
    }

    private void displayFullCatalog(Map<String, List<CatalogItem>> catalog) {
        System.out.println("Full Catalog");
        System.out.println("-------");
        for (Map.Entry<String, List<CatalogItem>> entry : catalog.entrySet()) {
            System.out.println(entry.getKey() + ": ");
            for (CatalogItem item : entry.getValue()) {
                System.out.println(item.toString());
            }
            System.out.println();
        }
        System.out.println();
        promptForReturn();
    }

    private void displayUsersForItemDisplay(Map<String, List<CatalogItem>> catalog) {
        System.out.println("Users");
        System.out.println("-------");
        for (Map.Entry<String, List<CatalogItem>> entry : catalog.entrySet()) {
            System.out.println(entry.getKey());
        }
        System.out.println();
    }

    private void displayUserItems(Map<String, List<CatalogItem>> catalog, String userName) {
        System.out.println("Items from " + userName);
        System.out.println("-------");
        if (catalog.containsKey(userName)) {
            List<CatalogItem> userItems = catalog.get(userName);
            for (CatalogItem item : userItems) {
                System.out.println(item);
            }
        } else {
            System.out.println("No user found for '" + userName + "'");
        }
        System.out.println();
        promptForReturn();
    }

    private void displayMatchesByName(Map<String, List<CatalogItem>> catalog, String searchName) {
        System.out.println("Matches by name: " + searchName);
        System.out.println("-------");
        for (Map.Entry<String, List<CatalogItem>> entry : catalog.entrySet()) {
            boolean hasMatches = false;
            System.out.println(entry.getKey() + ": ");
            for (CatalogItem item : entry.getValue()) {
                if (item.matchesName(searchName)) {
                    System.out.println(item);
                    hasMatches = true;
                }
            }
            if (!hasMatches) {
                System.out.println("--No matching items--");
            }
            System.out.println();
        }
        System.out.println();
        promptForReturn();
    }

    private void displayMatchesByCreator(Map<String, List<CatalogItem>> catalog, String searchCreator) {
        System.out.println("Matches by creator: " + searchCreator);
        System.out.println("-------");
        for (Map.Entry<String, List<CatalogItem>> entry : catalog.entrySet()) {
            boolean hasMatches = false;
            System.out.println(entry.getKey() + ": ");
            for (CatalogItem item : entry.getValue()) {
                if (item.matchesCreator(searchCreator)) {
                    System.out.println(item);
                    hasMatches = true;
                }
            }
            if (!hasMatches) {
                System.out.println("--No matching items--");
            }
            System.out.println();
        }
        System.out.println();
        promptForReturn();
    }

    private void displayMatchesByYear(Map<String, List<CatalogItem>> catalog, int searchYear) {
        System.out.println("Matches by year: " + searchYear);
        System.out.println("-------");
        for (Map.Entry<String, List<CatalogItem>> entry : catalog.entrySet()) {
            boolean hasMatches = false;
            System.out.println(entry.getKey() + ": ");
            for (CatalogItem item : entry.getValue()) {
                if (item.matchesYear(searchYear)) {
                    System.out.println(item);
                    hasMatches = true;
                }
            }
            if (!hasMatches) {
                System.out.println("--No matching items--");
            }
            System.out.println();
        }
        System.out.println();
        promptForReturn();
    }

    private int promptForMenuSelection(String prompt) {
        System.out.print(prompt);
        int menuSelection;
        try {
            menuSelection = Integer.parseInt(keyboard.nextLine());
        } catch (NumberFormatException e) {
            menuSelection = -1;
        }
        return menuSelection;
    }

    private String promptForString(String prompt) {
        System.out.print(prompt);
        return keyboard.nextLine();
    }

    private int promptForYear(String prompt) {
        int year;
        while (true) {
            System.out.println(prompt);
            try {
                year = Integer.parseInt(keyboard.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println("The year provided is not well-formed. It must be YYYY.");
            }
        }
        return year;
    }

    private void promptForReturn() {
        System.out.println("Press RETURN to continue.");
        keyboard.nextLine();
    }
}
