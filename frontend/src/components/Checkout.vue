<template>
   <v-app id="checkoutBody">

      <header id="header">
         <v-btn @click="removeAuthHome" icon  style="margin-bottom: 200px !important;">
            <img id="image" src="https://drive.google.com/uc?id=14Gw77Gfsha5KtxGkYibr9u2vW-d6bRdg" alt="My logo"/>
         </v-btn>
      </header>

      <v-container style="margin: auto;">
         <v-card id="checkoutCard" flat max-width="1000">
            <v-container>
               <v-container>
                  <v-row>
                     <v-flex xs12 sm5 md5>
                        <h2 id="checkoutTitle">CHECKOUT</h2>
                     </v-flex>
                     <v-flex xs1 sm3 md3>
                     </v-flex>
                     <v-flex xs6 sm4 md4>
                        <v-container style="text-align: right;">
                           <v-btn @click="removeAuthCheckout" style="text-transform: none;" text>
                                <v-icon color="gray">
                                {{cartIcon}}
                                </v-icon>
                                 Return to Cart
                           </v-btn>
                        </v-container>
                     </v-flex>
                  </v-row>
               </v-container>

                  <v-row>
                     <v-flex sm12 md8>
                        <v-container>
                           <v-card flat elevation="0">
                              <v-flex>
                                 <p id="deliveryInfoTitle">
                                       DELIVERY INFORMATION
                                 </p>

                                 <v-container>
                                       <v-divider></v-divider>
                                 </v-container>

                                 <!-- Guest checkout -->
                                 <div v-if="isGuest === true">
                                    <v-container>
                                       <v-row>
                                          <v-container>
                                             <v-alert text color="#003B63">
                                                <h4 style="text-align: left; color: #003B63;">
                                                   Dear Guest, your item(s) will be available for pick-up at the Campus Store front desk. Identification will be required for pick-up.
                                                </h4>
                                             </v-alert>
                                          </v-container>
                                       </v-row>
                                    </v-container>

                                    <v-container>
                                       <v-row>
                                          <p id="paymentInfoTitle">
                                             PAYMENT INFORMATION
                                          </p>

                                          <v-container>
                                             <v-divider></v-divider>
                                          </v-container>

                                          <v-flex md12>
                                             <h4 id="cardErrorMsg">{{userInfo.cardError}}</h4>
                                          </v-flex>
                                       </v-row>

                                       <v-form ref="form" v-model="valid" style="width: 100%;" @change.native="validFormChange" @click.native="validFormChange">
                                          <v-row>
                                             <v-flex xs12 sm12 md8>
                                                <v-container>
                                                   <v-text-field
                                                      v-model="userData.recipientName"
                                                      maxlength="20"
                                                      type="text"
                                                      label="Recipient"
                                                      placeholder="E.g. Michael Geary"
                                                      color="gray"
                                                      @change.native="validFormChange"
                                                      counter
                                                      outlined
                                                      clearable
                                                      dense
                                                   />
                                                </v-container>
                                             </v-flex>
                                          </v-row>
                                          <v-row>
                                             <v-flex xs12 sm12 md8>
                                                <v-container>
                                                   <v-text-field
                                                      v-model="userData.fullName"
                                                      :rules="recipientNameRules"
                                                      maxlength="20"
                                                      type="text"
                                                      label="Full Name"
                                                      placeholder="E.g. Michael Geary"
                                                      color="gray"
                                                      @change.native="validFormChange"
                                                      counter
                                                      required
                                                      outlined
                                                      clearable
                                                      dense
                                                   ></v-text-field>
                                                </v-container>
                                             </v-flex>
                                          </v-row>
                                          <v-row>
                                             <v-flex xs12 sm12 md8>
                                                <v-container>
                                                   <v-text-field
                                                         id="ccNumber"
                                                         v-model.trim="userInfo.ccNumber"
                                                         v-model="userInfo.ccNumber"
                                                         v-mask="creditCardNumber.mask"
                                                         :rules="creditCardNumberRules"
                                                         label="Credit Card Number"
                                                         placeholder="E.g. 1234 5678 9012 3456"
                                                         color="gray"
                                                         required
                                                         outlined
                                                         clearable
                                                         dense
                                                   ></v-text-field>
                                                </v-container>
                                             </v-flex>
                                          </v-row>
                                          <v-row>
                                             <v-flex sm12 md8>
                                                <v-container>
                                                   <v-text-field
                                                      id="cardholderName"
                                                      v-model.trim="userInfo.ccName"
                                                      v-model="userInfo.ccName"
                                                      :rules="recipientNameRules"
                                                      label="Cardholder name"
                                                      placeholder="Team Persevere"
                                                      value="Team Persevere"
                                                      color="gray"
                                                      required
                                                      outlined
                                                      clearable
                                                      dense
                                                   ></v-text-field>
                                                </v-container>
                                             </v-flex>
                                          </v-row>

                                          <v-row>
                                             <v-flex sm6 md4>
                                                <v-container>
                                                   <v-text-field
                                                      id="ccDate"
                                                      v-model.trim="userInfo.ccDate"
                                                      v-model="userInfo.ccDate"
                                                      v-mask="expirationDate.mask"
                                                      :rules="expirationDateRules"
                                                      label="Expriation date"
                                                      placeholder="MM/YY"
                                                      color="gray"
                                                      required
                                                      outlined
                                                      clearable
                                                      dense
                                                   ></v-text-field>
                                                </v-container>
                                             </v-flex>
                                             <v-flex sm6 md4>
                                                <v-container>
                                                   <v-text-field
                                                      id="userEdits.cvcNumber"
                                                      v-model.trim="userInfo.cvcNumber"
                                                      v-model="userInfo.cvcNumber"
                                                      v-mask="cvcNumber.mask"
                                                      :rules="cvcNumberRules"
                                                      label="CVC number"
                                                      placeholder="E.g. 123"
                                                      color="gray"
                                                      required
                                                      outlined
                                                      clearable
                                                      dense
                                                   ></v-text-field>
                                                </v-container>
                                             </v-flex>
                                          </v-row>
                                       </v-form>
                                    </v-container>
                                 </div>

                                 <div v-else>
                                    <v-row>
                                       <v-container>
                                          <v-container>
                                             <div>
                                                <v-flex sm6 md6 xl6>
                                                   <p id="deliveryTypeTxt">
                                                         Choose delivery type:
                                                   </p>
                                                </v-flex>

                                                <!-- Choose delivery type -->
                                                <v-flex sm12 md12 xl12>
                                                   <v-radio-group v-model="deliveryDetails" row :mandatory="true">
                                                      <v-radio v-model="myAddress" label="My address" name="active" color="#E77F2C" default checked="true" @click.native="showDeliveryForm = false, showPickupForm = false, isDeliveryInfoSaved = false, deliveryTypeCheck = true, valid = false"></v-radio>
                                                      <v-radio v-model="storePickup" label="Store pick-up" name="active" color="#E77F2C" @click.native="showDeliveryForm = false, showPickupForm = true, isDeliveryInfoSaved = false, deliveryTypeCheck = true, valid = false"></v-radio>
                                                      <v-radio v-model="editLocation" label="Choose another location" name="active" color="#E77F2C" @click.native="showDeliveryForm = true, showPickupForm = false, isDeliveryInfoSaved = false, deliveryTypeCheck = false"></v-radio>
                                                   </v-radio-group>
                                                   <v-alert text color="#003B63">
                                                         <h4 style="text-align: left; color: #003B63;">{{ deliveryDetails || null }}</h4>
                                                   </v-alert>
                                                </v-flex>
                                                </div>

                                             <!-- Custom delivery address form -->
                                             <div v-if="showDeliveryForm === true && isDeliveryInfoSaved === false">
                                                <v-form ref="form" v-model="valid" style="width: 100%;" @change.native="validFormChange" @click.native="validFormChange">
                                                   <v-row>
                                                      <v-flex xs12 sm12 md5>
                                                         <v-container>
                                                            <v-text-field
                                                               label="Recipient name"
                                                               placeholder="E.g. Michael Geary"
                                                               v-model="userData.recipientName"
                                                               :rules="recipientNameRules"
                                                               color="#E77F2C"
                                                               counter
                                                               maxlength="25"
                                                               dense
                                                               outlined
                                                               clearable
                                                               required
                                                            ></v-text-field>
                                                         </v-container>
                                                      </v-flex>

                                                      <v-flex xs12 sm7 md4>
                                                         <v-container>
                                                            <v-select
                                                               :items="dormList"
                                                               :rules="recipientNameRules"
                                                               placeholder="Select dorm"
                                                               v-model="dormSelected"
                                                               color="#E77F2C"
                                                               item-value="text"
                                                               style="font-size: 14.3px;"
                                                               dense
                                                               outlined
                                                            ></v-select>
                                                         </v-container>
                                                      </v-flex>
                                                      <v-flex xs12 sm5 md3>
                                                         <v-container>
                                                            <v-text-field
                                                               label="Room no."
                                                               placeholder="E.g. 1234"
                                                               :rules="roomNumberRules"
                                                               v-model="userData.roomNumber"
                                                               v-mask="roomNumberMask.mask"
                                                               number
                                                               color="#E77F2C"
                                                               dense
                                                               outlined
                                                               clearable
                                                               required
                                                            ></v-text-field>
                                                         </v-container>
                                                      </v-flex>
                                                   </v-row>
                                                </v-form>
                                             </div>
                                          </v-container>
                                       </v-container>
                                    </v-row>
                                 </div>

                                 <!-- Add Funds heading -->
                                 <div v-if="isGuest === false">
                                       <p id="addFundsTitle">
                                          ADD FUNDS
                                       </p>

                                       <v-container>
                                          <v-divider></v-divider>
                                       </v-container>

                                       <v-flex md12 v-if="isGuest === false">
                                          <v-container>
                                             <h3 id="acctBalance">ACCOUNT BALANCE: ${{userInfo.balance}}</h3>
                                          </v-container>
                                       </v-flex>

                                       <!-- Add funds button -->
                                       <v-container>
                                       <v-row>
                                          <v-flex md4>
                                             <v-container>
                                                <v-btn id="btnAddFunds" @click="addFunds.dialog = !addFunds.dialog" text block height="55">
                                                   ADD FUNDS
                                                </v-btn>
                                             </v-container>
                                          </v-flex>
                                       </v-row>
                                       </v-container>
                                 </div>
                              </v-flex>
                           </v-card>
                        </v-container>
                     </v-flex>

                     <!-- Order summary & Coupon -->
                     <v-flex sm12 md4>
                        <v-container>
                           <v-card flat elevation="0">

                              <!-- Apply coupon heading -->
                              <div>
                                 <p id="applyCoupon">
                                    APPLY COUPON
                                 </p>

                                 <v-container>
                                    <v-divider></v-divider>
                                 </v-container>

                                 <!-- Coupon form -->
                                 <v-container>
                                    <v-row justify="center">
                                       <span style="color: red;" class="errorMessageLabel">{{couponError}}</span>
                                    </v-row>
                                    <v-row>
                                       <v-flex md8>
                                          <v-container>
                                             <v-text-field
                                                label="Enter coupon"
                                                v-model="coupon"
                                                counter
                                                maxlength="10"
                                                style="border-width: 5px;"
                                                color="gray"
                                                :disabled="false"
                                                hide-details
                                                single-line
                                                outlined
                                                clearable
                                             ></v-text-field>
                                          </v-container>
                                       </v-flex>
                                       <v-flex md4>
                                          <v-container>
                                             <v-btn
                                                :disabled="false"
                                                text
                                                block height="55"
                                                style="background: white; color: #900C3E; border-style: solid; border-color: #900C3E;"
                                                @click="applyCoupon()"
                                             >
                                                APPLY
                                             </v-btn>
                                          </v-container>
                                       </v-flex>
                                    </v-row>

                                    <!-- Invalid coupon message -->
                                    <v-row>
                                       <v-container>
                                          <h4 style="color: red; text-align: left;"></h4>
                                       </v-container>
                                    </v-row>
                                 </v-container>
                              </div>

                              <!-- Order Summary -->
                              <p id="orderSummaryTitle">
                                 ORDER SUMMARY
                              </p>

                              <v-container>
                                 <v-divider></v-divider>
                              </v-container>

                              <v-container>
                                 <v-row>
                                    <v-flex sm6 md6>
                                       <v-container>
                                          <p id="priceInfo">
                                                Subtotal:
                                          </p>
                                          <p id="priceInfo">
                                                Tax (7%):
                                          </p>
                                          <p id="priceTotal">
                                                TOTAL:
                                          </p>
                                       </v-container>
                                    </v-flex>
                                    <v-flex sm6 md6>
                                       <v-container>
                                          <p id="orderSummaryInfo">
                                                ${{orderSummaryInfo.subtotal}}
                                          </p>
                                          <p id="orderSummaryInfo">
                                                ${{orderSummaryInfo.taxTotal}}
                                          </p>
                                          <p id="orderTotalPrice">
                                                ${{orderSummaryInfo.totalPrice}}
                                          </p>
                                       </v-container>
                                    </v-flex>
                                 </v-row>
                              </v-container>

                              <v-spacer></v-spacer>

                              <!-- Place order as guest or user -->
                              <v-container v-if="!isGuest && deliveryTypeCheck">
                                 <v-btn
                                    text
                                    block
                                    height="50"
                                    id="checkoutBtn"
                                    style=""
                                    @click="placeOrder"
                                    :disabled="valid"
                                 >
                                    PLACE YOUR ORDER
                                 </v-btn>
                              </v-container>
                              <v-container v-if="!deliveryTypeCheck && !isGuest">
                                 <v-btn
                                    text
                                    block
                                    height="50"
                                    id="checkoutBtn"
                                    @click="placeOrder"
                                    :disabled="!valid"
                                 >
                                       PLACE YOUR ORDER
                                 </v-btn>
                              </v-container>
                              <v-container v-if="isGuest">
                                 <v-btn
                                    text
                                    block
                                    height="50"
                                    id="checkoutBtn"
                                    @click="placeOrder"
                                    :disabled="!valid"
                                 >
                                       PLACE YOUR ORDER
                                 </v-btn>
                              </v-container>
                           </v-card>
                        </v-container>
                     </v-flex>
                  </v-row>
               </v-container>
            </v-card>
         </v-container>

        <!-- Add funds dialog box -->
        <template>
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
               userInfo.cvcNumber = ''
               userInfo.cardError = ''"
            >
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
                                 userInfo.cvcNumber = ''
                                 userInfo.cardError = ''"
                        >
                        X
                        </v-btn>
                     </v-row>
                  </v-col>
                  <v-container>
                     <v-row>
                        <v-flex md12>
                           <h3 style="font-family: Arial;">Add funds to your account</h3>
                        </v-flex>
                     </v-row>
                     <v-row justify="center">
                        <v-container>
                           <h3 style="color: red;" class="errorMessageLabel">{{userInfo.cardError}}</h3>
                        </v-container>
                     </v-row>
                  </v-container>
                  <v-container>
                     <v-form ref="form" v-model="addFundsFormCheck" style="width: 100%;" @change.native="validFormChange" @click.native="validFormChange">
                        <v-container>
                           <v-flex md12>
                              <v-row>
                                 <v-container>
                                    <v-text-field
                                       id="ccNumber"
                                       v-model.trim="userInfo.ccNumber"
                                       v-model="userInfo.ccNumber"
                                       v-mask="creditCardNumber.mask"
                                       label="Credit Card Number"
                                       placeholder="Ex. 1234 5678 9012 3456"
                                       color="#F7882F"
                                       :rules="creditCardNumberRules"
                                       required
                                       outlined
                                       clearable
                                       dense
                                    ></v-text-field>
                                 </v-container>
                              </v-row>
                           </v-flex>
                           <v-flex md12>
                              <v-row>
                                 <v-container>
                                    <v-text-field
                                       id="cardholderName"
                                       v-model.trim="userInfo.ccName"
                                       v-model="userInfo.ccName"
                                       label="Cardholder name"
                                       placeholder="Team Persevere"
                                       value="Team Persevere"
                                       color="#F7882F"
                                       :rules="recipientNameRules"
                                       required
                                       outlined
                                       clearable
                                       dense
                                    ></v-text-field>
                                 </v-container>
                              </v-row>
                           </v-flex>
                           <v-row>
                              <v-flex md6>
                                 <v-container>
                                    <v-text-field
                                       id="ccDate"
                                       v-model.trim="userInfo.ccDate"
                                       v-model="userInfo.ccDate"
                                       v-mask="expirationDate.mask"
                                       label="Expriation date"
                                       placeholder="MM/YY"
                                       color="#F7882F"
                                       :rules="expirationDateRules"
                                       required
                                       outlined
                                       clearable
                                       dense
                                    ></v-text-field>
                                 </v-container>
                              </v-flex>
                              <v-flex md6>
                                 <v-container>
                                    <v-text-field
                                       id="userEdits.cvcNumber"
                                       v-model.trim="userInfo.cvcNumber"
                                       v-model="userInfo.cvcNumber"
                                       v-mask="cvcNumber.mask"
                                       label="CVC number"
                                       placeholder="Ex. 123"
                                       color="#F7882F"
                                       :rules="cvcNumberRules"
                                       required
                                       outlined
                                       clearable
                                       dense
                                    ></v-text-field>
                                 </v-container>
                              </v-flex>
                           </v-row>
                           <v-flex md12>
                              <v-row>
                                 <v-container>
                                    <v-text-field
                                       id="userInfo.userAmount"
                                       v-model.trim="userInfo.userAmount"
                                       v-model="userInfo.userAmount"
                                       name="userAmount"
                                       label="Amount"
                                       placeholder="0.00"
                                       color="#F7882F"
                                       prefix="$"
                                       style="font-size: 25px; margin: auto; width: 275px;"
                                       :rules="recipientNameRules"
                                       require
                                       outlined
                                       clearable
                                       @keypress="onlyForCurrency"
                                    ></v-text-field>
                                 </v-container>
                              </v-row>
                           </v-flex>
                           <v-flex md12>
                              <v-card-actions>
                                 <v-btn
                                       id="addFundsBtn"
                                       text
                                       :disabled="!addFundsFormCheck"
                                       @click="addAccountFunds(), forceRerender"
                                       >Add
                                 </v-btn>
                              </v-card-actions>
                           </v-flex>
                           <v-row>
                              <span id="balanceAmount"><br/>${{userInfo.balance}}</span>
                           </v-row>
                        </v-container>
                  </v-form>
               </v-container>
            </v-card>
         </v-dialog>
      </template>

        <v-snackbar
            id="couponSnackBar"
            :timeout="couponCode.timeout"
            v-model="couponCode.snackbar"
            color="green"
            top
            >
            {{ couponCode.text }}
            <v-btn text icon height="50px" width="50px" color="white" @click.native="couponCode.snackbar = false">x</v-btn>
        </v-snackbar>

        <v-snackbar
            id="addFundsSnackBar"
            :timeout="addFunds.timeout"
            v-model="addFunds.snackbar"
            color="green"
            top
            >
            {{ addFunds.text }}
            <v-btn text icon height="50px" width="50px" color="white" @click.native="addFunds.snackbar = false">x</v-btn>
        </v-snackbar>

        <!-- Checkout notifications -->
        <v-snackbar
            :timeout= "checkout.timeout"
            v-model= "checkout.snackbar"
            color="red"
            center
            top
        >
            {{ checkout.error }}
            <v-btn
               text
               icon
               height="50px"
               width="50px"
               color="white"
               @click.native= "checkout.snackbar = false"
            >
                x
            </v-btn>
        </v-snackbar>
    </v-app>
