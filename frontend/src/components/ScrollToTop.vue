<template>

   <!-- 'To-top' scroll button -->
   <v-btn
      v-scroll="onScroll"
      v-on="on"
      v-show="fab"
      color="gray"
      @click="toTop"
      style="background: #D7D7D7 !important;"
      fab
      text
      fixed
      bottom
      right
      >
      <v-icon style="background: transparent !important;">keyboard_arrow_up</v-icon>
   </v-btn>

</template>

<script>
export default {
   data() {
      return {
         fab: false,
      }
    },

   methods: {
      /* eslint-disable */

      // Scroll to the top.
      onScroll (e) {
        if (typeof window === 'undefined') return
        const top = window.pageYOffset ||   e.target.scrollTop || 0
        this.fab = top > 500
      },

      toTop () {
        this.$vuetify.goTo(0)
      }
  }
}
</script>