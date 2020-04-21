<template>
    <v-container>
        <v-flex xs12 sm12 md12 lg12 xl12>
            <h1 style="margin-top: 80px; font-size: 30px; text-align: center; font-family: Arial, Helvetica, sans-serif; margin: 10px;">{{ similarItemsHeading() }}</h1>
            <v-sheet v-if= "loadedSimilarData"
                class="mx-auto"
                elevation="0"
                max-width="1900"
            >
                <v-slide-group
                    style="background: #EEEEEE;"
                    v-model="model"
                    class="pa-4"
                    active-class="success"
                    prev-icon="mdi-arrow-left"
                    next-icon="mdi-arrow-right"
                    show-arrows
                >
                    <v-slide-item
                        v-for="(id, product) in this.similarItems"
                        :key="id"
                    >
                    <v-card
                        id="featuredItemCard"
                        :color="white ? undefined : 'white'"
                        class="ma-4"
                        max-width="250"
                        max-height="475"
                        min-width="250"
                        min-height="375"
                        hover
                        @click="directProduct(product)"
                    >
                        <v-container>
                            <v-img
                                class="white--text align-end"
                                height="auto"
                                width="auto"
                                :src="similarItemImage(product)">
                            </v-img>
                        </v-container>
                        <v-container>
                            <h1 id="similarItemsName" style="background: white; overflow: auto;">
                                {{similarItemName(product)}}
                            </h1>
                        </v-container>
                        <v-container>
                            <span style="height: 40px;
                                        font-family: Century Gothic;
                                        text-align: center;
                                        background: #F7882F;
                                        color: white;
                                        font-weight: bold;
                                        font-size: 25px;
                                        position: absolute;
                                        bottom: 0;
                                        right: 0;
                                        left: 0;
                                        bottom: 0;
                                        text-align: center;"
                                        >
                                ${{similarItemPrice(product)}}
                            </span>
                        </v-container>

                        </v-card>
                    </v-slide-item>
                </v-slide-group>
            </v-sheet>
        </v-flex>

        <v-snackbar
            :timeout="addToCart.timeout"
            v-model="addToCart.snackbar"
            color="#E77F2C"
            top
            >
            {{ addToCart.text }}
            <v-btn text icon height="50px" width="50px" color="white" @click.native="addToCart.snackbar = false">x</v-btn>
        </v-snackbar>
    </v-container>
</template>

<script>
import cartApi               from './js/Cart-api'
import { eventBus }          from '../main'
import { mdiCartPlus }       from '@mdi/js';

export default {

    mounted() {
        this.getSimilarItems()

        eventBus.$on('items-update', (data) => {
            if(data) {
                this.getSimilarItems()
            }
        })
    },

    data() {
        return{
            loadedSimilarData: false,
            similarItemsText: 'SIMILAR ITEMS',

            icons: {
                addToCartIcon: mdiCartPlus
            },


            // Add to cart snackbar notification.s
            addToCart: {
                snackbar: false,
                y: 'top',
                x: null,
                mode: '',
                timeout: 4000,
                text: '',
            },
        }
    },

    methods: {
        /* eslint-disable */

        similarItems: [],

        getSimilarItems() {
            cartApi.getSimilarItems(15)
                .then(response => {
                    this.similarItems = response.data
                    this.loadedSimilarData = true
                })
        },

        // Similar items
        similarItemName(item) {
            return this.similarItems[item].productName
        },

        similarItemImage(item) {
            return this.similarItems[item].image
        },

        similarItemPrice(item) {
            return this.similarItems[item].price
        },

        // Similar items heading
        similarItemsHeading() {
            if(this.cartQuantity === 0)
                return ''
            else
                return 'SIMILAR ITEMS'
        },

        directProduct(item) {
            this.$router.push("/product/" + this.similarItems[item].productId);
        },
    }
}
</script>

<style scoped>
#similarItemsName {
    font-size: 15px;
    font-family: 'Century Gothic', 'Segoe UI', Verdana, sans-serif;
    margin-bottom: 10px;
    font-weight: 1000;
}

#featuredItemCard {
    background-color: rgba(185, 181, 181, 0.979) !important;
    background: white !important;
}

</style>