<template>
  <!-- <movie> -->
  <div id = "app">
    <!-- global header -->
    <header class="app-header">
      <!-- on logo click to get to homepage ("/" -->
      <router-link to="/" class="logo-link">
        <img src="@/assets/logo.png" alt="Logo" class="logo"/> <!-- stock vue logo -->
      </router-link>
    </header>

    <!-- Content that changes with routing -->
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
//header styling
.app-header {
  display: flex;
  align-items: center;
  background-color: #f5f5f5;
  padding: 10px 20px;
}
//logo size
.logo-link img {
  width: 40px;
  height: auto;
}
</style>
