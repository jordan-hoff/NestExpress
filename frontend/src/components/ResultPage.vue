<template>
   <v-app>

   <!-- Page Header -->
   <homeHeader/>

   <!-- Category navigation bar -->
   <navigation/>

   <v-container id="resultContainer">
      <v-layout>
         <v-row>
            <div id="DisplayItems"  v-for="product in displayedPosts">
               <v-card
                  :color= "active ? undefined : 'white'"
                  class="ma-4"
                  id="ProductItemCard"
                  width="400"
                  height="540"
                  hover>
                  <v-container style="max-height:400px;" v-on:click="directProduct(product.productId)">
                     <v-img
                           class="white--text align-end medium"
                           :src="product.productImage">
                     </v-img>
                  </v-container>
                  <v-container id="infoSection" v-on:click="directProduct(product.productId)">
                     <h1 id="ProductItemName">{{product.productName}}</h1>
                     <v-card-actions>
                        <div>
                           <h2 id="Price">
                              ${{product.productPrice}}
                           </h2>
                        </div>
                     </v-card-actions>
                  </v-container>
                     <v-hover v-slot:default="{ hover }" open-delay="300">
                        <v-tooltip right color="rgba(0, 0, 0)">
                           <template v-slot:activator="{ on }">
                              <v-btn
                                 id="btnAddToCartIcon"
                                          type="button"
                                          dark v-on="on"
                                          hover
                                          class="transition-swing v-btn v-btn--absolute v-btn--contained
                                                   v-btn--fab v-btn--right v-btn--round v-btn--top v-size--large orange"
                                          @click="addItem(product.productId)"
                                        >
                                            <v-icon color="white">
                                                {{cartIcon}}
                                            </v-icon>
                              </v-btn>
                           </template>
                                    <span style="color:white">Add to Cart</span>
                        </v-tooltip>
                     </v-hover>
               </v-card>
            </div>
         </v-row>
            <template v-slot:placeholder>
               <v-row v-show="!hasNoItems" class="fill-height ma-0" align="center" justify="center"></v-row>
                  <v-row>
                     <div v-show="hasNoItems">
                        <v-flex md12 >
                           <v-icon size="150" style="color: #F7882F; position: absolute; right: 50%; left: 47%; bottom: 50%; top: 27%;">
                              {{ noResultIcon }}
                           </v-icon>
                        </v-flex>
                        <v-flex md12>
                           <h2 id="resultMessage">
                              {{ resultsMessage() }}
                           </h2>
                           <h4 id="resultCheckMessage">
                              Try checking your spelling or use more general terms.
                           </h4>
                        </v-flex>
                     </div>
                  </v-row>
               </template>
            </v-layout>

            <!-- Pagination -->
            <nav>
               <v-flex md12 :class="`d-flex justify-center mb-6`">
                  <ul class="pagination">

                     <li class="page-item">
                        <v-btn type="button"
                               class="page-link"
                               v-if="page != 1"
                               @click= "page--; toTop()">
                           Previous
                        </v-btn>
                     </li>

                     <li class="page-item">
                        <v-btn type="button"
                               class="page-link"
                               v-for= "pageNumber in pages.slice(page-1, page+5)"
                               @click= "page = pageNumber; toTop()">
                           {{pageNumber}}
                        </v-btn>
                     </li>

                     <li class="page-item">
                        <v-btn type="button"
                               @click= "page++; toTop()"
                               v-if= "page < pages.length"
                               class="page-link"
                               v-scroll="onScroll">
                           Next
                        </v-btn>
                     </li>
                  </ul>
               </v-flex>
            </nav>

            <!-- Add to notifications -->
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

        <!-- 'To-top' scroll button -->
      <scrollToTop />

   </v-container>

   <!-- Page Footer -->
   <homeFooter/>

   </v-app>
</template>

