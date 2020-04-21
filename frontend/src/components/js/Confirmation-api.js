import axios from 'axios'

const AXIOS = axios.create({
   baseURL: `/confirmation`
});

export default {

	// Get the order details.
   getOrderDetail() {
      return AXIOS.get('/details')
   },

	// Invalidates session.
   invalidSession(){
      return AXIOS.post('/session/invalidate/')
   },

	// Authenticate the user.
   getAuthentication() {
      return AXIOS.get('/auCm')
   }
}