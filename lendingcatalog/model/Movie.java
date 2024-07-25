package com.lendingcatalog.model;

import com.lendingcatalog.util.FileStorageService;
import com.lendingcatalog.util.exception.FileStorageException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public class Movie implements CatalogItem { //Implements CatalogItem interface.

    //Variable Declarations.
    private String id;
    private String name;
    private String director;
    private LocalDate releaseDate;

    public Movie(String name, String director, LocalDate releaseDate){ //Constructor for the Movie class. It takes 3 parameters 2 strings and a LocalDate object.
        this.name = name;
        this.director = director;
        this.releaseDate = releaseDate;
    }

    //Getters.
    public String getName(){
        return name;
    }

    public String getDirector(){
        return director;
    }

    public LocalDate getReleaseDate(){
        return releaseDate;
    }

    //Setters.
    public void setName(String name){
        this.name = name;
    }

    public void setDirector(String director){
        this.director = director;
    }

    public void setReleaseDate(LocalDate releaseDate){
        this.releaseDate = releaseDate;
    }

    //toString method.
    public String toString(){
        return name + System.lineSeparator() +
            "Directed by: " + director + System.lineSeparator() +
            "Release Date: " + releaseDate + System.lineSeparator() +
            "ID: " + id + System.lineSeparator();
    }

    @Override
    public boolean matchesName(String searchStr) { //Implemented from the CatalogItem interface. This method checks to see if the string searched is equal to the name of any movie.
        if(searchStr.equalsIgnoreCase(name)){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public boolean matchesCreator(String searchStr) { //Implemented from the CatalogItem interface. This method checks to see if the string searched is equal to the director of any movie.
        if(searchStr.equalsIgnoreCase(director)){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public boolean matchesYear(int searchYear) { //Implemented from the CatalogItem interface. This method checks to see if the string searched is equal to the release year of any movie.
        if(searchYear == releaseDate.getYear()){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public void registerItem() { //Implemented from the CatalogItem interface. This method will assign the id to a movie.
        this.id = UUID.randomUUID().toString();

        String logPath = "src/main/resources/logs/movieLog.dat";
        String data = "Movie was registered at " + LocalDate.now() + " " + LocalTime.now() + " " + toString();

        FileStorageService.writeContentsToFile(data, logPath, true);
    }

}
