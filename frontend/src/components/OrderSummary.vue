<template>
   <v-card flat style="height: 600px; width: auto;">
      <v-flex>
         <img
            style="max-width: 100%; height: 255px; width: 100%; max-height: 100%; max-width: 100%; display: block;"
            src="https://image.freepik.com/free-photo/colorful-paper-bags-shopping_23-2147652053.jpg"
            alt="Shopping bags"
         />
      </v-flex>

      <div id="orderDiv">
         <v-container>
            <v-row>
               <v-flex sm4 md4 lg4>
                  <h2 class="paymentSummary">Subtotal:</h2>
               </v-flex>
               <v-flex sm8 md8 lg8>
                  <h2 :key="componentKey" class="paymentSummary" style="padding-right: 50px;">${{orderSummary.subtotal}}</h2>
               </v-flex>
            </v-row>

            <v-row>
               <v-flex sm4 md4 lg4>
                  <h2 class="paymentSummary">Tax (7%):</h2>
               </v-flex>
               <v-flex sm8 md8 lg8>
                  <h2 :key="componentKey" class="paymentSummary" style="padding-right: 50px;">
                     ${{orderSummary.totalTax}}
                  </h2>
               </v-flex>
            </v-row>
         </v-container>

         <v-divider style="margin-right: 15px; margin-left: 15px;"></v-divider>

         <v-container>
            <v-row>
               <v-flex sm6 md6 lg6>
                  <h2 id="totalTitle">TOTAL:</h2>
               </v-flex>
               <v-flex sm6 md6 lg6>
                  <h2 :key="componentKey" id="orderTotalPrice">
                     ${{orderSummary.totalPrice}}
                  </h2>
               </v-flex>
            </v-row>
         </v-container>
      </div>

      <!-- Checkout button -->
      <template>
         <div id="divCheckoutButton">
            <v-container>
               <v-btn @click.stop="userActiveStatus" elevation="0" block large color="#E77F2C" style="color: white; font-size: 25px; font-weight: bold; font-family: Century Gothic;">
                  <v-flex md12>
                     <span style="text-align: center;">CHECKOUT</span>
                  </v-flex>
               </v-btn>
               <v-container>
                  <v-row>
                     <v-flex xs12 sm12 md12>
                        <span style="font-family: Century Gothic;"><a href="/">Continue Shopping</a></span>
                     </v-flex>
                  </v-row>
               </v-container>
            </v-container>
         </div>
      </template>

      <!-- Checkout dialog box and button-->
      <v-dialog v-model="checkoutDialog" width="500">
         <v-card>
            <p
               class="headline lighten-2"
               style="text-align: center;
                      padding-top: 15px;
                      padding-bottom: 15px;
                      font-size: 20px;
                      font-family: Arial;
                      background: #0056B6;
                      color: white;"
            >
               How do you want to checkout?
            </p>
            <v-container>
               <v-card-text style="font-size: 20px; font-family: Arial;">
                  Already a member? Sign in and checkout.
               </v-card-text>
            </v-container>

            <v-btn
               large
               text
               color="#E77F2C"
               style="border-style: solid;
                      border-color: #E77F2C;
                      margin-top: 20px;
                      margin-bottom: 20px;
                      font-size: 20px;
                      font-family: Century Gothic;
                      font-weight: bold;"
               to="/signin"
            >
               SIGN IN
            </v-btn>

            <v-container>
               <v-card-text style="font-size: 20px; font-family: Arial;">
                  Don't have an account? No problem, you can checkout as a guest.
               </v-card-text>
            </v-container>

            <v-container>
               <v-btn
                  elevation="0"
                  @click="checkoutPage"
                  large
                  color="#E77F2C"
                  style="color: white;
                         font-size: 20px;
                         font-family: Century Gothic;
                         font-weight: bold;
                         margin-bottom: 25px;"
               >
                  CHECKOUT AS GUEST
               </v-btn>
            </v-container>

            <v-container>
               <v-divider></v-divider>
            </v-container>

            <v-card-actions>
               <v-spacer></v-spacer>
               <v-btn color="primary" text @click="checkoutDialog = false">
                  Return to cart
               </v-btn>
            </v-card-actions>
         </v-card>
      </v-dialog>
   </v-card>
</template>

<script>
import cartApi               from './js/Cart-api'
import headerApi             from './js/Header-api'
import { eventBus }          from '../main'
import checkoutApi           from './js/Checkout-api'
import userApi               from './js/User-api'

export default {
   mounted() {

      // Calculate the initial and total cost.
      this.calculateTotal()

      eventBus.$on('product-total', (data) => {
         if(data) {
               this.calculateTotal()
         }
      })
   },

   data() {
      return {

         checkoutDialog: false,

         orderSummary: {
            subtotal: '',
            totalTax: '',
            totalPrice: ''
         },
      }
   },

   methods: {
      /* eslint-disable */

      // Route to the confirmation page if the possible.
      checkoutPage() {
         userApi.setAuthentication()
            .then(
               window.location.href = '/checkout'
            )
      },

      calculateTotal() {
         cartApi.calculateTotal()
            .then(response => {
               this.orderSummary.subtotal   = response.data.subtotal
               this.orderSummary.totalTax   = response.data.taxTotal
               this.orderSummary.totalPrice = response.data.totalCost
            })
      },

      // Get active user.
      userActiveStatus() {
         headerApi.getActiveUser()
            .then(response => {
               if(response.data.firstname != 'guest' &&
                  response.data.cartQuantity != '0') {
                  this.checkoutDialog = false
                  checkoutApi.setPreviousOrder()
                  userApi.setAuthentication()
                  window.location.href = "/checkout";
               } else if(response.data.firstname == 'guest') {
                  checkoutApi.setPreviousOrder()
                  userApi.setAuthentication()
                  this.checkoutDialog = true
               }
            })
      },
   }
}
</script>

<!-- CSS -->
<style scoped src="./css/OrderSummary.css">

</style>