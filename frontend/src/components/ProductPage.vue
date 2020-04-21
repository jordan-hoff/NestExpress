<template>
   <v-app>

      <!-- Page Header -->
      <homeHeader/>
      <!-- Categories navigation bar -->
      <navigation/>

         <v-container class="container" fluid>
            <v-flex>
               <v-container style="margin-top:70px;">
                  <v-row>
                     <v-flex xs12 sm12 md4 lg6 xl6>
                        <v-container id="imageContainer">
                           <div @click="activateImageDialog">
                              <v-img class="white--text align-end medium"
                                     height="auto"
                                     id="productImg"
                                     style= "max-width:500px;transition: 0.3s;background-color:white;"
                                     :src="this.product.image">

                                 <template v-slot:placeholder>
                                    <v-row class="fill-height ma-0" align="center" justify="center">
                                       <v-progress-circular indeterminate color="grey lighten-5">
                                       </v-progress-circular>
                                    </v-row>
                                 </template>
                              </v-img>
                           </div>
                        </v-container>
                     </v-flex>

                     <!-- Expand image dialog boxes -->
                     <template>
                        <div class="text-center">
                           <v-dialog v-model="dialogImg" width="1000">
                              <v-card style="border-radius: 1%;">
                                 <v-img class="white lighten-2"
                                        style="1%; margin: auto;"
                                        max-height="600" max-width="1000"
                                        height="600"
                                        width="600"
                                        :src="this.product.image">
                                    <template v-slot:placeholder>
                                       <v-row class="fill-height ma-0"
                                              align="center"
                                              justify="center">
                                                    <v-progress-circular indeterminate color="grey lighten-5"></v-progress-circular>
                                       </v-row>
                                    </template>
                                 </v-img>

                                 <v-btn color="white"
                                        icon
                                        size="small"
                                        text
                                        @click="dialogImg= false"
                                        style="position: absolute;
                                               top: 0; right: 0;
                                               margin-right: 5px;
                                               margin-top: 8px;
                                               font-size: 15px;
                                               background:black;">
                                    X
                                 </v-btn>
                              </v-card>
                           </v-dialog>
                        </div>
                     </template>

                     <v-flex xs12 sm12 md8 lg6 xl6>
                        <v-row>
                           <v-container>
                              <v-flex lg12>
                                 <h1 id="productTitle">{{this.product.name}}</h1>
                              </v-flex>
                              <h2 id="price">
                                 {{this.product.price}}
                              </h2>
                              <h2 id="quantityTxt">
                                 Qty:
                              </h2>
                              <!-- Quantity -->
                              <v-row id="qtyRow">
                                 <v-flex  class= "d-flex justify-start mb-3" style="max-width: 75px; margin-left:10px;">
                                    <v-select :items="items"
                                              v-model="quantityAmount"
                                              value="1"
                                              outlined
                                              default="1">
                                    </v-select>
                              </v-flex>

                              <!--Add To Cart Button-->
                              <v-flex lg5 id="btnCartFlex">
                                 <v-btn id="btnAddToCart"
                                        elevation="0"
                                        color="#F7882F"
                                        type="submit"
                                        @click.native= "addItem(product.productId)">
                                       Add to Cart
                                 </v-btn>
                              </v-flex >

                              <!--Add To Wishlist Button-->
                              <v-flex xs5 md3 lg9 style="max-width:240px;
                                                         padding-left:50px;">
                                                <v-btn text tile rounded id="wishListBtn"  @click="addToWishlist(productItem)">
                                                    <v-icon id="wishListIcon" style="padding-bottom:5px;color:gray;" large>
                                                        {{ icons.wishListIcon }}
                                                    </v-icon>
                                                    <p id="wishlistAddTxt">
                                                        {{wishListData.textAdd}}
                                                    </p>
                                                </v-btn>
                                            </v-flex>
                                        </v-row>

                                        <!--Description-->
                                        <v-row>
                                            <v-flex md7 lg12 xl10 >
                                                <div id="descriptionBox">
                                                    <p id="descriptTitle">Description</p>
                                                    <p id="descTxt">{{this.product.descript}}</p>
                                                </div>
                                 </v-flex>
                              </v-row>
                           </v-container>
                        </v-row>
                     </v-flex >
                  </v-row>
               </v-container>

               <!-- Add to wishlist notifications -->
               <v-snackbar id="wishlistSnackBar"
                           :timeout= "wishList.timeout"
                           v-model= "wishList.snackbar"
                           color="#003B63"
                           top>
                  {{ wishList.text }}
                  <v-btn text icon height="50px" width="50px" color="white" @click.native= "wishList.snackbar = false">x</v-btn>
               </v-snackbar>
               <v-snackbar :timeout="error.timeout"
                           v-model="error.snackbar"
                           color="red"
                           top>
                  {{ error.text }}
                  <v-btn text icon height="50px" width="50px" color="white" @click.native= "error.snackbar = false">x</v-btn>
               </v-snackbar>

               <!-- Add to cart notifications -->
               <v-snackbar :timeout= "addToCart.timeout"
                            v-model= "addToCart.snackbar"
                            color="#E77F2C"
                            top>
                  {{ addToCart.text }}
                  <v-btn text
                         icon
                         height="50px"
                         width="50px"
                         color="white"
                         @click.native= "addToCart.snackbar = false">
                        x
                  </v-btn>
               </v-snackbar>

                <!-- Featured Items Slider -->
               <v-container >
                  <v-flex xs12 sm12 md12 lg12 xl12>
                     <v-flex xs12 sm12 md12 lg12 xl12>
                        <h1 class="productsHeading">
                           FEATURED ITEMS
                        </h1>
                        <v-sheet v-if="returnedFeatured"
                                 class="mx-auto"
                                 elevation="0"
                                 max-width="1550">
                           <v-slide-group v-model="model"
                                          style="background: #EEEEEE;margin-bottom:20px;"
                                          class="pa-4"
                                          active-class="success"
                                          prev-icon="mdi-arrow-left"
                                          next-icon="mdi-arrow-right"
                                          show-arrows
                                          center-active>
                              <v-slide-item
                                 v-for= "(id, product) in this.featuredItems" :key= "id"
                                 v-slot:default= "{ active }">
                                 <v-card id="featuredItemCard"
                                         :color= "active ? undefined : 'white'"
                                         class="ma-4"
                                         max-width="250"
                                         max-height="375"
                                         @click="directFeaturedItem(product)"
                                         hover>
                                    <v-container>
                                       <v-img
                                                class="white--text align-end"
                                                height="auto"
                                                width="auto"
                                                :src="featuredImage(product)">
                                       </v-img>
                                       <h1 id="featuredItemName">{{featuredName(product)}}</h1>
                                       <v-card-actions>
                                          <v-container>
                                             <h2 style="height: 40px;
                                                        position: absolute;
                                                        bottom: 0;
                                                        padding-top: 1px;
                                                        text-align: center;
                                                        right: 0;
                                                        left: 0;
                                                        background: #F7882F;
                                                        color: white;">
                                                ${{featuredPrice(product)}}
                                             </h2>
                                          </v-container>
                                       </v-card-actions>
                                    </v-container>
                                    <v-row
                                       class="fill-height"
                                       align="center"
                                       justify="center">
                                       <v-scale-transition>
                                          <v-icon
                                             v-if="active"
                                             color="white"
                                             size="48"
                                             v-text="'mdi-close-circle-outline'"
                                          ></v-icon>
                                       </v-scale-transition>
                                    </v-row>
                                 </v-card>
                              </v-slide-item>
                           </v-slide-group>
                        </v-sheet>
                     </v-flex>
                  </v-flex>
               </v-container>
            </v-flex>

    <!-- 'To-top' scroll button -->
      <scrollToTop />

         </v-container>

   <!-- Footer -->
   <homeFooter id="footer"/>

   </v-app>
