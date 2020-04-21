<template>
   <v-footer
      dark
      padless
   >
      <v-card id="footerCard" flat tile>
         <v-container>
            <v-row justify-space-around>
               <v-flex xs12 sm12 md4 lg4 xl4>
                  <v-card-text id="locationInfo" class="white--text pt-0">
                     <v-flex xs12 sm12 md12 lg12>
                        <v-container>
                           <h1>
                              <v-icon large color="white">
                                 {{ locationIcon }}
                              </v-icon>
                              FIND US
                           </h1>
                        </v-container>
                     </v-flex>
                     <address id="addressStyles">
                        <a href="https://www.google.com/maps/place/250+Brent+Ln,+Pensacola,+FL+32503/@30.4738685,-87.2345347,17z/data=!4m5!3m4!1s0x8890bfed2cbea7af:0x2b86c8bf56a995c4!8m2!3d30.4757456!4d-87.2340948?shorturl=1"
                           target="_blank">
                           250 Brent Lane
                        <br>Pensacola, FL 32503
                        </a>
                        <br>
                        <a href="tel:18007223570">
                           1-800-722-3570
                        </a>
                     </address>
                  </v-card-text>
               </v-flex>

               <v-flex xs12 sm12 md4 lg4 xl4>
                  <v-card-text id="servicesInfo" class="white--text pt-0">
                     <v-flex xs12 sm12 md12 lg12>
                        <v-container>
                           <h1>
                              <v-icon large color="white">
                                 {{ serviceIcon }}
                              </v-icon>
                              SERVICES
                           </h1>
                        </v-container>
                     </v-flex>
                     <p><a href="/about">Contact Us</a>
                        <br>
                        <a href="/about">
                           Ordering & Payment
                        </a>
                        <br>
                        <a href="/about">
                           Store Hours
                        </a>
                     </p>
                  </v-card-text>
               </v-flex>

               <v-flex xs12 sm12 md4 lg4 xl4>
                  <v-card-text id="info1" class="white--text pt-0">
                     <v-flex xs12 sm12 md12 lg12>
                        <v-container>
                           <h1>
                              <v-icon large color="white">
                                 {{ shareIcon }}
                              </v-icon>
                              FOLLOW US
                           </h1>
                        </v-container>
                     </v-flex>
                     <v-btn
                        style="width: 55px; height: 55px;"
                        class="mx-6 white--text"
                        icon
                     >
                        <v-icon size="25px">
                           {{ facebookIcon }}
                        </v-icon>
                     </v-btn>
                     <v-btn
                        style="width: 55px; height: 55px;"
                        class="mx-6 white--text"
                        icon
                     >
                        <v-icon size="25px">
                           {{ instagramIcon }}
                        </v-icon>
                     </v-btn>
                     <v-btn
                        style="width: 55px; height: 55px;"
                        class="mx-6 white--text"
                        icon
                     >
                        <v-icon size="25px">
                           {{ twitterIcon }}
                        </v-icon>
                     </v-btn>
                  </v-card-text>
               </v-flex>
            </v-row>
         </v-container>

         <v-divider></v-divider>

         <v-card-text class="white--text">
            <v-icon color="white" size="20">
               {{copyrightIcon}}
            </v-icon>
            <span style="font-size: 15px;">
               NestExpress
            </span>
         </v-card-text>
      </v-card>
   </v-footer>
</template>

<!-- JavaScript -->
<script>
import { mdiFacebookBox }  from '@mdi/js';
import { mdiInstagram }    from '@mdi/js';
import { mdiTwitter }      from '@mdi/js';
import { mdiClockOutline } from '@mdi/js';
import { mdiMapMarker }    from '@mdi/js';
import { mdiThumbUp }      from '@mdi/js';
import { mdiHandHeart }    from '@mdi/js';
import { mdiCopyright }    from '@mdi/js';

export default {

   data(){
      return {

         // Footer text
         footerText: 'NestExpress',

         // Footer Icons
         facebookIcon: mdiFacebookBox,
         instagramIcon: mdiInstagram,
         twitterIcon: mdiTwitter,
         clockIcon: mdiClockOutline,
         locationIcon: mdiMapMarker,
         shareIcon: mdiThumbUp,
         serviceIcon: mdiHandHeart,
         copyrightIcon: mdiCopyright,
      }
   }
}
</script>

<!-- CSS -->
<style scoped src="./css/FooterStyles.css">

</style>