package com.lendingcatalog.model;

public class Member { //Member Class

    //Variable Declarations
    private String firstName;
    private String lastName;

    public Member(String firstName, String lastName){ //The constructor takes 2 parameters both being strings.
        this.firstName = firstName;
        this.lastName = lastName;
    }

    //Getters
    public String getFirstName(){
        return firstName;
    }

    public String getLastName(){
        return lastName;
    }

    //Setters
    public void setFirstName(String firstName){
        this.firstName = firstName;
    }

    public void setLastName(String lastName){
        this.lastName = lastName;
    }

    //ToString
    public String toString(){
        return firstName + " " + lastName;
    }
}
