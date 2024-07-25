package com.lendingcatalog.util;

import com.lendingcatalog.util.exception.FileStorageException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileStorageService { //FileStorageSystem class

    // Requirement: File I/O
    public static void writeContentsToFile(String contents, String filename, boolean appendFile) { //This method takes 3 parameters 2 strings and a boolean.

        try (PrintWriter destinationWriter = new PrintWriter(new FileOutputStream(filename, appendFile))){ //Try-with-resources block will open a PrinterWriter object for the destination file
                destinationWriter.println(contents); //Writes the contents to the file.
        }catch(FileNotFoundException e){ //Catches FileNotFoundException.
            System.out.println("The file you are searching for cannot be found.");
        }
    }

    public static List<String> readContentsOfFile(String filename) throws FileStorageException { //This method takes 1 parameter a string.
        List<String> outputList = new ArrayList<>(); //Creates a list of strings to return.
        File dataFile = new File(filename); //Creates a new File object.

        try(Scanner inputData = new Scanner(dataFile)){ //Try-with-resources block will open the file for reading.

            while(inputData.hasNextLine()){ //This loop will run until it has reached the end of the file.
                String currentLine = inputData.nextLine(); //Creates a string to hold the current line the loop is on.
                outputList.add(currentLine); //Adds the currentLine variable to the return list.
            }
        } catch(FileNotFoundException e){ //Catches FileNotFoundException.
            System.out.println("The file you are searching for cannot be found.");
        }

        return outputList;
    }
}
