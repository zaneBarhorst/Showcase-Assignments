package com.techelevator.controller;

import com.techelevator.dao.CartDao;
import com.techelevator.model.Cart;
import com.techelevator.model.CartItem;
import com.techelevator.model.Product;
import com.techelevator.service.CartService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@PreAuthorize("isAuthenticated()") //Annotation to check and make sure the user is authorized.
public class CartController {
    private CartDao cartDao;
    private CartService cartService;

    public CartController(CartDao cartDao, CartService cartService){ //CartController Constructor
        this.cartDao = cartDao;
        this.cartService = cartService;
    }

    @RequestMapping(path = "/cart", method = RequestMethod.GET) //Mapping for the getCart method.
    public Cart getCart(Principal principal){
        Cart userCart = cartService.getUserCart(principal);
        return userCart;
    }

    @RequestMapping(path = "/cart/items", method = RequestMethod.POST) //Mapping for the addItem method.
    public CartItem addItem(@Valid @RequestBody CartItem cartItem, Principal principal){
        return cartDao.addItem(cartItem, principal);
    }

    @RequestMapping(path = "/cart/items/{itemId}", method = RequestMethod.DELETE) //Mapping for the deleteItem method.
    public int deleteProductFromCart(@PathVariable int itemId){
        return cartDao.deleteProductFromCart(itemId);
    }

    @RequestMapping(path = "/cart", method = RequestMethod.DELETE) //Mapping for the deleteAllProductsFromCart method.
    public int deleteAllProductsFromCart(){
        return cartDao.deleteAllProductsFromCart();
    }
}
