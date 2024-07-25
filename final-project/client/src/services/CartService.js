import axios from "axios";

export default {
    getItemsInCart(){
        return axios.get(`/cart`);
    },
    addProductToCart(productId){
        let cartItem = {productId: productId, quantity: 1};
        return axios.post(`/cart/items`, cartItem);
    },
    removeProductFromCart(id){
        return axios.delete(`/cart/items/${id}`)
    },
    removeAllProductsFromCart(){
        return axios.delete(`/cart`);
    }
}