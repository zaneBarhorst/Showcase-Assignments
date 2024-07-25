package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.CartItem;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcCartItemDao implements CartItemDao { //The The JdbcCartItemDao class implements the CartItemDao interface.

    private final JdbcTemplate jdbcTemplate;

    public JdbcCartItemDao(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<CartItem> getCartItemsByUserId(int userId) { //The method to get cart items by a users id.
        List<CartItem> items = new ArrayList<>();
        String sql = "SELECT * FROM cart_item ci " +
                "JOIN users u ON u.user_id = ci.user_id " +
                "WHERE u.user_id = ? " +
                "ORDER BY ci.cart_item_id;";

        try{
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
            while(results.next()){
                CartItem currentItem = mapRowToCartItem(results);
                items.add(currentItem);
            }
        }catch (CannotGetJdbcConnectionException e){
            throw new DaoException("Unable to connect to server or database", e);
        }

        return items;
    }

    @Override
    public CartItem getCartItemsByProductId(int productId){ //The method to get cart items based on a products id.
        CartItem cartItem = null;
        String sql = "SELECT * FROM cart_item ci " +
                "JOIN product p ON p.product_id = ci.product_id " +
                "WHERE p.product_id = ?;";

        try{
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, productId);
            if(results.next()){
                cartItem = mapRowToCartItem(results);
            }
        }catch (CannotGetJdbcConnectionException e){
            throw new DaoException("Unable to connect to server or database", e);
        }

        return cartItem;
    }

    private CartItem mapRowToCartItem(SqlRowSet rowSet){ //
        CartItem cartItem = new CartItem();
        cartItem.setCart_item_id(rowSet.getInt("cart_item_id"));
        cartItem.setUser_id(rowSet.getInt("user_id"));
        cartItem.setProduct_id(rowSet.getInt("product_id"));
        cartItem.setQuantity(rowSet.getInt("quantity"));
        return cartItem;
    }
}
