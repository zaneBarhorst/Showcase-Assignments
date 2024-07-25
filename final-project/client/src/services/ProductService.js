import axios from "axios";

export default {
    getAllProducts(){
        return axios.get('/products');
    },
    getProductById(id){
        return axios.get(`/products/${id}`);
    },
    getProductByName(name){
        const url = `/products?name=${name}`;
        return axios.get(url);
    }
}