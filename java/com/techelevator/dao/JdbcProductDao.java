package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.Product;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcProductDao implements ProductDao{ //The JdbcProductDao class implements the JdbcProductDao interface.

    private final JdbcTemplate jdbcTemplate;

    public JdbcProductDao(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Product> getProducts() { //The method to return all of the products.
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM product ORDER BY name;";

        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
            while(results.next()){
                Product currentProduct = mapRowToProduct(results);
                products.add(currentProduct);
            }
        }catch (CannotGetJdbcConnectionException e){
            throw new DaoException("Unable to connect to server or database", e);
        }

        return products;
    }

    @Override
    public List<Product> getProductByNameOrSku(String name, String sku, boolean useWildcard) { //The method to return products based on the given name or sku parameters.
        List<Product> product = new ArrayList<>();
        String sql = "SELECT * FROM product WHERE name = ? OR product_sku = ?;";

        if(useWildcard){
            name = "%" + name + "%";
            sku = "%" + sku + "%";
        }

        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, name, sku);
            while(results.next()){
                Product currentProduct = mapRowToProduct(results);
                product.add(currentProduct);
            }
        }catch (CannotGetJdbcConnectionException e){
            throw new DaoException("Unable to connect to server or database", e);
        }
        return product;
    }

    @Override
    public Product getProductById(int id) { //The method for returning products based on id.
        Product product = null;
        String sql = "SELECT * FROM product WHERE product_id = ?;";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);
            if(results.next()){
                product = mapRowToProduct(results);
            }
        }catch (CannotGetJdbcConnectionException e){
            throw new DaoException("Unable to connect to server or database", e);
        }
        return product;
    }

    @Override
    public List<Product> getProductByUserId(int userId) { //The method for returning products based on users id.
        List<Product> productList = new ArrayList<>();
        String sql = "SELECT * FROM product p " +
                "JOIN cart_item ci ON ci.product_id =  p.product_id " +
                "WHERE ci.user_id = ?;";

        try{
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
            while(results.next()){
                Product currentProduct = mapRowToProduct(results);
                productList.add(currentProduct);
            }
        }catch (CannotGetJdbcConnectionException e){
            throw new DaoException("Unable to connect to a server or database", e);
        }

        return productList;
    }

    private Product mapRowToProduct(SqlRowSet rowSet){ //The method to map the product based on the provided model.
        Product product = new Product();
        product.setId(rowSet.getInt("product_id"));
        product.setProductSku(rowSet.getString("product_sku"));
        product.setName(rowSet.getString("name"));
        product.setDescription(rowSet.getString("description"));
        product.setPrice(rowSet.getBigDecimal("price"));
        product.setImageName(rowSet.getString("image_name"));
        return product;
    }
}
