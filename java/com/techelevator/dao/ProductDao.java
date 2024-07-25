package com.techelevator.dao;

import com.techelevator.model.Product;

import java.util.List;

public interface ProductDao { //The ProductDao interface defines 4 methods.
    List<Product> getProducts();

    List<Product> getProductByNameOrSku(String name, String sku, boolean useWildcard);

    Product getProductById(int id);

    List<Product> getProductByUserId(int userId);
}
