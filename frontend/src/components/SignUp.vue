<template>
   <v-app id="background">
      <!-- Header -->
      <app-header/>

      <v-container id="signUpContainer" fluid>
            <v-flex xs12 sm12 md10 lg7 xl8  class="mx-auto" >
               <v-row class="mb-6" no-gutters>
               <v-col sm="6" md="6" offset-md="0">
                  <v-card class="pa-2" outlined tile id="welcomeImg">
                     <v-flex  style="margin-left:70px;margin-right:30px;" >
                        <v-row>
                           <v-img
                              style= "max-width:200px;cursor:pointer;margin-left:-50px;"
                              src="https://drive.google.com/uc?id=1LS1qmFFVv3buUyouFPduBoju35VfvMtB"
                              @click= "reload"
                           >
                           </v-img>
                        </v-row>
                     </v-flex>
                  </v-card>
               </v-col>

               <!-- User Information -->
               <v-col sm="6" md="6" offset-md="0">
                  <v-card
                     class="pa-2"
                     outlined
                     tile
                     color = rgba(255,255,255,0.85)
                  >
                     <!-- Validation message -->
                     <div id="validationMessage">
                        {{this.errors}}
                     </div>

                     <h1>Create An Account</h1>
                     <v-row id="rowCreateAcct">
                        <v-col cols="12" md="5">
                           <v-text-field
                              v-model.trim= "$v.firstname.$model"
                              type= "firstname"
                              autocomplete="off"
                              v-on:keyup.enter= "createUser"
                              color= #F7882F
                              :rules="firstnameRules"
                              :maxlength="max"
                              label="First name"
                              required
                           ></v-text-field>
                        </v-col>

                        <v-col cols="12" md="5">
                           <v-text-field
                              v-model.trim= "$v.lastname.$model"
                              type="lastname"
                              autocomplete="off"
                              v-on:keyup.enter= "createUser"
                              color= #F7882F
                              :rules="lastnameRules"
                              :maxlength="max"
                              label="Last name"
                              required
                           ></v-text-field>
                        </v-col>
                     </v-row>

                     <v-row id="rowCreateAcct">
                        <v-col
                           cols="12"
                           md="10"
                        >
                           <v-text-field
                              v-model.trim= "$v.username.$model"
                              type="username"
                              autocomplete="off"
                              v-on:keyup.enter= "createUser"
                              color= #F7882F
                              :rules="usernameRules"
                              :maxlength="max"
                              :minength="min"
                              label="Username"
                              required
                           ></v-text-field>
                        </v-col>
                     </v-row>

                     <v-row id="rowCreateAcct">
                        <v-col
                           cols="12"
                           md="10"
                        >
                           <v-text-field
                              v-model.trim= "$v.password.$model"
                              label= "Password"
                              v-on:keyup.enter= "createUser"
                              :rules="passwordRules"
                              id="password"
                              autocomplete="off"
                              :maxlength="max"
                              color= #F7882F
                              :append-icon= "passwordValue ? 'visibility_off': 'visibility'"
                              @click:append= "() => (passwordValue = !passwordValue)"
                              :type= "passwordValue ? 'password' : 'text'"
                              required
                           >
                        </v-text-field>
                        </v-col>
                     </v-row>

                     <v-row id="rowCreateAcct">
                        <v-col
                           cols="12"
                           md="10"
                        >
                           <v-text-field
                              v-model.trim= "$v.validPassword.$model"
                              label="Re-enter password"
                              id="validPassword"
                              v-on:keyup.enter= "createUser"
                              autocomplete="off"
                              :maxlength="max"
                              :append-icon= "passwordValue ? 'visibility_off': 'visibility'"
                              @click:append= "() => (passwordValue = !passwordValue)"
                              :type= "passwordValue ? 'password' : 'text'"
                              color= #F7882F
                              required
                           >
                           </v-text-field>

                           <!-- Re-enter Password Validation Message -->
                          <div class="invalid-feedback" id="errorValidPassword">
                              <span v-if="!$v.validPassword.sameAsPassword">
                                 {{ validPasswordErrorMessage }}
                              </span>
                           </div>
                        </v-col>
                     </v-row>
                     <h1>Delivery Information</h1>
                     <v-row style="margin-left:40px;margin-right:10px;">
                        <v-col
                           cols="12"
                           md="5"
                        >
                           <v-combobox
                              :items="dormList"
                              v-model="dormName"
                              id="dormName"
                              v-on:keyup.enter= "createUser"
                              label="Select Residence Hall"
                              :maxlength="max"
                              editable outlined=""
                              item-value="text"
                              color= #F7882F
                           >
                        </v-combobox>
                        </v-col>

                        <v-col
                           cols="12"
                           md="5"
                        >
                           <v-text-field
                              v-model.trim= "$v.roomNumber.$model"
                              label="Enter Room Number"
                              id="roomNumber"
                              :maxlength="max"
                              v-on:keyup.enter= "createUser"
                              type="number"
                              v-model="roomNumber"
                              autocomplete="off"
                              color= #F7882F
                              required
                              outlined=""
                           >
                           </v-text-field>
                        </v-col>
                     </v-row>

                     <!-- Signup Button -->
                     <v-row style="margin-left:40px;margin-right:10px;">
                        <v-col
                           cols="12"
                           md="10"
                        >
                        <v-flex xs12 sm12 md12 lg12 xl12>
                           <v-btn type="submit" block rounded  @click="createUser" id="btnSignUp">
                              CREATE ACCOUNT
                           </v-btn>
                        </v-flex>
                        </v-col>
                     </v-row>
                        <v-col cols="12" md="12">
                           <p id="returnToSignIn">
                              Already have an account?
                              <router-link id="signIn" to="/signin">Sign-In</router-link>
                           </p>
                        </v-col>
                     <v-row>
                     </v-row>
                  </v-card>
               </v-col>
            </v-row>
         </v-flex>
      </v-container>

       <!--Footer-->
      <appFooter id="footer"/>

   </v-app>
