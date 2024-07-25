<template>
  <section id="product-cards">
      <div class="product-card" v-for="product in products" v-bind:key="product.productId">
          <div id="product-name" v-on:click='pushToProduct(product.productId)'>{{product.name}}</div>
          <div id="product-sku" v-on:click='pushToProduct(product.productId)'>{{product.productSku}}</div>
          <div id="product-price">{{product.price}}</div>
          <img id="product-image" src = 'img/product_350x250.jpg' />
          <div id='add-to-cart-btn' v-if="$store.state.token">
          <button v-on:click='addProductToCart(product.productId)'>Add To Cart</button>
      </div>
      </div>
  </section>
</template>

<script>
import CartService from '../services/CartService';

export default {   
    data(){
        return {
 
        }
    },  
    props: ['products'],                 
    methods: {
    pushToProduct(id){
      this.$router.push({ name: 'product-detail', params: {id: id}});
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
}
}
</script>

<style>
#product-cards {
    display: flex;
    flex-direction: row;
    flex-wrap: wrap;
    background-color: aquamarine;
}

.product-card{
    width: 400px;
    height: 400px;
    padding: 10px;
    border: 1px solid slateblue;
    background-color: lightcoral;
    border-width: 5px;
    border-radius: 5px;
    margin-right: 15px;
    margin-top: 10px;
    margin-left: 65px;
    margin-bottom: 10px;
    display: grid;
    grid-template-areas: "sku price"
                         "product-name ."
                         "product-image .";
}

#product-name {
    grid-area: product-name;
}

#product-sku {
    grid-area: sku;
}

#product-price {
    grid-area: price;
}

#product-image {
    grid-area: product-image;
}

</style>