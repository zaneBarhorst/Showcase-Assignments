package com.techelevator.dao;

import com.techelevator.model.CartItem;
import com.techelevator.model.Product;

import java.security.Principal;

public interface CartDao { //The CartDao interface defines 3 methods.
    CartItem addItem(CartItem cartItem, Principal principal);
    int deleteProductFromCart(int productId);
    int deleteAllProductsFromCart();
}
