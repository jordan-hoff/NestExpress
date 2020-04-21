<template>
   <span class="navbar-toggle" id="js-navbar-toggle">
      <v-icon large color="white" @click="showNav()">
         {{burger}}
      </v-icon>

      <!-- Category Navigation Bar -->
      <v-row class="main-nav" id="js-menu" wrap >

         <!-- Eagles Gear menu -->
         <v-flex md3 >
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
            </v-menu>
         </v-flex>

         <!-- Snacks & Drinks menu -->
         <v-flex md3>
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
               </v-menu>
            </template>
         </v-flex>

         <!-- Beauty & Personal Care menu -->
         <v-flex md3>
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
               </v-menu>
            </template>
         </v-flex>

         <!-- School Supplies menu -->
         <v-flex md3>
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
               </v-menu>
            </template>
         </v-flex>

         <!-- Household Items menu -->
         <v-flex md3>
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
               </v-menu>
            </template>
         </v-flex>

         <!-- Books menu -->
         <v-flex md3>
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
               </v-menu>
            </template>
         </v-flex>
      </v-row>
   </span>
</template>

<!-- JavaScript -->
<script>
import { mdiMenu } from '@mdi/js';

export default {
   data() {
      return {

         // Menu icon
         burger: mdiMenu,
      }
   },

   // Methods
   methods:{
      /* eslint-disable */

      showNav(){
         let mainNav      = document.getElementById('js-menu');
         let navBarToggle = document.getElementById('js-navbar-toggle');

         navBarToggle.addEventListener('click', function () {
            mainNav.classList.toggle('active');
         })
      },

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