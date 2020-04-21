<template>

   <!-- Categories navigation bar -->
   <v-card id="catNavContainer" elevation="0">

      <!-- Hamburger Bar -->
      <navHamburgerBar/>

      <v-row class="main-nav" id="js-menu" wrap justify-space-around>

         <!-- Eagles Gear category -->
         <v-flex md2>
            <v-menu open-on-hover offset-y transition="slide-y-transition">
               <template v-slot:activator="{ on }">
                  <v-btn
                     class="navCatBtn"
                     elevation="0"
                     alt="Eagles Gear"
                     @click="directItems(32, 33)"
                     v-on="on"
                  >
                     EAGLES GEAR
                  </v-btn>
               </template>
               <v-list style="border-radius: 0px !important;">
                  <v-list-item
                     v-for="(item, index) in eaglesClothing"
                     :key="index"
                     @click= "getResults(item.cat)"
                     class="listItemTitle"
                  >
                     <v-list-item-title class="listItemTitle">
                        {{ item.cat }}
                     </v-list-item-title>
                  </v-list-item>
               </v-list>
            </v-menu>
         </v-flex>

         <!-- Snacks & Drinks category -->
         <v-flex md2>
            <template>
               <v-menu open-on-hover offset-y transition="slide-y-transition">
                  <template v-slot:activator="{ on }">
                     <v-btn
                        class="navCatBtn"
                        alt="Snacks & Drinks"
                        @click="directItems(11, 17)"
                        elevation="0"
                        v-on="on"
                     >
                        SNACKS & DRINKS
                     </v-btn>
                  </template>
                  <v-list>
                     <v-list-item
                        v-for="(item, index) in snacksDrinks"
                        :key="index"
                        @click= "getResults(item.cat)"
                        class="listItemTitle"
                     >
                        <v-list-item-title class="listItemTitle">
                           {{ item.cat }}
                        </v-list-item-title>
                     </v-list-item>
                  </v-list>
               </v-menu>
            </template>
         </v-flex>

         <!-- Beauty & Personal Care category -->
         <v-flex md2>
            <template>
               <v-menu open-on-hover offset-y transition="slide-y-transition">
                  <template v-slot:activator="{ on }">
                     <v-btn
                        class="navCatBtn"
                        alt="Beauty & Personal Care"
                        @click="directItems(18, 25)"
                        elevation="0"
                        v-on="on"
                     >
                        BEAUTY & PERSONAL CARE
                     </v-btn>
                  </template>
                  <v-list>
                     <v-list-item
                        v-for="(item, index) in beautyPersonalCare"
                        :key="index"
                        @click= "getResults(item.cat)"
                        class="listItemTitle"
                     >
                        <v-list-item-title class="listItemTitle">
                              {{ item.cat }}
                        </v-list-item-title>
                     </v-list-item>
                  </v-list>
               </v-menu>
            </template>
         </v-flex>

         <!-- School Supplies category -->
         <v-flex md2>
            <template>
               <v-menu open-on-hover offset-y transition="slide-y-transition">
                  <template v-slot:activator="{ on }">
                     <v-btn
                        class="navCatBtn"
                        elevation="0"
                        alt="School Supplies"
                        @click="directItems(1, 6)"
                        v-on="on"
                     >
                        SCHOOL SUPPLIES
                     </v-btn>
                  </template>
                  <v-list>
                     <v-list-item
                        v-for="(item, index) in schoolSupplies"
                        :key="index"
                        @click= "getResults(item.cat)"
                        class="listItemTitle"
                     >
                        <v-list-item-title class="listItemTitle">
                           {{ item.cat }}
                        </v-list-item-title>
                     </v-list-item>
                  </v-list>
               </v-menu>
            </template>
         </v-flex>

         <!-- Household Items category -->
         <v-flex md2>
            <template>
               <v-menu open-on-hover offset-y transition="slide-y-transition">
                  <template v-slot:activator="{ on }">
                     <v-btn
                        class="navCatBtn"
                        elevation="0"
                        alt="Household Essentials"
                        @click="directItems(28, 31)"
                        v-on="on"
                     >
                        HOUSEHOLD ITEMS
                     </v-btn>
                  </template>
                  <v-list>
                     <v-list-item
                        v-for="(item, index) in householdItems"
                        :key="index"
                        @click= "getResults(item.cat)"
                        class="listItemTitle"
                     >
                        <v-list-item-title class="listItemTitle">
                              {{ item.cat }}
                        </v-list-item-title>
                     </v-list-item>
                  </v-list>
               </v-menu>
            </template>
         </v-flex>

         <!-- Books category -->
         <v-flex md2>
            <template>
               <v-menu open-on-hover offset-y transition="slide-y-transition">
                  <template v-slot:activator="{ on }">
                     <v-btn
                        class="navCatBtn"
                        elevation="0"
                        alt="Books"
                        @click="directItems(7, 10)"
                        v-on="on"

                     >
                        BOOKS
                     </v-btn>
                  </template>
                  <v-list>
                     <v-list-item
                        v-for="(item, index) in books"
                        :key="index"
                        @click= "getResults(item.cat)"
                        class="listItemTitle"
                     >
                        <v-list-item-title class="listItemTitle">
                           {{ item.cat }}
                        </v-list-item-title>
                     </v-list-item>
                  </v-list>
               </v-menu>
            </template>
         </v-flex>
      </v-row>
   </v-card>
</template>

<!-- JavaScript -->
<script>
import navHamburger from './Hamburger.vue'

export default {

   data() {
      return {

      // Category navigation menu items
      snacksDrinks: [
         { cat: 'Candy' },
         { cat: 'Chips' },
         { cat: 'Drinks' },
      ],

      beautyPersonalCare: [
         { cat: 'Hygiene' },
         { cat: 'Hair Products' },
         { cat: 'Cosmetics' },
         { cat: 'Medicine' },
         ],

      schoolSupplies: [
         { cat: 'Office Supplies' },
         { cat: 'Notebooks' },
         { cat: 'Art' },
         ],

      householdItems: [
         { cat: 'Cleaners' },
         { cat: 'Toiletries' },
         { cat: 'Electronics' },
         ],

      books: [
         { cat: 'Children' },
         { cat: 'Bibles' },
         { cat: 'Biography' },
         { cat: 'Other Genres' },
         ],

      eaglesClothing: [
         { cat: 'Hats' },
         { cat: 'Shirts' },
         { cat: 'Hoodies' },
         { cat: 'Accessories' },
         ]
      };
   },

   // Imported components
   components:{
      navHamburgerBar: navHamburger,
   },

   // Methods
   methods:{
      /* eslint-disable */

      // Get product results and scroll to top.
      getResults(item){
         this.$router.push("/results/" + item );
         window.location.reload();
         window.scrollTo(500, 0);
      },

      // Get product results and scroll to top.
      directItems(firstCategory, lastCategory) {
         this.$router.push("/results/" + firstCategory + "/" + lastCategory);
         window.location.reload();
         window.scrollTo(500, 0);
      },
   }
}
</script>

<!-- CSS -->
<style scoped src="./css/HomeStyles.css">

</style>