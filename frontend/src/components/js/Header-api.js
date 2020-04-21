import axios from 'axios'

const AXIOS = axios.create({
   baseURL: `/userAccount`
});

export default {

   getActiveUser() {
      return AXIOS.get('/currentUser');
   },

   logoutUser() {
      return AXIOS.get('/logoutUser')
   },

   getEditInfo(){
      return AXIOS.get('/getEditInfo');
   },

   addItemsToCart(product, quantity) {
      return AXIOS.post('/addItem' + product + quantity)
   },

   addFunds(userFunds, ccNumber, cardName, cardDate, cvcNumber) {
      return AXIOS.post('/addFunds', {
         CC_Number: ccNumber,
         Card_Name: cardName,
         Card_Date: cardDate,
         CVC_Number: cvcNumber,
         funds: userFunds
      });
   },

   editAccount(firstName, lastName, password, validPassword, oldPassword, dorm, roomNumber) {
      return AXIOS.post('/editAccount', {
         first_name: firstName,
         last_name: lastName,
         password: password,
         validPassword: validPassword,
         oldPassword: oldPassword,
         dorm: dorm,
         roomNumber: roomNumber
      });
   }
}