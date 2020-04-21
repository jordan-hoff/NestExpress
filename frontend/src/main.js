import Vue from 'vue'
import App from './App.vue'
import vuetify from './plugins/vuetify';
import 'roboto-fontface/css/roboto/roboto-fontface.css'
import '@mdi/font/css/materialdesignicons.css'
import VueRouter from 'vue-router'
import Vuelidate from 'vuelidate'
import routes from './routes.js'
import VueResource from 'vue-resource'

/* eslint-disable */

// Add VueResource
Vue.use(VueResource)

// Add Vuetify
Vue.use(vuetify)

// Add VueRouter
Vue.use (VueRouter)

// Add Vuelidate
Vue.use (Vuelidate)

// Bussing data between components
export const eventBus = new Vue()

const router = new VueRouter({routes, mode : 'history'});

Vue.config.productionTip = false;

Vue.use(Vuelidate)

new Vue({
  vuetify,
  router, // Add router, to allow navigation
  render: h => h(App)
}).$mount('#app')
