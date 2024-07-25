package com.lendingcatalog.model;

import com.lendingcatalog.util.FileStorageService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public class Tool implements CatalogItem{ //Implements CatalogItem interface.
    private String id;
    private String type;
    private String manufacturer;
    private int count;

    public Tool(String type, String manufacturer, int count){  //Constructor for the Tool class. It takes 3 parameters 2 strings and a int.
        this.type = type;
        this.manufacturer = manufacturer;
        this.count = count;
    }

    //Getters
    public String getType(){
        return type;
    }

    public String getManufacturer(){
        return manufacturer;
    }

    public int getCount(){
        return count;
    }

    //Setters
    public void setType(String type){
        this.type = type;
    }

    public void setManufacturer(String manufacturer){
        this.manufacturer = manufacturer;
    }

    public void setCount(int count){
        this.count = count;
    }

    //ToString
    public String toString(){
        return type + System.lineSeparator() +
                "Manufactured by: " + manufacturer + System.lineSeparator() +
                "Current Count: " + count + System.lineSeparator() +
                "ID: " + id + System.lineSeparator();
    }

    @Override
    public boolean matchesName(String searchStr) { //Implemented from the CatalogItem interface. This method checks to see if the string searched is equal to the name of any tool.
        if(searchStr.equalsIgnoreCase(type)){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public boolean matchesCreator(String searchStr) { //Implemented from the CatalogItem interface. This method checks to see if the string searched is equal to the manufacturer of any tool.
        if(searchStr.equalsIgnoreCase(manufacturer)){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public boolean matchesYear(int searchYear) { //Implemented from the CatalogItem interface. This method will always return false since tools dont have a date.
        return false;
    }

    @Override
    public void registerItem() { //Implemented from the CatalogItem interface. This method will assign the id to a tool.
        this.id = UUID.randomUUID().toString();

        String logPath = "src/main/resources/logs/toolLog.dat";
        String data = "Tool was registered at " + LocalDate.now() + " " + LocalTime.now() + " " + toString();

        FileStorageService.writeContentsToFile(data, logPath, true);
    }
}
