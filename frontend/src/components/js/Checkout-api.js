import axios from 'axios'

const AXIOS = axios.create({
   baseURL: `/checkout`
});

export default {

   getDeliveryInfo() {
      return AXIOS.get('/info/delivery');
   },

   getPickupInfo()  {
      return AXIOS.get('/info/pickup');
   },

   getSummaryInfo() {
      return AXIOS.get('/info/summary')
   },

   setPreviousOrder() {
      return AXIOS.post('/set/order')
   },

   setAlternateInfo(dorm, roomNumber, recipient) {
      return AXIOS.post('/set/altInfo/' + dorm + ' ' + roomNumber + '/' + recipient)
   },

   getAuthentication() {
      return AXIOS.get('/auCo');
   },

   getCouponTotal(coupon) {
      return AXIOS.post('/couponTotal/' + coupon)
   },

   removeAuthentication() {
      return AXIOS.post("/rmAuCo")
   }
}