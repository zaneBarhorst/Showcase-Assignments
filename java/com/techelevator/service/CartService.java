package com.techelevator.service;

import com.techelevator.dao.CartDao;
import com.techelevator.dao.CartItemDao;
import com.techelevator.dao.ProductDao;
import com.techelevator.dao.UserDao;
import com.techelevator.model.Cart;
import com.techelevator.model.CartItem;
import com.techelevator.model.Product;
import com.techelevator.model.User;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Component
public class CartService { //The CartService class.
    private CartItemDao cartItemDao;
    private ProductDao productDao;
    private UserDao userDao;
    private TaxService taxService;

    //Constructor
    public CartService(CartItemDao cartItemDao, ProductDao productDao, UserDao userDao, TaxService taxService){
        this.cartItemDao = cartItemDao;
        this.productDao = productDao;
        this.userDao = userDao;
        this.taxService = taxService;
    }

    public Cart getUserCart(Principal principal){ //Returns the currently logged in users cart.
        String username = principal.getName();
        User user = userDao.getUserByUsername(username);
        int userId = user.getId();

        List<CartItem> items = cartItemDao.getCartItemsByUserId(userId);

        Cart cart = new Cart(items);

        List<Product> userProducts = productDao.getProductByUserId(userId);

        for(CartItem cartItem : items){
            int productId = cartItem.getProduct_id();
            Product matchingProduct = getMatchingProduct(productId, userProducts);
            cartItem.setProduct(matchingProduct);
        }

        BigDecimal taxAmount = taxService.getTaxRate(user.getStateCode());
        cart.setTax(taxAmount);
        cart.setSubtotal(getSubtotal(principal));
        cart.setTotal(getTotal(principal));

        return cart;
    }

    private BigDecimal getSubtotal(Principal principal){ //Helper method for getting the subtotal
        double subtotal = 0.00;
        String username = principal.getName();
        User user = userDao.getUserByUsername(username);
        int userId = user.getId();

        List<Product> userProducts = productDao.getProductByUserId(userId);

        for(Product product: userProducts){
            subtotal += product.getPrice().doubleValue();
        }

        BigDecimal subtotalBigDecimal = BigDecimal.valueOf(subtotal);

        return subtotalBigDecimal;
    }

    private BigDecimal getTotal(Principal principal){ //Helper method for getting the total
        String username = principal.getName();
        User user = userDao.getUserByUsername(username);

        BigDecimal subtotal = getSubtotal(principal);
        BigDecimal tax = taxService.getTaxRate(user.getStateCode());
        BigDecimal difference = subtotal.multiply(tax);
        BigDecimal total = subtotal.add(difference);

        return total;
    }

    private Product getMatchingProduct(int productId, List<Product> userProducts) { //Helper method for returning the same product based on id.
        for(Product product: userProducts){
            if(product.getId() == productId){
                return product;
            }
        }
        return null;
    }
}
