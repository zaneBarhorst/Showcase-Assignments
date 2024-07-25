package com.techelevator.model;

import org.springframework.security.core.parameters.P;

public class CartItem { //The CartItem model
    private int cart_item_id;
    private int user_id;
    private int product_id;
    private int quantity;
    private Product product;

    //Constructors
    public CartItem() { }

    public CartItem(int cart_item_id, int user_id, int product_id, int quantity, Product product) {
        this.cart_item_id = cart_item_id;
        this.user_id = user_id;
        this.product_id = product_id;
        this.quantity = quantity;
        this.product = product;
    }

    public CartItem(int user_id, int product_id, int quantity, Product product) {
        this(0, user_id, product_id, quantity, product);
    }

    //Getters/Setters.
    public int getCart_item_id(){
        return cart_item_id;
    }

    public void setCart_item_id(int id){
        this.cart_item_id = id;
    }

    public int getUser_id(){
        return user_id;
    }

    public void setUser_id(int id){
        this.user_id = id;
    }

    public int getProduct_id(){
        return product_id;
    }

    public void setProduct_id(int id){
        this.product_id = id;
    }

    public int getQuantity(){
        return quantity;
    }

    public void setQuantity(int quantity){
        this.quantity = quantity;
    }

    public Product getProduct(){
        return product;
    }

    public void setProduct(Product product){
        this.product = product;
    }
}