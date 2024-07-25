package com.techelevator.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Cart { //The cart model
    private List<CartItem> items;
    private BigDecimal tax;
    private BigDecimal subtotal;
    private BigDecimal total;

    //Constructors

    public Cart(){
        this.items = new ArrayList<>();
        this.tax = new BigDecimal("0.0");
        this.subtotal = new BigDecimal("0.0");
        this.total = new BigDecimal("0.0");
    }

    public Cart(List<CartItem> items){
        this.tax = new BigDecimal("0.0");
        this.items = items;
    }

    //Getters/Setters for the members of the class.

    public List<CartItem> getItems(){
        return items;
    }

    public void setItems(List<CartItem> items){
        this.items = items;
    }

    public BigDecimal getTax(){
        return tax;
    }

    public void setTax(BigDecimal tax){
        this.tax = tax;
    }

    public BigDecimal getSubtotal(){
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal){
        this.subtotal = subtotal;
    }

    public BigDecimal getTotal(){
        return total;
    }

    public void setTotal(BigDecimal total){
        this.total = total;
    }
}