</template>

<script>
import header                                            from './SignInHeader.vue'
import footer                                            from './SignInFooter.vue'
import {required, minLength, maxLength, numeric, sameAs} from 'vuelidate/lib/validators'
import api                                               from './js/SignUp-api'
import { mdiFacebookBox }                                from '@mdi/js';
import { mdiInstagram }                                  from '@mdi/js';
import { mdiTwitter }                                    from '@mdi/js';

export default {
   name: 'SignUp',

    data() {
      return {
         firstname:'',
         lastname:'',
         username:'',
         password:'',
         validPassword:'',
         errors:'',
         dorm:'',
         dormName:'',
         roomNumber:'',
         sameAsPassword: sameAs('password'),
         show1: false,
         max: 20,
         min: 3,
         passwordValue: String,
         dormList:[],
         firstnameRules: [
            v => !!v || '*First name is required',
         ],
        lastnameRules: [
        v => !!v || '*Last name is required',
        ],
        usernameRules: [
            v => !!v || '*Username is required',
            v => v.length > 2 || '*Name must be more than 3 characters',
         ],
         passwordRules: [
            v => !!v || '*Password is required',
            v => v.length > 4 || '*Name must be more than 5 characters',
         ],

         facebookIcon: mdiFacebookBox,
         instagramIcon: mdiInstagram,
         twitterIcon: mdiTwitter,
      }
   },
     validations: {
       firstname:{required},
       lastname:{required},
       username:{
           required,
           minLength: minLength(3),
       },
       password:{
           required,
           minLength: minLength(5)
       },
       validPassword:{
           required,
           sameAsPassword: sameAs('password')
       },
       roomNumber:{
           numeric,
           maxLength: maxLength(4),
           minLength: minLength(4)
       }
     },

   created() {
      api.getDormLists().then(response => {
         this.dormList = response.data
      })
   },

   // Checks empty text boxes.
   computed: {
      usernameErrorMessage () {
         if (!this.$v.username.required) {
               return '* Username is required';
         }
         return '';
      },

      passwordErrorMessage () {
         if (!this.$v.password.required) {
               return '* Password is required';
         }
         return '';
      },

      validPasswordErrorMessage () {
         if (!this.$v.validPassword.required) {
               return '* Password is required '
         }
         return '* Passwords must match';
      }
   },

   methods: {
      /* eslint-disable */

      reload(){
         this.$router.push("/");
         window.location.reload();
      },

      createUser () {
         this.$v.$touch()
         this.dorm = api.convertDormId(this.dormName, this.dormList)
         api.createUser(this.firstname, this.lastname, this.username,
                        this.password, this.validPassword, this.dorm, this.roomNumber)
               .then(response => {
                  if (response.data.error != null)
                     this.errors = response.data.error;
                     else
                     this.$router.push("/");
               })
               .catch(error => {
                  this.errors = error.response.data.message;
               })

               if (!this.$v.$invalid) {
                  this.$emit('submit')
               }
            }
         },
    components: {
      appHeader : header,
      appFooter : footer
    },
}
</script>

<!-- CSS -->
<style src="./css/SignUpStyles.css" scoped>

</style>