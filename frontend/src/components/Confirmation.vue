<template>
    <v-app id="page">
      <v-container fluid id="body">
         <v-flex  md6 id="flexBody">
            <v-container id="html">

               <!-- Order Details -->
               <v-card id="card" class="justify-center">
                  <v-flex style="max-width:1000px;margin-left:auto;margin-right:auto;">
                     <v-row>
                        <v-flex xs12 sm12 md12 lg12  xl12>
                           <h1 id="welcomeHeading">
                              Thank you, {{user.name}}
                           </h1>
                        </v-flex>
                     </v-row>
                     <v-row>
                           <v-flex xs12 id="flexH2">
                              <h2>
                                 Your order is being processed.
                              </h2>
                           </v-flex>
                     </v-row>
                  </v-flex>
                     <h3>
                        Order Details
                     </h3>

                     <v-row v-show=showRecipient class="justify-center">
                        <v-flex xs6 sm4 md5 lg4 xl3 id="flexOrderNumTxt">
                           <p>
                              Recipient:
                           </p>
                        </v-flex>

                        <v-flex xs5 sm4 md5 lg3 xl2 id="flexOrderNum">
                           <p id="orderNum">
                              {{user.recipient}}
                           </p>
                        </v-flex>
                     </v-row>

                     <v-row class="justify-center">
                        <v-flex xs6 sm4 md5 lg4 xl3 id="flexOrderNumTxt">
                           <p>
                              Order number:
                           </p>
                        </v-flex>

                        <v-flex xs5 sm4 md5 lg3 xl2 id="flexOrderNum">
                           <p id="orderNum">
                              {{user.orderNumber}}
                           </p>
                        </v-flex>
                     </v-row>

                     <v-row class="justify-center">
                        <v-flex xs6 sm4 md5 lg4 xl3 id="flexOrderTotalTxt">
                           <p>
                              Order total:
                           </p>
                        </v-flex>

                        <v-flex xs5 sm4 md5 lg3 xl2>
                           <p id="orderTotal">
                              ${{user.orderTotal}}
                           </p>
                        </v-flex>
                     </v-row>

                     <v-row class="justify-center">
                        <v-flex xs6 sm4 md5 lg4 xl3 id="flexDate">
                           <p>
                              Date:
                           </p>
                        </v-flex>

                        <v-flex xs5 sm5 md5 lg3 xl2>
                           <p id="date">
                              {{user.orderDate}}
                           </p>
                        </v-flex>
                     </v-row>

                     <v-row class="justify-center">
                        <v-flex xs6 sm4 md5 lg4 xl3 id="flexDelivery">
                           <p>
                              Delivery Time:
                           </p>
                        </v-flex>

                        <v-flex xs5 sm4 md5 lg3 xl2>
                           <p id="deliveryDate">
                              {{user.deliveryTime}}
                           </p>
                        </v-flex>
                     </v-row>

                     <v-row class="justify-center">
                        <v-flex xs6 sm4 md5 lg4 xl3 id="flexDeliveryAddress">
                           <p>
                              Delivery Address:
                           </p>
                        </v-flex>

                        <v-flex xs5 sm4 md5 lg3 xl2>
                           <p id="deliveryDate">
                              {{user.deliveryAddress}}
                           </p>
                        </v-flex>
                  </v-row>
               </v-card>

               <!--Continue Shopping Button-->
               <v-flex id="flexContinueShopping">
                  <v-btn id="btnContinueShopping" @click="returnHome">
                     <span>
                        Continue Shopping
                     </span>
                  </v-btn>
               </v-flex>
            </v-container>
         </v-flex>
      </v-container>

      <!--Scroll to top-->
      <scroll/>

   </v-app>
</template>

<script>
import confirmationApi from './js/Confirmation-api'
import scrollToTop     from './ScrollToTop.vue'

export default {
   data() {
      return {
         user: {
            name: '',
            recipient: '',
            orderNumber: '',
            orderTotal: '',
            orderDate: '',
            deliveryTime: '',
            deliveryAddress: ''
         },
         showRecipient: false,
      }
   },

   components: {
      scroll:    scrollToTop
   },


   created() {
      // Get order details.
      confirmationApi.getOrderDetail()
         .then(response => {
            this.user.name            = response.data.name
            this.user.recipient       = response.data.recipient
            this.user.orderNumber     = response.data.orderNumber
            this.user.orderTotal      = response.data.orderTotal
            this.user.orderDate       = response.data.orderDate
            this.user.deliveryTime    = response.data.deliveryTime
            this.user.deliveryAddress = response.data.deliveryAddress
            if (this.user.recipient != '')
               this.showRecipient = true
            else
               this.showRecipient = false
         })
         .catch(error => {
            console.log(error)
         })
   },

   methods:{
      /* eslint-disable */

      model: null,
      featuredItems: [],

      returnHome() {
         confirmationApi.invalidSession()

            window.location.href = '/';
      },

      directFeaturedItem(item) {
         this.$router.push("/product/" + this.featuredItems[item].productId);
         window.scrollTo(500, 0);
      },
   },
}
</script>

<!-- CSS -->
<style scoped src="./css/ConfirmationStyles.css">

</style>