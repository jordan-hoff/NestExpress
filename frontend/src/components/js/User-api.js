import axios from 'axios'

const AXIOS = axios.create({
   baseURL: `/userAccount`
});

export default {

   addToCart(id, quantity) {
      return AXIOS.post('/addItem/' + id + '/' + quantity)
   },

   removeAllItems() {
      return AXIOS.post('/removeAll/')
   },

   updateItemQuantity(productId, quantity) {
      return AXIOS.post('/updateQuantity/' + productId + '/' + quantity)
   },

   checkoutAsGuest(ccNumber, cardName, cardDate, cvcNumber, fullName, recipientName) {
      return AXIOS.post('/checkoutGuest', {
         CC_Number: ccNumber,
         Card_Name: cardName,
         Card_Date: cardDate,
         CVC_Number: cvcNumber,
         fullName: fullName,
         recipientName: recipientName
      });
   },

   checkoutAsUser() {
      return AXIOS.post('/checkout');
   },

	// Authenticate the user.
   setAuthentication() {
      return AXIOS.post('/setAuCo')
   }
}
