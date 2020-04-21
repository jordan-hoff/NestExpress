<template>
   <v-app>

      <!-- Page Header -->
      <homeHeader/>

      <!-- Category navigation bar -->
      <navigation/>

      <!-- Wishlist primary heading -->
      <v-row>
         <v-container>
               <h1 id="wishlistTitle">YOUR WISHLIST</h1>
         </v-container>
      </v-row>
      <v-row>
         <v-container>
            <div v-if="!emptyListCheck">
               <v-flex>
                  <v-container>
                     <v-flex md2>
                        <v-btn
                           @click="removeAllItems()"
                           width="150"
                           color="red"
                           outlined
                        >
                           <v-row>
                              <v-flex md3>
                                 <v-icon medium>
                                    {{ icons.removeIcon }}
                                 </v-icon>
                              </v-flex>
                           <v-flex md5>
                              Remove all
                           </v-flex>
                           </v-row>
                        </v-btn>
                     </v-flex>
                  </v-container>
               </v-flex>
            </div>

            <v-container>
               <v-flex md12>

                        <v-sheet
                           class="mx-auto"
                           elevation="0"
                           max-width="1620"
                           color="#FAFAFA"
                           id="wishlistSheet"
                        >

                            <!-- Wishlist items -->
                            <div>
                              <v-list id="sheetListWishlist">

                                 <div v-if="emptyListCheck">
                                    <v-list-item color="#FAFAFA" style="min-height: 400px;">
                                          <v-container>
                                             <v-icon size="50">
                                                {{icons.wishlistIcon}}
                                             </v-icon>
                                             <h3 style="color: gray;">
                                                {{this.emptyWishlistMessage}}
                                             </h3>
                                          </v-container>
                                    </v-list-item>
                                 </div>
                            <div>
                                <v-list-item v-if="!emptyListCheck" v-for="(id, product) in wishlist" :key="id">
                                    <v-container>
                                        <v-row>
                                            <v-flex xs12 sm12 md2>
                                                <v-container>
                                                     <router-link :to="/product/ + getProductId(product)">
                                                        <v-img
                                                            class="white--text align-end"
                                                            width="125"
                                                            height="125"
                                                            style="display: block; margin-left: auto; margin-right: auto;"
                                                            alt="View product"
                                                            :src="getWishlistImage(product)">
                                                        </v-img>
                                                    </router-link>
                                                </v-container>
                                            </v-flex>

                                            <v-flex xs12 sm10 md5>
                                                <v-container style="margin-top: 5px !important;">
                                                    <router-link style="padding-top: 5px !important;" :to="/product/ + getProductId(product)" id="productTxt">{{wishlistName(product)}}</router-link>
                                                </v-container>
                                            </v-flex>

                                            <v-flex xs12 sm2 md2>
                                                <v-container>
                                                    <p id="wishlistPrice">{{wishlistPrice(product)}}</p>
                                                </v-container>
                                            </v-flex>

                                            <v-flex xs9 sm10 md2>
                                                <v-container>
                                                    <v-flex>
                                                        <v-btn
                                                            block
                                                            color="#F78D37"
                                                            elevation="0"
                                                            height="50"
                                                            @click.native="addItem(product)"
                                                            style="font-size: 15px; font-family: Arial; color: white; text-transform: none; font-weight: thin;"
                                                        >
                                                            Add to cart
                                                        </v-btn>
                                                    </v-flex>
                                                </v-container>
                                            </v-flex>

                                            <v-flex xs3 sm2 md1>
                                                <v-container>
                                                   <v-btn icon color="red" style="margin: auto;" @click="removeWishlistItem(product)">
                                                      <v-icon medium>{{ icons.removeIcon }}</v-icon>
                                                   </v-btn>
                                                   <p style="font-size: 12px;">Remove</p>
                                                </v-container>
                                            </v-flex>

                                            <v-container>
                                                <v-divider></v-divider>
                                            </v-container>
                                          </v-row>
                                       </v-container>
                                    </v-list-item>
                                 </div>
                              </v-list>
                           </div>
                        </v-sheet>
                     </v-flex>
                  </v-container>
               </v-container>
            </v-row>

      <!-- Page Footer-->
      <div style="bottom: 0; width: 100%; position: absolute;">
        <homeFooter/>
      </div>

  </v-app>
</template>

<script>
import header             from './Header.vue'
import footer             from './Footer.vue'
import wishlistApi        from './js/Wishlist-api'
import userApi            from './js/User-api'
import navBar             from './Navigation.vue'
import { eventBus }       from '../main'
import { mdiCartPlus }    from '@mdi/js';
import { mdiCloseCircle } from '@mdi/js';
import { mdiCardsHeart }  from '@mdi/js';

export default {

  data() {
    return {

        componentKey: 0,

        wishlist: [],

        emptyWishlistMessage: 'Your wishlist is empty.',

        emptyListCheck: true,

        icons: {
            addToCartIcon: mdiCartPlus,
            removeIcon: mdiCloseCircle,
            wishlistIcon: mdiCardsHeart,
        },
    }
  },

    components: {
      homeHeader : header,
      homeFooter : footer,
      navigation : navBar,
  },

  model: null,

    mounted() {
        this.getWishlistItems()

        eventBus.$on('wishlist-update', (data) => {
            if(data) {
                this.emptyListCheck = false
                this.getWishlistItems()
            }
        })
    },

  methods: {
     /* eslint-disable */

      getWishlistItems() {
         wishlistApi.getWishlist()
               .then(response => {
                  if(response.data.item0 != null) {
                     this.emptyListCheck = false
                     this.wishlist = response.data;
                  } else {
                     this.emptyListCheck = true
                  }
               })
               .catch(error => {
                  this.emptyListCheck = true
         })
      },

         getProductId(item) {
            return this.wishlist[item].productId
      },

      addItem(item){
         userApi.addToCart(this.wishlist[item].productId, 1)
               .then(response => {
                  eventBus.$emit('cart-update', true);
               })
         window.location.href = '/cart';
      },

      removeWishlistItem(item) {
         wishlistApi.removeWishlistItem(this.wishlist[item].productId)
               .then(response => {
                  eventBus.$emit('wishlist-update', true);
               })
      },

      removeAllItems() {
         wishlistApi.clearWishlist()
               .then(response => {
                  eventBus.$emit('wishlist-update', true);
                  this.emptyListCheck = true
                  this.wishlist = null;
               })
         },

      wishlistName(item) {
         return this.wishlist[item].productName
      },

      wishlistPrice(item) {
         return this.wishlist[item].price
      },

      getWishlistImage(item) {
         return this.wishlist[item].image
      },

      directProduct(item) {
         this.$router.push("/product/" + item);
      },

      // Scroll-to-top
      onScroll (e) {
         if (typeof window === 'undefined') return
         const top = window.pageYOffset ||   e.target.scrollTop || 0
         this.fab = top > 500
      },

      toTop () {
         this.$vuetify.goTo(0)
      },

      forceRerender() {
         this.componentKey += 1;
      }
   }
}
</script>

<!-- CSS -->
<style scoped src="./css/WishList.css">

</style>