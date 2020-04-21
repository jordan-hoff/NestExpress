<template>
    <footer>
        <v-row>
            <v-flex>
                <v-icon color="black">{{copyrightIcon}}</v-icon>
                <span>{{footerText}}</span>
            </v-flex>
        </v-row>
    </footer>
</template>

<script>
import { mdiCopyright } from '@mdi/js';

export default {
      data(){
         return {
            footerText: 'NestExpress',
            copyrightIcon: mdiCopyright,
         }
      }
   }
</script>

<style>
    footer {
      padding: 20px;
      text-align: center;
      height: 50px;
      color: black;
      font-size: 20px;
      margin-bottom: 20px;
    }
</style>