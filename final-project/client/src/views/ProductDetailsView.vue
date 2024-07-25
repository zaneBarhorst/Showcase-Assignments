<template>
<div id="product-details">
    <h1>{{product.name}}</h1>
    <h3>{{product.description}}</h3>
    <table id="tbl">
        <th>{{product.productSku}}</th>
        <th>{{product.price}}</th>
    </table>
    <p>{{product.name}}</p>
    <img src = 'img/product_350x250.jpg' />
    <button v-on:click='addProductToCart(product.productId)' v-if="$store.state.token">Add To Cart</button>
</div>
</template>

<script>
import ProductService from '../services/ProductService.js';
import CartService from '../services/CartService';
import MessageBoxVue from '../components/MessageBox.vue';

export default {
    data() {
        return {
            product: {},
        }
    },
    methods: {
        getProduct(id){
            ProductService.getProductById(id).then(response => {
                this.product = response.data;
            })
        },
        addProductToCart(product){
        CartService.addProductToCart(product).then(response => {
            if(response.status === 200){
                window.alert("Item has been added to your cart.");

            }
        }).catch(error => {
            console.log(error + ' in addProductToCart');
        })
    }
    },
    created(){
        this.getProduct(this.$route.params.id)
    }
}

</script>

<style>

#tbl{
    border: none;
    border-style: none;
}

#product-details{
    background-color: aquamarine;
    border-style:double;
    border-color: slateblue;
}

</style>
