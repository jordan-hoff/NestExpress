<template :key="componentKey">
   <v-app style="background: #EEEEEE;" :key="componentKey">

      <!-- Page header -->
      <appheader/>

      <!-- Category navigation bar -->
      <navigation/>

      <v-container align-content-center style="background: #EEEEEE;">
         <v-row wrap justify-space-around>
            <v-flex xs12 sm12 md8 lg8 xl9>
                  <v-container style="margin-right: -10px;">

                     <!-- Cart primary heading -->
                     <v-row>
                        <v-flex xs12 sm6 md7 lg6>
                           <v-container>
                              <h1 :key="componentKey" id="cartTitle">SHOPPING CART </h1>
                           </v-container>
                        </v-flex>
                        <v-flex xs6 sm6 md5 lg6>
                           <v-container style="text-align: right; padding-right: 25px;">
                              <v-btn color="red"
                                       width="150"
                                       @click="removeAllItems(), forceRerender()"
                                       id="btnRemoveItems"
                                       outlined
                              >
                                 <v-flex>
                                    <v-icon medium color="red">{{ icons.deleteIcon }}</v-icon>
                                 </v-flex>
                                 <v-flex>
                                    Remove all
                                 </v-flex>
                              </v-btn>
                           </v-container>
                        </v-flex>
                     </v-row>

                     <v-card id="cartCard" flat>

                        <!-- Empty cart message -->
                        <div v-if="!loadedCartData">
                           <v-icon size="50" id="cartIcon">
                              {{cartIcon}}
                           </v-icon>
                           <h3 id="emptyCartMsg">{{ emptyCartMessage() }}</h3>
                        </div>

                        <!-- Cart items -->
                        <v-list v-else>
                           <v-list-item v-for="(id, cartItem) in this.cartItems" :key="id">
                              <v-row>
                                 <v-flex md2 xs12>
                                    <v-container>
                                       <router-link :to="/product/ + getProductId(cartItem)">
                                          <v-img
                                             class="white--text align-end"
                                             max-height="80"
                                             max-width="80"
                                             height="auto"
                                             width="auto"
                                             style="display: block; margin-left: auto; margin-right: auto;"
                                             alt="View product"
                                             :src="cartItemImage(cartItem)">
                                          </v-img>
                                       </router-link>
                                    </v-container>
                                 </v-flex>
                                 <v-flex md3>
                                    <v-container>
                                       <router-link :to="/product/ + getProductId(cartItem)" id="productText">{{ cartItemName(cartItem) }}</router-link>
                                    </v-container>
                                 </v-flex>
                                 <v-flex md2 sm6 xs6>
                                    <v-container>
                                       <p id="productPrice">${{ cartItemPrice(cartItem)}}</p>
                                    </v-container>
                                 </v-flex>

                                  <!-- Quantity-->
                                 <v-flex xs6 sm6 md2>
                                    <v-container>
                                       <div>
                                          <v-combobox
                                             :items="cartItemQuantity"
                                             :label="cartItemQty(cartItem)"
                                             v-model="quantityAmount"
                                             @change="setItemQty(cartItem, quantityAmount)"
                                             style="  text-align: center;
                                                      max-width: 100px;
                                                      border-style: solid;
                                                      border-color: #808080;
                                                      border-width: thin;"

                                             maxlength="2"
                                             hide-details
                                             flat
                                             dense
                                             solo
                                          ></v-combobox>
                                       </div>
                                    </v-container>
                                 </v-flex>
                                 <v-flex md1>
                                    <v-btn icon id="wishListBtn" style="margin-top: 5px;" @click="addToWishlist(cartItem)">
                                       <v-icon id="wishListIcon" color="#003B63" medium>{{ icons.wishListIcon }}</v-icon>
                                    </v-btn>
                                       <p style="font-size: 12px;">{{wishListData.textAdd}}</p>
                                 </v-flex>
                                 <v-flex md1>
                                    <v-btn icon color="red" style="margin-top: 5px;" @click="removeItem(cartItem)">
                                       <v-icon medium>{{ icons.deleteIcon }}</v-icon>
                                    </v-btn>
                                       <p style="font-size: 12px;">Remove</p>
                                 </v-flex>
                                 <v-container>
                                    <v-divider></v-divider>
                                 </v-container>
                              </v-row>
                           </v-list-item>
                        </v-list>
                  </v-card>
               </v-container>
            </v-flex>

            <!-- Order summary information -->
            <v-flex xs12 sm12 md4 lg4 xl3>
               <v-container>
                  <v-row>
                     <v-container>
                        <h1 id="orderSummaryTitle"><v-icon></v-icon>ORDER SUMMARY</h1>
                     </v-container>
                  </v-row>
                  <orderSummary/>
               </v-container>
            </v-flex>

            <!-- Similar items slide group -->
            <similarItems v-if='loadedCartData'/>

            <!-- Snackbar notifications -->
            <v-snackbar
                  id="wishlistSnackBar"
                  :timeout="wishList.timeout"
                  v-model="wishList.snackbar"
                  color="#003B63"
                  center
                  top
                  >
                  {{ wishList.text }}
                  <v-btn text icon height="50px" width="50px" color="white" @click.native="wishList.snackbar = false">x</v-btn>
            </v-snackbar>
            <v-snackbar
                  id="wishlistError"
                  :timeout="error.timeout"
                  v-model="error.snackbar"
                  color="red"
                  center
                  top
                  >
                  {{ error.text }}
                  <v-btn text icon height="50px" width="50px" color="white" @click.native="error.snackbar = false">x</v-btn>
            </v-snackbar>

         </v-row>
      </v-container>

    <!-- Page footer -->
    <appFooter/>

   </v-app>
