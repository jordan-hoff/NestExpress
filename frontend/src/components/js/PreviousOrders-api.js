import axios from 'axios';

const AXIOS = axios.create({
   baseURL: `/orders`
});

export default {

   getCompletedOrders() {
      return AXIOS.get('/completed');
   },

   getOrderDetails(orderId) {
      return AXIOS.get('/details/' + orderId);
   },
}