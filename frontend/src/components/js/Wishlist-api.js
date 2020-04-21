import axios from 'axios';

const AXIOS = axios.create({
   baseURL: `/wishlist`
});

export default {

   getWishlist() {
      return AXIOS.get('/getWishlist');
   },

   removeWishlistItem(productId) {
      return AXIOS.post('/removeWishlistItem/' + productId);
   },

   clearWishlist() {
      return AXIOS.post('/removeAllWishlistItems')
   },
}