import Home       from './components/Home.vue'
import SignUp     from './components/SignUp.vue'
import SignIn     from './components/SignIn.vue'
import Wishlist   from './components/Wishlist.vue'
import Cart       from './components/Cart.vue'
import headerApi  from './components/js/Header-api.js'
import Product    from './components/ProductPage.vue'
import Result     from './components/ResultPage.vue'
import About      from './components/About.vue'
import Error404   from './components/Error400.vue'
import Error500   from './components/Error500.vue'
import Confirm    from './components/Confirmation.vue'
import confirmApi from './components/js/Confirmation-api.js'
import Checkout   from './components/Checkout.vue'
import checkoutApi from './components/js/Checkout-api.js'
import errorApi    from './components/js/Error-api.js'
import PreviousOrders   from './components/PreviousOrders.vue'
/* eslint-disable */

const routes = [
    {
        path : '/',
        component : Home
    },

    {
        path : '/404',
        component : Error404,
    },

    {
        path : '/500',
        component : Error500
    },

    {
        path : '/product/:id',
        component : Product,
    },

    {
        path : '/results/:product',
        component : Result
    },

    {
        path : '/results/:firstRange/:lastRange',
        component : Result
    },

    {
        path : '/cart',
        component : Cart
    },

    {
        path : '/home',
        component : Home
    },

    {
        path : '/signin',
        component : SignIn,
        beforeEnter: (to, from, next) => {
            headerApi.getActiveUser()
            .then(response => {
                if (response.data.error != null) {
                    next(true)
                } else if (response.data.firstname != 'guest')
                    next('/404')
                else
                    next(true)
            })
            .catch(error => {
                next(true)
            }),
             errorApi.handleError()
             .then(response => {
                 if (response.data.statusCode == 'error404')
                 next('/404')
                 else if(response.data.statusCode == 'error500')
                     next('/500')
                 else
                     next(true)
                    })
        }
    },

    {
        path : '/signup',
        component : SignUp,
        beforeEnter: (to, from, next) => {
            headerApi.getActiveUser()
            .then(response => {
                if (response.data.error != null) {
                    next(true)
                } else if (response.data.firstname != 'guest')
                    next('/404')
                else
                next(true)
                })
                .catch(error => {
                    next(true)
                }),
             errorApi.handleError()
             .then(response => {
                 if (response.data.statusCode == 'error404')
                 next('/404')
                 else if(response.data.statusCode == 'error500')
                     next('/500')
                     else
                     next(true)
             })
            }
    },

    {
        path : '/about',
        component : About,
    },

    {
        path : '/checkout',
        component : Checkout,
         beforeEnter: (to, from, next) => {
             checkoutApi.getAuthentication()
             .then(response => {
                 if (response.data === 'approve')
                     next(true)
                 else
                     next('/404')
                 })
                 .catch(error => {
                     next('/404')
                 }),

                 errorApi.handleError()
             .then(response => {
                 if (response.data.statusCode == 'error404')
                     next('/404')
                     else if(response.data.statusCode == 'error500')
                     next('/500')
                 else
                 next(true)
             })
         }
        },

    {
        path : '/wishlist',
        component : Wishlist,
        beforeEnter: (to, from, next) => {
            headerApi.getActiveUser()
             .then(response => {
                if (response.data.firstname == 'guest')
                next('/404')
                else
                next(true)
             })
             .catch(error => {
                 if(from.path === '/wishlist')
                    next('/')
                    else
                    next('/404')
                }),
                errorApi.handleError()
                .then(response => {
                    if (response.data.statusCode == 'error404')
                    next('/404')
                    else if(response.data.statusCode == 'error500')
                        next('/500')
                    else
                        next(true)
                    })
            }
    },

    {
        path: '/orders',
        component : PreviousOrders,
        beforeEnter: (to, from, next) => {
            headerApi.getActiveUser()
            .then(response => {
                if(response.data.firstname == null)
                    validUserCheck = false;
                else
                    validUserCheck = true;

               // Guest user
               if (response.data.firstname == null && validUserCheck == false) {
                   next('/404');
               }

               // Valid user
               if(response.data.firstname == null || validUserCheck === true) {
                  next(true);
               }
            })
            .catch(error => {
                if(response.data.error != null)
                    next('/')
                else
                    next('/404')
               })
                errorApi.handleError()
                .then(response => {
                    if (response.data.statusCode == 'error404')
                        next('/404')
                    else if(response.data.statusCode == 'error500')
                        next('/500')
                    else
                        next(true)
                })
        }
    },

    {
        path : '/confirmation',
        component : Confirm,
        beforeEnter: (to, from, next) => {
            confirmApi.getAuthentication()
            .then(response => {
                if (response.data === 'approve')
                    next(true)
                else
                    next('/404')
                })
            .catch(error => {
                next('/404')
            }),
            errorApi.handleError()
            .then(response => {
                if (response.data.statusCode == 'error404')
                    next('/404')
                else if(response.data.statusCode == 'error500')
                    next('/500')
                else
                    next(true)
             })
        }
    },

     {
          path : '/error',
          component : Error404,
          beforeEnter: (to, from, next) => {
             errorApi.handleError()
             .then(response => {
                 if (response.data.statusCode == 'error404')
                     next('/404')
                 else if(response.data.statusCode == 'error500')
                     next('/500')
                 else
                     next(true)
             })
             .catch(error => {
                 next(true)
             })
         }
     },
];

var validUserCheck = false;

export default routes;
