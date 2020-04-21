<template>
   <v-app>
      <!-- Page header -->
      <page-header/>

      <!-- Category navigation bar -->
      <navigation/>

      <!-- Wishlist primary heading -->
      <v-row>
         <v-container>
            <h1 id="previousOrdersHeading">PREVIOUS ORDERS</h1>
         </v-container>
      </v-row>

      <v-row>
         <v-container>
            <v-flex md12>

               <v-sheet class="mx-auto"
                        elevation="0"
                        max-width="1620"
                        color="#FAFAFA"
                        id="ordersSheet">

               <!-- Previous orders list -->
               <v-list id="ordersList">
                  <v-list-item v-for="(id, orderDetail) in previousOrders" :key="id">
                     <v-container>
                        <v-card outlined>
                           <v-sheet elevation="0"
                                     max-width="1620"
                                     color="#DBE0E3"
                                     style="border-radius: 0%;">
                              <v-row>
                                 <v-container>
                                    <p id="orderDate">Ordered on {{orderDate(orderDetail)}}</p>
                                 </v-container>
                              </v-row>
                           </v-sheet>
                              <v-row>
                                 <v-flex sm12 md2 lg2 xl2>
                                   <v-container>
                                       <v-icon size="150" color="#FBBB89">
                                         {{icons.checkmarkIcon}}
                                      </v-icon>
                                   </v-container>
                                 </v-flex>
                                 <v-flex xs12 sm2 md2 lg2 xl2 style="margin: auto;">
                                    <v-container>
                                       <span style="color: gray;">Order Number:</span>
                                       <p style="color: black; font-weight: bold;">NE{{orderNumber(orderDetail)}}</p>
                                    </v-container>
                                 </v-flex>
                                 <v-flex xs12 sm2 md2 lg2 xl2 style="margin: auto;">
                                    <v-container>
                                       <span style="color: gray;">Delivery Address:</span>
                                       <p style="color: black; font-weight: bold;">{{deliveryAddress(orderDetail)}}</p>
                                    </v-container>
                                 </v-flex>
                                 <v-flex xs12 sm2 md2 lg2 xl2 style="margin: auto;">
                                    <v-container>
                                       <span style="color: gray;">Order Total:</span>
                                       <p style="color: black; font-weight: bold;">{{orderTotal(orderDetail)}} (Tax included)</p>
                                    </v-container>
                                 </v-flex>
                                 <v-flex xs12 sm12 md2 lg2 xl2 style="margin: auto;">
                                    <v-container>
                                       <span style="color: gray;">Payment method:</span>
                                       <p style="color: black; font-weight: bold;">NestExpress **4552</p>
                                    </v-container>
                                 </v-flex>
                                 <v-flex xs12 sm12 md12 lg2 xl2 style="margin: auto;">
                                    <v-container>
                                       <v-btn outlined
                                              @click="itemsDialog = true, getOrderDetails(orderDetail), setOrderNumber(orderDetail)"
                                              color="primary"
                                              elevation="0"
                                              height="50"
                                              id="viewItemsButton">
                                             View Items
                                        </v-btn>
                                    </v-container>
                                 </v-flex>
                              </v-row>
                           </v-card>
                        </v-container>
                     </v-list-item>
                  </v-list>
               </v-sheet>
            </v-flex>
         </v-container>
      </v-row>

        <!-- Previously ordered products dialog box -->
      <template>
         <v-row justify="center">
            <v-dialog v-model="itemsDialog" scrollable max-width="600px">
               <v-card>
                  <v-card-title class="lighten-2" id="viewItemsDialogHeading">
                    Item(s) from Order #NE{{this.orderNumberTitle}}
                 </v-card-title>
                 <v-divider></v-divider>
                 <v-card-text style="height: 600px;">
               <v-card outlined elevation="0" v-for="(id, product) in orderProducts" :key="id" id="viewItemsDialogCard">
                  <v-card-text>
                     <v-row>
                        <v-flex xs12 sm4 md4>
                           <v-container>
                              <v-img max-height="120" max-width="120"
                                     :src="getProductImage(product)">
                              </v-img>
                           </v-container>
                        </v-flex>

                        <v-flex xs12 sm8 md8 style="margin: auto;">
                           <v-container>
                              <p id="productName"> {{getProductName(product)}}</p>
                              <p id="productPrice"> {{getProductPrice(product)}}</p>
                              <p id="productQty">Qty: {{getProductQty(product)}}</p>
                           </v-container>
                        </v-flex>
                     </v-row>
                  </v-card-text>
               </v-card>
                  </v-card-text>
                  <v-divider></v-divider>
                     <v-card-actions style="text-align: left;">
                        <v-container style="text-align: right;">
                           <v-btn outlined text @click="itemsDialog = false">Close</v-btn>
                        </v-container>
                  </v-card-actions>
               </v-card>
            </v-dialog>
         </v-row>
      </template>

      <!-- Page footer -->
      <page-footer/>
   </v-app>
</template>

<script>
import header                    from './Header.vue'
import footer                    from './Footer.vue'
import navigation                from './Navigation'
import previousOrdersApi         from './js/PreviousOrders-api'
import { mdiCloseCircle }        from '@mdi/js'
import { mdiCheckCircleOutline } from '@mdi/js';

export default {
   components: {
      pageHeader: header,
      pageFooter: footer,
      navigation: navigation
   },

   data(){
      return {
         icons: {
            removeIcon: mdiCloseCircle,
            checkmarkIcon: mdiCheckCircleOutline,
         },

         previousOrders: [],

         orderProducts: [],

         orderNumberTitle: '',

         itemsDialog: false,
      }
   },

   mounted() {
      this.getPreviousOrders();
   },

   methods: {
      /* eslint-disable */

      // Get a list of completed orders.
      getPreviousOrders() {
         previousOrdersApi.getCompletedOrders()
            .then(response => {
               this.previousOrders = response.data;
            })
            .catch(error => {
            })
      },

      // Get the details for an order.
      getOrderDetails(item) {
         previousOrdersApi.getOrderDetails(this.previousOrders[item].OrderNumber)
            .then(response => {
               this.orderProducts = response.data;
            })
            .catch(error => {
            })
      },

      // Order Details.
      getOrderId(item) {
         return this.previousOrders[item].orderId;
      },

      setOrderNumber(item) {
         this.orderNumberTitle = this.previousOrders[item].OrderNumber;
      },

      orderNumber(item) {
         return this.previousOrders[item].OrderNumber;
      },

      orderDate(item) {
         return this.previousOrders[item].OrderDate;
      },

      orderTotal(item) {
         return this.previousOrders[item].OrderTotal;
      },

      deliveryAddress(item) {
         return this.previousOrders[item].DeliveryLocation;
      },

      // Product Details.
      getProductId(item) {
         return this.orderProducts[item].productId;
      },

      getProductImage(item) {
         return this.orderProducts[item].image;
      },

      getProductName(item) {
         return this.orderProducts[item].ProductName;
      },

      getProductPrice(item) {
         return this.orderProducts[item].Price;
      },

      getProductQty(item) {
         return this.orderProducts[item].Quantity;
      }
   },
}
</script>

<style scoped src="./css/PreviousOrders.css">

</style>