</template>

<script>
import header             from './Header.vue'
import footer             from './Footer.vue'
import navBar             from './Navigation.vue'
import orderSum           from './OrderSummary.vue'
import similarItems       from './SilderItems.vue'
import cartApi            from './js/Cart-api'
import userApi            from './js/User-api'
import headerApi          from './js/Header-api'
import { mdiCloseCircle } from '@mdi/js';
import { mdiCardsHeart }  from '@mdi/js';
import { mdiCart }        from '@mdi/js'
import { eventBus }       from '../main'

export default {

   components: {
      appheader:    header,
      appFooter:    footer,
      navigation :  navBar,
      orderSummary: orderSum,
      similarItems: similarItems,
   },

   mounted() {
      eventBus.$on('cartItems-update', (data) => {
         if(data) {
            this.loadedCartData = false
            this.getCartItems()
         }
      })
   },

   data() {
      return {
         cartIcon: mdiCart,

         loadedCartData: false,

         componentKey: 0,

         // Wish list data
         wishListData: {
            check: true,
            textAdd: 'Add to Wishlist',
         },

         // Wish List snackbar notification
         wishList: {
            snackbar: false,
            y: 'top',
            x: null,
            mode: '',
            timeout: 3000,
            text: 'Item added to Wish List',
         },

         // Empty cart message
         emptyCart: {
            text: ''
         },

         // Error snackbar notification
         error: {
            snackbar: false,
            y: 'top',
            x: null,
            mode: '',
            timeout: 3000,
            text: '',
         },

         cartQuantity: 0,

         cartItemQtyText: '',

         icons: {
            deleteIcon:   mdiCloseCircle,
            wishListIcon: mdiCardsHeart,
         },

         cartItemQuantity: ["1", "2", "3", "4", "5", "6", "7", "8", "9", "10"],

         similarItemsText: 'SIMILAR ITEMS',
      }
   },

   created() {

      // Get cart items.
      this.loadedCartData = false
      this.getCartItems()

      // Get the cart quantity.
      headerApi.getActiveUser()
      .then(response => {
         if (response.data.error == null) {
            this.cartQuantity = response.data.cartQuantity
            if(this.cartQuantity === 1)
               this.cartItemQtyText = 'item'
            else
               this.cartItemQtyText = 'items'
         } else
            this.emptyCartMessage()
      })
      .catch(this.emptyCartMessage())
   },

   methods: {
      /* eslint-disable */

      model: null,
      similarItems: [],
      cartItems: [],
      wishlistItem: [],

      getCartItems() {
         cartApi.getCartItems()
         .then(response => {
            if (response.data.error != null) {
               this.cartQuantity   = 0;
               this.loadedCartData = false;
            } else if (response.data.item0 != null) {
               this.cartItems      = response.data
               this.loadedCartData = true
            } else {
               this.cartQuantity   = 0
               this.loadedCartData = false
            }
         })
      },

      getProductId(item) {
         return this.cartItems[item].productId
      },

      // Remove an item from the cart.
      removeItem(item) {
         userApi.updateItemQuantity(this.cartItems[item].productId, 0)
         .then(response => {
            eventBus.$emit('product-total', true)
            eventBus.$emit('cart-update', true)
            this.loadedCartData = false
            eventBus.$emit('cartItems-update', true)
            this.cartQuantity = 0
            this.emptyCartMessage()

            if(this.cartQuantity === 1)
               this.cartItemQtyText = 'item'
            else
               this.cartItemQtyText = 'items'
         })
      },

       // Remove all items from the cart.
      removeAllItems() {
         userApi.removeAllItems()
         .then(response => {
            eventBus.$emit('product-total', true)
            eventBus.$emit('cart-update', true)
            this.loadedCartData = false
            eventBus.$emit('cartItems-update', true)
            this.cartQuantity = 0
            this.emptyCartMessage()
         })
      },


      // Get cart items and their descriptions.
      cartItemImage(item) {
         return this.cartItems[item].image
      },

      cartItemName(item) {
         return this.cartItems[item].productName
      },

      cartItemQty(item) {
         return this.cartItems[item].quantity
      },

      cartItemPrice(item) {
         return this.cartItems[item].price
      },

      // Set item quantity.
      setItemQty(item, quantityAmount) {
         userApi.updateItemQuantity(this.cartItems[item].productId, quantityAmount)
         .then(response => {
            eventBus.$emit('product-total', true)
            eventBus.$emit('cart-update', true)

            if(this.cartQuantity == 1)
               this.cartItemQtyText = 'item'
            else
               this.cartItemQtyText = 'items'
         })
      },

      // Empty cart message & Similar items heading.
      emptyCartMessage() {
         if(this.cartQuantity == 0)
            return 'Your cart is empty.'
         else
            return ''
      },

      // Wish list
      addToWishlist(item) {
         cartApi.addToWishlist(this.cartItems[item].productId)
         .then(response => {
            if (response.data.error == null) {
               this.wishList.text     = 'Item added to Wishlist'
               this.wishList.snackbar = true
            } else {
               this.error.text     = response.data.error
               this.error.snackbar = true
            }
         })
      },

      // Reload vue component.
      forceRerender() {
         this.componentKey += 1;
      }
   }
}
</script>

<!-- CSS -->
<style scoped src="./css/CartStyles.css">

</style>