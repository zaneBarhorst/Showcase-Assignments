package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.CartItem;
import com.techelevator.model.Product;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import com.techelevator.model.User;
import com.techelevator.dao.UserDao;
import java.security.Principal;
import java.util.List;

@Component
public class JdbcCartDao implements CartDao { //The JdbcCartDao class implements the CartDao interface.

    CartItemDao cartItemDao;
    UserDao userDao;

    private final JdbcTemplate jdbcTemplate;

    public JdbcCartDao(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
        this.userDao = new JdbcUserDao(jdbcTemplate);
        this.cartItemDao = new JdbcCartItemDao(jdbcTemplate);
    }

    @Override
    public CartItem addItem(CartItem cartItem, Principal principal) { //The method to add items to the users cart. Currently not working due to a nullpointer exception.
        int userId = userDao.getUserByUsername(principal.getName()).getId();
        cartItem.setUser_id(userId);
        CartItem newCartItem = null;
        String sql = "INSERT INTO public.cart_item(\n" +
                "\tuser_id, product_id, quantity)\n" +
                "\tVALUES (?, ?, ?) RETURNING cart_item_id;";

        System.out.println(cartItem.getUser_id() + " " + cartItem.getProduct_id() + " " + cartItem.getQuantity());

        try{
            int newCartItemId = jdbcTemplate.queryForObject(sql, Integer.class, cartItem.getUser_id(), cartItem.getProduct_id(), cartItem.getQuantity());
            newCartItem = cartItemDao.getCartItemsByProductId(newCartItemId);
        }catch(CannotGetJdbcConnectionException e){
            throw new DaoException("Unable to connect to server or database", e);
        }catch (DataIntegrityViolationException e){
            throw new DaoException("Data integrity violation", e);
        }
        return newCartItem;
    }

    @Override
    public int deleteProductFromCart(int productId) { //Deletes a product from the users cart based on the items id.
        int numOfRows = 0;

        String sql = "DELETE FROM public.cart_item\n" +
                "\tWHERE product_id = ?;";

        try{
            numOfRows = jdbcTemplate.update(sql, productId);
        }catch(CannotGetJdbcConnectionException e){
            throw new DaoException("Unable to connect to server or database", e);
        }catch (DataIntegrityViolationException e){
            throw new DaoException("Data integrity violation", e);
        }

        return numOfRows;
    }

    @Override
    public int deleteAllProductsFromCart() { //Deletes all products from the cart.
        int numOfRows = 0;

        String sql = "DELETE FROM public.cart_item;";

        try {
            numOfRows = jdbcTemplate.update(sql);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }

        return numOfRows;
    }
}
