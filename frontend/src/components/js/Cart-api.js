import axios from 'axios'

const AXIOS = axios.create({
   baseURL: `/cart`
});

export default {

   getCartItems() {
      return AXIOS.get('/getCart')
   },

   getSimilarItems(quantity) {
      return AXIOS.get('/getSimilarList/' + quantity)
   },

   addToWishlist(product) {
      return AXIOS.post('/addWishlist/' + product)
   },

   calculateTotal() {
      return AXIOS.get('/totalCost/')
   }
}