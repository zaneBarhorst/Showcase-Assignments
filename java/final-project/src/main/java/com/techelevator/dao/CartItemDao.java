package com.techelevator.dao;

import com.techelevator.model.Cart;
import com.techelevator.model.CartItem;
import com.techelevator.model.Product;

import java.util.List;

public interface CartItemDao { //The CartItemDao interface defines 2 methods.
    public List<CartItem> getCartItemsByUserId(int userId);

    public CartItem getCartItemsByProductId(int productId);
}
