<template>
   <v-app style="background: #EEEEEE;">
      <v-card style="margin: auto; background: #EEEEEE;" elevation="0">
         <v-flex md12>
            <v-icon size="100" color="#D8D8D8">{{serverAlertIcon}}</v-icon>
         </v-flex>
         <v-flex md12>
            <h1 style="font-size: 80px; font-family: 'Century Gothic';">OOPS!</h1>
         </v-flex>
         <v-container>
            <v-flex md12>
               <h3 style="font-size: 20px; font-family: 'Century Gothic';">{{errorType.error510}}</h3>
            </v-flex>
         </v-container>
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
import { mdiHome }             from '@mdi/js';
import { mdiServerNetworkOff } from '@mdi/js';

export default {
   data(){
      return {

         // Icon Display
         homeIcon:        mdiHome,
         serverAlertIcon: mdiServerNetworkOff,

         errorType: {
            error500: '500 - Our server is on a break.',
            error501: '501 - Our server either does not recognize the request method, or lacks the ability to fulfill your request. ',
            error502: '502 - Our server encountered a temporary error and couldn\'t complete your request.',
            error503: '503 - We\'re down for maintenance. We\'ll be back shortly.',
            error504: '504 - The site requested took too long to respond.',
            error505: '505 - We couldn\'t find the page that you\'ve requested.',
            error506: '506 - Our server is not properly configured. Contact the system administrator for assistance.',
            error507: '507 - Our server is out of free memory.',
            error509: '509 - The bandwidth limit imposed by the system administrator has been reached.',
            error510: '510 - The extension attached to the HTTP request is not supported by the web server.',
         }
      }
   }
}
</script>

<style scoped>

</style>