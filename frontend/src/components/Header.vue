<template>
   <v-card>
      <header id="subheader">
         <v-container fluid>
            <v-row justify="end" wrap>
               <v-flex xs12 sm12 md12 lg3 xl3 align-self: end>

                  <!--  Contact information -->
                  <div id="pageInformation">
                     <a id="contactInfo" href="/about">
                        {{contactInfo.contact}}
                     </a>
                     <a id="hoursInfo"     href="/about">
                        {{contactInfo.hours}}
                     </a>
                     <a id="helpAboutInfo" href="/about">
                        {{contactInfo.helpAbout}}
                     </a>
                  </div>
               </v-flex>
            </v-row>
         </v-container>

      <!--  User information -->
      <v-container fluid style="background-color:#EEEEEE;">
         <v-row>
            <v-flex md2 style="margin-left:70px;margin-right:30px;" >
               <v-img
                  style= "max-width:230px;margin-left:auto;margin-right:auto;height: 80px; cursor:pointer;"
                  src="https://drive.google.com/uc?id=1LS1qmFFVv3buUyouFPduBoju35VfvMtB"
                  @click= "reload"
               >
               </v-img>
            </v-flex>

            <!-- Search bar & search button-->
               <v-flex xs12 sm12 md5 lg5 xl6 class="justify-center" style="padding: 0px 70px 0px 20px; align-justify:center; width:30px;">
                  <ejs-autocomplete
                     v-model="productSearch"
                     :dataSource="products"
                     label="Search for a product"
                     style="background:white;
                            padding-top:20px;
                            margin-top:10px;
                            font-size:15pt;
                            border-width:1px;
                            border-style:solid;
                            border-color:orange;"
                     id="searchBar"
                     :query='search'
                     :highlight="true"
                     hide-details
                     color="white"
                     maxlength="60"
                     flat
                     outline
                     @select="select($event)"
                     @focus= "model=true"
                     hide-no-data
                     autocomplete="off"
                     :showClearButton="false"
                  >
                  </ejs-autocomplete>
               </v-flex>

               <!-- Search button -->
               <div style="margin-left:-75px;">
                  <v-btn elevation="0" id="btnSearch" @click="getResults">
                     <v-icon medium color="white" class="iconStyles">
                        {{ searchIcon }}
                     </v-icon>
                  </v-btn>
               </div>

               <!-- My account button -->
               <v-flex xs7 sm4 md2 lg2 xl2 id="flexAccount">
                  <v-menu offset-y v-if="!userInfo.userLogged" transition="slide-y-transition">
                     <template v-slot:activator="{ on }">
                        <v-btn
                           id="myAccountBtn"
                           dark
                           elevation="0"
                           v-on="on"
                           text
                           color="#003B63"
                           height="55"
                           width="220"
                        >
                           <v-flex>
                              <v-icon large color="#003B63" class="iconStyles">
                                 {{ accountIcon }}
                              </v-icon>
                                 {{userInfo.signIn}}
                              </v-flex>
                        </v-btn>
                     </template>
                     <v-list dense>
                        <v-list-item class="accountBtn">
                           <v-list-item-title class="accountBtn">
                              <v-btn
                                 depressed=false
                                 dark
                                 text
                                 class="myAccountInfoBtn"
                                 style="justify-content: left;"
                                 to='/signin'
                              >
                                 <v-flex xs3 sm3 md3>
                                    <v-icon>
                                       {{ signInIcon }}
                                    </v-icon>
                                 </v-flex>
                                    <v-flex xs9 sm9 md9>
                                       <div style="text-align: left !important;">
                                          Sign in
                                       </div>
                                    </v-flex>
                              </v-btn>
                           </v-list-item-title>
                        </v-list-item>
                        <v-list-item class="accountBtn">
                           <v-list-item-title class="accountBtn">
                              <v-btn
                                 depressed=false
                                 dark
                                 text
                                 class="myAccountInfoBtn"
                                 style="justify-content: left;"
                                 to='/signup'>
                                 <v-flex xs3 sm3 md3>
                                    <v-icon>
                                       {{ createAccountIcon }}
                                    </v-icon>
                                 </v-flex>
                                    <v-flex xs9 sm9 md9>
                                       Create account
                                    </v-flex>
                              </v-btn>
                           </v-list-item-title>
                        </v-list-item>
                     </v-list>
                  </v-menu>

                  <v-menu offset-y v-else transition="slide-y-transition">
                     <template v-slot:activator="{ on }">
                        <v-btn
                           id="myAccountBtn"
                           dark
                           text
                           elevation="0"
                           v-on="on"
                           color="#003B63"
                           height="55"
                           width="220"
                        >
                           <v-flex md2>
                              <v-icon large color="#003B63">
                                 {{ accountIcon }}
                              </v-icon>
                           </v-flex>
                           <v-flex md10>
                              {{userInfo.signIn}}
                           </v-flex>
                        </v-btn>
                     </template>
                     <v-list nav dense>
                        <v-list-item>
                           <v-list-item-title>
                              <v-row>
                                 <v-flex xs3 sm3 md3 lg3>
                                    <v-icon size="35" color="gray" id="walletStyles">
                                       {{ walletIcon }}
                                    </v-icon>
                                 </v-flex>
                                 <v-flex xs9 sm9 md9 lg9>
                                    <h3 id="userInfoBalance" style="">
                                       {{userInfo.balance}}
                                    </h3>
                                 </v-flex>
                              </v-row>
                           </v-list-item-title>
                        </v-list-item>

                        <div id="divDiv">
                           <v-divider></v-divider>
                        </div>

                        <v-list-item>
                           <v-list-item-title>
                              <v-btn
                                 v-on="on"
                                 class="myAccountInfoBtn"
                                 depressed=false
                                 color="transparent"
                                 elevation="0"
                                 style="justify-content: left;"
                                 left
                                 @click="addFunds.dialog = !addFunds.dialog"
                              >
                                 <v-flex xs3 sm3 md3>
                                    <v-icon medium>
                                       {{ dollarSignIcon }}
                                    </v-icon>
                                 </v-flex>
                                 <v-flex xs9 sm9 md9>
                                    Add Funds
                                 </v-flex>
                              </v-btn>
                           </v-list-item-title>
                              <v-divider></v-divider>
                        </v-list-item>
                        <v-list-item>
                           <v-list-item-title>
                              <v-btn
                                 elevation="0"
                                 class="myAccountInfoBtn"
                                 color="transparent"
                                 to="/wishlist"
                              >
                                 <v-flex xs3 sm3 md3>
                                    <v-icon medium>
                                       {{ wishListIcon }}
                                    </v-icon>
                                 </v-flex>
                                 <v-flex xs9 sm9 md9>
                                    {{contactInfo.wishList}}
                                 </v-flex>
                              </v-btn>
                           </v-list-item-title>
                        </v-list-item>

                        <v-list-item>
                           <v-list-item-title>
                              <v-btn
                                 elevation="0"
                                 class="myAccountInfoBtn"
                                 color="transparent"
                                 to="/orders"
                              >
                                 <v-flex xs3 sm3 md3>
                                    <v-icon medium>
                                       {{ ordersIcon }}
                                    </v-icon>
                                 </v-flex>
                                 <v-flex xs9 sm9 md9>
                                    {{contactInfo.orders}}
                                 </v-flex>
                              </v-btn>
                           </v-list-item-title>
                        </v-list-item>

                        <!-- Edit account details -->
                        <v-list-item>
                           <v-list-item-title>
                              <v-btn
                                 class="myAccountInfoBtn"
                                 depressed=false
                                 color="transparent"
                                 v-on="on"
                                 elevation="0"
                                 style="justify-content: left;"
                                 @click="editAccount.dialog = ! editAccount.dialog;
                                         getEditInfo()"
                              >
                                 <v-flex xs3 sm3 md3>
                                    <v-icon medium>
                                       {{ editAccountIcon }}
                                    </v-icon>
                                 </v-flex>
                                 <v-flex xs9 sm9 md9>
                                    Edit Account
                                 </v-flex>
                              </v-btn>
                           </v-list-item-title>
                        </v-list-item>

                        <v-list-item>
                           <v-list-item-title>
                              <v-btn
                                 color="#F7882F"
                                 class="myAccountInfoBtn"
                                 type="submit"
                                 elevation="0"
                                 style="justify-content:left;
                                        border-radius: 3px;"
                                 @click="signOut"
                              >
                                 <v-flex xs3 sm3 md3>
                                    <v-icon>
                                       {{ signOutIcon }}
                                    </v-icon>
                                 </v-flex>
                                 <v-flex xs9 sm9 md9>
                                    Sign Out
                                 </v-flex>
                              </v-btn>
                           </v-list-item-title>
                        </v-list-item>
                     </v-list>
                  </v-menu>
               </v-flex>
               <v-layout>
                  <v-flex xs5 sm3 md8 lg11 xl6 style="margin-top:25px;">
                     <router-link id="cartInfo" style="" to='/cart'>
                        <v-icon size="30" color="#003B63" class="iconStyles">
                           {{ cartIcon }}
                        </v-icon>
                        {{userInfo.cart}}
                     </router-link>
                  </v-flex>
               </v-layout>
            </v-row>
         </v-container>
      </header>

      <!-- Add funds dialog box -->
      <template>
         <v-row justify="center">
            <v-dialog
               v-model="addFunds.dialog"
               @keydown.esc="addFunds.dialog = true"
               max-width="600px"
               elevation="0"
               @click:outside="addFunds.dialog = false,
                               userInfo.userAmount = null,
                               userInfo.ccNumber = '',
                               userInfo.userAmount = '',
                               userInfo.ccName = '',
                               userInfo.ccDate = '',
                               userInfo.cvcNumber = ''">

               <v-card elevation="0">
                  <v-col>
                     <v-row justify="end">
                        <v-btn
                           class="dialogCloseBtn"
                           text
                           tile
                           icon
                           @click="addFunds.dialog = false,
                                   userInfo.userAmount = null,
                                   userInfo.ccNumber = '',
                                   userInfo.userAmount = '',
                                   userInfo.ccName = '',
                                   userInfo.ccDate = '',
                                   userInfo.cvcNumber = ''"
                        >
                           X
                        </v-btn>
                     </v-row>
                  </v-col>
                  <v-card-title>
                     <v-col>
                        <v-row>
                           <span class="headline">
                              Add funds to your account
                           </span>
                        </v-row>
                        <v-row justify="center">
                           <span class="errorMessageLabel">
                              {{userInfo.cardError}}
                           </span>
                        </v-row>
                     </v-col>
                  </v-card-title>
                     <v-card-text>
                        <v-container>
                           <v-row>
                              <v-col>
                                 <v-col>
                                    <v-text-field
                                       id="ccNumber"
                                       v-model.trim="userInfo.ccNumber"
                                       v-model="userInfo.ccNumber"
                                       v-mask="creditCardNumber.mask"
                                       label="Credit Card Number"
                                       placeholder="Ex. 1234 5678 9012 3456"
                                       color="#F7882F"
                                       required
                                       outlined
                                       hide-details
                                       clearable
                                    ></v-text-field>
                                 </v-col>
                                 <v-col>
                                    <v-text-field
                                       id="cardholderName"
                                       v-model.trim="userInfo.ccName"
                                       v-model="userInfo.ccName"
                                       label="Cardholder name"
                                       placeholder="Team Persevere"
                                       value="Team Persevere"
                                       color="#F7882F"
                                       hide-details
                                       required
                                       outlined
                                       clearable
                                    ></v-text-field>
                                 </v-col>
                                    <v-col>
                                       <v-row>
                                          <v-col>
                                             <v-text-field
                                                id="ccDate"
                                                v-model.trim="userInfo.ccDate"
                                                v-model="userInfo.ccDate"
                                                v-mask="expirationDate.mask"
                                                label="Expriation date"
                                                placeholder="MM/YY"
                                                color="#F7882F"
                                                hide-details
                                                required
                                                outlined
                                                clearable
                                             ></v-text-field>
                                          </v-col>
                                          <v-col>
                                             <v-text-field
                                                   id="userEdits.cvcNumber"
                                                   v-model.trim="userInfo.cvcNumber"
                                                   v-model="userInfo.cvcNumber"
                                                   v-mask="cvcNumber.mask"
                                                   label="CVC number"
                                                   placeholder="Ex. 123"
                                                   color="#F7882F"
                                                   required
                                                   outlined
                                                   hide-details
                                                   clearable
                                             ></v-text-field>
                                          </v-col>
                                       </v-row>
                                    </v-col>
                                    <v-col>
                                       <v-text-field
                                          id="userInfo.userAmount"
                                          v-model.trim="userInfo.userAmount"
                                          v-model="userInfo.userAmount"
                                          name="userAmount"
                                          label="Amount"
                                          placeholder="0.00"
                                          color="#F7882F"
                                          prefix="$"
                                          style="font-size: 25px;"
                                          hide-details
                                          require
                                          outlined
                                          clearable
                                          @keypress="onlyForCurrency"
                                       >
                                       </v-text-field>
                                    </v-col>
                                    <v-col>
                                       <v-card-actions>
                                          <v-btn
                                             id="addFundsBtn"
                                             text
                                             @click="addUserFunds"
                                          >
                                             Add
                                          </v-btn>
                                       </v-card-actions>
                                    </v-col>
                                       <v-row>
                                          <span id="balanceText">
                                             <br/>BALANCE
                                          </span>
                                       </v-row>
                                       <v-row>
                                          <span id="balanceAmount">
                                             <br/>{{userInfo.balance}}
                                          </span>
                                       </v-row>
                              </v-col>
                           </v-row>
                        </v-container>
                     </v-card-text>
                  </v-card>
            </v-dialog>
         </v-row>
      </template>

      <!-- Edit account dialogue box -->
      <template v-if="!editAccountDetails">
         <v-row justify="center">
            <v-dialog v-model="editAccount.dialog" max-width="600px" elevation="0">
               <v-card elevation="0">
                  <v-col>
                     <v-row>
                        <v-col>
                           <v-row justify="start" style="margin-left: 10%;">
                              <v-btn
                                 id="editAccountBtn"
                                 elevation="0"
                                 style="background: transparent;
                                        color: #F7882F;
                                        border-style: solid;
                                        border-color: #F7882F;
                                        border-radius: 5px;
                                        border-width: thin;"
                                 @click="editAccountDetails = true"
                              >
                                 Edit
                              </v-btn>
                           </v-row>
                        </v-col>
                        <v-col>
                           <v-row justify="end">
                              <v-btn
                                 class="dialogCloseBtn"
                                 text
                                 tile
                                 style="margin-right: 15px; margin-top: -10px;"
                                 icon
                                 @click="editAccount.dialog = false"
                              >
                                 X
                              </v-btn>
                           </v-row>
                        </v-col>
                     </v-row>
                  </v-col>
                  <v-card-title>
                     <v-col>
                        <v-row>
                           <span class="headline">
                              Your account details
                           </span>
                        </v-row>
                     </v-col>
                  </v-card-title>
                  <v-card-text>
                     <v-container>
                        <v-row>
                           <v-col>
                              <v-row>
                                 <v-col>
                                       <v-text-field
                                          :value="userEdits.firstName"
                                          label="First name"
                                          color="#F7882F"
                                          readonly="true"
                                          required
                                          outlined
                                       ></v-text-field>
                                       <v-text-field
                                          :value="userEdits.lastName"
                                          label="Last name"
                                          color="#F7882F"
                                          readonly="true"
                                          required
                                          outlined
                                       ></v-text-field>
                                 </v-col>
                              </v-row>
                              <v-row>
                                 <v-col>
                                    <v-text-field
                                       :value="userEdits.dorm"
                                       label="Dorm"
                                       color="#F7882F"
                                       readonly="true"
                                       required
                                       outlined
                                    >
                                    </v-text-field>
                                 </v-col>
                                 <v-col>
                                    <v-text-field
                                       :value="userEdits.roomNumber"
                                       label="Room number"
                                       color="#F7882F"
                                       type="number"
                                       min="0"
                                       readonly="true"
                                       required
                                       outlined
                                    >
                                    </v-text-field>
                                 </v-col>
                              </v-row>
                           </v-col>
                        </v-row>
                     </v-container>
                  </v-card-text>
               </v-card>
            </v-dialog>
         </v-row>
      </template>

      <template v-else>
         <v-row justify="center">
            <v-dialog
               v-model="editAccount.dialog"
               max-width="600px"
               elevation="0"
               @click:outside="editAccount.dialog = false;
                               editAccountDetails = false,
                               userEdits.firstName = null,
                               userEdits.lastName = null,
                               userEdits.password = null,
                               userEdits.validPassword = null,
                               userEdits.oldPassword = null,
                               userEdits.roomNumber = null"
               >
               <v-card elevation="0">
                  <v-col>
                     <v-row justify="end">
                        <v-col>
                           <v-row justify="start" style="margin-left: 5%;">
                              <v-btn
                                 id="editAccountBtn"
                                 elevation="0"
                                 @click="editAccountDetails = false; getEditInfo()"
                                 style="background: transparent;
                                        color: #F7882F;
                                        border-radius: 5px;
                                        border-width: thin;
                                        border-color: #F7882F;
                                        border-style: solid;"
                              >
                                 Cancel
                              </v-btn>
                           </v-row>
                        </v-col>
                        <v-btn
                           class="dialogCloseBtn"
                           text
                           icon
                           @click="editAccount.dialog = false;
                                   editAccountDetails = false,
                                   userEdits.firstName = null,
                                   userEdits.lastName = null,
                                   userEdits.password = null,
                                   userEdits.validPassword = null,
                                   userEdits.oldPassword = null,
                                   userEdits.dorm = null,
                                   userEdits.roomNumber = null"
                        >
                           X
                        </v-btn>
                     </v-row>
                  </v-col>
                  <v-card-title>
                     <v-col>
                        <v-row>
                           <span class="headline">
                              Edit your account details
                           </span>
                        </v-row>
                        <v-row justify="center">
                           <span class="errorMessageLabel">
                              {{userEdits.editError}}
                           </span>
                        </v-row>
                     </v-col>
                  </v-card-title>
                  <v-card-text>
                     <v-container>
                        <v-row>
                           <v-col>
                              <v-row>
                                 <v-col>
                                    <v-text-field
                                       v-model.trim="userEdits.firstName"
                                       v-model="userEdits.firstName"
                                       label="First name"
                                       color="#F7882F"
                                       required
                                       outlined
                                       hide-details
                                       clearable
                                    >
                                    </v-text-field>
                                 </v-col>
                                 <v-col>
                                    <v-text-field
                                       v-model.trim="userEdits.lastName"
                                       v-model="userEdits.lastName"
                                       label="Last name"
                                       color="#F7882F"
                                       required
                                       outlined
                                       hide-details
                                       clearable
                                    ></v-text-field>
                                 </v-col>
                              </v-row>
                              <v-row>
                                 <v-col>
                                    <v-row>
                                       <v-col>
                                          <v-text-field
                                             v-model.trim="userEdits.oldPassword"
                                             v-model="userEdits.oldPassword"
                                             label="Old password"
                                             type="password"
                                             color="#F7882F"
                                             style="margin-bottom: 10px;"
                                             hide-details
                                             require
                                             outlined
                                             clearable
                                          >
                                          </v-text-field>
                                       </v-col>
                                    </v-row>
                                    <v-text-field
                                       v-model.trim="userEdits.password"
                                       v-model="userEdits.password"
                                       label="New password"
                                       color="#F7882F"
                                       type="password"
                                       hide-details
                                       required
                                       outlined
                                       clearable
                                    ></v-text-field>
                                 </v-col>
                              </v-row>
                              <v-row>
                                 <v-col>
                                    <v-text-field
                                       v-model.trim="userEdits.validPassword"
                                       v-model="userEdits.validPassword"
                                       label="Confirm password"
                                       type="password"
                                       color="#F7882F"
                                       hide-details
                                       require
                                       outlined
                                       clearable
                                    >
                                    </v-text-field>
                                 </v-col>
                              </v-row>
                              <v-row>
                                 <v-col>
                                    <v-combobox
                                       :items="dormList"
                                       :value="userEdits.dormNameValue"
                                       :text="userEdits.dorm"
                                       v-model="userEdits.dorm"
                                       v-model.trim="userEdits.dorm"
                                       label="Dorm"
                                       color="#F7882F"
                                       required
                                       outlined
                                       hide-details
                                    >
                                    </v-combobox>
                                 </v-col>
                                 <v-col>
                                    <v-text-field
                                       v-model.trim="userEdits.roomNumber"
                                       v-model="userEdits.roomNumber"
                                       v-mask="dormRoomNumber.mask"
                                       label="Room number"
                                       color="#F7882F"
                                       required
                                       outlined
                                       hide-details
                                    ></v-text-field>
                                 </v-col>
                              </v-row>
                           </v-col>
                        </v-row>
                     </v-container>
                     <v-card-actions>
                        <v-btn
                           id="editAccountSaveBtn"
                           text
                           @click="accountEdits"
                        >
                           Save
                        </v-btn>
                     </v-card-actions>
                  </v-card-text>
               </v-card>

               <!-- Edit account notification snackbar -->
               <v-snackbar
                     id="editAccountSnackBar"
                     :timeout="editsAccount.timeout"
                     v-model="editsAccount.snackbar"
                     color="green"
                     top
                     >
                     {{ editsAccount.text }}
                     <v-btn
                        text
                        icon
                        height="50px"
                        width="50px"
                        color="white"
                        @click.native="editsAccount.snackbar = false"
                     >
                        x
                     </v-btn>
               </v-snackbar>
            </v-dialog>
         </v-row>
      </template>
   </v-card>
