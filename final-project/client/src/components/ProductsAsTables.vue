<template>
  <section id="product-table">
    <h2>Product Table</h2>
    <table id="product-table">
      <thead>
      <tr>
        <th>SKU</th>
        <th>Product</th>
        <th>Price</th>
        <th>Add To Cart</th>
      </tr>
      </thead>
      <tbody>
          <tr v-for="product in products" v-bind:key="product.productId">
            <td id="product-sku" v-on:click='pushToProduct(product.productId)'>
              {{product.productSku}}
            </td>
            <td id="product-name" v-on:click='pushToProduct(product.productId)'>
              {{product.name}}
            </td>
            <td id="product-price">
              ${{product.price}}
            </td>
            <td id="add-item-button" v-if="$store.state.token">
              <button v-on:click="addProductToCart(product.productId)">Add to cart</button>
            </td>
            </tr>
      </tbody>
    </table>
  </section>
</template>

<script>
import CartService from '../services/CartService';

export default {
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

table, thead, th, tbody, td{
  border-style: solid;
}

thead, th, tbody, td{
  padding-left: 20px;
  padding-right: 20px;
}

button{
  align-items: center;
}

#product-price{
  text-align: right;
}

</style>