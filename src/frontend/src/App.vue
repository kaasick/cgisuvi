<template>
  <div id="app">
    <header class="app-header d-flex justify-content-between align-items-center mb-3">
      <router-link to="/" class="logo-link d-flex align-items-center">
        <img src="@/assets/logo.png" alt="Logo" class="logo" style="height: 40px; width: auto;">
      </router-link>
      <div class="header-center-text">This is a header</div>
    </header>
    <router-view></router-view>
  </div>
</template>

<script>
// import movie from "@/components/movie.vue";
export default {
  name: 'App',

  //components: {
    //movie
  //}
}
</script>

<style>
.app-header {
  padding: 10px 0;
  background-color: lightgray;
}

.logo {
  height: auto;
  max-height: 40px; /* Adjust this value as needed */
}
.header-center-text {
  position: inherit;
  width: 100%;
  text-align: center;
  left: 0;
  top: 50%;
  transform: translateY(-50%);
  font-size: 14px;
  color: #333;
}

</style>