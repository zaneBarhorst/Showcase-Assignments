package com.techelevator.controller;

import com.techelevator.dao.ProductDao;
import com.techelevator.model.Product;
import com.techelevator.security.jwt.TokenProvider;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class ProductController {
    private ProductDao productDao;

    public ProductController(ProductDao productDao){
        this.productDao = productDao;
    }

    //Get request for products with a specific name or sku.
    @RequestMapping(path = "/products", method = RequestMethod.GET) //Mapping for the getProductsByNameOrSku method.
    public List<Product> getProductByNameOrSku(@RequestParam(required = false) String name,@RequestParam(required = false) String sku,@RequestParam(defaultValue = "false") boolean useWildcard){
        if(name == null && sku == null){
            return productDao.getProducts();
        }else{
            return productDao.getProductByNameOrSku(name, sku, useWildcard);
        }
    }

    //Get request for description of products with a specified id.
    @RequestMapping(path = "/products/{id}", method = RequestMethod.GET) //Mapping for the getProductById method.
    public Product getProductById(@PathVariable int id){
        Product retrievedProduct = productDao.getProductById(id);

        if(retrievedProduct == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found.");
        }else{
            return productDao.getProductById(id);
        }
    }
}
