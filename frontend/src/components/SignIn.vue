<template>
   <v-app id="appBackground">

      <!-- Page Header -->
      <appHeader/>

      <v-container id="signInContainer">
         <v-layout row>
            <v-flex>
               <v-card id="signInCard" color="rgba(255,255,255,0.85)" elevation="0">
                  <v-row>
                        <v-flex>
                           <v-img id="signInLogo" style="max-height: 65px; max-width: 130px; margin: auto;"
                              src="https://drive.google.com/uc?id=1LS1qmFFVv3buUyouFPduBoju35VfvMtB"
                              @click= "reload"
                           >
                           </v-img>
                        </v-flex>
                  </v-row>
                  <h1 id="signInTitle">Sign-In</h1><br>
                  <span id="spanWelcomeTxt">Welcome, sign-in for a greater experience.</span>
                <v-card-text>

                  <!-- Validation message -->
                  <v-alert text v-if="this.validationErrorMessage != ''" type="error" style="font-size: 15px;">
                     {{this.validationErrorMessage}}
                  </v-alert>
                  <v-container>
                    <form>
                        <div>
                           <!-- Username field -->
                           <v-layout row id="userInputBoxes">
                              <v-flex xs12>
                                 <v-text-field
                                    id="username"
                                    v-model="$v.username.$model"
                                    name="username"
                                    label="Username"
                                    type="username"
                                    :rules="usernameRules"
                                    required
                                    color="#F7882F"
                                    autocomplete="off">
                                 </v-text-field>
                                 <div class="invalid-feedback" id="errorUsername">
                                    <span v-if="$v.$dirty && $v.username.$invalid">
                                      {{usernameErrorMessage}}
                                    </span>
                                 </div>
                              </v-flex>
                           </v-layout>

                        <!-- Password field -->
                        <v-layout row id="userInputBoxes">
                          <v-flex xs12>
                            <v-text-field
                              id="password"
                              name="password"
                              label="Password"
                              :rules="passwordRules"
                              v-model="$v.password.$model"
                              :append-icon="passwordValue ? 'visibility_off': 'visibility'"
                              @click:append="() => (passwordValue = ! passwordValue)"
                              :type="passwordValue ? 'password' : 'text'"
                              required
                              color="#F7882F"
                              autocomplete="off">
                            </v-text-field>
                            <div class="invalid-feedback" id="errorPassword">
                              <span v-if="$v.$dirty && $v.password.$invalid">
                                {{passwordErrorMessage}}
                              </span>
                            </div>
                          </v-flex>
                        </v-layout>
                      </div>

                      <div>
                        <!-- Sign in button -->
                        <v-layout row>
                          <v-flex xs12>
                            <v-btn block id="btnSignIn" color="#F7882F" type="submit" @click.prevent="submitUserInfo" rounded>Sign in</v-btn>
                          </v-flex>
                        </v-layout>

                           <!-- Sign up link -->
                           <v-layout row>
                           <v-flex xs12>
                              <p id="newCustomerText">Don't have an account?
                              <router-link id="createNewAccountText" to="/signup">Sign-Up</router-link></p>
                              </v-flex>
                           </v-layout>
                         </div>
                      </form>
                     </v-container>
                  </v-card-text>
               </v-card>
            </v-flex>
         </v-layout>
      </v-container>

      <!-- Footer-->
      <appFooter id="footer"/>

   </v-app>
</template>

<!-- JavaScript -->
<script>
import header     from './SignInHeader.vue'
import footer     from './SignInFooter.vue'
import {required} from 'vuelidate/lib/validators'
import signinApi  from './js/SignIn-api'

export default {

    data () {
      return {
          username: '',
          password: '',
          validationErrorMessage: "",
          passwordValue: String,

          usernameRules: [
            v => !!v || '*Username is required'
         ],
         passwordRules: [
            v => !!v || '*Password is required'
         ],
      }
    },

    validations: {
      username: {required},
      password: {required}
    },

        computed: {
          usernameErrorMessage() {
            if(!this.$v.username.required) {
              return '';
            }
              return '';
          },

          passwordErrorMessage() {
            if(!this.$v.password.required) {
              return '';
            }
              return '';
          }
        },

        methods: {
         /* eslint-disable */

          submitUserInfo () {
            if(this.username != '' && this.password != '') {
                this.validationErrorMessage = ""
                this.$v.$touch()
                signinApi.postSignInData(this.username, this.password)
              .then(response => {
                  if (response.data.error != null)
                    this.validationErrorMessage = response.data.error;
                  else
                    this.$router.push("/");
              })
              .catch(error => {
                this.validationErrorMessage = error.response.data.message;
              })

              if(!this.$v.$invalid) {
                this.$emit('submit');
              }
            }

            if(this.username == '' || this.password == '') {
              this.validationErrorMessage = "Please enter a valid username or password.";
            }
          },

          reload(){
            this.$router.push("/");
            window.location.reload();
          },
        },

    components: {
      appHeader : header,
      appFooter : footer
    }
}
</script>

<!-- CSS -->
<style src="./css/SignInStyles.css">

</style>