</template>

<script>
import { mdiCheckBold } from '@mdi/js';
import { mdiNumeric2 }  from '@mdi/js';
import { mdiNumeric3 }  from '@mdi/js';
import { mdiMoonFull }  from '@mdi/js';
import { mdiWallet }    from '@mdi/js';
import { mdiCart }      from '@mdi/js';
import { mask }         from 'vue-the-mask';
import checkoutApi      from './js/Checkout-api'
import headerApi        from './js/Header-api'
import userApi          from './js/User-api'

export default {
    directives: {
        mask,
    },

    data () {
      return {
          userData: {
              fullName: '',
              recipientName: '',
              roomNumber: ''
          },

          cart: {
              dialog: false,
          },

          addFunds: {
              dialog: false,
              snackbar: false,
              y: 'top',
              x: null,
              mode: '',
              timeout: 4000,
              text: '',
          },

          // Checkout snackbar notifications
          checkout: {
              snackbar: false,
              check: true,
              y: 'top',
              x: null,
              mode: '',
              timeout: 4000,
              error: '',
          },

          componentKey: 0,

         // Icons Display
          checkmarkProgressIcon: mdiCheckBold,
          circleIcon:            mdiMoonFull,
          numberTwoIcon:         mdiNumeric2,
          numberThreeIcon:       mdiNumeric3,
          walletIcon:            mdiWallet,
          cartIcon:              mdiCart,

          valid: false,

          validGuestPayment: false,

          deliveryTypeCheck: true,

          addFundsFormCheck: true,

          showDeliveryForm: null,

          showPickupForm: false,

          isGuest: true,

          isDeliveryInfoSaved: null,

          leaveCheckoutPopup: true,

          roomNumberRules: [
              value => !!value || 'Required.',
              value => (value && value.length >= 4) || 'Minimum 4 characters',
          ],

          creditCardNumberRules: [
              value => !!value || 'Required.',
              value => (value && value.length >= 19) || 'Minimum 16 characters',
          ],

          expirationDateRules: [
              value => !!value || 'Required.',
              value => (value && value.length >= 5) || 'Minimum 4 characters',
          ],

          cvcNumberRules: [
              value => !!value || 'Required.',
              value => (value && value.length >= 3) || 'Minimum 3 characters',
          ],

          recipientNameRules: [
             value => !!value || 'Required.'
          ],

          radios: 'radio-1',

          dormSelected: '',

          dormList: [
             'Ballard',
             'Coberly',
             'Young',
             'Dixon',
             'Bradley',
             'Dixon',
             'Rice',
             'Campus House',
          ],

          deliveryDetails: '',

          myAddress: '',

          storePickup: 'Pick-up at the Campus Store desk.',

          editLocation: 'Recipient\'s delivery details',

          userInfo: {
                balance: '',
                userAmount: '',
                ccNumber: '',
                ccName: '',
                ccDate: '',
                cvcNumber: '',
                cardError: '',
          },

          orderSummaryInfo: {
              subtotal: '',
              taxTotal: '',
              totalPrice: '',
          },

          coupon: '',

          couponEntered: 'false',

          couponError: '',

          couponCode: {
              snackbar: false,
              y: 'top',
              x: null,
              mode: '',
              timeout: 4000,
              text: '',
          },

          error: {
              snackbar: false,
              y: 'top',
              x: null,
              mode: '',
              timeout: 4000,
              text: '',
          },

          // Room number mask
          roomNumberMask: {
              mask: '####',
              value: '',
          },

          // Credit card number mask
            creditCardNumber: {
                mask: '#### #### #### ####',
                value: '',
          },

          // Expiration date mask
          expirationDate: {
              mask: '##/##',
              value: '',
          },

          // CVC number mask
          cvcNumber: {
              mask: '###',
              value: '',
          },
      }
    },

    created() {
       // Get Active User Information.
        headerApi.getActiveUser()
            .then(response => {
                if(response.data.firstname != 'guest') {
                    checkoutApi.getDeliveryInfo()
                        .then(response => {
                            this.myAddress       = response.data.name + ', ' + "\n\n\n" +
                                                   response.data.deliveryAddress
                            this.deliveryDetails = response.data.name + ', ' + "\n\n\n" +
                                                   response.data.deliveryAddress
                            })
                    checkoutApi.getSummaryInfo()
                        .then(response => {
                            this.orderSummaryInfo.subtotal   = response.data.subtotal
                            this.orderSummaryInfo.taxTotal   = response.data.taxTotal
                            this.orderSummaryInfo.totalPrice = response.data.totalCost
                            this.couponEntered = response.data.couponEntered
                        })

                    this.userInfo.balance = response.data.balance
                    this.isGuest = false

                } else {
                    checkoutApi.getSummaryInfo()
                        .then(response => {
                            this.orderSummaryInfo.subtotal   = response.data.subtotal
                            this.orderSummaryInfo.taxTotal   = response.data.taxTotal
                            this.orderSummaryInfo.totalPrice = response.data.totalCost
                            this.couponEntered = response.data.couponEntered
                        })
                    this.isGuest = true
                    this.userInfo.balance = '0.00'
                }
            })
            .catch(
                this.isGuest = false,
                this.userInfo.balance = '0.00'
            )
    },

   methods: {
      /* eslint-disable */

      validFormChange() {
         window.addEventListener('beforeunload', this.confirmLeaving);
      },

      confirmLeaving (evt) {
         if (this.leaveCheckoutPopup) {
               const unsavedChangesWarning = "You have unsaved changes. Are you sure you wish to leave?";
               evt.returnValue = unsavedChangesWarning;
               return unsavedChangesWarning;
         }
      },

      validFormRemoveHandler() {
         this.leaveCheckoutPopup = false;
         window.location.href = '/confirmation';
      },

      onlyForCurrency ($event) {
         let keyCode = ($event.keyCode ? $event.keyCode : $event.which);

         // Only allow number and one dot.
         if ((keyCode < 48 || keyCode > 57) && (keyCode !== 46 || this.userInfo.userAmount.indexOf('.') != -1)) {
         $event.preventDefault();
         }

         // Restrict to 2 decimal places.
         if(this.userInfo.userAmount!=null && this.userInfo.userAmount.indexOf(".")>-1 && (this.userInfo.userAmount.split('.')[1].length > 1)
               && $event.target.selectionStart > this.userInfo.userAmount.indexOf(".")){
         $event.preventDefault();
         }
      },

      removeAuthCheckout() {
         checkoutApi.removeAuthentication()
         .then(
               window.location.href = '/cart'
         )
      },
      removeAuthHome() {
         checkoutApi.removeAuthentication()
         .then(
               window.location.href = '/'
         )
      },

      placeOrder() {
         if (this.showDeliveryForm === true) {
               checkoutApi.setAlternateInfo(this.dormSelected, this.userData.roomNumber,
                                          this.userData.recipientName)
                  .then(response => {
                     if (response.data.error == null) {
                        userApi.checkoutAsUser()
                           .then(response => {
                               if (response.data.error == null) {
                                    this.validFormRemoveHandler()
                               } else {
                                    this.checkout.error = response.data.error
                                    this.checkout.snackbar = true
                                }
                           })
                     } else {
                         this.checkout.error = response.data.error
                         this.checkout.snackbar = true
                     }
                  })
         } else if (this.showPickupForm === true) {
               checkoutApi.getPickupInfo()
                  .then(
                     userApi.checkoutAsUser()
                           .then(response => {
                              if (response.data.error == null) {
                                 this.validFormRemoveHandler()
                              } else {
                                 this.checkout.error = response.data.error
                                 this.checkout.snackbar = true
                              }
                           })
                           .catch (
                              this.$router.push("/Error500")
                           )
                  )
            } else if (this.showPickupForm === true) {
                  checkoutApi.getPickupInfo()
                     .then(
                        userApi.checkoutAsUser()
                           .then(response => {
                              if (response.data.error == null) {
                                 this.validFormRemoveHandler()
                              } else {
                                 this.checkout.error = response.data.error
                                 this.checkout.snackbar = true
                              }
                           })
                           .catch (
                              this.$router.push("/Error500")
                        )
                     )
            } else if (this.isGuest === false) {
                userApi.checkoutAsUser()
                  .then(response => {
                     if (response.data.error == null) {
                           this.validFormRemoveHandler()
                     } else {
                           this.checkout.error = response.data.error
                           this.checkout.snackbar = true
                     }
                  })
                  .catch(
                     this.$router.push("/Error500")
                  )
            } else if (this.isGuest === true) {
                userApi.checkoutAsGuest(this.userInfo.ccNumber,  this.userInfo.ccName,
                                        this.userInfo.ccDate,    this.userInfo.cvcNumber,
                                        this.userData.fullName,  this.userData.recipientName)
                    .then(response => {
                        if (response.data.error == null) {
                            this.validFormRemoveHandler()
                        } else {
                            this.checkout.error = response.data.error
                            this.checkout.snackbar = true
                        }
                    })
                   .catch(error =>
                        this.checkout.error = response.data.error,
                        this.checkout.snackbar = true
                    )
            }
        },

        addAccountFunds() {
            headerApi.addFunds(this.userInfo.userAmount, this.userInfo.ccNumber,
                this.userInfo.ccName, this.userInfo.ccDate, this.userInfo.cvcNumber)
            .then(response => {
                if (response.data.error == null) {
                    this.userInfo.balance = response.data.balance
                    this.userInfo.cardError = ''
                    this.addFunds.text = 'Funds added successfully.'
                    this.addFunds.snackbar = true
                    this.userInfo.cardError = ''
                } else
                    this.userInfo.cardError = response.data.error
            })
        },

        applyCoupon() {
            checkoutApi.getCouponTotal(this.coupon)
            .then(response => {
                if (response.data.error != null)
                    this.couponError = response.data.error
                else {
                    if (this.couponEntered == "false") {
                        this.orderSummaryInfo.subtotal   = response.data.subtotal
                        this.orderSummaryInfo.taxTotal   = response.data.taxTotal
                        this.orderSummaryInfo.totalPrice = response.data.totalCost
                        this.couponEntered       = response.data.couponEntered
                        this.couponCode.text     = "Coupon entered successfully."
                        this.couponCode.snackbar = true
                        this.couponError = ''
                    }
                    else {
                        this.error.text     = response.data.error
                        this.error.snackbar = true
                    }
                }
            })
        },

      validate () {
         if (this.$refs.form.validate()) {
            this.snackbar = true
         }
      },

      submitDeliveryForm () {
         this.$refs.observer.validate()
      },

      // Reload vue component.
      forceRerender() {
         this.componentKey += 1;
      }
   }
}
</script>

<!-- CSS -->
<style scoped src="./css/CheckoutStyles.css">

</style>