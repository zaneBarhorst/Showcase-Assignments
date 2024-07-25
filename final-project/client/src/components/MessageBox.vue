<template>
  <div id="message-box" v-bind="message">
  </div>
</template>

<script>
import ProductService from '../services/ProductService'

export default {
    data(){
        return{
            message: "",
            returnedProduct: {}
        }
    },
    methods: {
        generateMessage(id){
            this.returnedProduct = ProductService.getProductById(id);
            this.message = this.returnedProduct.name + " has been added to your cart."
        }
    }

}
</script>

<style>

</style>