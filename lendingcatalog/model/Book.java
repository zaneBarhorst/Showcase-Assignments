package com.lendingcatalog.model;

import com.lendingcatalog.util.FileStorageService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public class Book implements CatalogItem{ //Implements CatalogItem interface.

    //Variable Declarations.
    private String id;
    private String title;
    private String author;
    private LocalDate publishDate;

    public Book(String title, String author, LocalDate publishDate){ //Constructor for the Book class. It takes 3 parameters 2 strings and a LocalDate object.
        this.title = title;
        this.author = author;
        this.publishDate = publishDate;
    }

    //Getters
    public String getTitle(){
        return title;
    }

    public String getAuthor(){
        return author;
    }

    public LocalDate getPublishDate(){
        return publishDate;
    }

    //Setters
    public void setTitle(String title){
        this.title = title;
    }

    public void setAuthor(String author){
        this.author = author;
    }

    public void setPublishDate(LocalDate publishDate){
        this.publishDate = publishDate;
    }

    //ToString
    public String toString(){
        return title + System.lineSeparator() +
                "Written by: " + author + System.lineSeparator() +
                "Publish Date: " + publishDate + System.lineSeparator() +
                "ID: " + id + System.lineSeparator();
    }

    @Override
    public boolean matchesName(String searchStr) { //Implemented from the CatalogItem interface. This method checks to see if the string searched is equal to the name of any book.
        if(searchStr.equalsIgnoreCase(title)){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public boolean matchesCreator(String searchStr) { //Implemented from the CatalogItem interface. This method checks to see if the string searched is equal to the author of any book.
        if(searchStr.equalsIgnoreCase(author)){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public boolean matchesYear(int searchYear) { //Implemented from the CatalogItem interface. This method checks to see if the string searched is equal to the publishing of any book.
        if(searchYear == publishDate.getYear()){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public void registerItem() { //Implemented from the CatalogItem interface. This method will assign the id to a book.
        this.id = UUID.randomUUID().toString();

        String logPath = "src/main/resources/logs/bookLog.dat";
        String data = "Book was registered at " + LocalDate.now() + " " + LocalTime.now() + " " + toString();

        FileStorageService.writeContentsToFile(data, logPath, true);
    }
}