<script>
import { eventBus }        from '../main'
import { mdiMagnifyClose } from '@mdi/js';
import { mdiCartPlus }     from '@mdi/js';
import toTop               from './ScrollToTop.vue'
import header              from './Header.vue'
import footer              from './Footer.vue'
import resultsApi          from './js/Results-api'
import addItemApi          from './js/User-api'
import navBar              from './Navigation.vue'

export default {
   data() {
      return {
         cartIcon: mdiCartPlus,
         fab: false,
         addToCart: {
            snackbar: false,
            y: 'top',
            x: null,
            mode: '',
            timeout: 1500,
            text: 'Item added to Cart',
         },
         posts : [],
         page: 1,
         perPage: 12,
         pages: [],
         noResultIcon: mdiMagnifyClose,
         hasNoItems: false,
      };
   },

   productItems: [],
   cartItems:[],

   created() {
      if (isNaN(this.$route.params.product) && this.$route.params.product != null) {
         resultsApi.getKeyword(this.$route.params.product)
               .then(response => {
                  this.productItems = response.data
                  this.hasNoItems = false

                  for(let i = 0; i < parseInt(response.data.listLength.listLength, 10); i++) {
                     var item = "item"+ i
                     this.posts.push({
                        productName: this.productItems[item].productName,
                        productPrice: this.productItems[item].price,
                        productImage: this.productItems[item].image,
                        productId: this.productItems[item].productId
                     })
                  }
               })
               .catch(error => {
                  console.log(error)
                  this.hasNoItems = true
               })

      } else {
         resultsApi.getProducts(this.$route.params.firstRange, this.$route.params.lastRange)
               .then(response => {
                  this.productItems = response.data
                  this.hasNoItems = false

                  for(let i = 0; i < parseInt(response.data.listLength.listLength, 10); i++) {
                     var item = "item"+ i
                     this.posts.push({
                        productName: this.productItems[item].productName,
                        productPrice: this.productItems[item].price,
                        productImage: this.productItems[item].image,
                        productId: this.productItems[item].productId
                     })
                  }
                  this.hasNoItems = false
               })
               .catch(error => {
                  console.log(error)
                  this.hasNoItems = true
               })
         }

   },

   methods:{
       // Scrolls to the top after each pageination.
      toTop () {
        this.$vuetify.goTo(0)
      },

      // Add an item to cart.
      addItem(productId){
         addItemApi.addToCart(productId, 1)
            .then(response => {
               eventBus.$emit("cart-update", true)
               this.addToCart.snackbar = true;
            })
      },

      productName(itemIndex) {
         console.log(itemIndex)
         var item = "item"+ itemIndex
            return this.productItems[item].productName
      },

      productPrice(itemIndex) {
         var item = "item"+ itemIndex
         return this.productItems[item].price
      },

      productImage(itemIndex) {
         var item = "item"+ itemIndex
         return this.productItems[item].image
      },

      setPages() {
         let numberOfPages = Math.ceil(this.posts.length / this.perPage);
         for (let index = 1; index <= numberOfPages; index++) {
               this.pages.push(index);
         }
      },

      paginate(posts) {
         let page    = this.page;
         let perPage = this.perPage;
         let from    = (page * perPage) - perPage;
         let to      = (page * perPage);
         return  posts.slice(from, to);
      },

      // Route to the product page.
      directProduct(item) {
         this.$router.push("/product/" + item);
      },

      // Give an error message if product name isn't available.
      resultsMessage() {
         return 'We couldn\'t find anything matching \'' + this.$route.params.product + '.'
      },
   },

      computed: {
         displayedPosts() {
            return this.paginate(this.posts);
         }
      },

      watch: {
         posts () {
            this.setPages();
         }
      },

      filters: {
         trimWords(value){
            return value.split(" ").splice(0,20).join(" ") + '...';
         }
    },

    components: {
      homeHeader : header,
      homeFooter : footer,
      navigation : navBar,
      scrollToTop: toTop,
    }
}
</script>

<!-- CSS -->
<style scoped src="./css/ResultPage.css">

</style>
