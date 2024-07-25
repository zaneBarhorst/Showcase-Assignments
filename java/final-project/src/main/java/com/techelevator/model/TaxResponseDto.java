package com.techelevator.model;

import java.math.BigDecimal;

public class TaxResponseDto { //The TaxResponseDto model.
    private BigDecimal salesTax;
    private String lastUpdated;

    //Constructors.
    public TaxResponseDto(){ }

    public TaxResponseDto(BigDecimal salesTax, String lastUpdated){
        this.salesTax = salesTax;
        this.lastUpdated = lastUpdated;
    }

    //Getters/Setters.
    public BigDecimal getSalesTax(){
        return salesTax;
    }

    public void setSalesTax(BigDecimal salesTax){
        this.salesTax = salesTax;
    }

    public String getLastUpdated(){
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated){
        this.lastUpdated = lastUpdated;
    }
}
