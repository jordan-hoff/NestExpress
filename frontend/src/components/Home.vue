<template>
   <v-app>

      <!-- Page Header -->
      <homeHeader/>

      <!-- Categories navigation bar -->
      <navigation/>

      <!-- Carousel -->
      <v-row>
         <v-flex xs12 sm12 md12 lg12 xl12>
            <v-carousel
               id="carousel"
               cycle
               height="600"
               hide-delimiter-background
               show-arrows-on-hover
               progress-color="orange"
            >
               <v-carousel-item
                  src="./ads/Nest_Express_WG.png"
                  @click="directItems(28, 31)"
                  id="advertisementSlides"
               >
               </v-carousel-item>

               <v-carousel-item
                  src="./ads/Nest_Express_GRAD.png"
                  id="advertisementSlides"
               >
               </v-carousel-item>

               <v-carousel-item
                  src="./ads/Nest_Express_EAGLES.png"
                  @click="directItems(32, 33)"
                  id="advertisementSlides"
               >
               </v-carousel-item>

               <v-carousel-item
                  src="./ads/Nest_Express_ARIZONA.png"
                  @click="directItems(16,16)"
                  id="advertisementSlides"
               >
               </v-carousel-item>
            </v-carousel>
         </v-flex>
      </v-row>

      <!-- Featured Items Slider -->
      <v-flex xs12 sm12 md12 lg12 xl12>
         <div id="homeItems">
            <v-flex xs12 sm12 md12 lg12 xl12>
               <h1 class="productsHeading" style="margin-top: 40px;">
                  FEATURED ITEMS
               </h1>
                  <v-sheet v-if="returnedFeatured" class="mx-auto" elevation="0" max-width="1550">
                     <v-slide-group
                        v-model="model"
                        style="background: #EEEEEE;"
                        class="pa-4"
                        active-class="success"
                        prev-icon="mdi-arrow-left"
                        next-icon="mdi-arrow-right"
                        show-arrows
                        center-active
                     >
                        <v-slide-item
                           v-for= "(id, product) in this.featuredItems" :key= "id"
                           v-slot:default= "{ active }"
                        >
                           <v-card
                              id="featuredItemCard"
                              :color= "active ? undefined : 'white'"
                              class="ma-4"
                              max-width="250"
                              max-height="375"
                              @click="directFeaturedItem(product)"
                              hover
                           >
                              <v-container>
                                 <v-img
                                    class="white--text align-end"
                                    height="auto"
                                    width="auto"
                                    :src="featuredImage(product)"
                                 >
                                 </v-img>
                                 <h1 id="featuredItemName">
                                    {{featuredName(product)}}
                                 </h1>
                                 <v-card-actions>
                                    <v-container>
                                       <h2 id="featuredPrice">
                                          ${{featuredPrice(product)}}
                                       </h2>
                                    </v-container>
                                 </v-card-actions>
                              </v-container>
                              <v-row class="fill-height" align="center" justify="center">
                                 <v-scale-transition>
                                    <v-icon
                                       v-if="active"
                                       color="white"
                                       size="48"
                                       v-text="'mdi-close-circle-outline'"
                                    >
                                    </v-icon>
                                 </v-scale-transition>
                              </v-row>
                           </v-card>
                        </v-slide-item>
                     </v-slide-group>
                  </v-sheet>
            </v-flex>

            <!-- Recommended Eagles Gear Slider -->
            <v-flex xs12 sm12 md12 lg12 xl12>
               <h1 class="productsHeading" style="margin-top: 40px;">
                  RECOMMENDED EAGLES GEAR
               </h1>
               <v-sheet v-if="returnedEagleGear" class="mx-auto" max-width="1550">
                  <v-slide-group
                     v-model="model"
                     class="pa-4"
                     style="background: #EEEEEE;"
                     active-class="success"
                     prev-icon="mdi-arrow-left"
                     next-icon="mdi-arrow-right"
                     show-arrows
                     center-active
                  >
                     <v-slide-item v-for="(id, product) in this.eagleGearItems" :key="id" v-slot:default="{ active }">
                        <v-card
                           id="featuredItemCard"
                           :color="active ? undefined : 'white'"
                           class="ma-4"
                           max-width="250"
                           max-height="375"
                           @click="directEagleItem(product)"
                           hover
                        >
                           <v-container>
                              <v-img
                                 class="white--text align-end"
                                 height="auto"
                                 width="auto"
                                 :src="eagleGearImage(product)">
                              </v-img>
                           </v-container>
                           <h1 id="featuredItemName">{{eagleGearName(product)}}</h1>
                           <v-card-actions>
                              <v-container>
                                 <h2 id="eagleGearPrice">
                                    ${{eagleGearPrice(product)}}
                                 </h2>
                              </v-container>
                           </v-card-actions>
                           <v-row class="fill-height" align="center" justify="center">
                              <v-scale-transition>
                                 <v-icon
                                    v-if="active"
                                    color="white"
                                    size="48"
                                    v-text="'mdi-close-circle-outline'"
                                 >
                                 </v-icon>
                              </v-scale-transition>
                           </v-row>
                        </v-card>
                     </v-slide-item>
                  </v-slide-group>
               </v-sheet>
            </v-flex>
         </div>
      </v-flex>

      <!-- Shop by Category -->
      <v-row wrap justify-space-around>
         <v-flex xs12 sm12 md12 lg12 xl12 style="margin-bottom: 20px">
            <h1 id="shopCategory">SHOP BY CATEGORY</h1>
            <v-btn
               :ripple="false"
               class="categoryShopping"
               alt="Eagles Gear"
               icon
               @click="directItems(32, 33)"
               style="background-image: linear-gradient(rgba(12, 16, 33, 0.7), rgba(12, 16, 33, 0.7)), url(https://images-na.ssl-images-amazon.com/images/I/41mYZDE4N-L.jpg);"
            >
               Eagles Gear
            </v-btn>

            <v-btn
               :ripple="false"
               class="categoryShopping"
               alt="Snacks & Drinks"
               icon
               @click="directItems(11, 17)"
               style="background-image: linear-gradient(rgba(12, 16, 33, 0.7), rgba(12, 16, 33, 0.7)), url(https://webstockreview.net/images/drinking-clipart-drink-snack-4.jpg);"
            >
               Snacks &<br>Drinks
            </v-btn>

            <v-btn
               :ripple="false"
               class="categoryShopping"
               alt="Beauty & Personal Care"
               @click="directItems(18, 25)"
               icon style="background-image: linear-gradient(rgba(12, 16, 33, 0.7), rgba(12, 16, 33, 0.7)), url(https://images.fastcompany.net/image/upload/w_596,c_limit,q_auto:best,f_auto/fc/3039095-inline-i-2-deodorant-innovations-uni.jpg);"
            >
               Beauty &<br>Personal Care
            </v-btn>

            <v-btn
               :ripple="false"
               class="categoryShopping"
               alt="School Supplies"
               icon
               @click="directItems(1, 6)"
               style="background-image: linear-gradient(rgba(12, 16, 33, 0.7), rgba(12, 16, 33, 0.7)), url(https://cdn.kinsights.com/cache/e2/2c/e22c7fe6a56dbfcc9c5f0f60706b2eed.jpg);"
            >
               School<br>Supplies
            </v-btn>

            <v-btn
               :ripple="false"
               class="categoryShopping"
               alt="Household Essentials"
               icon
               @click="directItems(28, 31)"
               style="background-image: linear-gradient(rgba(12, 16, 33, 0.7), rgba(12, 16, 33, 0.7)), url(https://static9.depositphotos.com/1000619/1179/i/450/depositphotos_11799795-stock-photo-cleaning-supplies.jpg);"
            >
               Household<br>Essentials
            </v-btn>

            <v-btn
               :ripple="false"
               class="categoryShopping"
               alt="Books"
               icon
               @click="directItems(7, 10)"
               style="background-image: linear-gradient(rgba(12, 16, 33, 0.7), rgba(12, 16, 33, 0.7)), url(https://img1.cgtrader.com/items/1961398/95a7b680f4/stack-of-books-3d-model-low-poly-obj-mtl-3ds-fbx-stl-blend-dae.jpg);"
            >
               Books
            </v-btn>
        </v-flex>
      </v-row>

      <!-- 'To-top' scroll button -->
      <scrollToTop/>

      <!-- Page Footer-->
      <homeFooter/>

   </v-app>
