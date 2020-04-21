<template>
   <v-app>
      <!-- Page header -->
      <appheader/>

      <!-- Category navigation bar -->
      <navigation/>

      <v-container>
         <h1 id="aboutHeading" style="margin-top: 50px;">ABOUT CAMPUS STORE</h1>
         <v-divider id="divAboutCS" color="orange"></v-divider>
      </v-container>

      <v-container>
         <v-row>
            <v-flex xs4 sm4 md4>
               <v-container>
                  <div @click="activateImageDialog">
                     <v-img
                        class="grey lighten-2"
                        id="campusStoreImage"
                        max-height="400" max-width="800"
                        height="auto"
                        width="auto"
                        src="https://drive.google.com/uc?id=17fmZqbb_MCG6bqd6svAjCFaxSVbfzs2q"
                     >
                        <template v-slot:placeholder>
                           <v-row class="fill-height ma-0" align="center" justify="center">
                              <v-progress-circular indeterminate color="grey lighten-5"></v-progress-circular>
                           </v-row>
                        </template>
                     </v-img>
                  </div>
               </v-container>
            </v-flex>
            <v-flex xs4 sm4 md4>
               <v-container>
                  <div @click="activateImageDialog2">
                     <v-img
                        class="grey lighten-2"
                        id="campusStoreImage"
                        max-height="400" max-width="800"
                        height="auto"
                        width="auto"
                        src="https://drive.google.com/uc?id=1LdiihleDV6JTZSr06sxTVBlQdl_2Es_Z"
                     >
                        <template v-slot:placeholder>
                           <v-row class="fill-height ma-0" align="center" justify="center">
                              <v-progress-circular indeterminate color="grey lighten-5"></v-progress-circular>
                           </v-row>
                        </template>
                     </v-img>
                  </div>
               </v-container>
            </v-flex>
            <v-flex xs4 sm4 md4>
               <v-container>
                  <div @click="activateImageDialog3">
                     <v-img
                        class="grey lighten-2"
                        id="campusStoreImage"
                        max-height="400" max-width="800"
                        height="auto"
                        width="auto"
                        src="https://drive.google.com/uc?id=1z97fOHga7S2o5tU75R0Ijlvit4bqK2ZU">
                        <template v-slot:placeholder>
                           <v-row class="fill-height ma-0" align="center" justify="center">
                              <v-progress-circular indeterminate color="grey lighten-5"></v-progress-circular>
                           </v-row>
                        </template>
                     </v-img>
                  </div>
               </v-container>
            </v-flex>
         </v-row>
      </v-container>

      <v-container>
         <p id="aboutDescription">
               The Campus Store is an on-campus retail center serving the students,
               faculty, and staff of Pensacola Christian College with pride since 1974.
               Our primary goal is to provide a convenient destination that offers
               course materials, academic supplies, and everyday campus life essentials.
               We also offer an impressive line of Christian literature and gifts, as
               well as an exciting range of PCC imprinted items.
         </p>
         <p id="aboutDescription" style="padding-bottom: 50px;">
               When visiting PCC or the Pensacola area, we invite you to come in and have
               a look around. We are located in the student commons—in the heart of the campus.
         </p>
      </v-container>

      <v-container>
         <v-divider style="margin: auto;" width="500"></v-divider>
      </v-container>

      <v-row style="margin-bottom: 50px; margin-top: 20px;">
         <v-flex xs12 sm12 md12>
            <v-container>
               <v-row>
                  <v-flex md12>
                     <v-icon size="60" color="black">
                        {{icons.clockIcon}}
                     </v-icon>

                     <h2 color="black" id="aboutHeading">
                        CAMPUS STORE HOURS
                     </h2>
                  </v-flex>
               </v-row>
            </v-container>
            <v-container>
               <v-layout fluid justify-center>
                  <p id="storeDays">
                     Monday<br>
                     Tuesday<br>
                     Wednesday<br>
                     <br>Thursday<br>
                     Friday<br>
                     Saturday<br>
                     Sunday<br>
                  </p>

                  <p id="storeHours">
                     7:30am - 10:00pm<br>
                     7:30am - 10:00pm<br>
                     7:30am - 6:30pm <br>
                     8:00pm - 10:00pm<br>
                     7:30am - 10:00pm<br>
                     7:30am - 11:00pm<br>
                     9:00am - 11:00pm<br>
                     7:00pm - 10:00pm<br>
                  </p>
               </v-layout>
            </v-container>
         </v-flex>
      </v-row>

      <v-sheet id="storeInfo">
         <v-row>
               <v-flex xs12 sm12 md4>
                  <v-container>
                     <v-row>
                           <v-flex md12>
                              <v-icon size="40" color="white">
                                 {{icons.callIcon}}
                              </v-icon>

                              <h2 id="storeHeading">
                                 CALL US
                              </h2>
                           </v-flex>
                     </v-row>
                  </v-container>
                  <v-container>
                     <v-layout fluid justify-center>
                           <address id="addressStyles">
                              <v-flex md12>
                                 <a id="addressStyles" href="tel:18007223570">1-800-722-3570</a>
                              </v-flex>
                           </address>
                     </v-layout>
                  </v-container>
               </v-flex>

               <v-flex xs12 sm12 md4>
                  <v-container>
                     <v-row>
                           <v-flex md12>
                              <v-icon size="40" color="white">
                                 {{icons.emailIcon}}
                              </v-icon>

                              <h2 id="storeHeading">
                                 EMAIL US
                              </h2>
                           </v-flex>
                     </v-row>
                  </v-container>
                  <v-container>
                     <v-layout fluid justify-center>
                           <address id="addressStyles">
                              <v-flex md12>
                                 <a id="addressStyles" href="mailto:cstore@pcci.edu">cstore@pcci.edu</a>
                              </v-flex>
                           </address>
                     </v-layout>
                  </v-container>
               </v-flex>

               <v-flex xs12 sm12 md4>
                  <v-flex md12>
                     <v-container>
                           <v-icon size="40" color="white">
                              {{icons.locationIcon}}
                           </v-icon>
                           <h2 id="storeHeading">
                              FIND US
                           </h2>
                     </v-container>
                  </v-flex>
                  <v-container>
                     <v-layout fluid justify-center>
                           <address id="addressStyles">
                              <a href="https://www.google.com/maps/place/250+Brent+Ln,+Pensacola,+FL+32503/@30.4738685,-87.2345347,17z/data=!4m5!3m4!1s0x8890bfed2cbea7af:0x2b86c8bf56a995c4!8m2!3d30.4757456!4d-87.2340948?shorturl=1"
                                 target="_blank">
                              <v-flex md12>
                                 250 Brent Lane
                              </v-flex>
                              <v-flex md12>
                                 Pensacola, FL 32503
                              </v-flex>
                              </a>
                           </address>
                     </v-layout>
                  </v-container>
               </v-flex>
         </v-row>
      </v-sheet>

      <v-sheet id="aboutNestExpressSheet">
         <v-container>
               <h1 id="aboutHeading" style="margin-top: 50px;">ABOUT NEST EXPRESS</h1>
               <v-divider id="divAboutNE" color="orange"></v-divider>
         </v-container>

         <v-container>
         <v-row>
            <v-flex xs4 sm4 md4>
               <v-container>
                  <a href="/">
                     <v-img
                        class="grey lighten-2"
                        style="border-radius: 1%; margin: auto; background-color:transparent;"
                        max-height="250" max-width="600"
                        height="auto"
                        width="auto"
                        src="https://drive.google.com/uc?id=1tioJrkmmBZYhfJx2_s6c_NlSCBW9Ah8o">
                        <template v-slot:placeholder>
                           <v-row
                              class="fill-height ma-0"
                              align="center"
                              justify="center">
                              <v-progress-circular indeterminate color="grey lighten-5"></v-progress-circular>
                           </v-row>
                        </template>
                     </v-img>
                  </a>
               </v-container>
            </v-flex>
            <v-flex xs4 sm4 md4>
               <v-container>
                  <a href="https://spring.io/projects/spring-boot" target="_blank">
                     <v-img
                        class="grey lighten-2"
                        style="border-radius: 1%; margin: auto; background-color:transparent;"
                        max-height="250" max-width="600"
                        height="auto"
                        width="auto"
                        src="https://drive.google.com/uc?id=1_0jBWM_zG2u9kM2Wim0d9AOu6LHt_Ol6"
                     >
                        <template v-slot:placeholder>
                           <v-row
                              class="fill-height ma-0"
                              align="center"
                              justify="center">
                              <v-progress-circular indeterminate color="grey lighten-5"></v-progress-circular>
                           </v-row>
                        </template>
                     </v-img>
                  </a>
               </v-container>
               </v-flex>
               <v-flex xs4 sm4 md4>
                  <v-container>
                     <div class="imageContainer">
                        <a href="https://vuejs.org/" target="_blank">
                           <v-img
                              class="grey lighten-2"
                              style="border-radius: 1%; margin: auto; background-color:transparent;"
                              max-height="250" max-width="600"
                              height="auto"
                              width="auto"
                              src="https://drive.google.com/uc?id=1NPn94_3Sm0794TglhC2gYZd3s035xxeO">
                              <template v-slot:placeholder>
                                 <v-row
                                    class="fill-height ma-0"
                                    align="center"
                                    justify="center">
                                    <v-progress-circular indeterminate color="grey lighten-5"></v-progress-circular>
                                 </v-row>
                              </template>
                           </v-img>
                        </a>
                     </div>
                  </v-container>
               </v-flex>
         </v-row>
      </v-container>

         <v-container>
            <p id="descriptionNestExpress">
               NestExpress is a project produced as a requirement for CS-451 Software Engineering Project I &
               CS-452 Software Engineering Project II. This project is a display of the Pensacola Christian College Campus Store.
               All requirements are decided by the students, along with all its design and features. The front-end was developed with
               Vue.js JavaScript framework with the back-end using the Spring Boot framework. This project used two different
               frameworks in an attempt to allow for the front-end to be changed out at any point.
            </p>
            <p id="imgCampusStore">
               Campus Store images source: <a href="http://campusstoredesign.com/pensacola" target="_blank">campusstoredesign.com</a>
            </p>
         </v-container>
      </v-sheet>

      <v-sheet id="sheetTeamInfo">
         <v-container>
               <h1 id="teamHeading">MEET OUR CEO</h1>
               <v-divider id="divMeetCEO" color="orange"></v-divider>
         </v-container>
         <v-container>
            <v-row>
               <v-flex md12>
                  <v-container>
                     <v-avatar size="250">
                        <v-img src="https://drive.google.com/uc?id=1Rql85IsoVTbqoCwWU-dmF7922UHTDz57">
                        </v-img>
                     </v-avatar>
                     <v-container>
                        <v-flex md12>
                           <h3 class="teamNames">DR. MICHAEL GEARY</h3>
                           <p class="teamRoles">Chief Executive Officer & Course Instructor</p>
                           <p class="teamDescription">Created, planned, implemented, and integrated the strategic direction of the various teams at Sofware Engineers Inc.</p>
                        </v-flex>
                     </v-container>
                  </v-container>
               </v-flex>
            </v-row>
         </v-container>
      </v-sheet>

      <v-sheet id="sheetMeetOurTeam">
         <v-container>
               <h1 id="teamHeading">MEET OUR TEAM</h1>
               <v-divider id="divMeetCEO" color="orange"></v-divider>
         </v-container>
         <v-container>
            <v-row>
               <v-flex md4>
                  <v-container>
                     <v-avatar size="250">
                        <v-img src="https://drive.google.com/uc?id=1Gh3vPm-_qexjIqii_idfetwTKHyEqJqK">
                        </v-img>
                     </v-avatar>
                     <v-container>
                        <v-flex md12>
                           <h3 class="teamNames">SARAH HOGGATT</h3>
                           <p class="teamRoles">Project Manager</p>
                           <p>Provided overall direction, motivation, and assistance to the team; Managed the team and tracked their progress, helped them get access to the network and software they needed; Assisted in the approval process.</p>
                           <p id="quote">"Strive not to be a success, but rather to be of value." — Albert Einstein</p>
                        </v-flex>
                     </v-container>
                  </v-container>
               </v-flex>
               <v-flex md4>
                  <v-container>
                     <v-avatar size="250">
                        <v-img src="https://drive.google.com/uc?id=1hPezYh4aIQf8qxDrpFVlEZqjexbl3KT2">
                        </v-img>
                     </v-avatar>
                     <v-container>
                        <v-flex md12>
                           <h3 class="teamNames">STEPHEN THOMAS</h3>
                           <p class="teamRoles">Team Lead & Database Administrator</p>
                           <p>Managed teamwork; Motivated team members; Met weekly with the CEO; Designed/constructed the database; Set up required development frameworks, applications, and tools.</p>
                           <p id="quote">"Give your best now because life changes in hours."</p>
                        </v-flex>
                     </v-container>
                  </v-container>
               </v-flex>
               <v-flex md4>
                  <v-container>
                     <v-avatar size="250">
                        <v-img src="https://drive.google.com/uc?id=1EemX1pOUELgRb3CykceQcBxl78wWzxHv">
                        </v-img>
                     </v-avatar>
                     <v-container>
                        <v-flex md12>
                           <h3 class="teamNames">SENARIO BETHEL</h3>
                           <p class="teamRoles">Assistant Team Lead & UI/UX Developer</p>
                           <p>Supported the TL in leading and coordinating the team; Populated the product database; Prepared presentations; Developed frontend pages; Assisted in credit card design/website mockup design; Recorded notes.</p>
                           <p id="quote">"No one is a failure until they blame somebody else." — Charlie "Tremendous" Jones</p>

                        </v-flex>
                     </v-container>
                  </v-container>
               </v-flex>
            </v-row>
            <v-row>
               <v-flex md4>
                  <v-container>
                     <v-avatar size="250">
                        <v-img src="https://drive.google.com/uc?id=1vG6e-VdsmSJtN4t9hr1Mc474PjVZag3V">
                        </v-img>
                     </v-avatar>
                     <v-container>
                        <v-flex md12>
                           <h3 class="teamNames">SAM KUMBI</h3>
                           <p class="teamRoles">UI/UX Lead Developer</p>
                           <p>Led and organized the visual aspects of the project; Gathered requirements for the project's design; Led the design of the website's mockup; Developed frontend pages.</p>
                           <p id="quote">"If God brought you to it, He will bring you through it!"</p>
                        </v-flex>
                     </v-container>
                  </v-container>
               </v-flex>
               <v-flex md4>
                  <v-container>
                     <v-avatar size="250">
                        <v-img src="https://drive.google.com/uc?id=13oM1147SPgLXjHKTHGcQTKsvyi-4lui2">
                        </v-img>
                     </v-avatar>
                     <v-container>
                        <v-flex md12>
                           <h3 class="teamNames">JORDAN HOFF</h3>
                           <p class="teamRoles">Quality Assurance Manager & Backend Developer</p>
                           <p>Ensured proper testing of the team's project, coordination with other teams to test their projects, and adherence to coding standards; Wrote controllers on the backend.</p>
                           <p id="quote">"You will meet two kinds of people in life: ones who build you up and ones who tear you down. But in the end, you'll thank them both."</p>

                        </v-flex>
                     </v-container>
                  </v-container>
               </v-flex>
               <v-flex md4>
                  <v-container>
                     <v-avatar size="250">
                        <v-img src="https://drive.google.com/uc?id=1-tV7JIVBaFD7aJcrGemiG3FXORsn-aby">
                        </v-img>
                     </v-avatar>
                     <v-container>
                        <v-flex md12>
                           <h3 class="teamNames">KYLE LEJEUNE</h3>
                           <p class="teamRoles">Communications Coordinator & Backend Developer</p>
                           <p>Helped ensure effective communication channels among team members; Planned and implemented the team's communications strategy; Wrote controllers on the backend.</p>
                           <p id="quote">"People say nothing is impossible, but I do nothing everyday."</p>
                        </v-flex>
                     </v-container>
                  </v-container>
               </v-flex>
            </v-row>
            <v-row>
               <v-flex md6>
                  <v-container>
                     <v-avatar size="250">
                        <v-img src="https://drive.google.com/uc?id=128fnAyYzY0YW5tyrExc8k0sICiv-Yh1A">
                        </v-img>
                     </v-avatar>
                     <v-container>
                        <v-flex md12>
                           <h3 class="teamNames">JOHANAN CARTWRIGHT</h3>
                           <p class="teamRoles">Graphic Designer</p>
                           <p>Designed the logo/advertisement slides, and inspected the website's mockup.</p>
                           <p id="quote">"A Bahamian Living the American Dream."</p>
                           <p><a href="https://johanancartwright.com" target="_blank">johanancartwright.com</a></p>
                        </v-flex>
                     </v-container>
                  </v-container>
               </v-flex>
               <v-flex md6>
                  <v-container>
                     <v-avatar size="250">
                        <v-img src="https://drive.google.com/uc?id=1JsOb97ryaqQ2X2r1AmgzFDUY4K5logZs">
                        </v-img>
                     </v-avatar>
                     <v-container>
                        <v-flex md12>
                           <h3 class="teamNames">MEGAN GOFFE</h3>
                           <p class="teamRoles">Graphic Designer</p>
                           <p>Desgined the credit card, worked on the advertising slides, and redesigned the sign-up page.</p>
                        </v-flex>
                     </v-container>
                  </v-container>
               </v-flex>
            </v-row>
         </v-container>
      </v-sheet>

      <!-- Page footer -->
      <appFooter/>

      <!-- 'To-top' scroll button -->
      <scrollToTop />

      <!-- Expand image dialog boxes -->
      <template>
         <div class="text-center">
            <v-dialog v-model="dialogImg" width="1000">
               <v-card style="border-radius: 1%;">
                  <v-img
                     class="grey lighten-2"
                     style="margin: auto;"
                     max-height="700" max-width="1000"
                     height="auto"
                     width="auto"
                     src="https://drive.google.com/uc?id=17fmZqbb_MCG6bqd6svAjCFaxSVbfzs2q"
                  >
                     <template v-slot:placeholder>
                        <v-row class="fill-height ma-0" align="center" justify="center">
                           <v-progress-circular indeterminate color="grey lighten-5"></v-progress-circular>
                        </v-row>
                     </template>
                  </v-img>

                  <v-btn
                     color="white"
                     icon
                     text
                     @click="dialogImg = false"
                     style="position: absolute; top: 0; right: 0; margin-right: 5px; margin-top: 8px; font-size: 15px; background: black;"
                  >
                     X
                  </v-btn>
               </v-card>
            </v-dialog>
         </div>
      </template>

      <template>
         <div class="text-center">
            <v-dialog v-model="dialogImg2" width="1000">
               <v-card style="border-radius: 1%;">
                  <v-img
                     class="grey lighten-2"
                     style="margin: auto;"
                     max-height="700" max-width="1000"
                     height="auto"
                     width="auto"
                     src="https://drive.google.com/uc?id=1LdiihleDV6JTZSr06sxTVBlQdl_2Es_Z">
                     <template v-slot:placeholder>
                        <v-row class="fill-height ma-0" align="center" justify="center">
                           <v-progress-circular indeterminate color="grey lighten-5"></v-progress-circular>
                        </v-row>
                     </template>
                  </v-img>

                  <v-btn
                     color="white"
                     icon
                     text
                     @click="dialogImg2 = false"
                     style="position: absolute; top: 0; right: 0; margin-right: 8px; margin-top: 8px; font-size: 15px; background: black;"
                  >
                     X
                  </v-btn>
               </v-card>
            </v-dialog>
         </div>
      </template>

      <template>
         <div class="text-center">
            <v-dialog v-model="dialogImg3" width="1000">
               <v-card style="border-radius: 1%;">
                  <v-img
                     class="grey lighten-2"
                     style="1%; margin: auto;"
                     max-height="700" max-width="1000"
                     height="auto"
                     width="auto"
                     src="https://drive.google.com/uc?id=1z97fOHga7S2o5tU75R0Ijlvit4bqK2ZU"
                  >
                     <template v-slot:placeholder>
                        <v-row class="fill-height ma-0" align="center" justify="center">
                           <v-progress-circular indeterminate color="grey lighten-5"></v-progress-circular>
                        </v-row>
                     </template>
                  </v-img>

                  <v-btn
                     color="white"
                     icon
                     size="small"
                     text
                     @click="dialogImg3 = false"
                     style="position: absolute; top: 0; right: 0; margin-right: 5px; margin-top: 8px; font-size: 15px; background: black;"
                  >
                     X
                  </v-btn>
               </v-card>
            </v-dialog>
         </div>
      </template>
   </v-app>