</template>

<script>
import Vue                       from 'vue';
import headerApi                 from './js/Header-api'
import resultsApi                from './js/Results-api'
import dormListApi               from './js/SignUp-api'
import { mask }                  from 'vue-the-mask'
import { mdiAccount }            from '@mdi/js'
import { mdiCart }               from '@mdi/js'
import { mdiWallet }             from '@mdi/js';
import { mdiCashUsd }            from '@mdi/js';
import { mdiCardsHeart }         from '@mdi/js';
import { mdiAccountEdit }        from '@mdi/js';
import { mdiLogoutVariant }      from '@mdi/js';
import { mdiLoginVariant }       from '@mdi/js';
import { mdiAccountPlus }        from '@mdi/js';
import { mdiMagnify }            from '@mdi/js';
import { mdiCheckCircleOutline } from '@mdi/js';
import { eventBus }              from '../main'
import { AutoCompletePlugin }    from "@syncfusion/ej2-vue-dropdowns";

Vue.use(AutoCompletePlugin);

export default {

   data() {
      return {
         productSearch: null,
         search: null,
         modal: false,
         filteredProducts: [],
         products: [],

         product:'',

         // Header icons
         accountIcon: mdiAccount,
         cartIcon: mdiCart,
         walletIcon: mdiWallet,
         dollarSignIcon: mdiCashUsd,
         wishListIcon: mdiCardsHeart,
         editAccountIcon: mdiAccountEdit,
         signOutIcon: mdiLogoutVariant,
         signInIcon: mdiLoginVariant,
         createAccountIcon: mdiAccountPlus,
         searchIcon: mdiMagnify,
         ordersIcon: mdiCheckCircleOutline,

         justify: ['end'],

         /* Credit card number mask */
         creditCardNumber: {
            mask: '#### #### #### ####',
            value: '',
         },

         /* Expiration date mask */
         expirationDate: {
            mask: '##/##',
            value: '',
         },

         /* CVC number mask */
         cvcNumber: {
            mask: '###',
            value: '',
         },

         // Room number mask
         dormRoomNumber: {
            mask: '####',
            value: '',
         },

         userAmount: null,

         dormList: [],

         // Edit account details check
         editAccountDetails: false,

         // Contact information
         contactInfo: {
            contact:   'CONTACT',
            hours:     'STORE HOURS',
            wishList:  'Wishlist',
            orders:    'Previous Orders',
            helpAbout: 'ABOUT',
         },

         // Wish List snackbar notification
         editsAccount: {
            snackbar: false,
            y: 'top',
            x: null,
            mode: '',
            timeout: 4000,
            text: 'Account edit was successful.',
         },

         // Edit account dialog box
         editAccount: {
            dialog: false
         },

         // Add funds dialog box
         addFunds: {
            dialog: false,
            absolute: true,
            overlay: false,
         },

         // User details
         userInfo: {
            signIn:  '',
            balance: '',
            userAmount: '',
            cart: '',
            ccNumber: '',
            ccName: '',
            ccDate: '',
            cvcNumber: '',
            cardError: '',
            userLogged: false
         },

         userEdits: {
            firstName: null,
            lastName: null,
            password: null,
            validPassword: null,
            oldPassword: null,
            dorm: null,
            dormNameValue: null,
            roomNumber: null,
            editError: null
         }
      }
   },

   // Directive for text field masking.
   directives: {
      mask,
   },


   // Execute methods when components are mounted in the DOM.
   mounted() {
      this.getUserInfo();

      eventBus.$on('cart-update', (data) => {
         if(data) {
            this.getUserInfo()
         }
      })
   },

   // Execute methods when components are created in the DOM.
   created() {
      resultsApi.getTypeAhead()
         .then(response => {
            this.products = response.data;
         })
   },

   // Methods
   methods: {
      /* eslint-disable */

      watch: {
         search (val) {
            val && val !== this.select && this.querySelections(val)
         },
      },

      // Allow valid money amounts.
      onlyForCurrency ($event) {
         let keyCode = ($event.keyCode ? $event.keyCode : $event.which);

         // Only allow number and one dot for user funds.
         if ((keyCode < 48 || keyCode > 57) && (keyCode !== 46 || this.userInfo.userAmount.indexOf('.') != -1)) {
            $event.preventDefault();
         }

         // Restrict user amount to 2 decimal places.
         if(this.userInfo.userAmount!=null && this.userInfo.userAmount.indexOf(".")>-1
               && (this.userInfo.userAmount.split('.')[1].length > 1)
               && $event.target.selectionStart > this.userInfo.userAmount.indexOf(".")){
            $event.preventDefault();
         }
      },

      // Filter states in a product search.
      filterStates () {
         this.filterStates = this.products.filter(productSearch => {
            return productSearch.toLowerCase().startsWith(this.productSearch.toLowerCase());
         });
      },

      // Simulate an ajax query.
      querySelections (v) {
         setTimeout(() => {
            this.filteredProducts = this.products.filter(e => {
               return (e || '').toLowerCase().indexOf((v || '').toLowerCase()) > -1
            })
         }, 500)
      },

      // Route to home page.
      reload(){
         this.$router.push("/");
         window.location.reload();
      },

      // Get an active user's account details.
      getUserInfo() {
         headerApi.getActiveUser()
         .then(response => {
            if (response.data.error != null) {
               this.userInfo.signIn     = 'Your account';
               this.userInfo.cart       = '0 items';
               this.userInfo.userLogged = false;
            } else if (response.data.firstname != 'guest') {
               this.userInfo.signIn     = 'Welcome, ' + response.data.firstname;
               this.userInfo.balance    = '$' + response.data.balance;
               this.userInfo.cart       = response.data.cartQuantity + ' items';
               this.userInfo.userLogged = true;
            } else {
               this.userInfo.signIn     = 'Your account';
               this.userInfo.cart       = response.data.cartQuantity + ' items';
               this.userInfo.userLogged = false;
            }
         })
      },

      // Sign out a user.
      signOut() {
         headerApi.logoutUser()
         .then(response => {
            window.location.reload()
         })
         .catch(error => {
         })
      },

      // Add funds to a user's account.
      addUserFunds() {
         headerApi.addFunds(this.userInfo.userAmount, this.userInfo.ccNumber,
                            this.userInfo.ccName, this.userInfo.ccDate,
                            this.userInfo.cvcNumber)
         .then(response => {
               if (response.data.error == null) {
                  this.userInfo.balance = '$' + response.data.balance;
                  this.userInfo.cardError = ''
               } else
                  this.userInfo.cardError = response.data.error;
         })
      },

      // Get a user's account details for 'Edit Account' feature.
      getEditInfo() {
         headerApi.getEditInfo()
            .then(response => {
               this.userEdits.firstName  = response.data.firstName;
               this.userEdits.lastName   = response.data.lastName;
               this.userEdits.dorm       = response.data.dorm;
               this.userEdits.roomNumber = response.data.roomNumber;
            })

         dormListApi.getDormLists()
            .then(response => {
               this.dormList = response.data;
               this.userEdits.dormNameValue = dormListApi.convertDormId(this.userEdits.dorm, this.dormList);
            })
      },

      // Change a user's account details.
      accountEdits() {
         this.userEdits.dormNameValue = dormListApi.convertDormId(this.userEdits.dorm, this.dormList);
         headerApi.editAccount(this.userEdits.firstName, this.userEdits.lastName,
            this.userEdits.password, this.userEdits.validPassword, this.userEdits.oldPassword,
            this.userEdits.dormNameValue, this.userEdits.roomNumber)
         .then(response => {
            if (response.data.error == null) {
               this.getUserInfo();
               if(response.data.firstName != null)
                  this.userEdits.firstName = response.data.firstName;
               if(response.data.lastName != null)
                  this.userEdits.lastName = response.data.lastName;
               if(response.data.dorm != null)
                  this.userEdits.dorm = response.data.dorm;
               if(response.data.roomNumber != null)
                  this.userEdits.roomNumber = response.data.roomNumber;
               this.editsAccount.snackbar = true;
               this.userEdits.editError = '';
            } else {
               this.userEdits.editError = response.data.error;
            }
         })
      },

      select($event) {
         this.$router.push("/results/" + $event.itemData.text);
         window.location.reload();
      },

      // Get the product results after searching.
      getResults() {
         this.$router.push("/results/" + this.productSearch);
         window.location.reload();
      },
   },

   // Redirect to sign in page.
   redirectPage() {
      this.$router.push("/signin");
   },
}
</script>

<!-- CSS -->
<style scoped src="./css/HeaderStyles.css">

</style>