</template>

<script>
import header            from './Header.vue'
import footer            from './Footer.vue'
import homeApi           from './js/Home-api'
import navBar            from './Navigation.vue'
import cartApi           from './js/Cart-api'
import toTop               from './ScrollToTop.vue'
import { eventBus }      from '../main'
import productApi        from './js/Product-api'
import userApi           from './js/User-api'
import { mdiCardsHeart } from '@mdi/js';

export default {

   components: {
      homeHeader : header,
      homeFooter : footer,
      navigation : navBar,
       scrollToTop: toTop,
   },

   data () {
      return {
         returnedFeatured: false,

         quantityAmount:'1',

         items: [
            '1',
            '2',
            '3',
            '4',
            '5',
            '6',
            '7',
            '8',
            '9',
            '10'
         ],

         quantity: 1,

         product: {
            productId: '',
            name: '',
            image: '',
            price: '',
            descript: ''
         },

         dialogImg: false,

         // Wishlist data.
         wishListData: {
            check: true,
            textAdd: 'Add to Wishlist',
         },

         // Error snackbar notification.
         error: {
            snackbar: false,
            y: 'top',
            x: null,
            mode: '',
            timeout: 3000,
            text: '',
         },

         // Wish List snackbar notifications.
         wishList: {
            snackbar: false,
            check: true,
            y: 'top',
            x: null,
            mode: '',
            timeout: 3000,
            text: 'Item added to Wish List',
            textAdd: 'Add to Wish List',
         },

         // Add to Cart snackbar notifications.
         addToCart: {
            snackbar: false,
            y: 'top',
            x: null,
            mode: '',
            timeout: 3000,
            text: 'Item added to Cart',
         },

         icons: {
            wishListIcon: mdiCardsHeart,
         },
   };
},

   created() {
      this.product.productId = this.$route.params.id,

      productApi.getProductId(this.product.productId)
      .then(response => {
         this.product.name     = response.data.productName
         this.product.image    = response.data.image
         this.product.price    = response.data.price
         this.product.descript = response.data.descript
      })
      .catch(error => {
         console.log(error)
      }),

      homeApi.getFeaturedItems(15)
         .then(response => {
            this.featuredItems    = response.data
            this.returnedFeatured = true;
         })
   },

   // Methods
   methods: {
      /* eslint-disable */

      viewedItem: null,
      model: null,
      featuredItems: [],
      wishlistItem: [],

      addItem(productId){
         userApi.addToCart(productId, this.quantityAmount)
         .then(response => {
            this.addToCart.snackbar = true;
            eventBus.$emit("cart-update", true)
            this.$router.push("/cart")
         })
      },

      activateImageDialog() {
         this.dialogImg = true
      },

      // Add an item to wishlist.
      addToWishlist(item) {
         cartApi.addToWishlist(this.product.productId)
         .then(response => {
            if (response.data.error == null) {
               this.wishList.text     = this.product.name + ' added to your Wishlist.'
               this.wishList.snackbar = true
            } else {
               this.error.text     = response.data.error
               this.error.snackbar = true
            }
         })
         .catch(error => {
            this.$router.push("/error")
         })
    },


      featuredName(item) {
         return this.featuredItems[item].productName
      },

      featuredPrice(item) {
         return this.featuredItems[item].price
      },

      featuredImage(item) {
         return this.featuredItems[item].image
      },

      directFeaturedItem(item) {
         this.$router.push("/product/" + this.featuredItems[item].productId);
         window.location.reload();
         window.scrollTo(500, 0);
      },

   },
}
</script>

<!-- CSS-->
<style scoped src="./css/ProductStyles.css">

</style>