</template>

<script>
import header              from './Header.vue'
import footer              from './Footer.vue'
import navBar              from './Navigation.vue'
import toTop               from './ScrollToTop.vue'
import { mdiClockOutline } from '@mdi/js';
import { mdiMapMarker }    from '@mdi/js';
import { mdiPhone }        from '@mdi/js';
import { mdiEmail }        from '@mdi/js';
import { mdiTruck }        from '@mdi/js';

export default {

   components: {
      appheader:   header,
      appFooter:   footer,
      navigation:  navBar,
      scrollToTop: toTop,
   },

   data() {
      return {
         icons: {
            clockIcon:    mdiClockOutline,
            locationIcon: mdiMapMarker,
            callIcon:     mdiPhone,
            emailIcon:    mdiEmail,
            shippingIcon: mdiTruck,
         },

         dialogImg: false,
         dialogImg2: false,
         dialogImg3: false,

         items: [
            { src: 'https://drive.google.com/uc?id=17fmZqbb_MCG6bqd6svAjCFaxSVbfzs2q' },
            { src: 'https://drive.google.com/uc?id=1LdiihleDV6JTZSr06sxTVBlQdl_2Es_Z' },
            { src: 'https://drive.google.com/uc?id=1z97fOHga7S2o5tU75R0Ijlvit4bqK2ZU' },
            { src: 'https://drive.google.com/uc?id=1YpAAtbUg8VpD-Cyx4nLHPHcm6jUqKMJF' },
         ],

      }
   },

   methods: {
      /* eslint-disable */

      activateImageDialog() {
         this.dialogImg = true
      },

      activateImageDialog2() {
         this.dialogImg2 = true
      },

      activateImageDialog3() {
         this.dialogImg3 = true
      },

      campusStoreImage(item) {
         return this.items[item].src
      },
   },
}
</script>

<!-- CSS -->
<style scoped src="./css/AboutStyles.css">

</style>