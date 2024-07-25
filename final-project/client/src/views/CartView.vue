<template>
<div id="cart">
    <table id="header">
        <tr>
            <td>
                <h1><loading-spinner id="spinner" v-bind:spin="isLoading"/>Shopping Cart</h1>
            </td>
            <td>
                <button id="delete-all-button" v-on:click="removeAllItemsFromCart()">Clear Cart</button>
            </td>
        </tr>
    </table>
    <table>
        <thead>
            <tr>
                <th>Qty</th>
                <th>Product</th>
                <th>Price</th>
                <th>Amount</th>
            </tr>
        </thead>
        <tbody>
            <tr v-for="item in cart.items" v-bind:key="item.cartItemId">
                <td id="quantity">
                    {{item.quantity}}
                </td>
                <td id="product-name">
                    {{item.product.name}}
                </td>
                <td id="product-price">
                    ${{item.product.price}}
                </td>
                <td id="product-amount">
                    ${{item.quantity * item.product.price}}
                </td>
                <td>
                    <button id="delete-button" v-on:click="removeItemFromCart(item.cartItemId)">Remove Item</button>
                </td>
            </tr>
            <tr id='border'>
                <td id='subtotal'>Subtotal: ${{cart.itemSubtotal}}</td>
                <td id='tax'>Tax: ${{cart.tax}}</td>
                <td id='total'>Total: ${{cart.total}}</td>
            </tr>
        </tbody>
    </table>
</div>
</template>

<script>
import LoadingSpinner from '../components/LoadingSpinner.vue';
import CartService from '../services/CartService';

export default {
  components: { LoadingSpinner },
data(){
    return{
        cart: {},
        isLoading: false,
    }
},
methods: {
    getCurrentCart(){
        this.isLoading = true;
        CartService.getItemsInCart().then(response => {
            this.cart = response.data;
            this.isLoading = false;
        })
    },
    removeItemFromCart(id){
        this.isLoading = true;
        CartService.removeProductFromCart(id).then(() => {
                this.isLoading = false;
                this.getCurrentCart();
    }).catch(error => {
            this.isLoading = false;
            console.log(error + " in removeItemFromCart");
        })
    },
    removeAllItemsFromCart(){
        this.isLoading = true;
        CartService.removeAllProductsFromCart().then(() => {
            this.isLoading = false;
            this.getCurrentCart();
        }).catch(error => {
            this.isLoading = false;
            console.log(error + " in removeAllItemsFromCart");
        })
    }
    },
    created() {
        this.getCurrentCart();
    }
}

</script>

<style>

#spinner{
    color: green;
}

table{
    border-style: none;
}

#cart{
    background-color: aquamarine;
}

</style>