</template>

<!-- JavaScript -->
<script>
import header     from './Header.vue'
import footer     from './Footer.vue'
import navBar     from './Navigation.vue'
import toTop      from './ScrollToTop.vue'
import homeApi    from './js/Home-api'

export default {

   data() {
      return {

         // Home product sliders check
         returnedEagleGear: false,
         returnedFeatured: false,

         // Scroll-to-top check
         fab: false,

         // Carousel items
         items: [
            { title: 'Item 1' },
            { title: 'Item 2' },
            { title: 'Item 3' },
            { title: 'Item 4' },
         ],
      };
   },

   // Imported components
   components: {
      homeHeader  : header,
      homeFooter  : footer,
      navigation  : navBar,
      scrollToTop : toTop
   },

   model: null,

   featuredItems: [],
   eagleGearItems: [],
   specialItems: [],

   // Execute methods when components are created in the DOM
   created() {
      homeApi.getFeaturedItems(15)
      .then(response => {
         this.featuredItems    = response.data;
         this.returnedFeatured = true;
      }),

      homeApi.getEagleGear(15)
      .then(response => {
         this.eagleGearItems    = response.data;
         this.returnedEagleGear = true;
      })
   },

   // Methods
   methods: {
      /* eslint-disable */

      // Featured product details
      featuredName(item) {
         return this.featuredItems[item].productName;
      },

      featuredPrice(item) {
         return this.featuredItems[item].price;
      },

      featuredImage(item) {
         return this.featuredItems[item].image;
      },

      featuredProductId(item) {
         return this.featuredItems[item].productId;
      },

      // Eagles gear product details
      eagleGearName(item) {
         return this.eagleGearItems[item].productName;
      },

      eagleGearPrice(item) {
         return this.eagleGearItems[item].price;
      },

      eagleGearImage(item) {
         return this.eagleGearItems[item].image;
      },

      eagleProductId(item) {
         return this.eagleGearItems[item].productId;
      },

      // Route to the result page upon selecting a carousel item or product category.
      directItems(firstCategory, lastCategory) {
         this.$router.push("/results/" + firstCategory + "/" + lastCategory);
         window.scrollTo(500, 0);
      },

      // Route to the product page upon selecting a product from a slider.
      directFeaturedItem(item) {
         this.$router.push("/product/" + this.featuredItems[item].productId);
         window.scrollTo(500, 0);
      },

      directEagleItem(item) {
         this.$router.push("/product/" + this.eagleGearItems[item].productId);
         window.scrollTo(500, 0);
      },
   }
}
</script>

<!-- CSS -->
<style scoped src="./css/HomeStyles.css">

</style>