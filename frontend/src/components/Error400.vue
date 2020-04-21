<template>
   <v-app style="background: #EEEEEE;">
      <v-card style="margin: auto; background: #EEEEEE;" elevation="0">
         <v-flex md12>
            <v-icon size="100" color="#D8D8D8">{{alertIcon}}</v-icon>
         </v-flex>
         <v-flex md12>
            <h1 style="font-size: 80px; font-family: 'Century Gothic';">OOPS!</h1>
         </v-flex>
         <v-flex md12>
            <v-container>
               <h3 style="font-size: 20px; font-family: 'Century Gothic';">{{errorType.error404}}</h3>
            </v-container>
         </v-flex>
         <div>
            <v-container>
               <v-flex md12>
                  <h3 style="margin-bottom: 20px; font-size: 15px; font-family: 'Century Gothic';">
                     Refresh this page or contact the System Administrator if this problem persists.
                  </h3>
               </v-flex>
            </v-container>
         </div>
         <v-flex md12>
            <v-container>
               <v-btn color="#E77F2C" x-large elevation="0" style="color: white;" to="/">
                  <v-flex md2>
                     <v-icon>
                        {{homeIcon}}
                     </v-icon>
                  </v-flex>
                  <v-flex md10>
                     GO TO HOMEPAGE
                  </v-flex>
               </v-btn>
            </v-container>
         </v-flex>
      </v-card>
   </v-app>
</template>

<script>
import { mdiHome }  from '@mdi/js';
import { mdiAlert } from '@mdi/js';

export default {
   data(){
      return {

         // Icon Display
         homeIcon:  mdiHome,
         alertIcon: mdiAlert,

         errorType: {
            error400: '400 - We received a bad request.',
            error401: '401 - We cannot give you authorization to access this area. ',
            error403: '403 - For security reasons, we can\'t let you access this area.',
            error404: '404 - We couldn\'t find the page that you\'ve requested.',
         }
      }
   }
}
</script>

<style scoped>

</style>