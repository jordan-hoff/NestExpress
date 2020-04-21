import axios from 'axios'

const AXIOS = axios.create({
   baseURL: `/product`
});

export default {

   getProductId(product) {
      return AXIOS.get('/getProduct/' + product)
   },

   getSimilarItem(category) {
      return AXIOS.get('/getSimilar/' + category)
   },
}