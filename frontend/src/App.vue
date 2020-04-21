<template>
  <div id="app">
    <!--<web-appsignin/>-->
    <nav>
        <!--<router-link to='/'>Home <br></router-link>
        <router-link to='/signin'>Sign in</router-link>-->
        <router-view></router-view>
    </nav>
    <div class="layout">
        <!--<appFooter></appFooter>-->
    </div>
  </div>
</template>

<!-- JavaScript -->
<script>

  export default {
    name: 'App',

  };

</script>

<!-- CSS -->
<style scoped>
  #app {
    /* font-family: 'Segoe UI', Helvetica, Arial, sans-serif; */
    text-align: center;
  }
</style>
