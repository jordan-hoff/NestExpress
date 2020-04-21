import axios from 'axios'

const AXIOS = axios.create({
   baseURL: `/results`
});

export default {

   getProducts(firstCategory, lastCategory) {
      return AXIOS.get('/gtCatRg/' + firstCategory + '/' + lastCategory)
   },

   getKeyword(keyword) {
      return AXIOS.get('/getProducts/keyword/' + keyword)
   },

   getCategory(category) {
      return AXIOS.get('/getProducts/category/' + category)
   },

   getProductId(id) {
      return AXIOS.get('/getProducts/id/' + id)
   },

   getTypeAhead() {
      return AXIOS.get('/typeAhead')
   }
}
